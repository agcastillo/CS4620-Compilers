package symtable;
import java.util.*;
import ast.node.*;
import symtable.Scope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.PrintStream;
import exceptions.InternalException;
import exceptions.SemanticException;

/** 
 * SymTable
 */
public class SymTable {
    public static final String DOTMANGLE = "_dot_";
	private final HashMap<Node,Type> mExpType = new HashMap<Node,Type>();
    private Scope myGlobalScope = Scope.GLOBAL;
    private Stack<Scope> mScopeStack = new Stack<Scope>();
    
    public SymTable() {
    	mScopeStack.push(myGlobalScope);
    }
    
    /** Lookup a symbol in this symbol table.
     * Starts looking in innermost scope and then
     * look in enclosing scopes.
     * Returns null if the symbol is not found.
     */
    public STE lookup(String sym) {
//    	System.out.println("Looking for "+sym+" in any accessible scope."); // debug
    	@SuppressWarnings("unchecked")
		Stack<Scope> tempScopeStack = (Stack<Scope>) mScopeStack.clone();
    	STE ste = null;
        while (!mScopeStack.isEmpty()){
        	ste = lookupInnermost(sym);
        	if (ste != null){
//        		System.out.println("Found it!"); // debug
        		break;
        	}
//        	System.out.println("...not found."); // debug
        	mScopeStack.pop();
        }
        mScopeStack = tempScopeStack;
        return ste;
    }

    /** Lookup a symbol in innermost scope only.
     * return null if the symbol is not found
     */
    public STE lookupInnermost(String sym) {
//    	System.out.println("Looking for "+sym+" in scope "+mScopeStack.peek().getName()); // debug
        Scope currentScope = mScopeStack.peek();
        if (currentScope == null){
//        	System.out.println("...not found in any scope."); // debug
        	return null;
        }
        return currentScope.lookup(sym);
    }
 
    /** When inserting an STE will just insert
     * it into the scope at the top of the scope stack.
     */
    public boolean insert( STE ste) {
//    	System.out.println("Inserting "+ste.getName()+" into scope "+mScopeStack.peek().getName()); // debug
    	if (lookupInnermost(ste.getName()) != null){
    		return false;
    	}
        mScopeStack.peek().insert(ste);
        return true;
    }
    
    /** 
     * Lookup the given method scope and make it the innermost
     * scope.  IOW make it the top of the scope stack.
     */
    public void pushMethodScope(String id) {
//    	System.out.println("Looking for: "+id); //debug
//		System.out.println("About to do lookup: SymTable.pushMethodScope");//debug
    	STE symEntry = lookup(id);
//    	System.out.println("found: "+symEntry.getName()); //debug
    	MethodSTE methodEntry = (MethodSTE) symEntry;
    	mScopeStack.push(methodEntry.getScope());
//    	System.out.println("Pushing scope "+methodEntry.getScope().getName()); //Debug
    }
    
    /** 
     * Lookup the given class scope and make it the innermost
     * scope.  IOW make it the top of the scope stack.
     */
    public void pushClassScope(String id) {
//    	System.out.println("Looking for: "+id); //debug
//    	System.out.println("About to search for scope: "+id); // Debug
//		System.out.println("About to do lookup: SymTable.pushClassScope");//debug
    	STE symEntry = lookup(id);
//    	System.out.println("found: "+symEntry.getName()); //debug
    	ClassSTE classEntry = (ClassSTE) symEntry;
    	mScopeStack.push(classEntry.getScope());
//    	System.out.println("Pushing scope "+classEntry.getScope().getName()); // Debug
    }
    
    public void popScope() {
        mScopeStack.pop();
//    	System.out.println("After pop, num scopes left = "+mScopeStack.size()); //Debug
    }

	public void outputDot(PrintStream printer){
    	@SuppressWarnings("unchecked")
		Stack<Scope> tempScopeStack = (Stack<Scope>) mScopeStack.clone();
		printer.println("digraph SymTable {\n"+
			"\tgraph [rankdir=\"LR\"];\n"+
			"\tnode [shape=record];");
		myGlobalScope.outputDot(printer);
		while(!tempScopeStack.isEmpty()){
			tempScopeStack.peek().outputDot(printer);			
			tempScopeStack.pop();
		}
		printer.println("}");
	}
    
    public void setExpType(Node exp, Type t)
    {
    	this.mExpType.put(exp, t);
    }
    
    public Type getExpType(Node exp)
    {
    	return this.mExpType.get(exp);
    }

    public Type typeFromIType(IType t)
    {
    	if (t instanceof ByteType){
    		return Type.BYTE;
    	}
    	else if (t instanceof IntType){
    		return Type.INT;
    	}
    	else if (t instanceof BoolType){
    		return Type.BOOL;
    	}
    	else if (t instanceof ColorType){
    		return Type.COLOR;
    	}
    	else if (t instanceof ButtonType){
    		return Type.BUTTON;
    	}
    	else if (t instanceof ToneType){
    		return Type.TONE;
    	}
    	else if (t instanceof VoidType){
    		return Type.VOID;
    	}
    	throw new InternalException("IType not recognized.");
    }

	public String generateParamSig(List<? extends Node> args)
    {
    	String sig = "(";
		for(Node arg : args){
			sig += this.getExpType(arg)+", ";
		}
		sig += ")";
		return sig;
    }
   
    public String generateParamsAndRet(MethodDecl node)
    {
    	List<Formal> formals = node.getFormals();
    	for (Formal f : formals){
    		this.setExpType(f, this.typeFromIType(f.getType()));
    	}
    	return generateParamSig(formals) +
    			" returns " + 
    			this.typeFromIType(node.getType());
    }
	
	public Scope peekScope() {
		return mScopeStack.peek();
	}

	public String generateMethodName(MethodDecl node) {
//		System.out.println("generating method name, name of scope = "+mScopeStack.peek().getName()); // debug
		return mScopeStack.peek().getName() + DOTMANGLE + node.getName();
	}
	
	public String generateMethodName(GenericCall node){
		return getExpType(node.getExp()).toString() + DOTMANGLE + node.getId();
	}

	public String innermostClass() {
		@SuppressWarnings("unchecked")
		Stack<Scope> tempScopeStack = (Stack<Scope>) mScopeStack.clone();
		String innermostName = null;
		while (!mScopeStack.isEmpty()){
        	if (mScopeStack.peek().getScopeType() == VarScope.CLASS){
            	innermostName = mScopeStack.peek().getName();
        	}
        	mScopeStack.pop();
        }
        mScopeStack = tempScopeStack;
        return innermostName;
	}

}
