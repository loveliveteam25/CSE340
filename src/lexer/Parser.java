/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package A2;

/**
 *
 * @author FanjieLin
 */

import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;
/**
 *
 * @author javiergs
 */
public class Parser {
    
    private static DefaultMutableTreeNode root;
    private static Vector<Token> tokens;
    private static int currentToken;
    
    private static Gui gui;
    
    public static DefaultMutableTreeNode run(Vector<Token> t,Gui gui)
    {
        Parser.gui=gui;
        tokens = t;
        currentToken = 0;
        root = new DefaultMutableTreeNode("program");
        //
        rule_program(root);
        //
        return root;
    }
    
    //----------------------------rule-program
    
    private static boolean rule_program(DefaultMutableTreeNode parent)
    {
        boolean error=false;
        DefaultMutableTreeNode node;
        
        if(currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("{"))
        {
            node= new DefaultMutableTreeNode("{");
            parent.add(node);
            currentToken++;
        }
        else
        {
            error(1);
            while(currentToken<tokens.size()&&!(tokens.get(currentToken).getWord().equalsIgnoreCase("print")||tokens.get(currentToken).getToken().equalsIgnoreCase("IDENTIFIER")||tokens.get(currentToken).getWord().equalsIgnoreCase("int")||tokens.get(currentToken).getWord().equalsIgnoreCase("float")||tokens.get(currentToken).getWord().equalsIgnoreCase("boolean")||tokens.get(currentToken).getWord().equalsIgnoreCase("void")||tokens.get(currentToken).getWord().equalsIgnoreCase("char")||tokens.get(currentToken).getWord().equalsIgnoreCase("string")||tokens.get(currentToken).getWord().equalsIgnoreCase("while")||tokens.get(currentToken).getWord().equalsIgnoreCase("if")||tokens.get(currentToken).getWord().equalsIgnoreCase("return")||tokens.get(currentToken).getWord().equals("}")))
            {
                currentToken++;
            }
        }
        node = new DefaultMutableTreeNode("body");
        parent.add(node);
        error=rule_body(node);
        if(currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("}"))
        {
            
            node=new DefaultMutableTreeNode("}");
            parent.add(node);
            currentToken++;
        }
        else
        {
            currentToken--; //new addition
            error(2);
            currentToken++;
        }
        return error;
    }
    
    //------------------------------rule body
    
    private static boolean rule_body(DefaultMutableTreeNode parent)
    {
        boolean error=false;
        
        while(currentToken<tokens.size() && !tokens.get(currentToken).getWord().equals("}"))
        {
            if(tokens.get(currentToken).getToken().equalsIgnoreCase("identifier"))
            {
                DefaultMutableTreeNode node;
                node=new DefaultMutableTreeNode("assignment");
                parent.add(node);
                error=rule_assignment(node);
                if(currentToken<tokens.size()&&tokens.get(currentToken).getWord().equals(";"))
                {
                    node=new DefaultMutableTreeNode(";");
                    parent.add(node);
                    currentToken++;
                }
                else error(3);
            }
            else if(tokens.get(currentToken).getWord().equalsIgnoreCase("INT")||tokens.get(currentToken).getWord().equalsIgnoreCase("FLOAT")||tokens.get(currentToken).getWord().equalsIgnoreCase("BOOLEAN")||tokens.get(currentToken).getWord().equalsIgnoreCase("CHAR")||tokens.get(currentToken).getWord().equalsIgnoreCase("STRING")||tokens.get(currentToken).getWord().equalsIgnoreCase("VOID"))
            {
                DefaultMutableTreeNode node;
                node=new DefaultMutableTreeNode("variable");
                parent.add(node);
                error=rule_variable(node);
                if(currentToken<tokens.size()&&tokens.get(currentToken).getWord().equals(";"))
                {
                    node=new DefaultMutableTreeNode(";");
                    parent.add(node);
                    currentToken++;
                }
                else
                    error(3);
            }
            else if(tokens.get(currentToken).getWord().equalsIgnoreCase("print"))
            {
                DefaultMutableTreeNode node;
                node=new DefaultMutableTreeNode("PRINT");
                parent.add(node);
                error=rule_print(node);
                if(currentToken<tokens.size()&&tokens.get(currentToken).getWord().equals(";"))
                {
                    node=new DefaultMutableTreeNode(";");
                    parent.add(node);
                    currentToken++;
                }
                else
                {
                    if(currentToken<tokens.size())
                        error(3);
                }
            }
            
            else if(tokens.get(currentToken).getWord().equalsIgnoreCase("return"))
            {
                
                DefaultMutableTreeNode node;
                node=new DefaultMutableTreeNode("Return");
                parent.add(node);
                error=rule_return(node);
                if(currentToken<tokens.size()&& tokens.get(currentToken).getWord().equals(";"))
                {
                    node=new DefaultMutableTreeNode(";");
                    parent.add(node);
                    currentToken++;
                }
                else
                    error(3);
            }
            
            else if(tokens.get(currentToken).getWord().equalsIgnoreCase("while"))
            {
                DefaultMutableTreeNode node;
                node=new DefaultMutableTreeNode("while");
                parent.add(node);
                error=rule_while(node);
                
            }
            
            else if(tokens.get(currentToken).getWord().equals("if"))
            {
                DefaultMutableTreeNode node;
                node=new DefaultMutableTreeNode("IF");
                parent.add(node);
                error=rule_if(node);
            }
            
            else
            {
                error(4);
                while(currentToken<tokens.size()&&!(tokens.get(currentToken).getWord().equalsIgnoreCase("print")||tokens.get(currentToken).getToken().equalsIgnoreCase("IDENTIFIER")||tokens.get(currentToken).getWord().equalsIgnoreCase("int")||tokens.get(currentToken).getWord().equalsIgnoreCase("float")||tokens.get(currentToken).getWord().equalsIgnoreCase("boolean")||tokens.get(currentToken).getWord().equalsIgnoreCase("void")||tokens.get(currentToken).getWord().equalsIgnoreCase("char")||tokens.get(currentToken).getWord().equalsIgnoreCase("string")||tokens.get(currentToken).getWord().equalsIgnoreCase("while")||tokens.get(currentToken).getWord().equalsIgnoreCase("if")||tokens.get(currentToken).getWord().equalsIgnoreCase("return")||tokens.get(currentToken).getWord().equals("}")))
                {
                    currentToken++;
                }
            }
        }//while
        return error;
    }
    
    //----------------------------rule-assignment
    
    private static boolean rule_assignment(DefaultMutableTreeNode parent)
    {
        boolean error=false;
        DefaultMutableTreeNode node;
        
        node=new DefaultMutableTreeNode("identifier" + "(" + tokens.get(currentToken).getWord() +")");
        parent.add(node);
        currentToken++;
        if(currentToken<tokens.size()&&tokens.get(currentToken).getWord().equals("="))
        {
            node=new DefaultMutableTreeNode("=");
            parent.add(node);
            currentToken++;
        }
        
        else
        {
            error(5);
            while(currentToken<tokens.size()&&!(tokens.get(currentToken).getWord().equals("!")||tokens.get(currentToken).getWord().equals("-")||tokens.get(currentToken).getToken().equals("INTEGER")||tokens.get(currentToken).getToken().equals("OCTAL")||tokens.get(currentToken).getToken().equals("HEXADECIMAL")||tokens.get(currentToken).getToken().equals("BINARY")||tokens.get(currentToken).getToken().equals("STRING")||tokens.get(currentToken).getToken().equals("CHARACTER")||tokens.get(currentToken).getToken().equals("FLOAT")||tokens.get(currentToken).getToken().equals("IDENTIFIER")||tokens.get(currentToken).getWord().equals("true")||tokens.get(currentToken).getWord().equals("false")||tokens.get(currentToken).getWord().equals("(")||tokens.get(currentToken).getWord().equals(")")||tokens.get(currentToken).getWord().equals(";")))
            {
                currentToken++;
            }
        }
        
        node=new DefaultMutableTreeNode("expression");
        parent.add(node);
        error=rule_expression(node);
        
        return error;
    }
    
//----------------------------rule-variable
    
    private static boolean rule_variable(DefaultMutableTreeNode parent)
    {
        boolean error=false;
        DefaultMutableTreeNode node;
        
        node=new DefaultMutableTreeNode(tokens.get(currentToken).getWord());
        parent.add(node);
        currentToken++;
        if(currentToken<tokens.size() && tokens.get(currentToken).getToken().equalsIgnoreCase("identifier"))
        {
            node=new DefaultMutableTreeNode("identifier" + "(" + tokens.get(currentToken).getWord() +")");
            parent.add(node);
            currentToken++;
        }
        
        else
        {
            
            error(6);
        }
        
        return error;
    }
    
    //----------------------------rule-print
    
    private static boolean rule_print(DefaultMutableTreeNode parent)
    {
        boolean error=false;
        DefaultMutableTreeNode node;
        node=new DefaultMutableTreeNode(tokens.get(currentToken).getWord());
        parent.add(node);
        currentToken++;
        if(currentToken<tokens.size() && tokens.get(currentToken).getWord().equals("("))
        {
            node=new DefaultMutableTreeNode("(");
            parent.add(node);
            currentToken++;
        }
        
        else
        {
            error(8);
            while(currentToken<tokens.size()&&!(tokens.get(currentToken).getWord().equals("!")||tokens.get(currentToken).getWord().equals("-")||tokens.get(currentToken).getToken().equals("INTEGER")||tokens.get(currentToken).getToken().equals("OCTAL")||tokens.get(currentToken).getToken().equals("HEXADECIMAL")||tokens.get(currentToken).getToken().equals("BINARY")||tokens.get(currentToken).getToken().equals("STRING")||tokens.get(currentToken).getToken().equals("CHARACTER")||tokens.get(currentToken).getToken().equals("FLOAT")||tokens.get(currentToken).getToken().equals("IDENTIFIER")||tokens.get(currentToken).getWord().equals("true")||tokens.get(currentToken).getWord().equals("false")||tokens.get(currentToken).getWord().equals("(")||tokens.get(currentToken).getWord().equals(")")))
            {
                currentToken++;
            }
        }
        
        node=new DefaultMutableTreeNode("expression");
        parent.add(node);
        error=rule_expression(node);
        
        if(currentToken<tokens.size()&& tokens.get(currentToken).getWord().equals(")"))
        {
            node=new DefaultMutableTreeNode(")");
            parent.add(node);
            currentToken++;
        }
        
        else
            error(7);
        return error;
    }
    
    //----------------------------rule-return
    
    private static boolean rule_return(DefaultMutableTreeNode parent)
    {
        boolean error=false;
        DefaultMutableTreeNode node;
        
        node=new DefaultMutableTreeNode(tokens.get(currentToken).getWord());
        parent.add(node);
        currentToken++;
        return error;
    }
    
    //----------------------------rule-while
    
    private static boolean rule_while(DefaultMutableTreeNode parent)
    {
        boolean error=false;
        DefaultMutableTreeNode node;
        
        node=new DefaultMutableTreeNode("while");
        parent.add(node);
        currentToken++;
        if(currentToken<tokens.size()&&tokens.get(currentToken).getWord().equals("("))
        {
            node=new DefaultMutableTreeNode("(");
            parent.add(node);
            currentToken++;
        }
        
        else
        {
            error(8);
            while(currentToken<tokens.size()&&!(tokens.get(currentToken).getWord().equals("!")||tokens.get(currentToken).getWord().equals("-")||tokens.get(currentToken).getToken().equals("INTEGER")||tokens.get(currentToken).getToken().equals("OCTAL")||tokens.get(currentToken).getToken().equals("HEXADECIMAL")||tokens.get(currentToken).getToken().equals("BINARY")||tokens.get(currentToken).getToken().equals("STRING")||tokens.get(currentToken).getToken().equals("CHARACTER")||tokens.get(currentToken).getToken().equals("FLOAT")||tokens.get(currentToken).getToken().equals("IDENTIFIER")||tokens.get(currentToken).getWord().equals("true")||tokens.get(currentToken).getWord().equals("false")||tokens.get(currentToken).getWord().equals("(")||tokens.get(currentToken).getWord().equals(")")))
            {
                currentToken++;
            }
        }
        
        node=new DefaultMutableTreeNode("expression");
        parent.add(node);
        error=rule_expression(node);
        
        if(tokens.get(currentToken).getWord().equals(")"))
        {
            node=new DefaultMutableTreeNode(")");
            parent.add(node);
            currentToken++;
        }
        else
        {
            error(7);
            while(currentToken<tokens.size()&&!tokens.get(currentToken).getWord().equals("{"))
            {
                currentToken++;
            }
            
        }
        node=new DefaultMutableTreeNode("program");
        parent.add(node);
        error=rule_program(node);
        return error;
    }
    
    //----------------------------rule-if
    
    private static boolean rule_if(DefaultMutableTreeNode parent)
    {
        boolean error=false;
        DefaultMutableTreeNode node;
        
        node=new DefaultMutableTreeNode("if");
        parent.add(node);
        currentToken++;
        if(tokens.get(currentToken).getWord().equals("("))
        {
            node=new DefaultMutableTreeNode("(");
            parent.add(node);
            currentToken++;
        }
        else
        {
            error(8);
            while(currentToken<tokens.size()&&!(tokens.get(currentToken).getWord().equals("!")||tokens.get(currentToken).getWord().equals("-")||tokens.get(currentToken).getToken().equals("INTEGER")||tokens.get(currentToken).getToken().equals("OCTAL")||tokens.get(currentToken).getToken().equals("HEXADECIMAL")||tokens.get(currentToken).getToken().equals("BINARY")||tokens.get(currentToken).getToken().equals("STRING")||tokens.get(currentToken).getToken().equals("CHARACTER")||tokens.get(currentToken).getToken().equals("FLOAT")||tokens.get(currentToken).getToken().equals("IDENTIFIER")||tokens.get(currentToken).getWord().equals("true")||tokens.get(currentToken).getWord().equals("false")||tokens.get(currentToken).getWord().equals("(")||tokens.get(currentToken).getWord().equals(")")))
            {
                currentToken++;
            }
        }
        
        node=new DefaultMutableTreeNode("expression");
        parent.add(node);
        error=rule_expression(node);
        
        if(tokens.get(currentToken).getWord().equals(")"))
        {
            node=new DefaultMutableTreeNode(")");
            parent.add(node);
            currentToken++;
        }
        
        else
        {
            error(7);
            while(currentToken<tokens.size()&&!(tokens.get(currentToken).getWord().equals("{")||tokens.get(currentToken).getWord().equals("else")))
                currentToken++;
        }
        
        node=new DefaultMutableTreeNode("program");
        parent.add(node);
        error=rule_program(node);
        
        if(currentToken<tokens.size()&&tokens.get(currentToken).getWord().equalsIgnoreCase("else"))
        {
            node=new DefaultMutableTreeNode("ELSE");
            parent.add(node);
            currentToken++;
            node=new DefaultMutableTreeNode("program");
            parent.add(node);
            error=rule_program(node);
        }
        return error;
    }
    
    //----------------------------rule-expression
    
    private static boolean rule_expression(DefaultMutableTreeNode parent)
    {
        boolean error=false;
        DefaultMutableTreeNode node;
        
        node=new DefaultMutableTreeNode("X");
        parent.add(node);
        error=rule_X(node);
        
        while(currentToken<tokens.size()&&tokens.get(currentToken).getWord().equals("|"))
        {
            node=new DefaultMutableTreeNode("|");
            parent.add(node);
            currentToken++;
            node=new DefaultMutableTreeNode("X");
            parent.add(node);
            error=rule_X(node);
            
        }
        
        return error;
    }
    
    //----------------------------rule-X
    
    private static boolean rule_X(DefaultMutableTreeNode parent)
    {
        
        boolean error=false;
        DefaultMutableTreeNode node;
        node=new DefaultMutableTreeNode("Y");
        parent.add(node);
        error=rule_Y(node);
        
        
        while(currentToken<tokens.size()&&tokens.get(currentToken).getWord().equals("&"))
        {
            node=new DefaultMutableTreeNode("&");
            parent.add(node);
            currentToken++;
            node=new DefaultMutableTreeNode("Y");
            parent.add(node);
            error=rule_Y(node);
        }
        return error;
    }
    
    //----------------------------rule-Y
    
    private static boolean rule_Y(DefaultMutableTreeNode parent)
    {
        
        boolean error=false;
        DefaultMutableTreeNode node;
        if(currentToken<tokens.size()&&tokens.get(currentToken).getWord().equals("!"))
        {
            node=new DefaultMutableTreeNode("!");
            parent.add(node);
            currentToken++;
        }
        node=new DefaultMutableTreeNode("R");
        parent.add(node);
        error=rule_R(node);
        return error;
    }
    
    //----------------------------rule-R
    
    private static boolean rule_R(DefaultMutableTreeNode parent)
    {
        
        boolean error=false;
        DefaultMutableTreeNode node;
        node=new DefaultMutableTreeNode("E");
        parent.add(node);
        error=rule_E(node);
        while(currentToken<tokens.size()&&(tokens.get(currentToken).getWord().equals("<")||tokens.get(currentToken).getWord().equals(">")||tokens.get(currentToken).getWord().equals("==")||tokens.get(currentToken).getWord().equals("!=")))
        {
            if(tokens.get(currentToken).getWord().equals("<"))
            {
                node=new DefaultMutableTreeNode("<");
                parent.add(node);
            }
            if(tokens.get(currentToken).getWord().equals(">"))
            {
                node=new DefaultMutableTreeNode(">");
                parent.add(node);
            }
            if(tokens.get(currentToken).getWord().equals("=="))
            {
                node=new DefaultMutableTreeNode("==");
                parent.add(node);
            }
            if(tokens.get(currentToken).getWord().equals("!="))
            {
                node=new DefaultMutableTreeNode("!=");
                parent.add(node);
            }
            
            currentToken++;
            node=new DefaultMutableTreeNode("E");
            parent.add(node);
            error=rule_E(node);
        }
        return error;
    }
    
    //----------------------------rule-E
    
    private static boolean rule_E(DefaultMutableTreeNode parent) 
    {
       
        boolean error;
        DefaultMutableTreeNode node;
        node = new DefaultMutableTreeNode("A");
        parent.add(node);
        error = rule_A(node);
        
        
        while (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("+") || currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("-")) {
            
            if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("+")) {
                node = new DefaultMutableTreeNode("+");
                parent.add(node);
                currentToken++;
                node = new DefaultMutableTreeNode("A");
                parent.add(node);
                error = rule_A(node);
            }
            
            else if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("-")) {
                node = new DefaultMutableTreeNode("-");
                parent.add(node);
                currentToken++;
                node = new DefaultMutableTreeNode("A");
                parent.add(node);
                error = rule_A(node);
            }
        }
        return error;
    }
    
    //----------------------------rule-A
    
    private static boolean rule_A(DefaultMutableTreeNode parent) {
        boolean error;
        DefaultMutableTreeNode node = new DefaultMutableTreeNode("B");
        parent.add(node);
        error = rule_B(node);
        
        while (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("*") || currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("/")) {
            
            if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("*")) {
                node = new DefaultMutableTreeNode("*");
                parent.add(node);
                currentToken++;
                node = new DefaultMutableTreeNode("B");
                parent.add(node);
                error = rule_B(node);
            }
            
            else if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("/")) {
                
                node = new DefaultMutableTreeNode("/");
                parent.add(node);
                node = new DefaultMutableTreeNode("B");
                parent.add(node);
                currentToken++;
                error = rule_B(node);
            }
        }
        return error;
    }
    
    
    //----------------------------rule-B
    
    private static boolean rule_B(DefaultMutableTreeNode parent) {
        boolean error;
        DefaultMutableTreeNode node;
        
        if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("-")) {
            node = new DefaultMutableTreeNode("-");
            parent.add(node);
            currentToken++;
        }
        
        node = new DefaultMutableTreeNode("C");
        parent.add(node);
        error = rule_C(node);
        return error;
    }
    
    //----------------------------rule-c
    
    private static boolean rule_C(DefaultMutableTreeNode parent) {
        boolean error = false;
        DefaultMutableTreeNode node;
        
        if (currentToken < tokens.size() && tokens.get(currentToken).getToken().equals("INTEGER")) {
            node = new DefaultMutableTreeNode("integer" + "(" + tokens.get(currentToken).getWord() + ")");
            parent.add(node);
            currentToken++;
        }
        
        else if (currentToken < tokens.size() && tokens.get(currentToken).getToken().equals("OCTAL")) {
            node = new DefaultMutableTreeNode("octal" + "(" + tokens.get(currentToken).getWord() + ")");
            parent.add(node);
            currentToken++;
        }
        
        else if (currentToken < tokens.size() && tokens.get(currentToken).getToken().equals("HEXADECIMAL")) {
            node = new DefaultMutableTreeNode("hexadecimal" + "(" + tokens.get(currentToken).getWord() + ")");
            parent.add(node);
            currentToken++;
        }
        
        else if (currentToken < tokens.size() && tokens.get(currentToken).getToken().equals("BINARY")) {
            node = new DefaultMutableTreeNode("binary" + "(" + tokens.get(currentToken).getWord() + ")");
            parent.add(node);
            currentToken++;
        }
        
        else if (currentToken < tokens.size() && tokens.get(currentToken).getToken().equals("STRING")) {
            node = new DefaultMutableTreeNode("string" + "(" + tokens.get(currentToken).getWord() + ")");
            parent.add(node);
            currentToken++;
        }
        
        else if (currentToken < tokens.size() && tokens.get(currentToken).getToken().equals("CHARACTER")) {
            node = new DefaultMutableTreeNode("character" + "(" + tokens.get(currentToken).getWord() + ")");
            parent.add(node);
            currentToken++;
        }
        
        else if (currentToken < tokens.size() && tokens.get(currentToken).getToken().equals("FLOAT")) {
            node = new DefaultMutableTreeNode("float" + "(" + tokens.get(currentToken).getWord() + ")");
            parent.add(node);
            currentToken++;
        }
        
        else if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equalsIgnoreCase("true")) {
            node = new DefaultMutableTreeNode("boolean" + "(" + tokens.get(currentToken).getWord() + ")");
            parent.add(node);
            currentToken++;
        }
        
        else if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("false")) {
            node = new DefaultMutableTreeNode("boolean" + "(" + tokens.get(currentToken).getWord() + ")");
            parent.add(node);
            currentToken++;
        }
        
        else if (currentToken < tokens.size() && tokens.get(currentToken).getToken().equals("IDENTIFIER")) {
            node = new DefaultMutableTreeNode("identifier" + "(" + tokens.get(currentToken).getWord() + ")");
            parent.add(node);
            currentToken++;
        }
        
        else if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("(")) {
            node = new DefaultMutableTreeNode("(");
            parent.add(node);
            currentToken++;
            //
            node = new DefaultMutableTreeNode("expression");
            parent.add(node);
            error = rule_expression(node);
            //
            if(currentToken<tokens.size()&&tokens.get(currentToken).getWord().equals(")"))
            {
                node = new DefaultMutableTreeNode(")");
                parent.add(node);
                currentToken++;
            }
            else
                error(7);
        }
        
        else {
            error(9);
            
        }
        
        return false;
    }
    public static void error(int i)
    {
        int n=0;
        if(i!=4 && currentToken!=0 && (tokens.get(currentToken-1).getLine()<tokens.get(currentToken).getLine()))
        {
            n=tokens.get(currentToken-1).getLine();
        }
        else
        {
            n=tokens.get(currentToken).getLine();
        }
        switch(i)
        {
            case 1:
                gui.writeConsole("Line" +n+ ":expected {");
                break;
            case 2:
                gui.writeConsole("Line" +n+ ":expected }");
                break;
            case 3:
                gui.writeConsole("Line" +n+ ":expected ;");
                break;
            case 4:
                gui.writeConsole("Line" +n+ ":expected identifier or keyword");
                break;
            case 5:
                gui.writeConsole("Line" +n+ ": expected =");
                break;
            case 6:
                gui.writeConsole("Line" +n+": expected identifier");
                break;
            case 7:
                gui.writeConsole("Line" +n+": expected )");
                break;
            case 8:
                gui.writeConsole("Line" +n+": expected (");
                break;
            case 9:
                gui.writeConsole("Line" +n+": expected value, identifier, (");
                break;
        }
    }
    
}
