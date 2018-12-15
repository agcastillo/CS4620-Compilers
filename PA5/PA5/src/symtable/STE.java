package symtable;
import java.util.*;
import ast.node.*;

public class STE{
    
    protected String mName;

    public STE(String name){
        this.mName = name;
    }
    public String getName(){
        return this.mName;
    }
    public void setName(String name){
        this.mName = name;
    }

}