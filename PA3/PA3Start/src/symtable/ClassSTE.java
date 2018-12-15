package symtable;
import java.util.*;
import ast.node.*;

import exceptions.InternalException;

/** 
 * ClassSTE
 */
public class ClassSTE extends STE{
    private boolean is_main;
    private Scope myScope;
	private int size;

    public ClassSTE(String name, boolean main, Scope scope){
		super(name);
        is_main      = main;
        myScope      = scope;
    }

	public void setMain(boolean b){
    	is_main = b;
    }
    
    public boolean isMain(){
    	return is_main;
    }

    public void setScope(Scope s){
    	myScope = s;
    }
    
    public Scope getScope(){
    	return myScope;
	}
   
	public String toString(){
		return "<f0> ClassSTE | <f1> mName = "+myName+"| <f2> mMain = false| <f3> mSuperClass = null| <f4> mScope ";
	}

}
