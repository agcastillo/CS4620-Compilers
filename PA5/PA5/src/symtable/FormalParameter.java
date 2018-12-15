package symtable;
import java.util.*;
import ast.node.*;

public class FormalParameter{
    String mName;
    Type mType;
    public FormalParameter(String name, Type type){
        this.mName = name;
        this.mType = type;
    }
    public String getName(){
        return this.mName;
    }
    public void setName(String name){
        this.mName = name;
    }
    public Type getType(){
        return this.mType;
    }
    public void setType(Type type){
        this.mType = type;
    }
    public String toString(){
        return this.mName + " : " + this.mType.toString();
    }
}