package symtable;
import java.util.*;
import ast.node.*;

public class MethodSTE extends STE{
    private String mSignature;
    private Scope mBase;
    private ArrayList<VarSTE> locals;
    private ArrayList<VarSTE> formals;

    public MethodSTE(String name, String sig) {
		super(name);
    this.mSignature = sig;
    this.locals = new ArrayList<VarSTE>();
    this.formals = new ArrayList<VarSTE>();
    }
    
    public void insertLocals(VarSTE vste){
      locals.add(vste);
    }
    public ArrayList<VarSTE> getLocals(){
      return this.locals;
    }
    public String getSignature()
    {
    	return this.mSignature;
    }
    
    public void setSignature(String s)
    {
    	this.mSignature = s;
    }
    
    public Scope getScope()
    {
    	return this.mBase;
    }
    
    public void setScope(Scope s)
    {
    	this.mBase = s;
    }
   public void insertFormals(VarSTE vste){
     this.formals.add(vste);
   }
   public ArrayList<VarSTE> getFormals(){
     return this.formals;
   }
    
}