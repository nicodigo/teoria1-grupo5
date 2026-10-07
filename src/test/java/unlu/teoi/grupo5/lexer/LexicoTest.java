package unlu.teoi.grupo5.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
