package unlu.teoi.grupo5.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;

import org.junit.jupiter.api.Test;

import java_cup.runtime.Symbol;
import unlu.teoi.grupo5.parser.sym;

/**
 * Test de humo del kickstart: valida que el lexer generado por JFlex compila,
 * se instancia y que una entrada vacía llega inmediatamente a EOF.
 *
 * <p>
 */
class LexicoTest {

    @Test
    void entradaVaciaTerminaEnEof() throws Exception {
        Lexico lexico = new Lexico(new StringReader(""));
        assertNotNull(lexico);
        // Modo %cup: next_token() devuelve java_cup.runtime.Symbol; EOF = sym.EOF
        assertEquals(sym.EOF, lexico.next_token().sym);
    }

    @Test
    void comentarioSimpleEsIgnoradoYDevuelveEof() throws Exception {
        // Como el lexer ignora los comentarios, el siguiente token debe ser EOF
        String str = "//* mi comentario *//";
        Lexico lexico = new Lexico(new StringReader(str));
        assertEquals(sym.EOF, lexico.next_token().sym);
    }

    @Test
    void comentarioDobleEsIgnoradoYDevuelveEof() throws Exception {
        // Como el lexer ignora los comentarios, el siguiente token debe ser EOF
        String str = """
        //* Super comentario doble
        que ocupa //* más de una línea
        *// y termina aca *//
        """;
        Lexico lexico = new Lexico(new StringReader(str));
        assertEquals(sym.EOF, lexico.next_token().sym);
    }

    @Test
    void multiplesComentariosDentroDeUnComentarioPadreDevuelveEOF() throws Exception {
        // Como el lexer ignora los comentarios, el siguiente token debe ser EOF
        String str = """
//*
este comentario puede tener
estos caracteres: */ // /* sin problema,
    //* Puede tener un comentario anidado*//
    //* y cuando termina ese comentario,
    comenzar otro comentario anidado *//
    //* y así sucesivamente *//
hasta que termina
*//
        """;
        Lexico lexico = new Lexico(new StringReader(str));
        assertEquals(sym.EOF, lexico.next_token().sym);
    }

    @Test
    void reconoceIdentificador() throws Exception {
        Lexico lexico = new Lexico(new StringReader("variable_1"));
        Symbol token = lexico.next_token();
        assertEquals(sym.ID, token.sym);
        assertEquals("variable_1", token.value);
    }

    @Test
    void reconoceTemaEspecialSumaImpar() throws Exception {
        Lexico lexico = new Lexico(new StringReader("SUMAIMPAR"));
        assertEquals(sym.SUMAIMPAR, lexico.next_token().sym);
    }

    @Test
    void reconoceConstanteString() throws Exception {
        Lexico lexico = new Lexico(new StringReader("\"hola mundo\""));
        Symbol token = lexico.next_token();
        assertEquals(sym.CTE_STRING, token.sym);
        assertEquals("\"hola mundo\"", token.value);
    }

    @Test
    void reconoceConstanteEnteraYReal() throws Exception {
        Lexico lexico = new Lexico(new StringReader("150 45.5"));
        assertEquals(sym.CTE_E, lexico.next_token().sym);
        assertEquals(sym.CTE_F, lexico.next_token().sym);
    }

    // ========================================================================
    // CASOS DE ERROR LEXICO (token invalido)
    //
    // El lexer aborta el analisis lanzando java.lang.Error apenas encuentra
    // algo invalido, por eso cada caso se prueba por separado.
    // Ide.compilar() captura ese Error y lo muestra como [ERROR LEXICO].
    // ========================================================================

    /** Consume toda la entrada; deja propagar el Error lexico si aparece. */
    private static void lexearTodo(String fuente) throws Exception {
        Lexico lexico = new Lexico(new StringReader(fuente));
        while (lexico.next_token().sym != sym.EOF) {
            // solo interesa que el analisis llegue hasta el final
        }
    }

    private static void assertErrorLexico(String fuente, String textoEsperado) {
        Error e = assertThrows(Error.class, () -> lexearTodo(fuente));
        assertTrue(
                e.getMessage().contains(textoEsperado),
                "Se esperaba un mensaje con \"" + textoEsperado + "\" pero fue: " + e.getMessage());
    }

    // --- Caracteres fuera del alfabeto del lenguaje ---

    @Test
    void caracterNoPermitidoNumeral() {
        assertErrorLexico("a # b", "Caracter no permitido");
    }

    @Test
    void caracterNoPermitidoArroba() {
        assertErrorLexico("@", "Caracter no permitido");
    }

    @Test
    void caracterNoPermitidoPorcentaje() {
        assertErrorLexico("a % b", "Caracter no permitido");
    }

    // --- Constantes enteras: limite de 16 bits (32767) ---

    @Test
    void enteroFueraDeRango16Bits() {
        assertErrorLexico("x ::= 32768", "16 bits");
    }

    @Test
    void enteroDemasiadoGrandeParaUnInt() {
        assertErrorLexico("x ::= 99999999999999999999", "16 bits");
    }

    @Test
    void enteroEnElLimiteEsValido() throws Exception {
        Lexico lexico = new Lexico(new StringReader("32767"));
        assertEquals(sym.CTE_E, lexico.next_token().sym);
    }

    // --- Constantes reales: limite de 32 bits ---

    @Test
    void realFueraDeRango32Bits() {
        assertErrorLexico("x ::= " + "9".repeat(45) + ".9", "32 bits");
    }

    @Test
    void realesSinParteEnteraOSinParteDecimalSonValidos() throws Exception {
        Lexico lexico = new Lexico(new StringReader(".5 5."));
        assertEquals(sym.CTE_F, lexico.next_token().sym);
        assertEquals(sym.CTE_F, lexico.next_token().sym);
    }

    // --- Constantes string: maximo 30 caracteres ---

    @Test
    void stringDe31CaracteresEsInvalido() {
        assertErrorLexico("\"" + "a".repeat(31) + "\"", "30 caracteres");
    }

    @Test
    void stringDe30CaracteresEsValido() throws Exception {
        Lexico lexico = new Lexico(new StringReader("\"" + "a".repeat(30) + "\""));
        assertEquals(sym.CTE_STRING, lexico.next_token().sym);
    }

    @Test
    void stringSinComillaDeCierre() {
        // La comilla que quedo suelta no matchea CTE_STRING y cae en la regla [^]
        assertErrorLexico("x ::= \"hola", "Caracter no permitido");
    }

    // --- Identificadores y simbolos que no pertenecen al lenguaje ---

    @Test
    void identificadorNoPuedeEmpezarConGuionBajo() {
        assertErrorLexico("_hola", "Caracter no permitido");
    }

    @Test
    void signoIgualSoloNoEsUnToken() {
        // La asignacion del lenguaje es "::=", no "="
        assertErrorLexico("a = b", "Caracter no permitido");
    }

    @Test
    void dobleIgualNoEsUnToken() {
        assertErrorLexico("IF (a == b)", "Caracter no permitido");
    }

    @Test
    void asignacionSimpleNoEsUnToken() {
        // ":=" todavia no existe en el lexico (ver nota en prueba.txt)
        assertErrorLexico("a := 5", "Caracter no permitido");
    }

    // ========================================================================
    // Comentario sin cerrar y numero pegado a letras: hasta el fix de las
    // reglas en Lexico.flex estos dos casos pasaban sin dar error.
    // ========================================================================

    @Test
    void comentarioSinCerrarEsError() {
        assertErrorLexico("//* no cierro nunca", "comentario");
    }

    @Test
    void numeroPegadoALetrasEsError() {
        assertErrorLexico("1abc", "no puede empezar con un digito");
    }
}
