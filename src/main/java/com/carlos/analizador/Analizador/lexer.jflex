package com.carlos.analizador.Analizador;
import static com.carlos.analizador.Analizador.parser.*;
import java.util.ArrayList;
import java.util.List;

%%
/*segunda seccion, configuracion*/
%class lexer
/*%cup*/
/*%standalone*/
%unicode
%line
%column
%public

%{
    private List<String> errorsList = new ArrayList<>();

%}

%eofval{
    return new Yytoken("EOF",FINCADENA, (yyline+1), (yycolumn+1));
%eofval}

/*EXPRECIONES REGULARES*/

LineTerminator = [\r|\n|\r\n]+
WhiteSpace = [ \t\n]+
numeros = [0-9]+
identificador = [a-zA-Z]+([_\-]|[0-9])*

%{
    private void error(String lexeme) {
        System.out.printf("Error lexico: %s ,linea %d,  columna %d. \n", lexeme, yyline + 1, yycolumn + 1);
        errorsList.add(String.format("Error Lexico en el Texto: %s  linea %d, columna %d. Corrige e intenta de nuevo.", lexeme, yyline + 1, yycolumn + 1));
    }

    public List<String> getErrorsList() {
        return errorsList;
    }
%}

%%

/*LEXIX RULES*/
<YYINITIAL>{
    {numeros}
        {
            System.out.println("Numero: "+yytext());
            return new Yytoken(yytext(),NUM, (yyline+1), (yycolumn+1));
        }
    {identificador}
        {
            System.out.println("identificador: "+yytext());
            return new Yytoken(yytext(),ID, (yyline+1), (yycolumn+1));
        }
    "+"
        {
            System.out.println("Suma: "+yytext());
            return new Yytoken(yytext(),SUMA, (yyline+1), (yycolumn+1));
        }
    "*"
        {
            System.out.println("Por: "+yytext());
            return new Yytoken(yytext(),POR, (yyline+1), (yycolumn+1));
        }
    "("
        {
            System.out.println("Parentecis Apertura: "+yytext());
            return new Yytoken(yytext(),PAR_A, (yyline+1), (yycolumn+1));
        }
    ")"
        {
            System.out.println("Parentecis Cierre: "+yytext());
            return new Yytoken(yytext(),PAR_C, (yyline+1), (yycolumn+1));
        }
    {LineTerminator}
        {
            /*Do nothing*/
        }
    {WhiteSpace}
        {
            /*Do nothing*/
        }
}
[^]     {
            error(yytext());
        }