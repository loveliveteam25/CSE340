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
import javax.swing.tree.DefaultMutableTreeNode;
import lexer.Token;
/**
 *
 * @author javiergs
 */
public class Parser {

  private static DefaultMutableTreeNode root;
  private static Vector<Token> tokens;
  private static int currentToken;

  //GIVEN by Professor
  public static DefaultMutableTreeNode run(Vector<Token> t) 
  {
    tokens = t;
    currentToken = 0;
    root = new DefaultMutableTreeNode("expression");
    //
    rule_expression(root);
    //
    return root;
  }
  
  //rule-program
  private static boolean rule_program(DefaultMutableTreeNode parent) 
  {
    boolean error;
    DefaultMutableTreeNode node;
    if(currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("{"))
    {
        node = new DefaultMutableTreeNode("{");
        parent.add(node);
        currentToken++;
    }
        node = new DefaultMutableTreeNode("body");
        parent.add(node);
        error = rule_body(node);
    if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("}")) 
    {
        node = new DefaultMutableTreeNode("}");
        parent.add(node);
        currentToken++;
    }
    return error;
  }

  //rule-body
  private static boolean rule_body(DefaultMutableTreeNode parent) 
  {
	  boolean error = false;
	  DefaultMutableTreeNode node;
	  while(!tokens.get(currentToken).getWord().equals("}"))
	  {	  
		  if (tokens.get(currentToken).getWord().equals("print")) 
                  {
                        node = new DefaultMutableTreeNode("print");			  
                        parent.add(node);
                        error = rule_print(node);
		       	if (tokens.get(currentToken).getWord().equals(";"))    
			    node = new DefaultMutableTreeNode(";");
		            parent.add(node);
		            currentToken++;     
		   }
		  
		  else if (tokens.get(currentToken).getToken().equals("IDENTIFIER")) 
                  {
			  node = new DefaultMutableTreeNode("assignment");			  
			  parent.add(node);
			  error = rule_assignment(node);
			       
                        if (tokens.get(currentToken).getWord().equals(";"))    
				node = new DefaultMutableTreeNode(";");
                                parent.add(node);
                                currentToken++;     
		  }
                  else if (tokens.get(currentToken).getWord().equals("int") ||tokens.get(currentToken).getWord().equals("float") ||
			tokens.get(currentToken).getWord().equals("boolean") ||tokens.get(currentToken).getWord().equals("char")||
			tokens.get(currentToken).getWord().equals("string")||tokens.get(currentToken).getWord().equals("void"))
//			
		     {
			  node = new DefaultMutableTreeNode("assignment");			  
			  parent.add(node);
			  error = rule_variable(node);
			       
		          if (tokens.get(currentToken).getWord().equals(";"))    
				node = new DefaultMutableTreeNode(";");
		                parent.add(node);
		                currentToken++;     
		     }		  
		 
	           else if (tokens.get(currentToken).getToken().equals("while")) 
                     {
		           node = new DefaultMutableTreeNode("while");			  
		           parent.add(node);
		           error = rule_while(node);
		  
	             }
	           else if (tokens.get(currentToken).getToken().equals("if")) 
                     {
		                  node = new DefaultMutableTreeNode("if");			  
		                  parent.add(node);
		                  error = rule_if(node);
		  
	              }
	           else if (tokens.get(currentToken).getToken().equals("return")) 
                     {
		          node = new DefaultMutableTreeNode("return");			  
		          parent.add(node);
		          error = rule_return(node);
		  
		          if (tokens.get(currentToken).getWord().equals(";"))    
			        node = new DefaultMutableTreeNode(";");
	                        parent.add(node);
	                        currentToken++;
	             }
	  }
	     return error;
  }
  
  private static boolean rule_assignment(DefaultMutableTreeNode parent) {
	  boolean error = false;
	  DefaultMutableTreeNode node;
	  if(tokens.get(currentToken).getToken().equals("identifier"))
          {
              node = new DefaultMutableTreeNode("identifier");
              parent.add(node);
              currentToken ++;
          }
	  if(tokens.get(currentToken).getWord().equals("="))
          {
              node = new DefaultMutableTreeNode("=");
              parent.add(node);
              currentToken ++;
              
              node = new DefaultMutableTreeNode("expression");
              parent.add(node);
	      error = rule_expression(node);
          }
	  
     return error;
  }
	 	    
  //rule-variable
  private static boolean rule_variable(DefaultMutableTreeNode parent) 
  {
	  boolean error = false;
	  DefaultMutableTreeNode node;
	 if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("int")) 
         {
	    node = new DefaultMutableTreeNode("int");
	    parent.add(node);
	    currentToken++;
	 }
	 else if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("float")) 
         {
		 node = new DefaultMutableTreeNode("float");
		 parent.add(node);
		 currentToken++;
	 }
	 else if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("boolean")) 
         {
		 node = new DefaultMutableTreeNode("boolean");
		 parent.add(node);
		 currentToken++;
	 }
	 else if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("char")) 
         {
		 node = new DefaultMutableTreeNode("char");
		 parent.add(node);
		 currentToken++;
	}
	 else if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("string")) 
         {
		 node = new DefaultMutableTreeNode("string");
		 parent.add(node);
		 currentToken++;
	 }
	 else if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("void")) 
         {
		 node = new DefaultMutableTreeNode("void");
		 parent.add(node);
		 currentToken++;
	 }
	 else {error = true;}
	 
	 if (currentToken < tokens.size() && tokens.get(currentToken).getToken().equals("IDENTIFIER")) {
		 node = new DefaultMutableTreeNode("identifier");
		 parent.add(node);
		 currentToken++;
	 	 }
	 else 
         {
             return true;
         }
	 
	 return error;
  }
  
 //rule-while
  private static boolean rule_while(DefaultMutableTreeNode parent) 
  {
	  boolean error = false;
	  DefaultMutableTreeNode node;
	    
	  if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("while")) 
          {
			 node = new DefaultMutableTreeNode("while");
			 parent.add(node);
			 currentToken++;
	  }
	  if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("(")) 
          {
			 node = new DefaultMutableTreeNode("(");
			 parent.add(node);
                         currentToken++;
	  }
          
          node = new DefaultMutableTreeNode("expression");
	  parent.add(node);
	  error = rule_expression(node);
          
	  if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("(")) 
          {
			 node = new DefaultMutableTreeNode(")");
			 parent.add(node);
			 currentToken++;
	  }
	  node = new DefaultMutableTreeNode("program");
	  parent.add(node);
	  error = rule_program(node);
		
          return error; 
  }
		
	    
  //rule-if
  private static boolean rule_if(DefaultMutableTreeNode parent) 
  {
	  boolean error = false;
	    DefaultMutableTreeNode node;
	    if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("if")) 
            {
			 node = new DefaultMutableTreeNode("if");
			 parent.add(node);
			 currentToken++;
            }
	    
	    if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("(")) 
            {
			 node = new DefaultMutableTreeNode("(");
			 parent.add(node);
			 currentToken++;
            }
	    
		node = new DefaultMutableTreeNode("expression");
		parent.add(node);
	        error = rule_expression(node);
			 
	    if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals(")")) 
            {
				 node = new DefaultMutableTreeNode(")");
				 parent.add(node);
				 currentToken++;
            }
		 node = new DefaultMutableTreeNode("program");
		 parent.add(node);
		 error = rule_program(node);
						 
	     if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("else")) 
             {
		node = new DefaultMutableTreeNode("else");
		parent.add(node);
		currentToken++;
			 
             }
             node = new DefaultMutableTreeNode("program");
	     parent.add(node);
	     error = rule_program(node);
			 
		 return error;	 
 }
  
	    
  //rule-return
  private static boolean rule_return(DefaultMutableTreeNode parent) 
  {
      boolean error = false;
      DefaultMutableTreeNode node;
      
      if(currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("return")) 
      {
	    node = new DefaultMutableTreeNode("return");
	    parent.add(node);
	    currentToken++;
      }
      return error;
  }
  
  //rule-print
  private static boolean rule_print(DefaultMutableTreeNode parent) 
  {
      boolean error = true;  
      DefaultMutableTreeNode node = null;
	  
      if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("print")) 
      {
		  node = new DefaultMutableTreeNode("print");
		  parent.add(node);
		  currentToken++;
      }	 
			
      if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("(")) 
      {
		node = new DefaultMutableTreeNode("(");
		parent.add(node);
		currentToken++;
       }
       if (currentToken < tokens.size() && tokens.get(currentToken).getToken().equals("expression")) 
       {
		node = new DefaultMutableTreeNode("expression");
		parent.add(node);
       }	error = rule_expression(node);
	   
        if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals(")")) 
        {
		node = new DefaultMutableTreeNode(")");
		parent.add(node);
		currentToken++;
        }
		
        return error;
  }
  
  //Given by Professor
  private static boolean rule_expression(DefaultMutableTreeNode parent) {
    boolean error;
    DefaultMutableTreeNode node;
    node = new DefaultMutableTreeNode("A");
    parent.add(node);
    error = rule_A(node);
    while (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("+") || currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("-")) {
      if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("+")) 
      {
        node = new DefaultMutableTreeNode("+");
        parent.add(node);
        currentToken++;
        node = new DefaultMutableTreeNode("A");
        parent.add(node);
        error = rule_A(node);
      } 
      else if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("-")) 
      {
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
  
  //rule-X
  private static boolean rule_X(DefaultMutableTreeNode parent) 
  {
	boolean error = false;
        DefaultMutableTreeNode node;
        node = new DefaultMutableTreeNode("Y");
        parent.add(node);
        error = rule_Y(node);
        while (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("&")) 
        {
            if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("&")) 
            {
                node = new DefaultMutableTreeNode("&");
                parent.add(node);
                currentToken++;
                node = new DefaultMutableTreeNode("Y");
                parent.add(node);
                error = rule_Y(node);//node method call
            }
        }
         return error;
}
  
  
//rule-Y
  private static boolean rule_Y(DefaultMutableTreeNode parent) 
  {
	boolean error= false;
	DefaultMutableTreeNode node;
	    
	if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("!"))
    	{	node = new DefaultMutableTreeNode("!");
	    	parent.add(node); 
	    	currentToken++;
    	}
	node = new DefaultMutableTreeNode("R");
    	parent.add(node); 
    	error = rule_R(node);
    	
        return error;
  }
  
//rule-R
  private static boolean rule_R(DefaultMutableTreeNode parent) 
  {
	boolean error,loop = false;
	DefaultMutableTreeNode node;
  
	do{
	    	node = new DefaultMutableTreeNode("E");
	    	parent.add(node); 
	    	error = rule_E(node);
	    	
	    if (currentToken < tokens.size() && (tokens.get(currentToken).getWord().equals("<") ||tokens.get(currentToken).getWord().equals(">") ||
		tokens.get(currentToken).getWord().equals("==")|| tokens.get(currentToken).getWord().equals("!=")))
	       {    
                   loop = true;
		    if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("<")) 
                    {
		        node = new DefaultMutableTreeNode("<");
		        parent.add(node);
		        currentToken++;
                    }
		    if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals(">"))
                    {
		        node = new DefaultMutableTreeNode(">");
		        parent.add(node);
		        currentToken++;
                    }		    
		    if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("==")) 
                    {
		        node = new DefaultMutableTreeNode("==");
		        parent.add(node);
		        currentToken++;
                    }
		    if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("!=")) 
                    {
		        node = new DefaultMutableTreeNode("!=");
		        parent.add(node);
		        currentToken++;
                    }
	        }
	 
	  } 
        while(loop == true);
  
        return error;
  }
  
//rule-E
  private static boolean rule_E(DefaultMutableTreeNode parent) 
  {
	    boolean error,loop = false;
	    DefaultMutableTreeNode node;
	    
	    do
	    {   
	    	node = new DefaultMutableTreeNode("A");
	    	parent.add(node); 
	    	error = rule_A(node);
	    	
	    	if (currentToken < tokens.size() && (tokens.get(currentToken).getWord().equals("+") ||tokens.get(currentToken).getWord().equals("-") ))
	    	{ 
                    loop = true;
	    	    if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("+")) 
                    {
		        node = new DefaultMutableTreeNode("+");
		        parent.add(node);
		        currentToken++;
                    }
	    	    if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("-")) 
                    {
		        node = new DefaultMutableTreeNode("-");
		        parent.add(node);
		        currentToken++;
                    }
	        }
	    }
            while(loop == true);
            
            return error;
  }
  
  //Given by Professor
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

      } else if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("/")) {
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

  //Given by Professor
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

  //Given by Professor
  private static boolean rule_C(DefaultMutableTreeNode parent) {
    boolean error = false;
    DefaultMutableTreeNode node;
    if (currentToken < tokens.size() && tokens.get(currentToken).getToken().equals("INTEGER")) {
      node = new DefaultMutableTreeNode("integer" + "(" + tokens.get(currentToken).getWord() + ")");
      parent.add(node);
      currentToken++;
    } else if (currentToken < tokens.size() && tokens.get(currentToken).getToken().equals("IDENTIFIER")) {
      node = new DefaultMutableTreeNode("identifier" + "(" + tokens.get(currentToken).getWord() + ")");
      parent.add(node);
      currentToken++;
    } else if (currentToken < tokens.size() && tokens.get(currentToken).getWord().equals("(")) {
      node = new DefaultMutableTreeNode("(");
      parent.add(node);
      currentToken++;
      //
      node = new DefaultMutableTreeNode("expression");
      parent.add(node);
      error = rule_expression(node);
      //
      node = new DefaultMutableTreeNode(")");
      parent.add(node);
      currentToken++;      
    } else {
      return true;
    }
    return false;
  }

}
