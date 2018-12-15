package symtable;
import java.util.*;
import ast.node.*;
import java.io.PrintStream;

import exceptions.InternalException;

public class SymTable {
	
	private final HashMap<Node,Type> mExpType = new HashMap<Node,Type>();
	private Stack<Scope> mScopeStack = new Stack<Scope>();
	private Scope mGlobalScope = Scope.GLOBAL;
	private PrintStream out;
	private int size;
    public final HashMap<String, MethodSTE> map = new HashMap<String,MethodSTE>();

    public SymTable() {
		mScopeStack.push(mGlobalScope);
	}
	public void insertMethod(MethodSTE mste){
		this.map.put(mste.getName(), mste);
	}
	public MethodSTE getMethod(String mste){
		return this.map.get(mste);
	}
	public boolean insert(STE ste){
      mScopeStack.peek().insert(ste);
      return true;
	}
	public int getSize(){
		return this.size;
	}
	public void setSize(int size){
		this.size = size;
	}
    public STE lookup(String sym) {
		Stack<Scope> tSS = (Stack<Scope>) this.mScopeStack.clone();
        STE ste = null;
      	while (!this.mScopeStack.isEmpty()){
        	ste = lookupInnermost(sym);
        	if (ste != null){
          		break;
        	}
        	this.mScopeStack.pop();
      	}
        this.mScopeStack = tSS;
        return ste;
    }

    public STE lookupInnermost(String sym) {
        Scope currentScope = mScopeStack.peek();
        return currentScope.lookup(sym);
    }

    public void setExpType(Node exp, Type t){
    	this.mExpType.put(exp, t);
    }
    
    public Type getExpType(Node exp){
    	return this.mExpType.get(exp);
	}

    public void pushMethodScope(String id) {
    	MethodSTE mste = (MethodSTE) lookup(id);
    	this.mScopeStack.push(mste.getScope());
    }
	public void pushMethodScope(MethodSTE id) {
    	this.mScopeStack.push(id.getScope());
    }
    public void pushClassScope(String id) {
    	STE ste = lookup(id);
    	ClassSTE cste = (ClassSTE) ste;
    	this.mScopeStack.push(cste.getScope());
    }
    
    public void popScope() {
      	this.mScopeStack.pop();
	}
	public Scope peekScope() {
		return this.mScopeStack.peek();
	}

	public String innermostClass() {
		Stack<Scope> temp = (Stack<Scope>) mScopeStack.clone();
		String name = null;
		while (!temp.isEmpty()){
			if (temp.peek().getScopeType() == ScopeType.CLASS){
				name = temp.peek().getName();
			}
			temp.pop();
		}
		return name;
	}
  
	public Type getTypeData(IType type){
        Type mType = null;
        if(type instanceof IntType){
            mType = Type.INT;
        }
        else if (type instanceof BoolType){
            mType = Type.BOOL;
        }
        else if (type instanceof ButtonType){
            mType = Type.BUTTON;
        }
        else if (type instanceof ByteType){
            mType = Type.BYTE;
        }
        else if (type instanceof ColorType){
            mType = Type.COLOR;
        }
        else if (type instanceof VoidType){
            mType = Type.VOID;
        }
        else if (type instanceof ToneType){
            mType = Type.TONE;
        }
        return mType;
	}

}
