package rs.ac.bg.etf.pp1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.Addop;
import rs.ac.bg.etf.pp1.ast.AddopMinus;
import rs.ac.bg.etf.pp1.ast.AddopPlus;
import rs.ac.bg.etf.pp1.ast.AddressingElem;
import rs.ac.bg.etf.pp1.ast.AddressingMember;
import rs.ac.bg.etf.pp1.ast.AssStatement;
import rs.ac.bg.etf.pp1.ast.BreakStatement;
import rs.ac.bg.etf.pp1.ast.ClassDecl;
import rs.ac.bg.etf.pp1.ast.ClassName;
import rs.ac.bg.etf.pp1.ast.CondFactExpr;
import rs.ac.bg.etf.pp1.ast.Condition;
import rs.ac.bg.etf.pp1.ast.ConditionTerm;
import rs.ac.bg.etf.pp1.ast.Const;
import rs.ac.bg.etf.pp1.ast.ConstFactor;
import rs.ac.bg.etf.pp1.ast.ConstructorDecl;
import rs.ac.bg.etf.pp1.ast.ConstructorName;
import rs.ac.bg.etf.pp1.ast.ContinueStatement;
import rs.ac.bg.etf.pp1.ast.DecStatement;
import rs.ac.bg.etf.pp1.ast.DesignOfMethodFactor;
import rs.ac.bg.etf.pp1.ast.DesignOfVarFactor;
import rs.ac.bg.etf.pp1.ast.Designator;
import rs.ac.bg.etf.pp1.ast.DoWhileCondStart;
import rs.ac.bg.etf.pp1.ast.DoWhileLoopStart;
import rs.ac.bg.etf.pp1.ast.DoWhileStatement;
import rs.ac.bg.etf.pp1.ast.DoesExtend;
import rs.ac.bg.etf.pp1.ast.ElseStart;
import rs.ac.bg.etf.pp1.ast.ExprWithAddop;
import rs.ac.bg.etf.pp1.ast.IfElseStatement;
import rs.ac.bg.etf.pp1.ast.IfStart;
import rs.ac.bg.etf.pp1.ast.IfStatement;
import rs.ac.bg.etf.pp1.ast.IncStatement;
import rs.ac.bg.etf.pp1.ast.InstanceofCondFact;
import rs.ac.bg.etf.pp1.ast.MethodCall;
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
import rs.ac.bg.etf.pp1.ast.NewObjectFactor;
import rs.ac.bg.etf.pp1.ast.OrCondition;
import rs.ac.bg.etf.pp1.ast.PrintStatement;
import rs.ac.bg.etf.pp1.ast.PrintWithPadStatement;
import rs.ac.bg.etf.pp1.ast.ReadStatement;
import rs.ac.bg.etf.pp1.ast.RecordDecl;
import rs.ac.bg.etf.pp1.ast.Relop;
import rs.ac.bg.etf.pp1.ast.RelopCondFact;
import rs.ac.bg.etf.pp1.ast.RelopEq;
import rs.ac.bg.etf.pp1.ast.RelopGe;
import rs.ac.bg.etf.pp1.ast.RelopGt;
import rs.ac.bg.etf.pp1.ast.RelopLe;
import rs.ac.bg.etf.pp1.ast.RelopLt;
import rs.ac.bg.etf.pp1.ast.RelopNeq;
import rs.ac.bg.etf.pp1.ast.ReturnValStatement;
import rs.ac.bg.etf.pp1.ast.ReturnVoidStatement;
import rs.ac.bg.etf.pp1.ast.SuperMethodCall;
import rs.ac.bg.etf.pp1.ast.SuperMethodFactor;
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

	static class CondControl {
		List<Integer> condFactJumps = new LinkedList<>();
		List<Integer> condSuccJumps = new LinkedList<>();
		int endJump = -1;

		boolean isLoop = false;
	}
	
	static class LoopControl {
		List<Integer> continueJumps = new LinkedList<>();
		List<Integer> breakJumps = new LinkedList<>();
		
		int loopStart = -1;
	}

	int mainPc;

	int nVars;
	int vftStart;
	Map<String, Obj> classConstructors = new HashMap<String, Obj>();

	List<Integer> vftBuffer = new LinkedList<>();

	Obj currentClass;
	Obj currentMethod;
	boolean inConstructor = false;

	Obj currentSuperclass;

	Stack<CondControl> condControlStack = new Stack<>();
	Stack<LoopControl> loopControlStack = new Stack<>();

	private void initVft() {
		for (int v : vftBuffer) {
			Code.loadConst(v);
			Code.put(Code.putstatic);
			Code.put2(vftStart++);
		}
	}

	public void visit(MethodSignature methSig) {
		Obj obj = methSig.obj;
		if (obj.getName().equals("main") && obj.getLevel() == 0) {
			mainPc = Code.pc;
			initVft();
		}
		obj.setAdr(Code.pc);
		MethodDecl methNode = (MethodDecl) methSig.getParent();

		FormParamCounter forParCnt = new FormParamCounter();
		methNode.traverseTopDown(forParCnt);

		if (obj.getLevel() != 0) {
			++forParCnt.count; // this
		}

		VarCounter varCnt = new VarCounter();
		methNode.traverseTopDown(varCnt);

		Code.put(Code.enter);
		Code.put(forParCnt.count);
		Code.put(forParCnt.count + varCnt.count);

		currentMethod = obj;
	}

	public void visit(MethodDecl methodDecl) {
		if (methodDecl.getMethodSignature().obj.getType() == ExtendedTab.voidType) {
			Code.put(Code.exit);
			Code.put(Code.return_);
		} else {
			Code.put(Code.trap);
			Code.put(1);
		}

		currentMethod = null;
	}

	public void visit(ConstructorName constructorName) {
		Obj obj = constructorName.obj;
		obj.setAdr(Code.pc);
		ConstructorDecl constNode = (ConstructorDecl) constructorName.getParent();

		VarCounter varCnt = new VarCounter();
		constNode.traverseTopDown(varCnt);

		Code.put(Code.enter);
		Code.put(1);
		Code.put(1 + varCnt.count);

		inConstructor = true;
	}

	public void visit(ConstructorDecl constructorDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);

		inConstructor = false;
	}

	private void loadDesignator(Designator design) {
		SyntaxNode parent = design.getParent();
		if (parent instanceof Designator) {
			Code.load(design.obj);
		}
	}

	public void visit(NamedDesignator design) {
		if (design.obj.getKind() == Obj.Fld || (design.obj.getKind() == Obj.Meth && design.obj.getLevel() > 0)) {
			Code.put(Code.load_n); // this
		}
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
		Struct arrType = factor.getType().obj.getType();
		if (arrType == Tab.charType) {
			Code.put(0);
		} else {
			Code.put(1);
		}
	}

	public void visit(ReturnValStatement ReturnExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(ReturnVoidStatement ReturnNoExpr) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(DesignOfMethodFactor factor) {
		Obj obj = factor.getDesignator().obj;
		if (obj == Tab.chrObj || obj == Tab.ordObj) {
			// vratili bi istu vrednost-> ne radimo nista
		} else if (obj == Tab.lenObj) {
			Code.put(Code.arraylength);
		} else {
			if (obj.getLevel() == 0) {
				int offset = obj.getAdr() - Code.pc;
				Code.put(Code.call);
				Code.put2(offset);
			} else {
				// ucitava ponovo referencu objekta za koji se poziva metoda
				factor.getDesignator().traverseBottomUp(new CodeGenerator());
				Code.put(Code.getfield);
				Code.put2(0);
				Code.put(Code.invokevirtual);
				String name = obj.getName();
				for (int i = 0; i < name.length(); ++i) {
					Code.put4(name.charAt(i));
				}
				Code.put4(-1);
			}
		}
	}

	public void visit(RecordDecl recordDecl) {
		Obj recordObj = recordDecl.getRecordName().obj;
		recordObj.setAdr(-1);
	}

	public void visit(ClassName className) {
		currentClass = className.obj;
	}

	public void visit(DoesExtend ext) {
		currentSuperclass = ext.getType().obj;
	}

	public void visit(ClassDecl classDecl) {
		Obj classObj = classDecl.getClassName().obj;
		classObj.setAdr(nVars);
		for (Obj meth : classObj.getType().getMembers()) {
			if (meth.getKind() == Obj.Meth) {
				String name = meth.getName();
				for (int i = 0; i < name.length(); ++i) {
					vftBuffer.add((int) name.charAt(i));
					nVars++;

				}
				vftBuffer.add(-1);
				nVars++;
				if (meth.getAdr() == 0 && currentSuperclass != null) {
					String soughtName = null;

					if (classConstructors.containsKey(currentClass.getName()) && name.equals(currentClass.getName())) {
						soughtName = currentSuperclass.getName();
					} else {
						soughtName = name;
					}

					for (Obj superMeth : currentSuperclass.getType().getMembers()) {
						if (superMeth.getName().equals(soughtName)) {
							meth.setAdr(superMeth.getAdr());
							break;
						}
					}
				}
				vftBuffer.add(meth.getAdr());
				nVars++;
			}
		}
		vftBuffer.add(-2);
		nVars++;

		currentClass = null;
		currentSuperclass = null;
	}

	public void visit(NewObjectFactor factor) {
		Code.put(Code.new_);
		Code.put2(factor.getType().obj.getType().getNumberOfFields() * 4 + 4);
		Code.put(Code.dup);
		Code.loadConst(factor.getType().obj.getAdr());
		if (classConstructors.containsKey(factor.getType().obj.getName())) {
			Code.put(Code.dup2);
		}
		Code.put(Code.putfield);
		Code.put2(0);
		if (classConstructors.containsKey(factor.getType().obj.getName())) {
			Code.put(Code.invokevirtual);
			String name = factor.getType().obj.getName();
			for (int i = 0; i < name.length(); ++i) {
				Code.put4(name.charAt(i));
			}
			Code.put4(-1);
		}
	}

	public void visit(SuperMethodFactor factor) {
		Code.put(Code.load_n);

		Code.loadConst(currentSuperclass.getAdr());

		String name = null;

		if (inConstructor) {
			name = currentSuperclass.getName();
		} else {
			name = currentMethod.getName();
		}

		Code.put(Code.invokevirtual);
		for (int i = 0; i < name.length(); ++i) {
			Code.put4(name.charAt(i));
		}
		Code.put4(-1);
	}

	public void visit(MethodCall stmt) {
		Obj obj = stmt.getDesignator().obj;
		if (obj == Tab.chrObj || obj == Tab.ordObj) {
			// vratili bi istu vrednost-> ne radimo nista
		} else if (obj == Tab.lenObj) {
			Code.put(Code.arraylength);
		} else {
			if (obj.getLevel() == 0) {
				int offset = obj.getAdr() - Code.pc;
				Code.put(Code.call);
				Code.put2(offset);
			} else {
				// ucitava ponovo referencu objekta za koji se poziva metoda
				stmt.getDesignator().traverseBottomUp(new CodeGenerator());
				Code.put(Code.getfield);
				Code.put2(0);
				Code.put(Code.invokevirtual);
				String name = obj.getName();
				for (int i = 0; i < name.length(); ++i) {
					Code.put4(name.charAt(i));
				}
				Code.put4(-1);
			}
		}	
		if (obj.getType() != ExtendedTab.voidType && obj.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
	}

	public void visit(SuperMethodCall smt) {
		Code.put(Code.load_n);

		Code.loadConst(currentSuperclass.getAdr());

		String name = null;

		if (inConstructor) {
			name = currentSuperclass.getName();
		} else {
			name = currentMethod.getName();
		}

		Code.put(Code.invokevirtual);
		for (int i = 0; i < name.length(); ++i) {
			Code.put4(name.charAt(i));
		}
		Code.put4(-1);
		if (!inConstructor && currentMethod.getType() != ExtendedTab.voidType) {
			Code.put(Code.pop);
		}
	}

	public void visit(IncStatement stmt) {
		stmt.getDesignator().traverseBottomUp(new CodeGenerator());
		Code.load(stmt.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(stmt.getDesignator().obj);

	}

	public void visit(DecStatement stmt) {
		stmt.getDesignator().traverseBottomUp(new CodeGenerator());
		Code.load(stmt.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(stmt.getDesignator().obj);

	}

	public void visit(IfStart start) {
		condControlStack.push(new CondControl());
	}

	public void visit(IfStatement stmt) {
		CondControl ctr = condControlStack.pop();
		for (int adr : ctr.condFactJumps) {
			Code.fixup(adr);
		}
	}

	public void visit(ElseStart start) {
		CondControl ctr = condControlStack.peek();
		Code.putJump(0);
		ctr.endJump = Code.pc - 2;
		for (int adr : ctr.condFactJumps) {
			Code.fixup(adr);
		}
		ctr.condFactJumps.clear();
	}

	public void visit(IfElseStatement stmt) {
		CondControl ctr = condControlStack.pop();
		Code.fixup(ctr.endJump);
	}

	public void visit(DoWhileLoopStart start) {
		CondControl ctr = new CondControl();
		ctr.isLoop = true;
		condControlStack.push(ctr);
		
		LoopControl loop = new LoopControl();
		loop.loopStart = Code.pc;
		loopControlStack.push(loop);
	}

	public void visit(DoWhileCondStart start) {
		LoopControl loop = loopControlStack.peek();
		for (int adr : loop.continueJumps) {
			Code.fixup(adr);
		}
	}

	public void visit(DoWhileStatement stmt) {
		CondControl ctr = condControlStack.pop();
		for (int adr : ctr.condFactJumps) {
			Code.fixup(adr);
		}
		LoopControl loop = loopControlStack.pop();
		for (int adr : loop.breakJumps) {
			Code.fixup(adr);
		}
	}

	private int getCodeFromRelop(Relop relop) {
		if (relop instanceof RelopEq) {
			return Code.eq;
		}
		if (relop instanceof RelopNeq) {
			return Code.ne;
		}
		if (relop instanceof RelopGt) {
			return Code.gt;
		}
		if (relop instanceof RelopGe) {
			return Code.ge;
		}
		if (relop instanceof RelopLt) {
			return Code.lt;
		}
		if (relop instanceof RelopLe) {
			return Code.le;
		}

		return -1;
	}

	public void visit(RelopCondFact fact) {
		Code.putFalseJump(getCodeFromRelop(fact.getRelop()), 0);
		condControlStack.peek().condFactJumps.add(Code.pc - 2);
	}

	public void visit(InstanceofCondFact fact) {
		
		Code.load(fact.getDesignator().obj);
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0);
		int nullJump = Code.pc - 2;
		
		fact.getDesignator().traverseBottomUp(new CodeGenerator());
		Code.load(fact.getDesignator().obj);
		Code.put(Code.getfield);
		Code.put2(0);
		Code.loadConst(fact.getType().obj.getAdr());
		Code.putFalseJump(Code.eq, 0);
		condControlStack.peek().condFactJumps.add(Code.pc - 2);
		
		Code.fixup(nullJump);
	}

	public void visit(CondFactExpr fact) {
		Code.loadConst(1);
		Code.putFalseJump(Code.eq, 0);
		condControlStack.peek().condFactJumps.add(Code.pc - 2);
	}

	private void handleCond(Condition cond) {
		SyntaxNode parent = cond.getParent();
		CondControl ctr = condControlStack.peek();
		if (parent instanceof OrCondition) {
			if (!ctr.isLoop) {
				Code.putJump(0);
				ctr.condSuccJumps.add(Code.pc - 2);
			} else {
				LoopControl loop = loopControlStack.peek();
				Code.putJump(loop.loopStart);
			}
			for (int adr : ctr.condFactJumps) {
				Code.fixup(adr);
			}
			ctr.condFactJumps.clear();
		} else {
			if (!ctr.isLoop) {
				for (int adr : ctr.condSuccJumps) {
					Code.fixup(adr);
				}
				ctr.condSuccJumps.clear();
			} else {
				LoopControl loop = loopControlStack.peek();
				Code.putJump(loop.loopStart);
				for (int adr : ctr.condFactJumps) {
					Code.fixup(adr);
				}
				ctr.condFactJumps.clear();
			}
		}
	}

	public void visit(ConditionTerm cond) {
		handleCond(cond);
	}

	public void visit(OrCondition cond) {
		handleCond(cond);
	}

	public void visit(BreakStatement stmt) {
		LoopControl loop = loopControlStack.peek();
		Code.putJump(0);
		loop.breakJumps.add(Code.pc - 2);
	}

	public void visit(ContinueStatement stmt) {
		LoopControl loop = loopControlStack.peek();
		Code.putJump(0);
		loop.continueJumps.add(Code.pc - 2);
	}
}
