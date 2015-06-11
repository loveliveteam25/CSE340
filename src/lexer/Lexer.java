/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lexer;

/**
 *
 * @author FanjieLin
 */



import java.util.Vector;

public class Lexer {
    
    private String text;
    private Vector<Token> tokens;
    private static final String[] KEYWORD = {"if", "else", "while", "switch",
    "case", "return", "int", "float", "void", "char", "string", "boolean",
    "true", "false", "print"};
    private int keyword = 0;
    
    //Constants; YOU WILL NEED TO DEFINE MORE CONSTANTS
    
    private static final int B         =  0;
    
    private static final int ZERO      =  1;
    private static final int ONE       =  2;
    
    private static final int TWO       =  3;
    private static final int THREE     =  4;
    private static final int FOUR      =  5;
    private static final int FIVE      =  6;
    private static final int SIX       =  7;
    private static final int SEVEN     =  8;
    private static final int EIGHT     =  9;
    private static final int NINE      = 10;
    private static final int DOT       = 11;
    private static final int xORX      = 12;
    private static final int eORE      = 13;
    private static final int PLUSMINUS = 14;
    private static final int AFaf      = 15;
    private static final int QUOTE2    = 16;
    private static final int BACKSLASH = 17;
    private static final int QUOTE1    = 18;
    private static final int OTHER     = 19;
    private static final int DELIMITER = 20;
    private static final int ERROR     = 22;
    private static final int STOP      = -2;
    private static final int SSTOP     = -3;
    private static final int SSSTOP    = -4;
    private static final int DOLLAR    = 21;
    private static final int DASH      = 22;
    private static final int GWTOgw    = 23;
    private static final int YZTOyz    = 24;
    
    
    // states table; THIS IS THE TABLE FOR BINARY NUMBERS; YOU SHOLD COMPLETE IT
    private static final int[][] stateTable = {
    /*s0*/{	23,	1,	4,	4,	4,	4,	4,	4,	4,	4,	4,	ERROR,	23,	23,	STOP,	23,	14,	STOP,	18,	STOP,	STOP,	23,	23,	23,	23,	23,	23,	24,	24},
    /*s1*/{	2,	12,	12,	12,	12,	12,	12,	12,	12,	ERROR,	ERROR,	5,	10,	ERROR,	ERROR,	ERROR,	SSSTOP,	ERROR,	SSSTOP,	ERROR,	STOP,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR},
    /*s2*/{	ERROR,	3,	3,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	STOP,	ERROR,	STOP,	ERROR,	STOP,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR},
    /*s3*/{	ERROR,	3,	3,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	SSSTOP,	ERROR,	SSSTOP,	ERROR,	STOP,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR},
    /*s4*/{	ERROR,	4,	4,	4,	4,	4,	4,	4,	4,	4,	4,	5,	ERROR,	7,	ERROR,	ERROR,	SSSTOP,	ERROR,	SSSTOP,	ERROR,	STOP,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR},
    /*s5*/{	ERROR,	6,	6,	6,	6,	6,	6,	6,	6,	6,	6,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	SSSTOP,	ERROR,	SSSTOP,	ERROR,	STOP,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR},
    /*s6*/{	ERROR,	6,	6,	6,	6,	6,	6,	6,	6,	6,	6,	ERROR,	ERROR,	7,	ERROR,	ERROR,	SSSTOP,	ERROR,	SSSTOP,	ERROR,	STOP,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR},
    /*s7*/{	ERROR,	ERROR,	9,	9,	9,	9,	9,	9,	9,	9,	9,	ERROR,	ERROR,	ERROR,	8,	ERROR,	STOP,	ERROR,	STOP,	ERROR,	STOP,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR},
    /*s8*/{	ERROR,	ERROR,	9,	9,	9,	9,	9,	9,	9,	9,	9,	ERROR,	ERROR,	ERROR,	13,	ERROR,	STOP,	ERROR,	STOP,	ERROR,	STOP,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR},
    /*s9*/{	ERROR,	9,	9,	9,	9,	9,	9,	9,	9,	9,	9,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	SSSTOP,	ERROR,	SSSTOP,	ERROR,	STOP,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR},
    /*s10*/{	11,	11,	11,	11,	11,	11,	11,	11,	11,	11,	11,	ERROR,	ERROR,	ERROR,	ERROR,	11,	STOP,	ERROR,	STOP,	ERROR,	STOP,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR},
    /*s11*/{	11,	11,	11,	11,	11,	11,	11,	11,	11,	11,	11,	ERROR,	ERROR,	ERROR,	ERROR,	11,	SSSTOP,	ERROR,	SSSTOP,	ERROR,	STOP,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR},
    /*s12*/{	ERROR,	12,	12,	12,	12,	12,	12,	12,	12,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	SSSTOP,	ERROR,	SSSTOP,	ERROR,	STOP,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR},
    /*s13*/{	13,	13,	13,	13,	13,	13,	13,	13,	13,	13,	13,	13,	ERROR,	13,	13,	ERROR,	STOP,	ERROR,	STOP,	ERROR,	STOP,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR},
    /*s14*/{	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	SSTOP,	16,	ERROR,	ERROR,	15,	15,	15,	15,	15,	15,	15,	ERROR,	ERROR},
    /*s15*/{	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	/*17*/SSTOP,	16,	15,	ERROR,	15,	15,	15,	15,	15,	15,	15,	ERROR,	ERROR},
    /*s16*/{	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	15,	ERROR,	15,	15,	15,	15,	15,	15,	15,	15,	ERROR,	ERROR},
    /*s17*/{	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	STOP,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR},
    /*s18*/{	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	19,	ERROR,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20},
    /*s19*/{	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20,	20},
    /*s20*/{	26,	26,	26,	26,	26,	26,	26,	26,	26,	26,	26,	26,	26,	26,	26,	26,	26,	26,	SSTOP,	26,	26,	26,	26,	26,	26,	26,	26,	26,	26},
    /*s21*/{	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	STOP,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR},
    /*s22*/{	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	STOP,	ERROR,	STOP,	ERROR,	STOP,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR},
    /*s23*/{	23,	23,	23,	23,	23,	23,	23,	23,	23,	23,	23,	23,	23,	23,	ERROR,	23,	STOP,	ERROR,	STOP,	ERROR,	STOP,	23,	23,	23,	23,	23,	23,	ERROR,	ERROR},
    /*s24*/{	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	STOP,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	25,	ERROR},
    /*s25*/{	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	STOP,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR,	ERROR},
    /*s26*/{	26,	26,	26,	26,	26,	26,	26,	26,	26,	26,	26,	26,	26,	26,	26,	26,	26,	26,	STOP,	26,	26,	26,	26,	26,	26,	26,	26,	26,	26},
    };
    
    
    //constructor
    public Lexer(String text) {
        this.text = text;
    }
    
    //run
    public void run () {
        tokens = new Vector<Token>();
        String line;
        int counterOfLines= 1;
        // split lines
        do {
            int eol_index = text.indexOf("\n");
            if (eol_index >= 0) {
                line = text.substring(0,eol_index);
                if (text.length()>0) text = text.substring(eol_index+1);
            } else {
                line = text;
                text = "";
            }
            splitLine (counterOfLines, line);
            counterOfLines++;
        } while ( !text.equals("") );
    }
    
    //slit line
    private void splitLine(int row, String line) {
        int state = 0;
        int index = 0;
        char currentChar;
        String string="";
        if (line.equals("")) return;
        //DFA 
        int go;
        do {
            currentChar = line.charAt(index);
            go = calculateNextState(state, currentChar);
            if( go != STOP && go != SSTOP && go != SSSTOP) //special stop states for quotes.
            {
                string = string + currentChar;
                state = go;
            }
            if (go == SSTOP)
            {
                string = string + currentChar;
                if (currentChar == '\"' ) state = 17; // to include the second quote
                if (currentChar == '\'') state = 21;
            }
            index++;
        } while (index < line.length() && go != STOP && go != SSTOP && go != SSSTOP);
        
//final state
        
        if (state == 3)
        {
            tokens.add(new Token(string, "BINARY", row));
        }
        else if (state == 1)
        {
            tokens.add(new Token(string, "INTEGER", row));
        }
        else if (state == 4)
        {
            tokens.add(new Token(string, "INTEGER", row));
        }
        else if (state == 5)
        {
            tokens.add(new Token(string, "FLOAT", row));
        }
        else if (state == 6)
        {
            tokens.add(new Token(string, "FLOAT", row));
        }
        else if (state == 9)
        {
            tokens.add(new Token(string, "FLOAT", row));
        }
        else if (state == 11)
        {
            tokens.add(new Token(string, "HEXADECIMAL", row));
        }
        else if (state == 12)
        {
            tokens.add(new Token(string, "OCTAL", row));
        }
        else if (state == 17)
        {
            tokens.add(new Token(string, "STRING", row));
        }
        else if (state == 21)
        {
            tokens.add(new Token(string, "CHAR", row));
        }
        else if (state == 25)
        {
            tokens.add(new Token(string, "OPERATOR", row));
        }
        else if (state == 23) // check for keyword and identifier
        {   keyword = 0;
            for (int x=0; x<KEYWORD.length; x++) {
                if (string.equals(KEYWORD [x]))
                {
                    tokens.add(new Token(string, "KEYWORD", row));
                    keyword =1; break;
                }
            }
            if (keyword != 1) // FOR identifier if not a keyword
            {tokens.add(new Token(string, "IDENTIFIER", row));}
        }
        
        else {
            if (!string.equals(""))
                tokens.add(new Token(string, "ERROR", row));
        }
        // current char
        if( isDelimiter(currentChar))
            tokens.add(new Token(currentChar+"", "DELIMITER", row));
        else if (isOperator(currentChar)  )
            tokens.add(new Token(currentChar+"", "OPERATOR", row));
        
        
        if (index < line.length() && (currentChar == '\'' || currentChar == '\"') && go == SSSTOP)
            splitLine(row, line.substring(index -1));
        else    if (index < line.length())
            splitLine(row, line.substring(index));
        
    }
    
    // calculate state
    private int calculateNextState(int state, char currentChar) {
        if (isSpace(currentChar)  || isDelimiter(currentChar)  ||
            (isOperator(currentChar) && (state != 7 && state !=8 && state !=13 && state !=0 && state != 24)))
            return stateTable[state][DELIMITER];
     
        if ((currentChar == '\"') && state != 0 && state != 15 && state != 20 && state != 18 && state != 14)
            return stateTable[state][QUOTE2];
        if ((currentChar == '\'') && state != 0 && state != 15 && state != 20 && state != 18 && state != 14)
            return stateTable[state][QUOTE1];
        
        else if (currentChar == 'b' || currentChar == 'B')
            return stateTable [state][B];
        else if (currentChar == '0')
            return stateTable [state][ZERO];
        else if (currentChar == '1')
            return stateTable [state][ONE];
        
        else if (currentChar == '2') 
            return stateTable [state][TWO];
        else if (currentChar == '3') 
            return stateTable [state][THREE];
        else if (currentChar == '4') 
            return stateTable [state][FOUR];
        else if (currentChar == '5') 
            return stateTable [state][FIVE];
        else if (currentChar == '6') 
            return stateTable [state][SIX];
        else if (currentChar == '7') 
            return stateTable [state][SEVEN];
        else if (currentChar == '8') 
            return stateTable [state][EIGHT];
        else if (currentChar == '9') 
            return stateTable [state][NINE];
        else if (currentChar == '.') 
            return stateTable [state][DOT];
        else if (currentChar == 'X' ||currentChar == 'x') 
            return stateTable [state][xORX];
        else if (currentChar == 'e' ||currentChar == 'E') 
            return stateTable [state][eORE];
        else if (currentChar == '+' ||currentChar == '-') 
            return stateTable [state][PLUSMINUS];
        else if (currentChar >= 'A' &&currentChar <= 'F') 
            return stateTable [state][AFaf];
        else if (currentChar >= 'a' &&currentChar <= 'f') 
            return stateTable [state][AFaf];
        else if (currentChar == '"') 
            return stateTable [state][QUOTE2];
        else if (currentChar == '\\') 
            return stateTable [state][BACKSLASH];
        else if (currentChar == '\'') 
            return stateTable [state][QUOTE1];
        else if (currentChar == '$') 
            return stateTable [state][DOLLAR];
        else if (currentChar == '_') 
            return stateTable [state][DASH];
        else if (currentChar == '\'') 
            return stateTable [state][QUOTE1];
        else if (currentChar >= 'G' && currentChar <= 'W') 
            return stateTable [state][GWTOgw];
        else if (currentChar >= 'g' && currentChar <= 'w') 
            return stateTable [state][GWTOgw];
        else if (currentChar >= 'Y' && currentChar <= 'Z') 
            return stateTable [state][YZTOyz];
        else if (currentChar >= 'y' && currentChar <= 'z') 
            return stateTable [state][YZTOyz];
        
        
        return stateTable [state][OTHER];
    }
    
    // isDelimiter
    private boolean isDelimiter(char c) {
        char [] delimiters = {':', ';', '}','{', '[',']','(',')',','};
        for (int x=0; x<delimiters.length; x++) {
            if (c == delimiters[x]) return true;      
        }
        return false;
    }
    
    // isOperator
    private boolean isOperator(char o) {
        char [] operators = {'+', '-', '*','/','<','>','=','!','&','|'};
        for (int x=0; x<operators.length; x++) {
            if (o == operators[x]) return true;      
        }
        return false;
    }
    
    // isQuotationMark
    private boolean isQuotationMark(char o) {
        char [] quote = {'"', '\''};
        for (int x=0; x<quote.length; x++) {
            if (o == quote[x]) return true;      
        }
        return false;
    }
    
    // isSpace
    private boolean isSpace(char o) {
        return o == ' ';
    }
    
    // getTokens
    public Vector<Token> getTokens() {
        return tokens;
    }
    
}