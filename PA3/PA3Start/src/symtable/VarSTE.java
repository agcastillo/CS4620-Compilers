package symtable;
import java.util.*;
import ast.node.*;

import exceptions.InternalException;

/** 
 * VarSTE
 */
public class VarSTE extends STE{
    private Type myType;
    private VarScope myBase;
    private int myOffset;

    public VarSTE(String name, Type type, Scope myScope){
		super(name);
        myType   = type;
        myBase   = myScope.getScopeType();
        myOffset = myScope.getOffset();
		myScope.incrementOffset(myType.getAVRTypeSize());
    }

    public void setType(Type t)    {
    	myType = t;
    }
    
    public Type getType()    {
    	return myType;
    }

    public void setBase(VarScope i)    {
    	myBase = i;
    }
    
    public VarScope getBase()    {
    	return myBase;
    }

    public void setOffset(int i)    {
    	myOffset = i;
    }
    
    public int getOffset()    {
    	return myOffset;
    }

	public String toString(){
		 return "<f0> VarSTE | <f1> mName = "+myName+"| <f2> mType = "+myType+"| <f3> mBase = INVALID| <f4> mOffset = 0";
	}
   
/*
 */

}
