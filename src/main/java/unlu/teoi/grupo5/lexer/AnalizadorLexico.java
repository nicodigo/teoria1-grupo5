package unlu.teoi.grupo5.lexer;

import java.io.IOException;

import java_cup.runtime.Symbol;
import unlu.teoi.grupo5.parser.sym;
import unlu.teoi.grupo5.tablasimbolos.EntradaTS;
import unlu.teoi.grupo5.tablasimbolos.TablaSimbolos;

public class AnalizadorLexico {
    private TablaSimbolos tablaSimbolos = new TablaSimbolos();

    public TablaSimbolos getTablaSimbolos() {
        return tablaSimbolos;
    }

    public void analizar(Lexico lexico) throws IOException {
        Symbol symbol;

        do {
            symbol = lexico.next_token();

            if (debeRegistrarseEnTabla(symbol)) {
                EntradaTS entrada = convertirAEntrada(symbol);
                tablaSimbolos.insertar(entrada);
            }
        } while (symbol.sym != sym.EOF);

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
                entrada = new EntradaTS(
                        nombre,
                        "ID",
                        "",
                        null,
                        -1,
                        symbol.left,
                        symbol.right);
                break;

            case sym.CTE_E:
                entrada = new EntradaTS(
                        nombre,
                        "CTE_E",
                        "",
                        nombre, // Guarda el valor
                        nombre.length(), // Guarda la longitud
                        symbol.left,
                        symbol.right);
                break;

            case sym.CTE_F:
                entrada = new EntradaTS(
                        nombre,
                        "CTE_F",
                        "",
                        nombre, // Guarda el valor
                        nombre.length(), // Guarda la longitud
                        symbol.left,
                        symbol.right);
                break;

            case sym.CTE_STRING:
                // Le quitamos las comillas del principio y el final al valor
                String valorLimpio = nombre.substring(1, nombre.length() - 1);

                entrada = new EntradaTS(
                        nombre,
                        "CTE_STRING",
                        "",
                        valorLimpio, // Guarda el valor sin comillas
                        valorLimpio.length(), // Longitud del texto real
                        symbol.left,
                        symbol.right);
                break;

            default:
                break;
        }

        return entrada;
    }

}
