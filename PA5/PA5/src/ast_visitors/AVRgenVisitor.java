package ast_visitors;
import ast.node.*;
import ast.visitor.*;
import symtable.*;
import java.util.*;
import java.io.*;
import java.util.Collection;



public class AVRgenVisitor extends DepthFirstVisitor{
  private int label;
  private SymTable mCurrentST;
  private PrintWriter out;

  public int offset;

  public AVRgenVisitor(PrintWriter pw, SymTable st){
    this.label = 0;
    this.out = pw;
    this.mCurrentST = st;
  }
/*
      print("tst  r18");
      print("brlt MJ_L" + this.label++);
      print("ldi  r19, 0");
      print("jmp  MJ_L" + this.label++);
      print("MJ_L" + (this.label-2) + ":");
      print("ldi  r19, hi8(-1)");
      print("MJ_L" + (this.label-1) + ":");
  */

  public void print(String str){
    //System.print(str);
    out.println(str);
  }

  @Override 
  public void inMainClass(MainClass node){}
  public void outMainClass(MainClass node){}

  @Override
  public void visitMainClass(MainClass node) {
    if(node.getStatement() != null)
    {
      node.getStatement().accept(this);
    }    
   
    inMainClass(node);
    /*
    PUSH SCOPE HERE?
    */
    outMainClass(node);

    //print("/* epilogue start */");
    //print("\tendLabel:");
    //print("\tjmp endLabel");
    //print("\tret");
    //print("\t.size   main, .-main");
    
  }

  @Override
  public void inTopClassDecl(TopClassDecl node) {
    print("");
    print("");
    print("# In topClassDecl");
    //System.out.println("Top class decl" + node.getName());
    this.mCurrentST.pushClassScope(node.getName());
  }
  @Override
  public void outTopClassDecl(TopClassDecl node) {
    print("#out top class decl");
    this.mCurrentST.popScope();;
    print("");
    print("");
  }


  

  // CHANGE
  @Override
  public void inMethodDecl(MethodDecl node) {
    print("");
    print("");
    print("# in Method decl");
    MethodSTE mste = (MethodSTE) this.mCurrentST.getMethod(node.getName());
    offset = 3;
    int register = 22;
    this.mCurrentST.pushMethodScope(mste.getName());
    String classname = this.mCurrentST.peekScope().getEnclosing().getName();
    
    print("/* prologuestart */");
    print("endLabel: ");
    print("jmp endLabel ");
    print("ret ");
    print(".size   main, .-main");
    print(".text");
    print(".global " + classname + "_"+node.getName());
    print(".type "+ classname +"_"+node.getName() +", @function");
    print(classname + "_" + node.getName() +":");
    print("push   r29");
    print("push   r28");
    print("# make space for locals and params");
    print("ldi    r30, 0");

    //Iterator iterator = mste.getScope().getDict().entrySet().iterator();
    
    for (VarSTE ste : mste.getLocals()){
      for (int i = 0; i < ste.getType().getAVRTypeSize(); i++){
        print(" push   r30 ");
      }
    }
      print(" # Copy stack pointer to frame pointer");
      print("in     r28,__SP_L__ ");
      print("in     r29,__SP_H__ ");

      print("std Y+1, r24");
      print("std Y+2, r25");
      //print("std Y + 2, r25");

      for (Formal ste : node.getFormals()){
        //if (ste.getName() != "this"){
          if (this.mCurrentST.getTypeData(ste.getType()).getAVRTypeSize()== 2){
              print("std Y+" + offset + ", r" + register);
              offset ++;
              print("std Y+" + offset + ", r" + (register+1));
              register -= 2;
              offset ++;
          }
          else if (this.mCurrentST.getTypeData(ste.getType()).getAVRTypeSize() == 1){
              print("std Y+" + offset + ", r" + register);
              offset ++;
              register -= 2;
          }
        //}
      }
      /*
      while(iterator.hasNext()){
        VarSTE vste = (VarSTE)mste.getScope().getDict().get((String) iterator.next());  //lookup((String)iterator.next());
        print("std Y + "+vste.getOffset()+", r"+ register);
        register = register -2;
      }
      */
      print("/* done with function s"+node.getName() +" prologue */");
      out.flush();
  }
  // CHANGE
  @Override
  public void outMethodDecl(MethodDecl node) {
    mCurrentST.popScope();
    MethodSTE method_ste = this.mCurrentST.getMethod(node.getName());
	  //MethodSTE method_ste = (MethodSTE)mCurrentST.lookup(this.mCurrentST.peekScope().getName() + "_"+ node.getName());
    //System.out.println(method_ste.getName());
    String classname = method_ste.getScope().getEnclosing().getName();
    
    Type type = this.mCurrentST.getTypeData(node.getType());
    
    if (type != Type.VOID){
      if (type.getAVRTypeSize() == 2){
        print("pop  r24");
        print("pop  r25");
      }
      else if (type.getAVRTypeSize() == 1){
        print("pop  r24");
      }
    }

    

    
    for (VarSTE ste : method_ste.getLocals()){
      for (int i = 0; i < ste.getType().getAVRTypeSize(); i++){
        print(" pop   r30 ");
      }
    }
    print("# restoring frame pointer");
    print("pop    r28");
    print("pop    r29");
    print("ret");
    print(".size "+ classname + "_"+node.getName() +", .- " + classname+"_"+node.getName());
    print("# out method decl");
    print("");
    print("");
  }

  @Override 
  public void inAndExp(AndExp node){}
  @Override 
  public void outAndExp(AndExp node){}

  @Override 
  public void visitAndExp(AndExp node){
    print("");
    print("");
    inAndExp(node);
    //String second = String(++this.label);
    //String first = String(++this.label);
    if (node.getLExp() != null){
      node.getLExp().accept(this);;
    }

    if(node.getRExp() != null){
      node.getRExp().accept(this);
    }
    print("# In and exp");
    print("\t# &&: if left operand is false do not eval right");
    print("\t# load a one byte expression off stack");
    print("\tpop    r24");
    print("\t# push one byte expression onto stack");
    print("\tpush   r24");
    print("\t# compare left exp with zero");
    print("\tldi r25, 0");
    print("\tcp    r24, r25");
    print("\t# Want this, breq MJ_L3");
    print("\tbrne  MJ_L" + this.label++);
    print("\tjmp   MJ_L" + this.label);
    print("MJ_L" + (this.label - 1) + ":");
    print("\t# right operand");
    print("\t# load a one byte expression off stack");
    print("\tpop    r24");
    int tmp = this.label++;
    print("MJ_L"+ tmp + ":");
    print("#out and exp");
    outAndExp(node);
    print("");
    print("");
  }

  public void outNewExp(NewExp node)
  {
    ClassSTE cste = (ClassSTE) this.mCurrentST.lookup(node.getId());
    int size = 0;
    //System.out.println("Was class found? " + (cste != null));
    for (VarSTE v : cste.getFields()){
      size += v.getType().getAVRTypeSize();
    }
    print("");
    print("");
     print("ldi r24, lo8("+size+")");
     print("ldi r25, hi8("+size+")");
     print("call  malloc");
     print("push  r25");
     print("push  r24");  
  
  }
  
  
  @Override 
  public void outAssignStatement(AssignStatement node){
    String name = node.getId();
    VarSTE vste = (VarSTE) this.mCurrentST.lookup(name);

    print("/* Begin assign statement */");
    //System.out.println("Variable exists? " + vste.getName());
    Type type = vste.getType();
    if (vste.getBase() == "Z"){
      print("ldd  r31, Y+2");
      print("ldd  r30, Y+1");
    }

    if (type.getAVRTypeSize() == 1){
        String offset = vste.getBase() + "+" + vste.getOffset();
        print("pop  r22");
        print("std  " + offset + ", r22");
      }
    else{
        String offset = vste.getBase() + "+" + (vste.getOffset() +1);
        print("pop  r22");
        print("pop  r23");
        
        print("std  " + offset + ", r23");
        offset = vste.getBase() + "+" + vste.getOffset();
        print("std  " + offset + ", r22");

      }
  
    print("/* End assign statement */");
  }
  @Override 
  public void inBlockStatement(BlockStatement node){}
  @Override 
  public void outBlockStatement(BlockStatement node){}


  @Override 
  public void inButtonExp(ButtonLiteral node){
    print("");
    print("");
    print("# in button exp");
    String button = node.getLexeme();
    button =button.substring(6).replace(".","_");
    print("lds   r24, " + button);
    print("#out button exp");

  }


  @Override
  public void outCallStatement(CallStatement node) {
    print("");
    print("");
    print("# in Call Statement");

    
    IExp expression = node.getExp();
    String id = node.getId();
    MethodSTE mste = this.mCurrentST.getMethod(node.getId());
    int size = 0;
    for (VarSTE vste : mste.getFormals()){
      if (vste.getName() != "this"){
        size += 1;
      }
    }
    //System.out.println("Size = " + size);
    int register = 24 - ((size)*2 );
    String classname = mste.getScope().getEnclosing().getName();
      ArrayList<VarSTE> vars = mste.getFormals();
      Collections.reverse(vars);
      for (VarSTE vste : vars){
        //System.out.println("r" + register + " gets: " + vste.getName());
        if (vste.getType().getAVRTypeSize() == 2){
          print("pop r"+register);
          register++;
          print("pop r"+register);
          register++;
        }
        else{
          print("pop r"+register);
          register += 2;
        
        }
      //System.out.println("Allocating space for: " + vste.getName());
      
      }
    print("pop  r24");
    print("pop  r25");
    print("call    "+classname + "_"+id);/*
    Type rType = this.mCurrentST.getExpType(node);
    if (rType == Type.VOID){}
    else if  (rType.getAVRTypeSize() == 1){
      print("push r24");
    }
    else{
			print("# push two byte expression onto stack");
			print("push	r25");
			print("push	r24");
    }
    */
  }


  @Override
  public void outCallExp(CallExp node) {
    print("");
    print("");
    print("# in Call Exp");

    
    IExp expression = node.getExp();
    String id = node.getId();
    MethodSTE mste = this.mCurrentST.getMethod(node.getId());
    int size = 0;
    for (VarSTE vste : mste.getFormals()){
      if (vste.getName() != "this"){
        size += 1;
      }
    }
    //System.out.println("Size = " + size);
    int register = 24 - ((size)*2 );
    String classname = mste.getScope().getEnclosing().getName();
      ArrayList<VarSTE> vars = mste.getFormals();
      Collections.reverse(vars);
      for (VarSTE vste : vars){
        //System.out.println("r" + register + " gets: " + vste.getName());
        if (vste.getType().getAVRTypeSize() == 2){
          print("pop r"+register);
          register++;
          print("pop r"+register);
          register++;
        }
        else{
          print("pop r"+register);
          register += 2;
        
        }
      }
    print("pop  r24");
    print("pop  r25");
    print("call    "+classname + "_"+id);
    Type rType = this.mCurrentST.getExpType(node);
    if (rType == Type.VOID){}
    else if  (rType.getAVRTypeSize() == 1){
      print("push r24");
    }
    else{
			print("# push two byte expression onto stack");
			print("push	r25");
			print("push	r24");
    }
    
    
  }



  @Override 
  public void outButtonExp(ButtonLiteral node){}

  @Override 
  public void inButtonType(ButtonType node){}
  @Override 
  public void outButtonType(ButtonType node){}
  @Override 
  public void inByteCast(ByteCast node){}
  @Override 
  public void outByteCast(ByteCast node){
    print("");
    print("");
    print("# in byte case");
    print("pop  r24");
    print("pop  r25");
    print("push r24"); 
    print("# out byte cast");

  }

  @Override
  public void inToneExp(ToneLiteral node) {
    print("");
    print("");
    print("# in tone exp");
    print("ldi  r25, hi8(" + node.getIntValue() + ")");
    print("ldi  r24, lo8(" + node.getIntValue() + ")");
    print("push r25");
    print("push r24");
    print("# out tone exp");

  }

  @Override 
  public void inColorExp(ColorLiteral node){
    print("");
    print("");
    print("# in color exp?");
    print("\tldi  r22," + node.getIntValue());
    print("\tpush r22");
    print("# out color exp");

  }
  @Override 
  public void outColorExp(ColorLiteral node){}
  
  @Override 
  public void inColorType(ColorType node){}
  
  @Override 
  public void outColorType(ColorType node){}
  @Override 
  public void inEqualExp(EqualExp node){}
/*
  
    print("tst  " + r1);
    print("brlt MJ_L" + this.label++);
    print("ldi  " + r2 + ", 0");
    print("jmp   MJ_L" + this.label++);
    print("MJ_L" + (this.label-2) + ":");
    print("ldi  " + r2 + ", hi8(-1)");
    print("MJ_L" + (this.label-1) + ":");
  }
  */
  @Override 
  public void outEqualExp(EqualExp node){
    print("");
    print("");
    print("# in equal exp");
    Type left = this.mCurrentST.getExpType(node.getLExp());
    boolean leftMatched = false;
    Type right = this.mCurrentST.getExpType(node.getRExp());
    boolean rightMatched = false;

    switch(right.toString()){
      case ("INT"): {
        print("pop  r18");
        print("pop  r19");
        rightMatched = true;
      }
      case ("BYTE"): {
        print("pop  r18");
        print("tst  r18");
        print("brlt MJ_L" + this.label++);
        print("ldi  r19, 0");
        print("jmp  MJ_L" + this.label++);
        print("MJ_L" + (this.label-2) + ":");
        print("ldi  r19, hi8(-1)");
        print("MJ_L" + (this.label-1) + ":");
        rightMatched = true;
      }
    }
    if (!rightMatched){
      print("pop  r18");
    }
    switch(left.toString()){
      case ("INT"): {
        print("pop  r24");
        print("pop  r25");
        print("cp r24, r18");
        print("cpc  r25, r19");
        leftMatched = true;
      }
      case ("BYTE"): {
        print("pop  r24");
        print("tst  r24");
        print("brlt MJ_L" + this.label++);
        print("ldi  r25, 0");
        print("jmp  MJ_L" + this.label++);
        print("MJ_L" + (this.label-2) + ":");
        print("ldi  r25, hi8(-1)");
        print("MJ_L" + (this.label-1) + ":");
        print("cp r24, r18");
        print("cpc  r25, r19");
        leftMatched = true;
      }
    }
    if (!leftMatched){
      print("pop  r24");
      print("cp r24, r18");
    }
    print("breq MJ_L" + (++this.label));
    print("MJ_L" + (this.label - 1) + ":");
    print("ldi  r24, 0");
    print("jmp  MJ_L" + (this.label + 1));
    print("MJ_L"+ this.label  + ":");
    print("ldi  r24, 1");
    print("MJ_L" + (this.label + 1) +":");
    print("push r24");
    this.label += 2;
    print("# out equal exp");

  }
  
  @Override
  public void inVarDecl(VarDecl node) {
    
  }

  @Override
  public void outVarDecl(VarDecl node) {
  }

  @Override
  public void outIdLiteral(IdLiteral node) {
    print("");
    print("");
    print("# in id literal");

    /*
    VarSTE var_ste = (VarSTE)mCurrentST.lookup(node.getLexeme());
        print("# IdExp0 \n  # load value for variable a 
        \n # variable is a local or param variable \n # load a one byte 
        variable from base+offset \n ldd    r24, Y + "+var_ste.mOffset+" \n 
        # push one byte expression onto stack \n push   r24");
    */
    VarSTE vste = (VarSTE) this.mCurrentST.lookup(node.getLexeme());
    //System.out.println("Offset for "+ vste.getName() + ": " + vste.getOffset());
    if (vste.getBase() == "Z"){
      print("ldd  r31, Y+2");
      print("ldd  r30, Y+1");
    }
    // THIS NEEDS TO BE CHANGED
    if (vste.getType().getAVRTypeSize() == 2){
      print("ldd  r21," + vste.getBase() + "+" + vste.getOffset());
      print("ldd  r22," + vste.getBase() + "+" + (vste.getOffset() + 1));
      print("push r21");
      print("push r22");
    }
    else if (vste.getType().getAVRTypeSize() == 1){
      System.out.println("Offset set as: " + vste.getOffset());
      print("ldd  r22, Y + "+ vste.getOffset());
      print("push r22");
    }
    print("");
    print("");
  }
  @Override 
  public void inFalseExp(FalseLiteral node){}

  @Override 
  public void outFalseExp(FalseLiteral node){
    print("");
    print("");
    print("# in false exp");
    print("ldi  r22, 0");
    print("push r22");
    print("# out false exp");

  }
  @Override 
  public void inIfStatement(IfStatement node){}
  
  @Override 
  public void visitIfStatement(IfStatement node){
    print("");
    print("");
    print("#in If stmt");
    inIfStatement(node);

    if(node.getExp() != null){
      node.getExp().accept(this);
    }
    print("pop  r24");
    print("ldi  r25, 0");
    print("cp r24, r25");
    print("#True goes to the first label");
    print("brne MJ_L" + this.label++);
    print("jmp  MJ_L" + this.label);
    print("# then Label for if");
    print("MJ_L" + (this.label-1) + ":");
    int templabel = this.label;
    this.label++;
    if(node.getThenStatement() != null){
      node.getThenStatement().accept(this);
    }
    print("jmp  MJ_L"+ this.label++);
    int endlabel = this.label-1;
    print("MJ_L" + templabel + ":");
    if(node.getElseStatement() != null){
      node.getElseStatement().accept(this);
    }
    print("MJ_L" + endlabel + ":");
    this.label++;

    outIfStatement(node);
    print("#out if stmt");
  
  }

  //note If statement and else are the same
  @Override 
  public void outIfStatement(IfStatement node){}
  
  @Override 
  public void inIntegerExp(IntLiteral node){}

  @Override 
  public void outIntegerExp(IntLiteral node){
    print("");
    print("");
    print("# in Integer exp");
    print("#Load constant int " + node.getIntValue());
    print("ldi  r24,lo8(" + node.getIntValue() + ")");
    print("ldi  r25,hi8(" + node.getIntValue() + ")");
    print("push r25");
    print("push r24");
    print("# out Integer exp");
  
  }

  @Override 
  public void inLtExp(LtExp node){}

  @Override 
  public void outLtExp(LtExp node){
    print("");
    print("");
    print("# in Lt exp");
    Type lExp = this.mCurrentST.getExpType(node.getLExp());
    Type rExp = this.mCurrentST.getExpType(node.getRExp());
    if(rExp==Type.BYTE){
      print("pop  r18");
      print("tst  r18");
      print("brlt MJ_L" + this.label++);
      print("ldi  r19, 0");
      print("jmp  MJ_L" + this.label++);
      print("MJ_L" + (this.label-2) + ":");
      print("ldi  r19, hi8(-1)");
      print("MJ_L" + (this.label-1) + ":");
      print("#End promoting Byte to Int");
    }
    else if(rExp == Type.INT){
      print("pop  r18");
      print("pop  r19");
    }
    else{
      print("pop  r18");
    }
    if(lExp==Type.BYTE){
      print("pop  r24");
      print("tst  r24");
      print("brlt MJ_L" + this.label++);
      print("ldi  r25, 0");
      print("jmp  MJ_L" + this.label++);
      print("MJ_L" + (this.label-2) + ":");
      print("ldi  r25, hi8(-1)");
      print("MJ_L" + (this.label-1) + ":");
      print("cp r24, r18");
      print("cpc  r25, r19");

    }
    else if(lExp == Type.INT){
      print("pop  r24");
      print("pop  r25");
      print("cp r24, r18");
      print("cpc  r25, r19");
    }
    else{
      print("pop  r24");
      print("cp r24, r18");
    }
    print("#Less than expression");
    print("brlt MJ_L" + (++this.label));
    print("# load false");
    print("MJ_L" + (this.label-1) + ":");
    print("ldi  r24, 0");
    print("jmp  MJ_L" + (this.label+1));
    print("#load true");
    print("MJ_L" + this.label++ + ":");
    print("ldi  r24, 1");
    print("MJ_L" + this.label++ + ":");
    print("push r24");
    print("# out lt exp");

  }
  
  @Override 
  public void inMeggyCheckButton(MeggyCheckButton node){
    print("");
    print("");
    print("# in meggy checkbutton");
    print("call _Z16CheckButtonsDownv");

   }

   
  @Override 
  public void outMeggyCheckButton(MeggyCheckButton node){

    print("");
    print("");
    print("# Of button value == zero, push 0 else push 1");
    print("# if button value is zero, push 0 else push 1");
    print("tst  r24");
    print("breq MJ_L" + ++this.label);
    print("MJ_L" + (this.label-1) + ":");
    print("ldi  r24, 1");
    print("jmp MJ_L" + ++this.label);
    print("MJ_L" + (this.label-1) + ":");
    print("MJ_L" + this.label++ + ":");
    print("push r24");
    print("# out meggy check button");

    
  }
  @Override 
  public void inMeggyDelay(MeggyDelay node){}

  @Override 
  public void outMeggyDelay(MeggyDelay node){
    print("");
    print("");
    print("# in meggy delay");
    print("pop  r24");
    print("pop  r25");
    print("call _Z8delay_msj");
    print("# out meggy delay");
  
  }
  @Override 
  public void inMeggyGetPixel(MeggyGetPixel node){}
  
  @Override 
  public void outMeggyGetPixel(MeggyGetPixel node){
    print("");
    print("");
    print("# in meggy get pixel");
    print("pop  r22");
    print("pop  r24");
    print("call _Z6ReadPxhh");
    print("push r24");
    print("# out meggy get pixel");
  
  }
  @Override 
  public void inMeggySetPixel(MeggySetPixel node){}

  @Override
  public void inNewExp(NewExp node) {}

  
  @Override 
  public void outMeggySetPixel(MeggySetPixel node){
    print("");
    print("");
    print("# in meggy set pixel");
    print("pop  r20");
    print("pop  r22");
    print("pop  r24");
    print("call _Z6DrawPxhhh");
    print("call _Z12DisplaySlatev");
    print("# out meggy set pixel");

  }
  @Override 
  public void inMinusExp(MinusExp node){}
  @Override 
  public void outMinusExp(MinusExp node){
    print("");
    print("");
    print("# in minus exp");
    Type lExp = this.mCurrentST.getExpType(node.getLExp());
    Type rExp = this.mCurrentST.getExpType(node.getRExp());
    if (rExp != Type.BYTE){
      print("pop  r18");
      print("pop  r19");
    }
    else{
      print("pop  r18");
      print("tst  r18");
      print("brlt MJ_L" + this.label++);
      print("ldi  r19, 0");
      print("jmp  MJ_L" + this.label++);
      print("MJ_L" + (this.label-2) + ":");
      print("ldi  r19, hi8(-1)");
      print("MJ_L" + (this.label-1) + ":");
    }
    if (lExp != Type.BYTE){
      print("pop  r24");
      print("pop  r25");
    }
    else{
      print("pop  r24");
      print("tst  r24");
      print("brlt MJ_L" + this.label++);
      print("ldi  r25, 0");
      print("jmp  MJ_L" + this.label++);
      print("MJ_L" + (this.label-2) + ":");
      print("ldi  r25, hi8(-1)");
      print("MJ_L" + (this.label-1) + ":");
    }
    print("sub  r24, r18");
    print("sbc  r25, r19");
    print("push r25");
    print("push r24"); 
    print("# out minus exp");
   
  }
  @Override 
  public void inMulExp(MulExp node){}
  @Override 
  public void outMulExp(MulExp node){
    print("");
    print("");
    print("# in mul exp");
    print("pop  r18");
    print("pop  r22");
    print("mov  r24, r18");
    print("mov  r26, r22");
    print("muls r24, r26");
    print("push r1");
    print("push r0");
    print("eor  r0, r0");
    print("eor  r1,r1");
    print("# out mul exp");
  
  }
  @Override 
  public void inNegExp(NegExp node){}

  @Override 
  public void outNegExp(NegExp node){
    print("");
    print("");
    print("# in neg exp");
    Type exp = this.mCurrentST.getExpType(node.getExp());
    switch(this.mCurrentST.getExpType(node.getExp()).toString()){
      case("BYTE"):{
        print("pop  r24");
        print("tst  r24");
        print("brlt MJ_L" + this.label++);
        print("ldi  r25, 0");
        print("jmp  MJ_L" + this.label++);
        print("MJ_L" + (this.label-2) + ":");
        print("ldi  r25, hi8(-1)");
        print("MJ_L" + (this.label-1) + ":");
      }
      case("INT"):{
        print("pop  r24");
        print("pop  r25");
      }
    }
    print("ldi  r22, 0");
    print("ldi  r23, 0");
    print("sub  r22, r24");
    print("sbc  r23, r25");
    print("push r23");
    print("push r22");
    print("# out neg exp");
  }
  @Override 
  public void inNotExp(NotExp node){}

  @Override 
  public void outNotExp(NotExp node){
    print("");
    print("");
    print("# in not exp");
    print("pop  r24");
    print("ldi  r22, 1");
    print("eor  r24,r22");
    print("push r24");
    print("# out not exp");
  }

  @Override 
  public void inPlusExp(PlusExp node){}

  @Override 
  public void outPlusExp(PlusExp node){
    print("");
    print("");
    print("# in plus exp");
    Type lExp = this.mCurrentST.getExpType(node.getLExp());
    Type rExp = this.mCurrentST.getExpType(node.getRExp());
    if(rExp!=Type.BYTE){
      print("pop r18");
      print("pop r19");
    }
    else{
      print("pop r18");
      print("tst  r18");
      print("brlt MJ_L" + this.label++);
      print("ldi  r19, 0");
      print("jmp  MJ_L" + this.label++);
      print("MJ_L" + (this.label-2) + ":");
      print("ldi  r19, hi8(-1)");
      print("MJ_L" + (this.label-1) + ":");
    }
    if(lExp!=Type.BYTE){
      print("pop r24");
      print("pop r25");
    }
    else{
      print("pop r24");
      print("tst  r24");
      print("brlt MJ_L" + this.label++);
      print("ldi  r25, 0");
      print("jmp  MJ_L" + this.label++);
      print("MJ_L" + (this.label-2) + ":");
      print("ldi  r25, hi8(-1)");
      print("MJ_L" + (this.label-1) + ":");
    }
    print("add r24, r18");
    print("adc r25, r19");
    print("push  r25");
    print("push  r24");
    print("# out plus exp");
    print("");
    print("");
  }

  @Override
  public void inThisExp(ThisLiteral node) {
    print("");
    print("");
    print("# in this exp");
    print("ldd  r25, Y + 2");
    print("ldd  r24, Y + 1");
    print("push r25");
    print("push r24"); 
    print("# out this exp");
  }
  @Override
  public void outThisExp(ThisLiteral node) {}

  @Override 
  public void inProgram(Program node){
    print( "\t.file  \"main.java\"");
    print("__SREG__ = 0x3f");
    print("__SP_H__ = 0x3e");
    print("__SP_L__ = 0x3d");
    print("__tmp_reg__ = 0");
    print("__zero_reg__ = 1");
    print("\t.global __do_copy_data");
    print("\t.global __do_clear_bss");
    print("\t.text");
    print(".global main");
    print("\t.type   main, @function");
    print("main:");
    print("\tpush r29");
    print("\tpush r28");
    print("\tin r28,__SP_L__");
    print("\tin r29,__SP_H__");
    print("\t/* prologue: function */");
    print("\tcall _Z18MeggyJrSimpleSetupv");

  }
  @Override 
  public void outProgram(Program node){
    print("");
    print("");
    
    print("/* epilogue start */");
    print("\tendLabel:");
    print("\tjmp endLabel");
    print("\tret");
    print("\t.size   main, .-main");
    out.flush();
    print("# out program");
  }

 
  @Override 
  public void inTrueExp(TrueLiteral node){}
  @Override 
  public void outTrueExp(TrueLiteral node){
    print("");
    print("");
    print("# in true exp");
    print("ldi \t r22, 1");
    print("push \t r22");
    print("# out true exp");
  }

  

  @Override
  public void visitWhileStatement(WhileStatement node){
    print("");
    print("");
    print("# in while stmt");
    int first = this.label++;
    print("MJ_L" + (first)+ ":");
    if(node.getExp() != null){
      node.getExp().accept(this);
    }
    print("pop  r24");
    print("ldi  r25, 0");
    print("cp r24, r25");
    print("brne MJ_L" + this.label);
    print("jmp  MJ_L" + ++this.label);
    int end = this.label++;
    print("MJ_L" + (this.label-2)+ ":");
    if(node.getStatement() != null){
      node.getStatement().accept(this);
    }
    print("jmp  MJ_L" + first);
    print("MJ_L" + end + ":");
    outWhileStatement(node);
    print("# out while stmt");
  }
  @Override 
  public void outWhileStatement(WhileStatement node){}

}
