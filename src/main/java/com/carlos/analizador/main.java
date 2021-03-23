package com.carlos.analizador;

import com.carlos.analizador.Analizador.lexer;
import com.carlos.analizador.Analizador.parser;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class main {
    public static void main(String[] args) {
        lexer lex = new lexer(new StringReader("(5+8)*9+8"));
        parser parser = new parser(lex);
        
        try {
            parser.parse();
        } catch (IOException ex) {
            System.out.println("Error el momento de ejecutar el parser");
        }
    }
}
