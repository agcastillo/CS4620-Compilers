package symtable;
import java.util.*;
import ast.node.*;
import exceptions.InternalException;

/** 
 * MethodSTE
 */

public class MethodSTE extends STE{
    private String mySignature;
    private Scope myScope;

    public MethodSTE(String name, String sig) {
		super(name);
		mySignature = sig;
    }
    
    public String getSignature()
    {
    	return mySignature;
    }
    
    public void setSignature(String s)
    {
    	mySignature = s;
    }
    
    public Scope getScope()
    {
    	return myScope;
    }
    
    public void setScope(Scope s)
    {
    	myScope = s;
    }
   

	public String toString(){
		return " <f0> MethodSTE | <f1> mName = "+myName+"| <f2> mSignature = "+mySignature+"| <f3> "+myScope+" ";
	}
/*
 */

}
