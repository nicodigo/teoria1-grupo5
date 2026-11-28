package unlu.teoi.grupo5.tablasimbolos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import unlu.teoi.grupo5.lexer.AnalizadorLexico;
import unlu.teoi.grupo5.lexer.Lexico;
import unlu.teoi.grupo5.parser.Parser;
import unlu.teoi.grupo5.parser.sym;

class TablaSimbolosTest {

    @Test
    void asignarTipoCompletaLaEntradaSinMoverElOrden() {
        TablaSimbolos ts = new TablaSimbolos();
        ts.insertar(new EntradaTS("a1", "ID", null, null, -1));
        ts.insertar(new EntradaTS("b1", "ID", null, null, -1));
        ts.insertar(new EntradaTS("x", "ID", null, null, -1));

        assertTrue(ts.asignarTipo("b1", Tipo.INT));

        List<String> nombres = new ArrayList<>();
        for (EntradaTS entrada : ts) {
            nombres.add(entrada.getNombre());
        }
        assertEquals(List.of("a1", "b1", "x"), nombres);
        assertEquals(Tipo.INT, ts.buscar("b1").getTipo());
        assertNull(ts.buscar("a1").getTipo());
    }

    @Test
    void asignarTipoNoCreaSimbolosInexistentes() {
        TablaSimbolos ts = new TablaSimbolos();

        assertFalse(ts.asignarTipo("fantasma", Tipo.FLOAT));
        assertFalse(ts.contiene("fantasma"));
    }

    @Test
    void writerMuestraElTipoYGuionCuandoNoHayTipo() {
        TablaSimbolos ts = new TablaSimbolos();
        ts.insertar(new EntradaTS("a1", "ID", null, null, -1));
        ts.insertar(new EntradaTS("b1", "ID", null, null, -1));
        ts.insertar(new EntradaTS("_hola", "CTE_STRING", null, "hola", 4));
        ts.asignarTipo("b1", Tipo.FLOAT);

        String tabla = TablaSimbolosWriter.tablaToString(ts);

        assertTrue(tabla.contains("TIPO"), "debe estar el encabezado TIPO");
        assertTrue(lineaDe(tabla, "b1").contains("FLOAT"));
        assertFalse(lineaDe(tabla, "a1").contains("FLOAT"));
        assertFalse(lineaDe(tabla, "_hola").contains("FLOAT"));
    }

    private static String lineaDe(String tabla, String nombre) {
        for (String linea : tabla.split("\\R")) {
            if (linea.stripLeading().startsWith(nombre + " ")) {
                return linea;
            }
        }
        throw new AssertionError("No se encontro la fila de " + nombre + " en:\n" + tabla);
    }

    @Test
    void elParserComparteLaTablaDelAnalizador() throws Exception {
        TablaSimbolos ts = new TablaSimbolos();
        AnalizadorLexico adaptador = new AnalizadorLexico(
                new Lexico(new StringReader("a1 ::= 5;")), ts);
        Parser parser = new Parser(adaptador, ts);

        assertSame(ts, parser.getTablaSimbolos());

        while (adaptador.next_token().sym != sym.EOF) {
        }
        assertTrue(ts.contiene("a1"));
        assertEquals("ID", ts.buscar("a1").getToken());
    }

    @Test
    void asignarTipoDesdeElParserLlegaALaTabla() throws Exception {
        TablaSimbolos ts = new TablaSimbolos();
        AnalizadorLexico adaptador = new AnalizadorLexico(new Lexico(new StringReader("")), ts);
        Parser parser = new Parser(adaptador, ts);
        ts.insertar(new EntradaTS("a1", "ID", null, null, -1));

        parser.asignarTipo("a1", Tipo.FLOAT);
        assertEquals(Tipo.FLOAT, ts.buscar("a1").getTipo());

        parser.asignarTipo("a1", "STRING");
        assertEquals(Tipo.STRING, ts.buscar("a1").getTipo());
    }

    @Test
    void laGramaticaAsignaCadaTipoALaVariableQueLeCorresponde() throws Exception {
        String fuente = "DECLARE.SECTION\n"
                + "    [a, b, c] ::= [INT, FLOAT, STRING]\n"
                + "ENDDECLARE.SECTION\n"
                + "PROGRAM.SECTION\n"
                + "    a ::= 1;\n"
                + "ENDPROGRAM.SECTION\n";

        TablaSimbolos ts = parsear(fuente);

        assertEquals(Tipo.INT, ts.buscar("a").getTipo());
        assertEquals(Tipo.FLOAT, ts.buscar("b").getTipo());
        assertEquals(Tipo.STRING, ts.buscar("c").getTipo());
    }

    @Test
    void obtenerTipoDevuelveElTipoOCorrespondienteNull() {
        TablaSimbolos ts = new TablaSimbolos();
        ts.insertar(new EntradaTS("a", "ID", null, null, -1));
        assertNull(ts.obtenerTipo("a"));
        assertNull(ts.obtenerTipo("inexistente"));

        ts.asignarTipo("a", Tipo.INT);
        assertEquals(Tipo.INT, ts.obtenerTipo("a"));
    }

    @Test
    void unaDeclaracionDesbalanceadaLanzaErrorSintactico() {
        String fuente = "DECLARE.SECTION\n"
                + "    [a, b] ::= [INT]\n"
                + "ENDDECLARE.SECTION\n"
                + "PROGRAM.SECTION\n"
                + "    WRITE 1;\n"
                + "ENDPROGRAM.SECTION\n";

        RuntimeException ex = assertThrows(RuntimeException.class, () -> parsear(fuente));
        assertTrue(ex.getMessage().contains("la declaracion tiene 2 variables pero 1 tipos"));
    }

    @Test
    void programaCompletoPruebaTxtCompilaSinErrores() throws Exception {
        String fuente = java.nio.file.Files.readString(java.nio.file.Path.of("docs/entrega2/prueba.txt"));
        TablaSimbolos ts = parsear(fuente);
        assertTrue(ts.contiene("a"));
        assertTrue(ts.contiene("resultado"));
    }

    @Test
    void variableYaDeclaradaLanzaErrorSemantico() {
        String fuente = "DECLARE.SECTION\n"
                + "    [a] ::= [INT]\n"
                + "    [a] ::= [FLOAT]\n"
                + "ENDDECLARE.SECTION\n"
                + "PROGRAM.SECTION\n"
                + "    WRITE 1;\n"
                + "ENDPROGRAM.SECTION\n";

        RuntimeException ex = assertThrows(RuntimeException.class, () -> parsear(fuente));
        assertTrue(ex.getMessage().contains("Variable ya declarada: 'a'"));
    }

    @Test
    void variableNoDeclaradaLanzaErrorSemantico() {
        String fuente = "DECLARE.SECTION\n"
                + "    [a] ::= [INT]\n"
                + "ENDDECLARE.SECTION\n"
                + "PROGRAM.SECTION\n"
                + "    b ::= 1;\n"
                + "ENDPROGRAM.SECTION\n";

        RuntimeException ex = assertThrows(RuntimeException.class, () -> parsear(fuente));
        assertTrue(ex.getMessage().contains("Variable no declarada: 'b'"));
    }

    @Test
    void asignarStringAVariableNumericaLanzaErrorSemantico() {
        String fuente = "DECLARE.SECTION\n"
                + "    [a] ::= [INT]\n"
                + "ENDDECLARE.SECTION\n"
                + "PROGRAM.SECTION\n"
                + "    a ::= \"hola\";\n"
                + "ENDPROGRAM.SECTION\n";

        RuntimeException ex = assertThrows(RuntimeException.class, () -> parsear(fuente));
        assertTrue(ex.getMessage().contains("Tipos incompatibles"));
    }

    @Test
    void asignarExpresionNumericaAVariableStringLanzaErrorSemantico() {
        String fuente = "DECLARE.SECTION\n"
                + "    [s] ::= [STRING]\n"
                + "ENDDECLARE.SECTION\n"
                + "PROGRAM.SECTION\n"
                + "    s ::= 10;\n"
                + "ENDPROGRAM.SECTION\n";

        RuntimeException ex = assertThrows(RuntimeException.class, () -> parsear(fuente));
        assertTrue(ex.getMessage().contains("Tipos incompatibles"));
    }

    @Test
    void operacionAritmeticaConVariableStringLanzaErrorSemantico() {
        String fuente = "DECLARE.SECTION\n"
                + "    [s] ::= [STRING]\n"
                + "    [b] ::= [INT]\n"
                + "ENDDECLARE.SECTION\n"
                + "PROGRAM.SECTION\n"
                + "    b ::= s + 1;\n"
                + "ENDPROGRAM.SECTION\n";

        RuntimeException ex = assertThrows(RuntimeException.class, () -> parsear(fuente));
        assertTrue(ex.getMessage().contains("debe ser numerica"));
    }

    @Test
    void writeConVariableStringLanzaErrorSemantico() {
        String fuente = "DECLARE.SECTION\n"
                + "    [s] ::= [STRING]\n"
                + "ENDDECLARE.SECTION\n"
                + "PROGRAM.SECTION\n"
                + "    WRITE s;\n"
                + "ENDPROGRAM.SECTION\n";

        RuntimeException ex = assertThrows(RuntimeException.class, () -> parsear(fuente));
        assertTrue(ex.getMessage().contains("debe ser numerica"));
    }

    @Test
    void pivotSumaimparMenorAUnoLanzaErrorSemantico() {
        String fuente = "DECLARE.SECTION\n"
                + "    [a] ::= [INT]\n"
                + "ENDDECLARE.SECTION\n"
                + "PROGRAM.SECTION\n"
                + "    a ::= SUMAIMPAR(0; [1, 3]);\n"
                + "ENDPROGRAM.SECTION\n";

        RuntimeException ex = assertThrows(RuntimeException.class, () -> parsear(fuente));
        assertTrue(ex.getMessage().contains("pivot de SUMAIMPAR debe ser >= 1"));
    }

    @Test
    void sumaimparConEnteroNoPositivoLanzaErrorSemantico() {
        String fuente = "DECLARE.SECTION\n"
                + "    [a] ::= [INT]\n"
                + "ENDDECLARE.SECTION\n"
                + "PROGRAM.SECTION\n"
                + "    a ::= SUMAIMPAR(2; [0, 3]);\n"
                + "ENDPROGRAM.SECTION\n";

        RuntimeException ex = assertThrows(RuntimeException.class, () -> parsear(fuente));
        assertTrue(ex.getMessage().contains("solo admite enteros positivos"));
    }

    private static TablaSimbolos parsear(String fuente) throws Exception {
        TablaSimbolos ts = new TablaSimbolos();
        Parser parser = new Parser(new AnalizadorLexico(new Lexico(new StringReader(fuente)), ts), ts);

        java.io.PrintStream original = System.out;
        System.setOut(new java.io.PrintStream(java.io.OutputStream.nullOutputStream()));
        try {
            parser.parse();
        } finally {
            System.setOut(original);
        }
        return ts;
    }
}
