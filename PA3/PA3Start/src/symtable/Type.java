package symtable;

import exceptions.*;

import java.util.*;
import ast.node.*;

public class Type
{

private static HashMap<String,Type> classTypes = new HashMap<String,Type>();
private String typeName = null;

  public static final Type BOOL = new Type();
  public static final Type INT = new Type();
  public static final Type BYTE = new Type();
  public static final Type COLOR = new Type();
  public static final Type BUTTON = new Type();
  public static final Type TONE = new Type();
  public static final Type VOID = new Type();

  private Type()
  {

  }
  
  public Type(String name){
	  typeName = name;
//	  if (classTypes.containsKey(typeName)){
//		  throw new SemanticException(
//				  "Multiple definitions of class "+name+" found!");
//	  }
	  classTypes.put(typeName,this);
  }
    
/*
*/

  public String toString()
  {
    if(this == INT)
    {
      return "INT";
    }

    if(this == BOOL)
    {
      return "BOOL";
    }

    if(this == BYTE)
    {
      return "BYTE";
    }

    if(this == COLOR)
    {
      return "COLOR";
    }

    if(this == BUTTON)
    {
      return "BUTTON";
    }

    if(this == TONE)
    {
      return "TONE";
    }

    if(this == VOID)
    {
      return "VOID";
    }
    
    if(typeName != null)
    {
    	return typeName;
    }
   
    return "MAINCLASS;";
  }
  
  public int getAVRTypeSize() {
      if(this == INT) { return 2; }
      if(this == BOOL) { return 1; }
      if(this == BYTE) { return 1; }
      if(this == COLOR) { return 1; }
      if(this == BUTTON) { return 1; }
      if(this == TONE) { return 2; }
      if(this == VOID) { return 0; }
      return 2; // class references are 2 bytes
  }

public static Type parseType(String typeString) {
	if (typeString.equals(INT.toString()))
		return INT;
	if (typeString.equals(BOOL.toString()))
		return BOOL;
	if (typeString.equals(BYTE.toString()))
		return BYTE;
	if (typeString.equals(COLOR.toString()))
		return COLOR;
	if (typeString.equals(BUTTON.toString()))
		return BUTTON;
	if (typeString.equals(TONE.toString()))
		return TONE;
	else if (classTypes.containsKey(typeString)){
		return classTypes.get(typeString);
	}
	return VOID;
}

public static Type getOrMake(String typename){
	if (classTypes.containsKey(typename)){
		return classTypes.get(typename);
	}
	return new Type(typename);
}

    
/*  
*/

}
