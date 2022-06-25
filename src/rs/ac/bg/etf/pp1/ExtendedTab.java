package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

public class ExtendedTab {

	public static final Struct boolType = new Struct(Struct.Bool);
	public static final Struct voidType = new Struct(Struct.None);
	
	public static void extendedInit() {
		Tab.init();
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
		Tab.find("chr").getLocalSymbols().forEach(p -> p.setFpPos(1));
		Tab.find("ord").getLocalSymbols().forEach(p -> p.setFpPos(1));
		Tab.find("len").getLocalSymbols().forEach(p -> p.setFpPos(1));
	}
	
	public static String printObj(Obj obj) {
		DumpSymbolTableVisitor	stv = new ExtendedDumpSymbolTableVisitor();
		obj.accept(stv);
		return stv.getOutput();
	}
}
