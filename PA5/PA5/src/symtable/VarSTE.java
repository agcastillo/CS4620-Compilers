package symtable;
import java.util.*;
import ast.node.*;

import exceptions.InternalException;

/** 
 * VarSTE
 */
public class VarSTE extends STE{
    private Type mType;
    //private ScopeType mBase;
    private int mOffset;
    private String mBase;

    public VarSTE(String name, Type type, String base, int offset){
		super(name);
        this.mType   = type;
        this.mBase   = base;
        this.mOffset = offset;
    }

    public void setType(Type t)    {
    	this.mType = t;
    }
    
    public Type getType()    {
    	return this.mType;
    }

    public void setBase(String i)    {
    	this.mBase = i;
    }
    
    public String getBase()    {
    	return this.mBase;
    }

    public void setOffset(int i)    {
    	this.mOffset = i;
    }
    
    public int getOffset()    {
    	return this.mOffset;
    }
/*
	public String toString(){
		 return "<f0> VarSTE | <f1> mName = "+mName+"| <f2> mType = "+mType+"| <f3> mBase = INVALID| <f4> mOffset = 0";
	}
   */
/*
 */

}