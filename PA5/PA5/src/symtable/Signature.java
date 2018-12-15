package symtable;
import java.util.*;
import ast.node.*;

public class Signature{
    
    ArrayList<FormalParameter> mParameters;
    Type mReturnType;

    public Signature(ArrayList<FormalParameter> parameters, Type returnType){
        this.mParameters = parameters;
        this.mReturnType = returnType;
    }
    public String toString(){
        String ret = "";
        ret += "Signature::\nReturn Type: " + this.mReturnType.toString() + "\n";
        ret += "Formal Parameters: ";
        for (FormalParameter param : this.mParameters){
            ret += param.toString() + "\n";
        }
        return ret;
    }

    public Type getReturnType(){
        return this.mReturnType;
    }
    public ArrayList<FormalParameter> getParameters(){
        return this.mParameters;
    }

}