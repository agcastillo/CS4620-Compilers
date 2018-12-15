package symtable;

import exceptions.*;

import java.util.*;

public class Type
{

  private static HashMap<String, Type> otherTypes = new HashMap<String,Type>();
  private String type = null;
  public static final Type BOOL = new Type();
  public static final Type INT = new Type();
  public static final Type BYTE = new Type();
  public static final Type COLOR = new Type();
  public static final Type BUTTON = new Type();
  public static final Type VOID = new Type();
  public static final Type TONE = new Type();
  public static final Type MAINCLASS = new Type();


  private Type(){}
  public Type(String name){
	  type = name;
	  otherTypes.put(type,this);
  }
  public static boolean hasType(String str){
    return otherTypes.containsKey(str);
  }
  public static Type getType(String typename){
    Type type = otherTypes.get(typename);
    if (type == null){
      type = new Type(typename);
      otherTypes.put(typename, type);
    }
    return type;
  }
  public String toString()
  {
    if(this == INT)     {return "INT";}
    if(this == BOOL)    {return "BOOL";}
    if(this == BYTE)    {return "BYTE";}
    if(this == COLOR)   {return "COLOR";}
    if(this == BUTTON)  {return "BUTTON";}
    if(this == TONE)    {return "TONE";}
    if(this == VOID)    {return "VOID";}
    if(type != null)    {return type;}
    return "MAINCLASS;";
  }

  public static Type parseType(String type) {
    switch(type){
      case ("INT"):     return INT;
      case ("BOOL"):    return BOOL;
      case ("BYTE"):    return BYTE;
      case ("COLOR"):   return COLOR;
      case ("BUTTON"):  return BUTTON;
      case ("TONE"):    return TONE;
    }
    if (otherTypes.containsKey(type)){
        return otherTypes.get(type);
    }
    return VOID;
  }
  
  public int getAVRTypeSize() {
      if (this == INT)     { return 2; }
      if (this == BOOL)    { return 1; }
      if (this == BYTE)    { return 1; }
      if (this == COLOR)   { return 1; }
      if (this == BUTTON)  { return 1; }
      if (this == VOID)    { return 0; }
      if (this == TONE)    { return 2; }
      return 2; 
  }

}
