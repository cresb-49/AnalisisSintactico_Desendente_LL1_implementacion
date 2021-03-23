package com.carlos.analizador.Analizador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class parser {

    public static final int ID = 1;
    public static final int NUM = 2;
    public static final int PAR_A = 3;
    public static final int PAR_C = 4;
    public static final int SUMA = 5;
    public static final int POR = 6;
    public static final int FINCADENA = 0;

    private lexer lex;
    private int token;
    private Yytoken cur_token;
    
    private int errores =0;

    public parser(lexer lex) {
        this.lex = lex;
    }

    public void parse() throws IOException {
        avanzar();
        E();
        System.out.println("Se termino de leer el texto, numero de errores: "+errores);
    }

    private void error(int token) {
        errores++;
        System.err.println("Parametro inesperado: \"" + this.cur_token.getValue() + "\", Linea: " + cur_token.getLinea() + ", Columna: " + cur_token.getColumna());
    }

    private void error(int token, List<String> esperado) {
        errores++;
        System.err.println("Se esperaba: " + esperado.toString() + ", error sintacito \"" + cur_token.getValue().toString() + "\", Linea: " + cur_token.getLinea() + ", Columna: " + cur_token.getColumna());
    }

    private void error(int token, int esperado) {
        errores++;
        System.err.println("Se esperaba: [" + esperado + "], error sintacito \"" + cur_token.getValue().toString() + "\", Linea: " + cur_token.getLinea() + ", Columna: " + cur_token.getColumna());
    }

    private void avanzar() throws IOException {
        cur_token = lex.yylex();
        this.token = cur_token.getToken();
    }

    private void consumir(int tok) {
        if (tok == this.token) {
            try {
                avanzar();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            error(tok, this.token);
        }
    }

    private void E() {
        switch (token) {
            case ID:
            case NUM:
            case PAR_A:
                T();
                Ep();
                break;
            default:
                List<String> es = new ArrayList<>();
                es.add("ID");es.add("numero");es.add("(");
                error(token,es);
        }
    }

    private void T() {
        switch (token) {
            case ID:
            case NUM:
            case PAR_A:
                F();
                Tp();
                break;
            default:
                List<String> es = new ArrayList<>();
                es.add("ID");es.add("numero");es.add("(");
                error(token,es);
        }
    }

    private void Ep() {
        switch (token) {
            case PAR_C:
            case FINCADENA:
                /*lambda, no hago nada*/
                break;
            case SUMA:
                consumir(SUMA);
                T();
                Ep();
                break;
            default:
                List<String> es = new ArrayList<>();
                es.add(")");es.add("EOF");es.add("+");
                error(token,es);
        }
    }

    private void Tp() {
        switch (token) {
            case PAR_C:
            case SUMA:
            case FINCADENA:
                /*lambda, no hago nada*/
                break;
            case POR:
                consumir(POR);
                F();
                Tp();
                break;
            default:
                List<String> es = new ArrayList<>();
                es.add(")");es.add("+");es.add("EOF");es.add("*");
                error(token,es);
        }
    }

    private void F() {
        switch (token) {
            case ID:
                consumir(ID);
                break;
            case NUM:
                consumir(NUM);
                break;
            case PAR_A:
                consumir(PAR_A);
                E();
                consumir(PAR_C);
                break;
            default:
                List<String> es = new ArrayList<>();
                es.add("ID");es.add("numero");es.add("(");
                error(token,es);
        }
    }
}
