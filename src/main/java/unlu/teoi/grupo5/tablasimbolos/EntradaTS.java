package unlu.teoi.grupo5.tablasimbolos;

public class EntradaTS {
    private final String nombre;
    private final String token;
    private final String tipo;
    private final Object valor;
    private final int longitud;

    public EntradaTS(String nombre, String token, String tipo, Object valor, int longitud) {
        this.nombre = nombre;
        this.token = token;
        this.tipo = tipo;
        this.valor = valor;
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }

    public Object getValor() {
        return valor;
    }

    public int getLongitud() {
        return longitud;
    }

}
