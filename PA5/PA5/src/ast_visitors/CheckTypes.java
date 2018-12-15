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


import symtable.*;
import exceptions.InternalException;
import exceptions.SemanticException;

public class CheckTypes extends DepthFirstVisitor{
    
    private SymTable symbolTable;
    
    public CheckTypes(SymTable st){
       this.symbolTable = st;
    }
   
    public void outAndExp(AndExp node){
      if (this.symbolTable.getExpType(node.getLExp()) == Type.BOOL && this.symbolTable.getExpType(node.getRExp()) == Type.BOOL){
		this.symbolTable.setExpType(node, Type.BOOL);
	  }
	  else{
		throw new SemanticException("&& expression must be of form BOOL && BOOL",
			node.getLExp().getLine(), node.getLExp().getPos());
	  }
    }
   
    public void outPlusExp(PlusExp node){
		Type ltype = this.symbolTable.getExpType(node.getLExp());
		Type rtype = this.symbolTable.getExpType(node.getRExp());
		if ((ltype == Type.INT || ltype == Type.BYTE) && (rtype == Type.INT || rtype == Type.BYTE)){
			this.symbolTable.setExpType(node, ltype);
		}
		else{
			throw new SemanticException("+ expression must be of form BYTE - BYTE or INT - INT",
			node.getLExp().getLine(), node.getLExp().getPos());
		   }
        
    }

    public void outMinusExp(MinusExp node){
		Type ltype = this.symbolTable.getExpType(node.getLExp());
		Type rtype = this.symbolTable.getExpType(node.getRExp());
		if ((ltype == Type.INT || ltype == Type.BYTE) && (rtype == Type.INT || rtype == Type.BYTE)){
			this.symbolTable.setExpType(node, Type.INT);
		}
		else{
			throw new SemanticException("- expression must be of form BYTE - BYTE or INT - INT",
			node.getLExp().getLine(), node.getLExp().getPos());
		   }
    }

    public void outMulExp(MulExp node){
		Type ltype = this.symbolTable.getExpType(node.getLExp());
	   	Type rtype = this.symbolTable.getExpType(node.getRExp());
	   if (ltype == Type.BYTE && rtype == Type.BYTE){
		   //this.symbolTable.setExpType(node, Type.INT);
	   }
	   else{
		throw new SemanticException("Multiplication expression must have type BYTE * BYTE",
		node.getLExp().getLine(), node.getLExp().getPos());
	   }
	}

    public void outNotExp(NotExp node){
        if (this.symbolTable.getExpType(node.getExp()) == Type.BOOL){
			this.symbolTable.setExpType(node, Type.BOOL);
		}
		else{
			throw new SemanticException("Not expression expects type BOOL",
		   node.getExp().getLine(), node.getExp().getPos());
		}
    }

    public void outEqualExp(EqualExp node){
		Type ltype = this.symbolTable.getExpType(node.getLExp());
		Type rtype = this.symbolTable.getExpType(node.getRExp());
        if ((ltype==Type.INT  || ltype==Type.BYTE) && (rtype==Type.INT  || rtype==Type.BYTE)){
				this.symbolTable.setExpType(node, Type.BOOL);
		} 
		else if (ltype == rtype){
            this.symbolTable.setExpType(node, Type.BOOL);
		} 
		else {		
            throw new SemanticException(
				"Operands for == must be of the same type",
				node.getLExp().getLine(),node.getLExp().getPos());
        }
    }

    public void outByteCast(ByteCast node){
		Type type = this.symbolTable.getExpType(node.getExp());
        if (type==Type.INT  || type==Type.BYTE){
            this.symbolTable.setExpType(node, Type.BYTE);
        } else {
            throw new SemanticException(
				"Byte Cast must have an operand of type BYTE or INT",
				node.getExp().getLine(), node.getExp().getPos());
        }	
	}

    public void outIntegerExp(IntLiteral node){
       this.symbolTable.setExpType(node, Type.INT);
    }
    public void outButtonExp(ButtonLiteral node){
        this.symbolTable.setExpType(node, Type.BUTTON);
    }

    public void outColorExp(ColorLiteral node){
        this.symbolTable.setExpType(node, Type.COLOR);
    }
    @Override
    public void outToneExp(ToneLiteral node){
    	this.symbolTable.setExpType(node, Type.TONE);
    }

    public void outFalseExp(FalseLiteral node){
		this.symbolTable.setExpType(node, Type.BOOL);
    }

    public void outTrueExp(TrueLiteral node){
		this.symbolTable.setExpType(node, Type.BOOL);
	}
	
	

    public void outWhileStatement(WhileStatement node){
		if (this.symbolTable.getExpType(node.getExp()) != Type.BOOL){
			throw new SemanticException("While statement must have a boolean statement",
										node.getExp().getLine(), node.getExp().getPos());
		}
    }

    public void outIfStatement(IfStatement node){
		if (this.symbolTable.getExpType(node.getExp()) != Type.BOOL){
			throw new SemanticException("If statement must have a boolean statement",
										node.getExp().getLine(), node.getExp().getPos());
		}
    }

    public void outMeggyCheckButton(MeggyCheckButton node){
        if (this.symbolTable.getExpType(node.getExp()) == Type.BUTTON){
			this.symbolTable.setExpType(node, Type.BOOL);
		}
		else{
			throw new SemanticException("MeggyCheckButton takes an operand of type BUTTON",
										node.getExp().getLine(), node.getExp().getPos());
		}
    }

    public void outMeggyDelay(MeggyDelay node){
        if (this.symbolTable.getExpType(node.getExp()) != Type.INT){
			throw new SemanticException("MeggyDelay takes an operand of type INT",
										node.getExp().getLine(), node.getExp().getPos());
		}
    }

    public void outMeggyGetPixel(MeggyGetPixel node){
    	Type xtype = symbolTable.getExpType(node.getXExp());
		Type ytype = symbolTable.getExpType(node.getYExp());
		if (xtype == Type.BYTE && ytype == Type.BYTE){
			symbolTable.setExpType(node, Type.COLOR);
		}
		else if(xtype != Type.BYTE) {
        	throw new SemanticException(
				"First parameter for MeggyGetPixel must be of type BYTE",
				node.getXExp().getLine(), node.getXExp().getPos());
		} 
		else {
        	throw new SemanticException(
				"Second parameter for MeggyGetPixel must be of type BYTE",
				node.getYExp().getLine(), node.getYExp().getPos());
      	} 
    }

    public void outMeggySetPixel(MeggySetPixel node){
        Type xtype = this.symbolTable.getExpType(node.getXExp());
		Type ytype = this.symbolTable.getExpType(node.getYExp());
		Type colortype = this.symbolTable.getExpType(node.getColor());   
		if (xtype != Type.BYTE){
			throw new SemanticException("MeggyGetPixel expects X parameter of type BYTE",
										node.getXExp().getLine(), node.getXExp().getPos());
		}
		if (ytype != Type.BYTE){
			throw new SemanticException("MeggyDelay takes a Y parameter of type BYTE",
			node.getYExp().getLine(), node.getYExp().getPos());
		}
		if (colortype != Type.COLOR){
			throw new SemanticException("MeggySetPixel takes a Color parameter of type COLOR",
			node.getColor().getLine(), node.getColor().getPos());
		}
    }

    @Override
    public void outMeggyToneStart(MeggyToneStart node){
        Type ttype = this.symbolTable.getExpType(node.getToneExp());
		Type dtype = this.symbolTable.getExpType(node.getDurationExp());
		if(ttype != Type.TONE) {
        	throw new SemanticException(
				"Tone parameter for MeggyToneStart must be of type TONE",
				node.getToneExp().getLine(), node.getToneExp().getPos());
      	} else if(dtype != Type.INT) {
        	throw new SemanticException(
				"Duration parameter for MeggyToneStart must be of type INT",
				node.getDurationExp().getLine(), node.getDurationExp().getPos());
      	}
    }
    
    @Override
    public void inTopClassDecl(TopClassDecl node)
    {
		Scope pscope = symbolTable.peekScope();
		if (pscope == Scope.GLOBAL){
			try {
				symbolTable.pushClassScope(node.getName());
			} catch (ClassCastException e){
				throw new SemanticException(
						"Can't declare class " + node.getName() + " because its name is already in use", node.getLine(),node.getPos());
			}
		}
    }

    @Override
    public void outTopClassDecl(TopClassDecl node){
		symbolTable.popScope();
	}
	
    @Override
    public void inMethodDecl(MethodDecl node){
		try {
			this.symbolTable.pushMethodScope(node.getName());
		} 
		catch (ClassCastException e){
			throw new SemanticException( node.getName() + " has already been declared as a class name", node.getLine(),node.getPos());
		}
    }
	
    @Override
    public void outMethodDecl(MethodDecl node){
		if (node.getExp() == null) this.symbolTable.setExpType(node.getExp(), Type.VOID);
		MethodSTE mste = (MethodSTE) this.symbolTable.getMethod(node.getName());
		/*
		for (VarSTE vste : mste.getLocals()){
			System.out.println(vste.getName());
		}
		*/
		//System.out.println("Method name: "+ node.getName());
		//System.out.println("Does method exist in method table? " + (mste != null));
		//Type rtype = getTypeData(node.getType());

		String type = mste.getSignature();
		type = type.substring(type.indexOf("returns ") + 8, type.length());
		
		Type rtype = Type.parseType(type);
		Type ret = this.symbolTable.getExpType(node.getExp());
		//System.out.println("Is node.getExp() in exptype?  " + (this.symbolTable.getExpType(node.getExp()) != null));
		//System.out.println("Expected return type " + rtype.toString());
		if (rtype == ret){
			this.symbolTable.setExpType(node, rtype);
			this.symbolTable.popScope();
		}
    	else{
    		throw new SemanticException("Expected return type of " + node.getName() + " does not match actual return type",node.getLine(),node.getPos());
    	}
    }
	
    @Override
    public void outCallStatement(CallStatement node){
		//symbolTable.pushClassScope(symbolTable.getExpType(node.getExp()).toString().substring(6));
		symbolTable.pushClassScope(symbolTable.getExpType(node.getExp()).toString());
		//MethodSTE mste = (MethodSTE) symbolTable.lookup((symbolTable.getExpType(node.getExp()).toString() + "_" + node.getId()).substring(6));
		MethodSTE mste = (MethodSTE) symbolTable.lookup(node.getId());
		String args = "(";
		for (Node n : node.getArgs()){
			args += symbolTable.getExpType(n) + ", ";
		}
		args += ")";
		String msignature = mste.getSignature();
		String param_signature = msignature.substring(0, msignature.lastIndexOf(", )")+3);
		//System.out.println(node.getId());
		//System.out.println(param_signature);
		//System.out.println(args);
		//System.out.println(args);
		//System.out.println(param_signature);
    	if (mste != null){
			if (!param_signature.equals(args)){
				throw new SemanticException("Improper arguments for method", node.getLine(),node.getPos());
			}
			else{
				symbolTable.popScope();
				this.symbolTable.setExpType(node, Type.parseType(msignature.substring(msignature.lastIndexOf("returns ")+8)));
			}
		}
		else{
			throw new SemanticException("Method is not defined.", node.getLine(),node.getPos());
		}
	}
	
    @Override
    public void outCallExp(CallExp node){
		symbolTable.pushClassScope(symbolTable.getExpType(node.getExp()).toString());
		//MethodSTE mste = (MethodSTE) symbolTable.lookup((symbolTable.getExpType(node.getExp()).toString() + "_" + node.getId()).substring(6));
		MethodSTE mste = (MethodSTE) symbolTable.lookup(node.getId());
		String args = "(";
		for (Node n : node.getArgs()){
			args += symbolTable.getExpType(n) + ", ";
		}
		args += ")";
		String msignature = mste.getSignature();
		String param_signature = msignature.substring(0, msignature.lastIndexOf(", )")+3);
    	if (mste != null){
			if (!param_signature.equals(args)){
				throw new SemanticException("Improper arguments for method", node.getLine(),node.getPos());
			}
			else{
				symbolTable.popScope();
				this.symbolTable.setExpType(node, Type.parseType(msignature.substring(msignature.lastIndexOf("returns ")+8)));
			}
		}
		else{
			throw new SemanticException("Method is not defined.", node.getLine(),node.getPos());
		}
	}

    public void outLtExp(LtExp node){
		symbolTable.setExpType(node, Type.BOOL);
	}
	
	@Override
    public void outAssignStatement(AssignStatement node){
		VarSTE ste = (VarSTE)this.symbolTable.lookup(node.getId());
		if (ste == null){
			throw new SemanticException("Variable " + node.getId() + " has not been intantiated", node.getLine(), node.getPos());
		}
		if 	(ste.getType() != this.symbolTable.getExpType(node.getExp())){
				//System.out.println(ste.getType());
				//System.out.println(this.symbolTable.getExpType(node.getExp()));
    			throw new SemanticException("Assignment type mismatch",node.getLine(),node.getPos());
		}
		//System.out.println(ste.getType());
		//System.out.println(this.symbolTable.getExpType(node.getExp()));
	}
	
    @Override
    public void outThisExp(ThisLiteral node){
		Type type = ( (VarSTE) this.symbolTable.lookup("this") ).getType();
		this.symbolTable.setExpType(node, type);
	}

	public void outFormal(Formal node){
		this.symbolTable.setExpType(node, this.symbolTable.getExpType(node.getType()));
	}

	public void outVarDecl(VarDecl node){
		/*
		- determine whether a local or a formal (How?)
		- create a VarSTE with type, base, and offset information
		- insert VarSTE into the symbol table
		*/
		/*
		if (this.symbolTable.lookup(node.getName()) == null){
			VarSTE vste = new VarSTE(	node.getName(), 
										this.getTypeData(node.getType()), 
										symbolTable.peekScope().getScopeType(), 
										symbolTable.peekScope().getOffset());
			symbolTable.insert(vste);
			int newOffset = this.symbolTable.peekScope().getOffset();
			newOffset += vste.getType().getAVRTypeSize();
			this.symbolTable.peekScope().setOffset(newOffset);
		}
		else{
			throw new SemanticException("Cannot redeclare formal " + node.getName(), node.getLine(), node.getPos());
		}
		
		*/
		//System.out.println("Checking type for node: " + node.getName());
		if (this.symbolTable.lookup(node.getName()) != null){
			this.symbolTable.setExpType(node, this.getTypeData(node.getType()));
		}
		else{
			throw new SemanticException("Variable " + node.getName() + " is not declared", node.getLine(), node.getPos());
		}
	}

	@Override
	public void outIdLiteral(IdLiteral node){
		VarSTE ste = (VarSTE) this.symbolTable.lookup(node.getLexeme());
		if (ste == null){
			this.symbolTable.setExpType(node, Type.VOID);
		}
		else {
			this.symbolTable.setExpType(node, ste.getType());
		}
	}

    public void outNewExp(NewExp node){
		if (Type.hasType(node.getId())){
			symbolTable.setExpType(node, Type.getType(node.getId()));
		}
      	
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

    public void outBlockStatement(BlockStatement node){}

    @Override
    public void outProgram(Program node){}

    @Override
    public void outMainClass(MainClass node){}

	public void outVoidType(VoidType node){}

    public void outByteType(ByteType node){}

    public void outIntType(IntType node){}

    public void outBoolType(BoolType node){}

    public void outColorType(ColorType node){}

    public void outButtonType(ButtonType node){}

    public void outToneType(ToneType node){}
	public void outClassType(ClassType node){}

	

	
}
