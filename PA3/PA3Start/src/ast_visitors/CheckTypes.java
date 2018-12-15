package ast_visitors;

/** 
 * CheckTypes
 * 
 * This AST visitor traverses a MiniJava Abstract Syntax Tree and checks
 * for a number of type errors.  If a type error is found a SymanticException
 * is thrown
 * 
 * CHANGES to make next year (2012)
 *  - make the error messages between *, +, and - consistent <= ??
 *
 * Bring down the symtab code so that it only does get and set Type
 *  for expressions
 */

import ast.node.*;
import ast.visitor.DepthFirstVisitor;
import java.util.*;

import symtable.MethodSTE;
import symtable.STE;
import symtable.Scope;
import symtable.SymTable;
import symtable.Type;
import symtable.VarSTE;
import symtable.VarScope;
import exceptions.InternalException;
import exceptions.SemanticException;

public class CheckTypes extends DepthFirstVisitor{
    
    private SymTable symTable;
    
    public CheckTypes(SymTable st){
      if(st==null) {
           throw new InternalException("unexpected null argument");
       }
       symTable = st;
    }
   
    //========================= Overriding the visitor interface

    public void defaultOut(Node node){
        System.err.println("Node not implemented in CheckTypes, " + node.getClass());
    }
   
    public void outAndExp(AndExp node){
      if(this.symTable.getExpType(node.getLExp()) != Type.BOOL) {
        throw new SemanticException(
          "Invalid left operand type for operator &&",
          node.getLExp().getLine(), node.getLExp().getPos());
      } 
      if(this.symTable.getExpType(node.getRExp()) != Type.BOOL) {
        throw new SemanticException(
          "Invalid right operand type for operator &&",
          node.getRExp().getLine(), node.getRExp().getPos());
      } 
      this.symTable.setExpType(node, Type.BOOL);
    }
   
    public void outPlusExp(PlusExp node){
        Type lexpType = this.symTable.getExpType(node.getLExp());
        Type rexpType = this.symTable.getExpType(node.getRExp());
//        System.out.println("my types are: "+lexpType+" and "+rexpType); //debug
        if ((lexpType==Type.INT  || lexpType==Type.BYTE) &&
            (rexpType==Type.INT  || rexpType==Type.BYTE)
           ){
            this.symTable.setExpType(node, Type.INT);
        } 
        else {
            throw new SemanticException(
                    "Operands to + operator must be INT or BYTE",
                    node.getLExp().getLine(),
                    node.getLExp().getPos());
        }
    }

    public void outMinusExp(MinusExp node){
        Type lexpType = this.symTable.getExpType(node.getLExp());
        Type rexpType = this.symTable.getExpType(node.getRExp());
        if ((lexpType==Type.INT  || lexpType==Type.BYTE) &&
            (rexpType==Type.INT  || rexpType==Type.BYTE)
           ){
            this.symTable.setExpType(node, Type.INT);
        } 
        else {
            throw new SemanticException(
                    "Operands to - operator must be INT or BYTE",
                    node.getLExp().getLine(),
                    node.getLExp().getPos());
        }
    }

    public void outMulExp(MulExp node){
		if(this.symTable.getExpType(node.getLExp()) != Type.BYTE) {
			throw new SemanticException(
				"Invalid left operand type for operator *, must be BYTE.",
				node.getLExp().getLine(), node.getLExp().getPos());
		} 
		if(this.symTable.getExpType(node.getRExp()) != Type.BYTE) {
			throw new SemanticException(
				"Invalid right operand type for operator *, must be BYTE.",
				node.getRExp().getLine(), node.getRExp().getPos());
		} 
			this.symTable.setExpType(node, Type.INT);
    }

    public void outNegExp(NegExp node){
        Type expType = this.symTable.getExpType(node.getExp());
        if ((expType==Type.INT  || expType==Type.BYTE)){
            this.symTable.setExpType(node, Type.INT);
        } 
        else {
            throw new SemanticException(
                    "Operands to negation operator must be INT or BYTE",
                    node.getExp().getLine(),
                    node.getExp().getPos());
        }
    }

    public void outNotExp(NotExp node){
        if(this.symTable.getExpType(node.getExp()) == Type.BOOL){
			this.symTable.setExpType(node, Type.BOOL);
        } 
        else {
        	throw new SemanticException(
            	"Invalid operand type for operator '!': "+symTable.getExpType(node.getExp()),
            	node.getExp().getLine(), node.getExp().getPos());
        }
    }

    public void outEqualExp(EqualExp node){
        Type lexpType = this.symTable.getExpType(node.getLExp());
        Type rexpType = this.symTable.getExpType(node.getRExp());
        if ((lexpType==Type.INT  || lexpType==Type.BYTE) &&
            (rexpType==Type.INT  || rexpType==Type.BYTE)){
				this.symTable.setExpType(node, Type.BOOL);
        } 
        else if (lexpType==Type.BOOL && rexpType==Type.BOOL){
            this.symTable.setExpType(node, Type.BOOL);
        } 
        else if (lexpType==Type.BUTTON && rexpType==Type.BUTTON){
            this.symTable.setExpType(node, Type.BOOL);
        } 
        else if (lexpType==Type.COLOR && rexpType==Type.COLOR){
            this.symTable.setExpType(node, Type.BOOL);
        } 
        else if (lexpType == rexpType){
            this.symTable.setExpType(node, Type.BOOL);
        } 
        else {		
            throw new SemanticException(
				"Operands to == operator must be the same type.",
				node.getLExp().getLine(),node.getLExp().getPos());
        }
    }

    public void outByteCast(ByteCast node){
        Type expType = this.symTable.getExpType(node.getExp());
        if (expType==Type.INT  || expType==Type.BYTE){
            this.symTable.setExpType(node, Type.BYTE);
        } else {
            throw new SemanticException(
				"Operand to byte cast must be INT or BYTE",
				node.getExp().getLine(), node.getExp().getPos());
        }
    }

    public void outIntegerExp(IntLiteral node){
        this.symTable.setExpType(node, Type.INT);
    }

    public void outButtonExp(ButtonLiteral node){
        this.symTable.setExpType(node, Type.BUTTON);
    }

    public void outColorExp(ColorLiteral node){
        this.symTable.setExpType(node, Type.COLOR);
    }
    
    @Override
    public void outToneExp(ToneLiteral node){
    	this.symTable.setExpType(node, Type.TONE);
    }

    public void outFalseExp(FalseLiteral node){
        this.symTable.setExpType(node, Type.BOOL);
    }

    public void outTrueExp(TrueLiteral node){
        this.symTable.setExpType(node, Type.BOOL);
    }

    public void outWhileStatement(WhileStatement node){
        if (this.symTable.getExpType(node.getExp())!=Type.BOOL){
            throw new SemanticException(
				"Expression in While statement must be of type BOOL.",
				node.getExp().getLine(), node.getExp().getPos());
        }
    }

    public void outIfStatement(IfStatement node){
//		System.out.println("type = "+this.mCurrentST.getExpType(node.getExp()));
        if (this.symTable.getExpType(node.getExp())!=Type.BOOL){
            throw new SemanticException(
				"Expression in If statement must be of type BOOL.",
				node.getExp().getLine(), node.getExp().getPos());
        }
    }

    public void outMeggyCheckButton(MeggyCheckButton node){
        Type expType = this.symTable.getExpType(node.getExp());
		if(expType != Type.BUTTON) {
        	throw new SemanticException(
				"Invalid param for MeggyCheckButton. Must be of type BUTTON.",
				node.getExp().getLine(), node.getExp().getPos());
		} else {
			this.symTable.setExpType(node, Type.BOOL);
		}
    }

    public void outMeggyDelay(MeggyDelay node){
        Type expType = this.symTable.getExpType(node.getExp());
		if(expType != Type.INT) {
        	throw new SemanticException(
				"Invalid param for MeggyDelay. Must be of type INT.",
				node.getExp().getLine(), node.getExp().getPos());
		}
    }

    public void outMeggyGetPixel(MeggyGetPixel node){
        Type xExpType = this.symTable.getExpType(node.getXExp());
        Type yExpType = this.symTable.getExpType(node.getYExp());
		if(xExpType != Type.BYTE) {
        	throw new SemanticException(
				"Invalid first param for MeggyGetPixel. Must be of type BYTE.",
				node.getXExp().getLine(), node.getXExp().getPos());
          } 
        else if(yExpType != Type.BYTE) {
        	throw new SemanticException(
				"Invalid second param for MeggyGetPixel. Must be of type BYTE.",
				node.getYExp().getLine(), node.getYExp().getPos());
          } 
        else {
			this.symTable.setExpType(node, Type.COLOR);
		}
    }

    public void outMeggySetPixel(MeggySetPixel node){
        Type xExpType = this.symTable.getExpType(node.getXExp());
        Type yExpType = this.symTable.getExpType(node.getYExp());
        Type colorType = this.symTable.getExpType(node.getColor());
		if(xExpType != Type.BYTE) {
        	throw new SemanticException(
				"Invalid first param for MeggySetPixel. Must be of type BYTE, but found type "+node.getXExp(),
				node.getXExp().getLine(), node.getXExp().getPos());
          } 
        else if(yExpType != Type.BYTE) {
        	throw new SemanticException(
				"Invalid second param for MeggySetPixel. Must be of type BYTE, but found type "+yExpType,
				node.getYExp().getLine(), node.getYExp().getPos());
          } 
        else if(colorType != Type.COLOR) {
        	throw new SemanticException(
				"Invalid third param for MeggySetPixel. Must be of type COLOR, but found type "+colorType,
				node.getColor().getLine(), node.getColor().getPos());
      	} 
    }

    @Override
    public void outMeggyToneStart(MeggyToneStart node){
        Type toneType = this.symTable.getExpType(node.getToneExp());
        Type durType = this.symTable.getExpType(node.getDurationExp());
		if(toneType != Type.TONE) {
        	throw new SemanticException(
				"Invalid first param for MeggyToneStart. Must be of type TONE.",
				node.getToneExp().getLine(), node.getToneExp().getPos());
          } 
        else if(durType != Type.INT) {
        	throw new SemanticException(
				"Invalid second param for MeggyToneStart. Must be of type INT.",
				node.getDurationExp().getLine(), node.getDurationExp().getPos());
      	}
    }
    
    @Override
    public void inTopClassDecl(TopClassDecl node)
    {

    	String className  = node.getName();
		Scope parentScope = symTable.peekScope();
		assert (parentScope == Scope.GLOBAL);
		try {
			symTable.pushClassScope(className);
		} catch (ClassCastException e){
			throw new SemanticException(
					"Cannot declare class "+node.getName()+"; identifier is already\n" +
					"used as method name",
					node.getLine(),node.getPos());
		}
    }

    @Override
    public void outTopClassDecl(TopClassDecl node){
    	symTable.popScope();
    }
    
    @Override
    public void inMethodDecl(MethodDecl node){
//    	System.out.println("Current scope = "+symTable.peekScope().getName()); //debug
    	try {
        	this.symTable.pushMethodScope(symTable.generateMethodName(node));
		} catch (ClassCastException e){
			throw new SemanticException(
					"Cannot declare method "+node.getName()+"; identifier is already\n" +
					"used as class name",
					node.getLine(),node.getPos());
		}
    	if (node.getNumExpChildren() > 9){
    		throw new SemanticException(
    			"Too many parameters in method "+node.getName(),
    			node.getLine(),node.getPos());
    	}
    }
	
    @Override
    public void outMethodDecl(MethodDecl node){
    	if (node.getExp() == null) this.symTable.setExpType(node.getExp(), Type.VOID);
    	Type retType = this.symTable.typeFromIType(node.getType());
    	Type retExpType = this.symTable.getExpType(node.getExp());
    	if (retType != retExpType){
    		throw new SemanticException(
    			"Return type of "+retType+" required for method "+node.getName()+";\n" +
    			"Given return expression is of type " + retExpType,
    			node.getLine(),node.getPos());
    	}
    	this.symTable.setExpType(node, retType);
    	this.symTable.popScope();
    }

    @Override
    public void outCallStatement(CallStatement node){
    	this.symTable.setExpType(node, outGenericCall(node));
	}
    
    @Override
    public void outCallExp(CallExp node){
    	this.symTable.setExpType(node, outGenericCall(node));
    }

    private Type outGenericCall(GenericCall node){	
    	String containingClass = symTable.getExpType(node.getExp()).toString();
//		System.out.println("About to push class scope: "+containingClass);//debug
    	try {
    		symTable.pushClassScope(containingClass);
		} catch (ClassCastException e){
			throw new SemanticException(
					"Cannot declare class "+node.getId()+"; identifier is already\n" +
					"used as method name",
					node.getLine(),node.getPos());
		}
		String methodName = symTable.generateMethodName(node);
//		System.out.println("About to look up " +methodName); // Debug
    	MethodSTE methodEntry = (MethodSTE) this.symTable.lookup(methodName);
    	LinkedList<IExp> args = node.getArgs();
    	String argSig = this.symTable.generateParamSig(args);
    	if (methodEntry != null){
    		String methodSig = methodEntry.getSignature();
    		String paramSig = methodSig.substring(0, methodSig.lastIndexOf(", )")+3);
    		if (!paramSig.equals(argSig)){
    			throw new SemanticException(
    					"Invalid arguments for method "+methodName+"\n"+
    							"Required: "+paramSig+
    							"\nFound: "+argSig+"\n",
    			    			node.getLine(),node.getPos());
    		}
    		String retSig = methodSig.substring(methodSig.lastIndexOf("returns ")+8);
        	symTable.popScope();
        	return Type.parseType(retSig);
    	}
    	else {
    		throw new SemanticException(
    				"Method "+methodName+" not recognized.",
        			node.getLine(),node.getPos());
    	}
    }

    public void outLtExp(LtExp node){
		this.symTable.setExpType(node, Type.BOOL);
	}
    
    @Override
    public void outThisExp(ThisLiteral node){
		/* Do Nothing */
	}

    public void outVoidType(VoidType node){
		/* Do Nothing */
	}

    public void outByteType(ByteType node){
		/* Do Nothing */
	}

    public void outIntType(IntType node){
		/* Do Nothing */
	}

    public void outBoolType(BoolType node){
		/* Do Nothing */
	}

    public void outColorType(ColorType node){
		/* Do Nothing */
	}

    public void outButtonType(ButtonType node){
		/* Do Nothing */
	}

    public void outToneType(ToneType node){
		/* Do Nothing */
	}
    
    public void outClassType(ClassType node){
    	/* Do Nothing */
    }
    
    public void outVarDecl(VarDecl node){
    	if (symTable.getExpType(node.getType()) == Type.VOID){
    		throw new SemanticException(
    				"Cannot declare a void type!",
    				node.getLine(),node.getPos());
    	}
    }
    
    @Override
    public void outAssignStatement(AssignStatement node){
    	VarSTE idSte = (VarSTE) this.symTable.lookup(node.getId());
    	if (idSte.getType() != symTable.getExpType(node.getExp())){
    		if (idSte.getType() != Type.INT || symTable.getExpType(node.getExp()) != Type.BYTE){
    			throw new SemanticException(
    					"Cannot assign "+symTable.getExpType(node.getExp())+" to var "+idSte.getName()+",\n"+
    							"which is of type "+idSte.getType(),
    							node.getLine(),node.getPos());
    		}
    	}
    }

    public void outFormal(Formal node){
		this.symTable.setExpType(node, this.symTable.typeFromIType(node.getType()));
	}

    public void outIdLiteral(IdLiteral node){
		VarSTE idSte = (VarSTE) this.symTable.lookup(node.getLexeme());
		if (idSte == null){
			this.symTable.setExpType(node, Type.VOID);
		}
		else {
			this.symTable.setExpType(node, idSte.getType());
		}
	}

    public void outNewExp(NewExp node){
		/* Do Nothing */
	}

    public void outBlockStatement(BlockStatement node){
		/* Do Nothing */
	}

    @Override
    public void outProgram(Program node){
		/* Do Nothing */
	}

    @Override
    public void outMainClass(MainClass node){
		/* Do Nothing */
	}

  

}
