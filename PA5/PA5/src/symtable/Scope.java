package symtable;
import java.util.*;
import java.io.PrintWriter;
import java.io.PrintStream;
import ast.node.*;

public class Scope{
    public static final Scope GLOBAL = new Scope();
	private final HashMap<String,STE> mDict = new HashMap<String,STE>();
    private Scope mEnclosing;
    private ScopeType mScopeType;
    private int mOffset;
    private String mName;
    
    private Scope(){
    	this.mEnclosing = null;
    	this.mScopeType = null;
    	this.mName = "GLOBAL";
    }
    
    public Scope(ScopeType type, String name){
        this.mEnclosing = null;
        this.mScopeType = type;
        this.mName = name;
        if (this.mScopeType == ScopeType.LOCAL){
            this.mOffset = 3;
        }
        else{
            this.mOffset = 0;
        }
    }

    public Scope(ScopeType type, String name, Scope parent){
        this.mEnclosing = parent;
        this.mScopeType = type;
        this.mName = name;
        if (this.mScopeType == ScopeType.LOCAL){
            this.mOffset = 3;
        }
        else{
            this.mOffset = 0;
        }
    }
    public STE lookup(String key){
    	return mDict.get(key);
    }
    
    public void insert(STE entry){
        mDict.put(entry.getName(), entry);
        
    }

	public ScopeType getScopeType() {
		return mScopeType;
	}

	public void setScopeType(ScopeType mScopeType) {
		this.mScopeType = mScopeType;
    }
    public void setOffset(int offset){
        this.mOffset = offset;
    }

	public int getOffset() {
		return this.mOffset;
	}
	
	public String getName(){
		return this.mName;
    }
    public HashMap<String,STE> getDict(){
        return this.mDict;
    }
    public Scope getEnclosing(){
        return this.mEnclosing;
    }

}