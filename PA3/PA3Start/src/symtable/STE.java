package symtable;
import java.util.*;
import ast.node.*;


/** 
 * STE
 * 
 */
public class STE{
    protected String myName; 

	public STE(String name){
		myName = name;
	}

    public String getName(){
		return myName;
	}

	public void setName(String s){
		myName = s;
	}

}
