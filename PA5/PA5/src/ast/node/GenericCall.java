package ast.node;

import java.util.LinkedList;

public interface GenericCall {

	String getId();
	
	LinkedList<IExp> getArgs();
	
	int getLine();
	
	int getPos();
	
	IExp getExp();

}
