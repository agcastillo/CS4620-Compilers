	/**
	 * We extend the DepthFirstAdapter.  
	 * Visitors invoke a defaultCase method on each node they visit.  
	 * We override this method so that it
	 * prints out dot info about a node.
	 */


	/**
	 * This ast walker generates AVR output for the AST.  
	 */

	package ast_visitors;

	import ast.node.*;
	import ast.visitor.*;
	import symtable.SymTable;
	import symtable.Type;
	
	import java.util.*;
	import java.io.*;

	public class AVRgenVisitor extends DepthFirstVisitor
	{
	private int label;
	private SymTable symbolTable;
	private PrintWriter out;

	public AVRgenVisitor(PrintWriter printWriter, SymTable symbolTable)
	{
		this.label = 0;
		this.out = printWriter;
		this.symbolTable = symbolTable;
	}

	private void output(String str){
		out.println(str);
	}

	private void promoteByteToInt(String r1, String r2){
		output("tst \t" + r1);
		output("brlt \t MJ_L" + label++);
		output("ldi \t" + r2 + ", 0");
		output("jmp \t MJ_L" + label++);
		output("MJ_L" + (label-2) + ":");
		output("ldi \t" + r2 + ", hi8(-1)");
		output("MJ_L" + (label-1) + ":");
		output("#End promoting Byte to Int");
		}
		
	@Override 
	public void inAndExp(AndExp node){
	
	}

	@Override 
	public void visitAndExp(AndExp node){
		inAndExp(node);
		if (node.getLExp() != null){ // What is this doing?
			node.getLExp().accept(this);
		}
		output("\n### Left hand expr then right hand expr");
		output("\npop \t r24");
		output("\npush \t r24");
		output("\nldi \t r25, 0");
		output("\ncp \t r24, r25");
		output("\nbrne \t MJ_L" + label++);
		output("\njmp \t MJ_L" + label);
		output("\nMJ_L" + (label - 1) + ":");
		output("pop \t r24");
		if(node.getRExp() != null){
		node.getRExp().accept(this);
		}
		int tempLabel = label++;
		output("MJ_L"+ tempLabel + ":");
		outAndExp(node);
	}


	
	@Override 
	public void outAndExp(AndExp node){

	}
	@Override 
	public void inAssignStatement(AssignStatement node){

	}
	@Override 
	public void outAssignStatement(AssignStatement node){

	}
	@Override 
	public void inBlockStatement(BlockStatement node){

	}

	@Override 
	public void outBlockStatement(BlockStatement node){

	}
	@Override 
	public void inButtonExp(ButtonLiteral node){
		String button = node.getLexeme();
		if (button.equals("Meggy.Button.A")){
			button = "Button_A";
		}
		else if (button.equals("Meggy.Button.B")){
			button = "Button_B";
		}
		else if (button.equals("Meggy.Button.Up")){
			button = "Button_Up";
		}
		else if (button.equals("Meggy.Button.Down")){
			button = "Button_Down";
		}
		else if (button.equals("Meggy.Button.Left")){
			button = "Button_Left";
		}
		else if (button.equals("Meggy.Button.Right")){
			button = "Button_Right";
		}
		output("\nlds \t r24, " + button);
	}
	@Override 
	public void outButtonExp(ButtonLiteral node){

	}
	@Override 
	public void inButtonType(ButtonType node){
		
	}
	@Override 
	public void outButtonType(ButtonType node){

	}
	@Override 
	public void inByteCast(ByteCast node){

	}
	@Override 
	public void outByteCast(ByteCast node){
		output("### Byte Cast Operation");
		output("pop \t r24");
		output("pop \t r25");
		output("push \t r24");
	}

	@Override 
	public void inColorExp(ColorLiteral node){
		output("### Color expression");
		output("ldi \t r22, " + node.getIntValue());
		output("push \t r22");
	}
	@Override 
	public void outColorExp(ColorLiteral node){

	}
	@Override 
	public void inColorType(ColorType node){

	}
	@Override 
	public void outColorType(ColorType node){

	}
	@Override 
	public void inEqualExp(EqualExp node){

	}
	@Override 
	public void outEqualExp(EqualExp node){
		Type lExpression = this.symbolTable.getExpType(node.getLExp());
		Type rExpression = this.symbolTable.getExpType(node.getRExp());
		if(rExpression == Type.BYTE){
			output("pop \t r18");
			promoteByteToInt("r18","r19");
		  }
		else if(rExpression == Type.INT){
			output("pop \t r18");
			output("pop \t r19");
		}
		else{
			output("pop \t r18");
		}
		if(lExpression == Type.BYTE){
			output("pop \t r24");
			promoteByteToInt("r24","r25");
			output("cp r24, r18");
			output("cpc \t r25, r19");
		
		}
		else if(lExpression == Type.INT){
			output("pop \t r24");
			output("pop \t r25");
			output("cp r24, r18");
			output("cpc \t r25, r19");
		}
		else{//Color and BOOl are the same                                                                                                                                              
			output("pop \t r24");
			output("cp r24, r18");
		}
		output("breq MJ_L" + ++label);
		output("\n");
		output("MJ_L" + (label-1) + ":");
		output("ldi \t r24, 0");
		output("jmp \t MJ_L" + (label+1));
		output("\nMJ_L"+ label + ":");
		output("ldi \t r24, 1");
		output("\nMJ_L" + (label+1) +":");
		output("push \t r24");
		label+=2;
	}
	@Override 
	public void inFalseExp(FalseLiteral node){

	}
	@Override 
	public void outFalseExp(FalseLiteral node){
		output("ldi \t r22, 0");
		output("push \t r22");
	}
	@Override 
	public void inIfStatement(IfStatement node){
		output("###In if Statement");
	}
	@Override 
	public void visitIfStatement(IfStatement node){
		inIfStatement(node);
		if (node.getExp() != null){
			node.getExp().accept(this);
		}
		output("pop \t r24");
		output("ldi \t r25, 0");
		output("cp \t r24,r25");
		output("### True goes to the first label");
		output("brne \t MJ_L" + label++);
		output("jmp \t MJ_L" + label);
		output("### Then label for if statement");
		output("MJ_L" + (label - 1) + ":");
		int tempLabel = label;
    	label++;
    	if(node.getThenStatement() != null){
      		node.getThenStatement().accept(this);
    	}
    	output("jmp \t MJ_L"+ label++);
    	int endLabel = label-1;
    	output("MJ_L" + tempLabel + ":");
    	if(node.getElseStatement() != null){
     		node.getElseStatement().accept(this);
   		}
    	output("MJ_L" + endLabel + ":");
    	label++;
    	outIfStatement(node);
	}

	//note If statement and else are the same
	@Override 
	public void outIfStatement(IfStatement node){
		output("### End of If Statement");
	}
	
	@Override 
	public void inIntegerExp(IntLiteral node){

	}
	@Override 
	public void outIntegerExp(IntLiteral node){
		output("### Load constant int " + node.getIntValue());
		output("ldi \t r24, lo8(" + node.getIntValue() + ")");
		output("ldi \t r25, hi8(" + node.getIntValue() + ")");
		output("push \t r25");
		output("push \t r24");
	}
	@Override 
	public void inLtExp(LtExp node){

	}
	//This actually wasn't until PA4... o well
	@Override 
	public void outLtExp(LtExp node){
	
	}
	@Override 
	public void inMainClass(MainClass node){

	}
	//both the above and below need to be implemented to output prolouge and epilouge respectivley
	public void outMainClass(MainClass node){

	}
	@Override 
	public void inMeggyCheckButton(MeggyCheckButton node){
		output("call\t _Z16CheckButtonsDownv");
	}
	@Override 
	public void outMeggyCheckButton(MeggyCheckButton node){
		output("# if button value is zero, push 0 else push 1");
		output("tst \t r24");
		output("breq \t MJ_L" + ++label);
		output("MJ_L" + (label-1) + ":");
		output("ldi \t r24, 1");
		output("jmp MJ_L" + ++label);
		output("MJ_L" + (label-1) + ":");
		output("MJ_L" + label++ + ":");
		output("push \t r24");
	}
	@Override 
	public void inMeggyDelay(MeggyDelay node){

	}
	@Override 
	public void outMeggyDelay(MeggyDelay node){
		output("pop \t r24");
		output("pop \t r25");
		output("call \t _Z8delay_msj");
	}
	@Override 
	public void inMeggyGetPixel(MeggyGetPixel node){

	}
	@Override 
	public void outMeggyGetPixel(MeggyGetPixel node){
		output("pop \t r22");
		output("pop \t r24");
		output("call _Z6ReadPxhh");
		output("push \t r24");
	}
	@Override 
	public void inMeggySetPixel(MeggySetPixel node){

	}
	@Override 
	public void outMeggySetPixel(MeggySetPixel node){
		output("#Load bytes");
		output("pop \t r20");
		output("pop \t r22");
		output("pop \t r24");
		output("call _Z6DrawPxhhh");
		output("call _Z12DisplaySlatev");
	}
	@Override 
	public void inMinusExp(MinusExp node){
	
	}
	@Override 
	public void outMinusExp(MinusExp node){
		Type lExp = this.symbolTable.getExpType(node.getLExp());
		Type rExp = this.symbolTable.getExpType(node.getRExp());
		if(rExp == Type.BYTE){
			output("pop \t r18");
			promoteByteToInt("r18","r19");
		}
		else{
			output("pop \t r18");
			output("pop \t r19");
		}
		if(lExp == Type.BYTE){
			output("pop \t r24");
			promoteByteToInt("r24","r25");
		}
		else{
			output("pop \t r24");
			output("pop \t r25");
		}
		output("sub \t r24, r18");
		output("sbc \t r25, r19");
		output("push \t r25");
		output("push \t r24");
	}
	@Override 
	public void inMulExp(MulExp node){

	}
	@Override 
	public void outMulExp(MulExp node){
		output("pop \t r18");
		output("pop \t r22");
		output("mov \t r24, r18");
		output("mov \t r26, r22");
		output("muls \t r24, r26");
		output("push \t r1");
		output("push \t r0");
		output("eor \t r0, r0");
		output("eor \t r1,r1");

	}
	@Override 
	public void inNegExp(NegExp node){

	}
	@Override 
	public void outNegExp(NegExp node){
		Type exp = this.symbolTable.getExpType(node.getExp());
		if(exp==Type.BYTE){
			output("pop \t r24");
			promoteByteToInt("r24","r25");
		}
		else if(exp == Type.INT){
			output("pop \t r24");
			output("pop \t r25");
		}
		output("ldi \t r22, 0");
		output("ldi \t r23, 0");
		output("sub \t r22, r24");
		output("sbc \t r23, r25");
		output("push \t r23");
		output("push \t r22");
	}
	@Override 
	public void inNotExp(NotExp node){

	}
	@Override 
	public void outNotExp(NotExp node){
		output("pop \t r24");
		output("ldi \t r22, 1");
		output("eor \t r24,r22");
		output("push \t r24");
	}
	@Override 
	public void inPlusExp(PlusExp node){

	}
	@Override 
	public void outPlusExp(PlusExp node){
		Type lExp = this.symbolTable.getExpType(node.getLExp());
		Type rExp = this.symbolTable.getExpType(node.getRExp());
		if(rExp==Type.BYTE){
			output("pop \t r18");
			promoteByteToInt("r18","r19");
		}
		else{
			output("pop \t r18");
			output("pop \t r19");
		}
		if(lExp==Type.BYTE){
			output("pop \t r24");
			promoteByteToInt("r24","r25");
		}
		else{
			output("pop \t r24");
			output("pop \t r25");
		}
		output("# finished popping");
		output("add \t r24, r18");
		output("adc \t r25, r19");
		output("push \t r25");
		output("push \t r24");
	}


	@Override 
	public void inTrueExp(TrueLiteral node){

	}
	@Override 
	public void outTrueExp(TrueLiteral node){
		output("ldi \t r22, 1");
   	 	output("push \t r22");
	}
	public void inWhileStatement(WhileStatement node,int num){
		output("MJ_L" + num + ":");
    	output("\n");
	}

	@Override
	public void visitWhileStatement(WhileStatement node){
		int firstNum = label++;
		inWhileStatement(node, firstNum);
		if(node.getExp() != null){
		node.getExp().accept(this);
		}
		output("pop \t r24");
		output("ldi \t r25, 0");
		output("cp \t r24, r25");
		output("brne \t MJ_L" + label);
		output("jmp \t MJ_L" + ++label);
		int endLoop = label++;
		output("\n\nMJ_L" + (label-2)+ ":");
		if(node.getStatement() != null){
		node.getStatement().accept(this);
		}
		output("jmp \t MJ_L" + firstNum);
		output("\n\nMJ_L" + endLoop + ":");
		outWhileStatement(node);

	}
	@Override 
	public void outWhileStatement(WhileStatement node){
	
	}


	@Override 
	public void inProgram(Program node){
	InputStream mainPrologue = null;
	BufferedReader reader = null;
	try{
		mainPrologue = this.getClass()
		.getClassLoader()
		.getResourceAsStream( "avrH.rtl.s");
		reader = new BufferedReader(new InputStreamReader(mainPrologue));

		String line = null;
		while((line = reader.readLine()) != null){
		out.println(line);
		}
	}catch(Exception e2){
		e2.printStackTrace();

	}finally{
		try{
		if(mainPrologue != null) mainPrologue.close();
		if(reader!=null)reader.close();
		}catch(IOException e){
		e.printStackTrace();
		}
	}
	}

	@Override 
	public void outProgram(Program node){
		InputStream mainEpilogue =null;
		BufferedReader reader=null;
		try{
			mainEpilogue = this.getClass()
			.getClassLoader()
			.getResourceAsStream("avrF.rtl.s");
			reader = new BufferedReader(new InputStreamReader(mainEpilogue));

			String line = null;
			while((line = reader.readLine()) != null){
			out.println(line);
			}
		} catch( Exception e2){
			e2.printStackTrace();
		}finally{
			try{
			if(mainEpilogue != null) mainEpilogue.close();
			if(reader!=null) reader.close();
			}catch(IOException e){
			e.printStackTrace();
			}

		out.flush();
		out.close();  
			}
		}
	}