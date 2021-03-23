package com.carlos.analizador.Analizador;

public class Yytoken {
    private Object value;
    private int token;
    private int linea;
    private int columna;

    public Yytoken(Object value, int token, int linea, int columna) {
        this.value = value;
        this.token = token;
        this.linea = linea;
        this.columna = columna;
    }

    public Yytoken() {
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    
    
    @Override
    public String toString() {
        return "Yytoken{" + "value=" + value + ", token=" + token + ", linea=" + linea + ", columna=" + columna + '}';
    }
    
    
}
