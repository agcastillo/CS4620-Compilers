package symtable;
import java.util.*;
import ast.node.*;

public class ClassSTE extends STE{
    private boolean isMain;
    private ClassSTE mSuperClass;
    private Scope mScope;
    private ArrayList<VarSTE> classFields;
    //int size;

    public ClassSTE(String name, boolean main, Scope scope){
        super(name);
        this.isMain = main;
        this.mScope = scope;
        this.mSuperClass = null;
        this.classFields = new ArrayList<VarSTE>();
        //this.size = 0;
    }

    public boolean getMain(){
        return this.isMain;
    }
    public void setMain(boolean main){
        this.isMain = main;
    }

    public Scope getScope(){
        return this.mScope;
    }
    public void setScope(Scope scope){
        this.mScope = scope;
    }
    public void insertField(VarSTE vste){
        this.classFields.add(vste);
    }
    public ArrayList<VarSTE> getFields(){
        return this.classFields;
    }
    /*
    public void setSize(int s){
        this.size = s;
    }
    public int getSize(){
        return this.size;
    }
    */
}