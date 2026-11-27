package unlu.teoi.grupo5.lexer;

import java.io.IOException;

import java_cup.runtime.Symbol;
import unlu.teoi.grupo5.parser.sym;
import unlu.teoi.grupo5.tablasimbolos.EntradaTS;
import unlu.teoi.grupo5.tablasimbolos.TablaSimbolos;

/**
 * Adaptador entre el lexer generado por JFlex y JAVA CUP.
 *
 * <p>
 * Implementa {@link java_cup.runtime.Scanner}, de modo que el {@code Parser}
 * generado puede consumir los tokens directamente sobre esta clase, y registra
 * cada simbolo en la tabla de simbolos que se le inyecta. El tipo de cada
 * variable lo completa despues el analizador sintactico mediante
 * {@link TablaSimbolos#asignarTipo(String, unlu.teoi.grupo5.tablasimbolos.Tipo)}.
 */
public class AnalizadorLexico implements java_cup.runtime.Scanner {

    private final Lexico lexico;
    private final TablaSimbolos tablaSimbolos;

    public AnalizadorLexico(Lexico lexico, TablaSimbolos tablaSimbolos) {
        if (lexico == null) {
            throw new IllegalArgumentException("El lexer no puede ser null");
        }
        if (tablaSimbolos == null) {
            throw new IllegalArgumentException("La tabla de simbolos no puede ser null");
        }
        this.lexico = lexico;
        this.tablaSimbolos = tablaSimbolos;
    }

    public TablaSimbolos getTablaSimbolos() {
        return tablaSimbolos;
    }

    /**
     * Devuelve el siguiente token y, si corresponde, lo registra en la tabla de
     * simbolos. Es el punto de entrada que usa el parser.
     */
    @Override
    public Symbol next_token() throws IOException {
        Symbol symbol = lexico.next_token();
        // En modo %cup los errores lexicos se lanzan como Error desde Lexico.flex;
        // esta rama queda como defensa por si el lexer cambia de estrategia.
        if (symbol.sym == sym.error) {
            throw new Error("Token no reconocido en la linea " + (symbol.left + 1));
        }
        procesarToken(symbol);
        return symbol;
    }

    public void procesarToken(Symbol symbol) {
        if (debeRegistrarseEnTabla(symbol)) {
            EntradaTS entrada = convertirAEntrada(symbol);
            tablaSimbolos.insertar(entrada);
        }
    }

    private boolean debeRegistrarseEnTabla(Symbol symbol) {
        switch (symbol.sym) {
            case sym.ID:
            case sym.CTE_E:
            case sym.CTE_F:
            case sym.CTE_STRING:
                return true;
            default:
                return false;
        }
    }

    private EntradaTS convertirAEntrada(Symbol symbol) {
        EntradaTS entrada = null;
        String nombre = symbol.value.toString();

        switch (symbol.sym) {
            case sym.ID:
                // El tipo lo asigna el analizador sintactico al procesar DECLARE.SECTION.
                entrada = new EntradaTS(
                        nombre,
                        "ID",
                        null,
                        null,
                        -1);
                break;

            case sym.CTE_E:
                entrada = new EntradaTS(
                        nombre,
                        "CTE_E",
                        null,
                        nombre, // Guarda el valor
                        -1); // No hace falta longitud para constantes numericas
                break;

            case sym.CTE_F:
                entrada = new EntradaTS(
                        nombre,
                        "CTE_F",
                        null,
                        nombre, // Guarda el valor
                        -1); // No hace falta longitud para constantes numericas
                break;

            case sym.CTE_STRING:
                // Le quitamos las comillas del principio y el final al valor
                String valorLimpio = nombre.substring(1, nombre.length() - 1);

                entrada = new EntradaTS(
                        nombre,
                        "CTE_STRING",
                        null,
                        valorLimpio, // Guarda el valor sin comillas
                        valorLimpio.length()); // Longitud del texto real
                break;

            default:
                break;
        }

        return entrada;
    }

}
