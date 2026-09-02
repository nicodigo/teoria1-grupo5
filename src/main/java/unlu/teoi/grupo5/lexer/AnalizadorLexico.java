package unlu.teoi.grupo5.lexer;

import java.io.IOException;

import java_cup.runtime.Symbol;
import unlu.teoi.grupo5.parser.sym;
import unlu.teoi.grupo5.tablasimbolos.TablaSimbolos;
import unlu.teoi.grupo5.tablasimbolos.EntradaTS;

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
                return true;
            default:
                return false;
        }
    }

    private EntradaTS convertirAEntrada(Symbol symbol) {
        EntradaTS entrada = null;
        switch (symbol.sym) {
            case sym.ID:
                entrada = new EntradaTS(
                        symbol.value.toString(),
                        "ID",
                        "",
                        null,
                        -1,
                        symbol.left,
                        symbol.right);
                break;

            default:
                break;
        }

        return entrada;
    }

}
