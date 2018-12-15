package ast_visitors;
import java.io.PrintWriter;
import java.util.Stack;
import exceptions.*;
import ast.visitor.*;
import ast.node.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import symtable.*;


/*
 1. Include mOffsets for VarSTE :- 
    - THIS will always be mOffsets 1 and 2
    - All other offsets start at 3
 2. Next step is type checking
 
*/
public class SymbolTableBuilder extends DepthFirstVisitor{
    private SymTable symbolTable;
    private int mOffset;
    private boolean inMethod;
    private ClassSTE currentClass;
    private MethodSTE currentMethod;
   // private int lastIndex;
    
    public SymbolTableBuilder(){
        this.symbolTable = new SymTable();
        this.mOffset = 0;
    }

    public SymTable getSymTable(){
        return this.symbolTable;
    }
    
    @Override
    public void inFormal(Formal node) { }

    @Override
    public void outFormal(Formal node) {
        String name = node.getName();
        if (this.symbolTable.lookup(name) != null){
            throw new SemanticException("Formal already exists",node.getLine(), node.getPos());
        }
        VarSTE vste = new VarSTE(node.getName(), 
                                this.symbolTable.getTypeData(node.getType()), 
                                "Y", 
                                this.mOffset);
        //this.mOffset += vste.getType().getAVRTypeSize();
        this.currentMethod.insertLocals(vste);
        this.currentMethod.insertFormals(vste);
        this.symbolTable.insert(vste);
        this.mOffset += vste.getType().getAVRTypeSize();
    }

    public void inMethodDecl(MethodDecl node){
        this.mOffset = 1;
        this.inMethod = true;
        String mName = node.getName();
        String signature = "(";
        String id = this.symbolTable.peekScope().getName();
        if(symbolTable.lookup(mName) != null){
            throw new SemanticException("Method with name " + node.getName() + " already exists");
        }
        for (Formal f : node.getFormals()){
			//this.symbolTable.setExpType(f, this.symbolTable.getTypeData(f.getType()));
			signature += this.getTypeData(f.getType())+", ";
    	}
        signature += ") returns " + this.symbolTable.getTypeData(node.getType());
        MethodSTE mste = new MethodSTE(mName, signature);
        this.symbolTable.insert(mste);
        //this.symbolTable.insertMethod(mste);

        
        mste.setScope(new Scope(ScopeType.LOCAL, mste.getName(), symbolTable.peekScope()));
        this.symbolTable.pushMethodScope(mste);
        VarSTE vste = new VarSTE(   "this", 
                                        Type.getType(this.currentClass.getName()), 
                                        "Y", 
                                        this.mOffset);
        this.mOffset += 2;
        //System.out.println("OFFSET = " + this.mOffset);
        mste.insertLocals(vste);
        /*
        for (Formal f : node.getFormals()){
			//this.symbolTable.setExpType(f, this.symbolTable.getTypeData(f.getType()));
            VarSTE v= new VarSTE(   f.getName(), 
                                        this.symbolTable.getTypeData(f.getType()), 
                                        "Y", 
                                        this.mOffset);
            mste.insertLocals(v);
            mste.insertFormals(v);
            this.symbolTable.insert(vste);
            this.mOffset += v.getType().getAVRTypeSize();
            //System.out.println("Offset for formal " + f.getName() + ": " + v.getOffset());
            //System.out.println("OFFSET = " + this.mOffset);

    	}
        */
        //this.symbolTable.insertMethod(mste);
        //this.mOffset += 2;
        this.symbolTable.insert(vste);



       // System.out.println("Added method to symbol table: " + mste.getName());
        this.currentMethod = mste;
    }
    public void outMethodDecl(MethodDecl node){
        this.symbolTable.insertMethod(this.currentMethod);
        symbolTable.popScope();
        this.mOffset = 0;
        this.inMethod = false;
    }
    @Override
    public void inVarDecl(VarDecl node) {}

    @Override
    public void outVarDecl(VarDecl node) {
        if (this.inMethod){
            //System.out.println("To " + this.currentMethod.getName() + " adding variable: " + node.getName());
            VarSTE vste = new VarSTE(   node.getName(), 
                                        this.getTypeData(node.getType()), 
                                        "Y", 
                                        this.mOffset);
            //System.out.println("Processing variable node: " + node.getName());
            //System.out.println("Node has type: " + Type.getType(node.getType().toString()));

            this.mOffset += vste.getType().getAVRTypeSize();
            this.symbolTable.insert(vste);   
            this.currentMethod.insertLocals(vste);
            //System.out.println("Offset for variable " + vste.getName() + ": " + vste.getOffset());
            //System.out.println("Size of variable = " + vste.getType().getAVRTypeSize());
        }

        else{
            //System.out.println("beginning class var decl for node: " + node.getName());
            VarSTE vste = new VarSTE(   node.getName(), 
                                        this.getTypeData(node.getType()), 
                                        "Z", 
                                        this.mOffset);
            
            //System.out.println("Processing node in VarDecl with Type: " + node.getType().toString());
            this.mOffset += vste.getType().getAVRTypeSize();
            this.symbolTable.insert(vste);
            this.symbolTable.setSize(this.symbolTable.getSize() + vste.getType().getAVRTypeSize());
            //System.out.println("Inserting variable to class: " + vste.getName());
            //System.out.println("Classname: " + this.currentClass.getName());
            this.currentClass.insertField(vste);
        }
         
        
    
    }
    
    public void inTopClassDecl(TopClassDecl node){
        this.mOffset = 0;
        //System.out.println("Proccessing class: " + node.getName());
        ClassSTE cste = new ClassSTE(   node.getName(),false, 
                                        new Scope(ScopeType.CLASS, node.getName(), 
                                        symbolTable.peekScope()));

        if(symbolTable.lookup(cste.getName()) != null){
            throw new SemanticException("Class name already in use");
        }
        else{
            symbolTable.insert(cste);
            symbolTable.pushClassScope(cste.getName());
            this.currentClass = cste;
            //this.mOffset = 3 ;
        }

      }
      
    public void outTopClassDecl(TopClassDecl node){
        symbolTable.insert(this.currentClass);
        //System.out.println(" CLASS OFFSET = " + this.mOffset);
        this.mOffset = 0;
        symbolTable.popScope();
        
    
    }



    

    public Type getTypeData(IType type){
        Type mType = null;
        if(type instanceof IntType){
            mType = Type.INT;
        }
        else if (type instanceof BoolType){
            mType = Type.BOOL;
        }
        else if (type instanceof ButtonType){
            mType = Type.BUTTON;
        }
        else if (type instanceof ByteType){
            mType = Type.BYTE;
        }
        else if (type instanceof ColorType){
            mType = Type.COLOR;
        }
        else if (type instanceof VoidType){
            mType = Type.VOID;
        }
        else if (type instanceof ToneType){
            mType = Type.TONE;
        }
        else{
            mType = Type.getType(((ClassType)type).getName());
        }
        return mType;
    }
    public void print(String s){
        //System.out.println(s);
    }
    
}