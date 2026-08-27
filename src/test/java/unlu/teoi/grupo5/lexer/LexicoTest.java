package unlu.teoi.grupo5.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.StringReader;

import org.junit.jupiter.api.Test;

/**
 * Test de humo del kickstart: valida que el lexer generado por JFlex compila,
 * se instancia y que una entrada vacía llega inmediatamente a EOF.
 *
 * <p>TODO: reemplazar por los tests reales del TP.
 */
class LexicoTest {

    @Test
    void entradaVaciaTerminaEnEof() throws Exception {
        Lexico lexico = new Lexico(new StringReader(""));
        assertNotNull(lexico);
        assertEquals(Lexico.YYEOF, lexico.yylex());
    }
}
