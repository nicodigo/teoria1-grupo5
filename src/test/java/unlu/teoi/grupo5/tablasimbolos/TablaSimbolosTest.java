package unlu.teoi.grupo5.tablasimbolos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import unlu.teoi.grupo5.lexer.AnalizadorLexico;
import unlu.teoi.grupo5.lexer.Lexico;
import unlu.teoi.grupo5.parser.Parser;
import unlu.teoi.grupo5.parser.sym;

/**
 * Valida la infraestructura de la columna TIPO antes de que exista la
 * gramatica: la tabla acepta tipos del parser y el writer los imprime.
 */
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
        // El orden de aparicion en el programa no cambia al asignar el tipo.
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
            // la gramatica todavia es vacia: solo se consume el lexico
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

        // Lo que usara la gramatica de DECLARE.SECTION.
        parser.asignarTipo("a1", Tipo.FLOAT);
        assertEquals(Tipo.FLOAT, ts.buscar("a1").getTipo());

        parser.asignarTipo("a1", "STRING");
        assertEquals(Tipo.STRING, ts.buscar("a1").getTipo());
    }
}
