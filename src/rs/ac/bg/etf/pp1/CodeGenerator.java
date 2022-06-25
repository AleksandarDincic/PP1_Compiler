package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.Addop;
import rs.ac.bg.etf.pp1.ast.AddopMinus;
import rs.ac.bg.etf.pp1.ast.AddopPlus;
import rs.ac.bg.etf.pp1.ast.AddressingElem;
import rs.ac.bg.etf.pp1.ast.AddressingMember;
import rs.ac.bg.etf.pp1.ast.AssStatement;
import rs.ac.bg.etf.pp1.ast.Const;
import rs.ac.bg.etf.pp1.ast.ConstFactor;
import rs.ac.bg.etf.pp1.ast.DesignOfVarFactor;
import rs.ac.bg.etf.pp1.ast.Designator;
import rs.ac.bg.etf.pp1.ast.ExprWithAddop;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodReturnsValue;
import rs.ac.bg.etf.pp1.ast.MethodReturnsVoid;
import rs.ac.bg.etf.pp1.ast.MethodSignature;
import rs.ac.bg.etf.pp1.ast.Mulop;
import rs.ac.bg.etf.pp1.ast.MulopDiv;
import rs.ac.bg.etf.pp1.ast.MulopMod;
import rs.ac.bg.etf.pp1.ast.MulopMul;
import rs.ac.bg.etf.pp1.ast.NamedDesignator;
import rs.ac.bg.etf.pp1.ast.NegativeExpr;
import rs.ac.bg.etf.pp1.ast.NewArrayFactor;
import rs.ac.bg.etf.pp1.ast.PrintStatement;
import rs.ac.bg.etf.pp1.ast.PrintWithPadStatement;
import rs.ac.bg.etf.pp1.ast.ReadStatement;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.TermWithMulop;
import rs.ac.bg.etf.pp1.ast.ThisDesignator;
import rs.ac.bg.etf.pp1.ast.VarDecl;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;

	public int getMainPc() {
		return mainPc;
	}

	public void visit(MethodSignature methSig) {
		Obj obj = methSig.obj;
		if (obj.getName().equals("main") && obj.getLevel() == 0) {
			mainPc = Code.pc;
		}
		obj.setAdr(Code.pc);
		MethodDecl methNode = (MethodDecl) methSig.getParent();

		FormParamCounter forParCnt = new FormParamCounter();
		methNode.traverseTopDown(forParCnt);

		VarCounter varCnt = new VarCounter();
		methNode.traverseTopDown(varCnt);

		Code.put(Code.enter);
		Code.put(forParCnt.getCount());
		Code.put(forParCnt.getCount() + varCnt.getCount());
	}

	public void visit(MethodDecl methodDecl) {
		if (methodDecl.getMethodSignature().obj.getType() == ExtendedTab.voidType) {
			Code.put(Code.exit);
			Code.put(Code.return_);
		} else {
			Code.put(Code.trap);
			Code.put(1);
		}
	}

	private void loadDesignator(Designator design) {
		SyntaxNode parent = design.getParent();
		if (parent instanceof Designator) {
			Code.load(design.obj);
		}
	}
	
	public void visit(NamedDesignator design) {
		loadDesignator(design);
	}
	
	public void visit(ThisDesignator design) {
		loadDesignator(design);
	}
	
	public void visit(AddressingMember design) {
		loadDesignator(design);
	}
	
	public void visit(AddressingElem design) {
		loadDesignator(design);
	}

	public void visit(DesignOfVarFactor factor) {
		Code.load(factor.getDesignator().obj);
	}

	public void visit(ConstFactor factor) {
		Code.load(factor.getConst().obj);
	}

	public void visit(TermWithMulop term) {
		Mulop mulop = term.getMulop();
		if (mulop instanceof MulopMul) {
			Code.put(Code.mul);
		} else if (mulop instanceof MulopDiv) {
			Code.put(Code.div);
		} else if (mulop instanceof MulopMod) {
			Code.put(Code.rem);
		}
	}

	public void visit(ExprWithAddop expr) {
		Addop addop = expr.getAddop();
		if (addop instanceof AddopPlus) {
			Code.put(Code.add);
		} else if (addop instanceof AddopMinus) {
			Code.put(Code.sub);
		}
	}

	public void visit(NegativeExpr expr) {
		Code.put(Code.neg);
	}

	public void visit(AssStatement ass) {
		Code.store(ass.getDesignator().obj);
	}

	public void visit(PrintStatement stmt) {
		Struct type = stmt.getExpr().struct;
		if (type == Tab.charType) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		} else if (type == Tab.intType) {
			Code.loadConst(1);
			Code.put(Code.print);
		} else if (type == ExtendedTab.boolType) {
			Code.loadConst(0);

			Code.put(Code.jcc + Code.eq);
			Code.put2(34);

			Code.loadConst('t');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('r');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('u');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('e');
			Code.loadConst(1);
			Code.put(Code.bprint);

			Code.put(Code.jmp);
			Code.put2(38);

			Code.loadConst('f');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('a');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('l');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('s');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('e');
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}

	public void visit(PrintWithPadStatement stmt) {
		Struct type = stmt.getExpr().struct;
		if (type == Tab.charType) {
			Code.load(stmt.getConst().obj);
			Code.put(Code.bprint);
		} else if (type == Tab.intType) {
			Code.load(stmt.getConst().obj);
			Code.put(Code.print);
		} else if (type == ExtendedTab.boolType) {
			Code.loadConst(0);

			Code.put(Code.jcc + Code.eq);
			int jeqPc = Code.pc;
			Code.put2(0);

			Code.loadConst('t');
			Code.loadConst(stmt.getConst().obj.getAdr() - 3);
			Code.put(Code.bprint);
			Code.loadConst('r');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('u');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('e');
			Code.loadConst(1);
			Code.put(Code.bprint);

			Code.put(Code.jmp);
			int jmpPc = Code.pc;
			Code.put2(0);

			Code.fixup(jeqPc);

			Code.loadConst('f');
			Code.loadConst(stmt.getConst().obj.getAdr() - 4);
			Code.put(Code.bprint);
			Code.loadConst('a');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('l');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('s');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('e');
			Code.loadConst(1);
			Code.put(Code.bprint);

			Code.fixup(jmpPc);
		}
	}

	public void visit(ReadStatement stmt) {
		Struct type = stmt.getDesignator().obj.getType();
		if (type == Tab.charType) {
			Code.put(Code.bread);
			Code.store(stmt.getDesignator().obj);
		} else if (type == Tab.intType) {
			Code.put(Code.read);
			Code.store(stmt.getDesignator().obj);
		} else if (type == ExtendedTab.boolType) {
			Code.put(Code.read);
			Code.loadConst(0);
			
			Code.put(Code.jcc + Code.eq);
			int jeqPc = Code.pc;
			Code.put2(0);
			
			Code.loadConst(1);
			Code.store(stmt.getDesignator().obj);
			
			Code.put(Code.jmp);
			int jmpPc = Code.pc;
			Code.put2(0);
			
			Code.fixup(jeqPc);
			
			Code.loadConst(0);
			Code.store(stmt.getDesignator().obj);

			Code.fixup(jmpPc);
		}
	}
	
	public void visit(NewArrayFactor factor) {
		Code.put(Code.newarray);
		Struct arrType = factor.getType().struct;
		if(arrType == Tab.charType) {
			Code.put(0);
		}
		else {
			Code.put(1);
		}
	}
	/*
	 * private int varCount;
	 * 
	 * private int paramCnt;
	 * 
	 * private int mainPc;
	 * 
	 * public int getMainPc() { return mainPc; }
	 * 
	 * @Override public void visit(MethodTypeName MethodTypeName) { if
	 * ("main".equalsIgnoreCase(MethodTypeName.getMethName())) { mainPc = Code.pc; }
	 * MethodTypeName.obj.setAdr(Code.pc);
	 * 
	 * // Collect arguments and local variables. SyntaxNode methodNode =
	 * MethodTypeName.getParent(); VarCounter varCnt = new VarCounter();
	 * methodNode.traverseTopDown(varCnt); FormParamCounter fpCnt = new
	 * FormParamCounter(); methodNode.traverseTopDown(fpCnt);
	 * 
	 * // Generate the entry. Code.put(Code.enter); Code.put(fpCnt.getCount());
	 * Code.put(varCnt.getCount() + fpCnt.getCount()); }
	 * 
	 * @Override public void visit(VarDecl VarDecl) { varCount++; }
	 * 
	 * @Override public void visit(FormalParamDecl FormalParam) { paramCnt++; }
	 * 
	 * @Override public void visit(MethodDecl MethodDecl) { Code.put(Code.exit);
	 * Code.put(Code.return_); }
	 * 
	 * @Override public void visit(ReturnExpr ReturnExpr) { Code.put(Code.exit);
	 * Code.put(Code.return_); }
	 * 
	 * @Override public void visit(ReturnNoExpr ReturnNoExpr) { Code.put(Code.exit);
	 * Code.put(Code.return_); }
	 * 
	 * @Override public void visit(Assignment Assignment) {
	 * Code.store(Assignment.getDesignator().obj); }
	 * 
	 * @Override public void visit(Const Const) { Code.load(new Obj(Obj.Con, "$",
	 * Const.struct, Const.getN1(), 0)); }
	 * 
	 * @Override public void visit(Designator Designator) { SyntaxNode parent =
	 * Designator.getParent(); if (Assignment.class != parent.getClass() &&
	 * FuncCall.class != parent.getClass()) { Code.load(Designator.obj); } }
	 * 
	 * @Override public void visit(FuncCall FuncCall) { Obj functionObj =
	 * FuncCall.getDesignator().obj; int offset = functionObj.getAdr() - Code.pc;
	 * Code.put(Code.call); Code.put2(offset); }
	 * 
	 * @Override public void visit(PrintStmt PrintStmt) { Code.put(Code.const_5);
	 * Code.put(Code.print); }
	 * 
	 * @Override public void visit(AddExpr AddExpr) { Code.put(Code.add); }
	 */
}
