package symtable;
import java.util.*;
import java.io.PrintWriter;
import java.io.PrintStream;
import ast.node.*;

import exceptions.InternalException;

/** 
 * Scope
 * 
 * 
 */
public class Scope {
    public static final Scope GLOBAL = new Scope();
	private final HashMap<String,STE> mDict = new HashMap<String,STE>();
    private Scope myEnclosing;
    private VarScope scopeType;
    private int offset;
    private String scopeName;
    
    private Scope(){
    	myEnclosing = null;
    	scopeType = null;
    	scopeName = "GLOBAL";
    }
    
    public Scope(VarScope type, String name){
        myEnclosing = null;
        scopeType = type;
        setStartingOffset();
        scopeName = name;
    }

    public Scope(VarScope type, String name, Scope parent){
        myEnclosing = parent;
        scopeType = type;
        setStartingOffset();
        scopeName = name;
    }
    
    private void setStartingOffset(){
    	switch (scopeType){
    	case LOCAL: offset = 3; break;
    	case CLASS: offset = 0; break;
    	default: assert false;
    	}
    }
	
	public void outputDot(PrintStream printer){
		for(String key : mDict.keySet()){
			printer.println(mDict.get(key).toString());
		}
	}
   
    
    public STE lookup(String key)    {
    	return mDict.get(key);
    }
    
    public void insert(STE entry)    {
    	mDict.put(entry.getName(), entry);
    }
   
	public String toString(){
		String retStr = "<f0> Scope | ";
		int count = 1;
		for(String key : mDict.keySet()){		
			retStr += "<f"+count+"> mDict\\["+key+"\\] ";
		}
		return retStr;
	}

	public VarScope getScopeType() {
		return scopeType;
	}

	public void setScopeType(VarScope scopeType) {
		this.scopeType = scopeType;
	}

	public int getOffset() {
		return offset;
	}
	
	public void incrementOffset(int sizeAdded){
		offset += sizeAdded;
	}
	
	public String getName(){
		return scopeName;
	}

}
