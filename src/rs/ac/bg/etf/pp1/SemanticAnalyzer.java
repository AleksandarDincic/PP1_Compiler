package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.ActualParameters;
import rs.ac.bg.etf.pp1.ast.AddressingElem;
import rs.ac.bg.etf.pp1.ast.AddressingMember;
import rs.ac.bg.etf.pp1.ast.AndCondTerm;
import rs.ac.bg.etf.pp1.ast.AssStatement;
import rs.ac.bg.etf.pp1.ast.BoolConst;
import rs.ac.bg.etf.pp1.ast.BreakStatement;
import rs.ac.bg.etf.pp1.ast.CharConst;
import rs.ac.bg.etf.pp1.ast.ClassBodyStart;
import rs.ac.bg.etf.pp1.ast.ClassDecl;
import rs.ac.bg.etf.pp1.ast.ClassMethodsStart;
import rs.ac.bg.etf.pp1.ast.ClassName;
import rs.ac.bg.etf.pp1.ast.ClassNoMethods;
import rs.ac.bg.etf.pp1.ast.CondFactExpr;
import rs.ac.bg.etf.pp1.ast.CondTermFact;
import rs.ac.bg.etf.pp1.ast.Const;
import rs.ac.bg.etf.pp1.ast.ConstFactor;
import rs.ac.bg.etf.pp1.ast.ConstNameVal;
import rs.ac.bg.etf.pp1.ast.ConstType;
import rs.ac.bg.etf.pp1.ast.ConstructorDecl;
import rs.ac.bg.etf.pp1.ast.ConstructorName;
import rs.ac.bg.etf.pp1.ast.ContinueStatement;
import rs.ac.bg.etf.pp1.ast.DecStatement;
import rs.ac.bg.etf.pp1.ast.DesignOfMethodFactor;
import rs.ac.bg.etf.pp1.ast.DesignOfVarFactor;
import rs.ac.bg.etf.pp1.ast.DoWhileLoopStart;
import rs.ac.bg.etf.pp1.ast.DoWhileStatement;
import rs.ac.bg.etf.pp1.ast.DoesExtend;
import rs.ac.bg.etf.pp1.ast.DoesNotExtend;
import rs.ac.bg.etf.pp1.ast.Expr;
import rs.ac.bg.etf.pp1.ast.ExprTerm;
import rs.ac.bg.etf.pp1.ast.ExprWithAddop;
import rs.ac.bg.etf.pp1.ast.ExtendsError;
import rs.ac.bg.etf.pp1.ast.Factor;
import rs.ac.bg.etf.pp1.ast.FieldNameOfArray;
import rs.ac.bg.etf.pp1.ast.FieldNameOfSingle;
import rs.ac.bg.etf.pp1.ast.FieldType;
import rs.ac.bg.etf.pp1.ast.FormalParamOfArray;
import rs.ac.bg.etf.pp1.ast.FormalParamOfSingle;
import rs.ac.bg.etf.pp1.ast.GlobalVarNameOfArray;
import rs.ac.bg.etf.pp1.ast.GlobalVarNameOfSingle;
import rs.ac.bg.etf.pp1.ast.IncStatement;
import rs.ac.bg.etf.pp1.ast.InstanceofCondFact;
import rs.ac.bg.etf.pp1.ast.IntConst;
import rs.ac.bg.etf.pp1.ast.MethodCall;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodReturnsValue;
import rs.ac.bg.etf.pp1.ast.MethodReturnsVoid;
import rs.ac.bg.etf.pp1.ast.MethodSignature;
import rs.ac.bg.etf.pp1.ast.NamedDesignator;
import rs.ac.bg.etf.pp1.ast.NegativeExpr;
import rs.ac.bg.etf.pp1.ast.NewArrayFactor;
import rs.ac.bg.etf.pp1.ast.NewObjectFactor;
import rs.ac.bg.etf.pp1.ast.ParenFactor;
import rs.ac.bg.etf.pp1.ast.PositiveExpr;
import rs.ac.bg.etf.pp1.ast.PrintStatement;
import rs.ac.bg.etf.pp1.ast.PrintWithPadStatement;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.ast.ProgramName;
import rs.ac.bg.etf.pp1.ast.ReadStatement;
import rs.ac.bg.etf.pp1.ast.RecordDecl;
import rs.ac.bg.etf.pp1.ast.RecordName;
import rs.ac.bg.etf.pp1.ast.RelopCondFact;
import rs.ac.bg.etf.pp1.ast.RelopEq;
import rs.ac.bg.etf.pp1.ast.RelopNeq;
import rs.ac.bg.etf.pp1.ast.ReturnValStatement;
import rs.ac.bg.etf.pp1.ast.ReturnVoidStatement;
import rs.ac.bg.etf.pp1.ast.SingleActualParam;
import rs.ac.bg.etf.pp1.ast.SuperMethodCall;
import rs.ac.bg.etf.pp1.ast.SuperMethodFactor;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.TermFactor;
import rs.ac.bg.etf.pp1.ast.TermWithMulop;
import rs.ac.bg.etf.pp1.ast.ThisDesignator;
import rs.ac.bg.etf.pp1.ast.Type;
import rs.ac.bg.etf.pp1.ast.VarNameOfArray;
import rs.ac.bg.etf.pp1.ast.VarNameOfSingle;
import rs.ac.bg.etf.pp1.ast.VarType;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.HashTableDataStructure;

public class SemanticAnalyzer extends VisitorAdaptor {
	boolean errorDetected = false;
	int nVars = 0;

	Struct currentVarType = null;

	Logger log = Logger.getLogger(getClass());

	Map<String, Struct> records = new HashMap<>();

	String methName = null;
	Struct methRetType = null;

	ClassTree classTree = new ClassTree("0", null, null);
	boolean declaringClass = false;

	Obj currentClass = null;
	Obj currentSuperclass = null;

	ClassTree currentClassNode = null;
	ClassTree currentSuperclassNode = null;

	boolean inConstructor = false;

	Map<String, Obj> classConstructors = new HashMap<>();
	List<Obj> formPars = new ArrayList<>();
	List<Struct> actPars = new ArrayList<>();

	Map<String, Boolean> overriddenMethods = new HashMap<>();

	int nestedLoops = 0;

	boolean mainFound = false;

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	public void visit(Program program) {
		if (!mainFound) {
			report_error("Program ne sadrzi main metodu.", program);
		}

		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgramName().obj);
		Tab.closeScope();
	}

	public void visit(ProgramName programName) {
		programName.obj = Tab.insert(Obj.Prog, programName.getProgName(), Tab.noType);
		Tab.openScope();
	}

	public void visit(ConstType constType) {
		Struct typeStruct = constType.getType().obj.getType();
		if (typeStruct != Tab.charType && typeStruct != Tab.intType && typeStruct != ExtendedTab.boolType) {
			report_error(
					"Konstanta  mora biti tipa int, char ili bool, dat je tip " + constType.getType().getTypeName(),
					constType);
			typeStruct = Tab.noType;
		}
		currentVarType = typeStruct;
	}

	public void visit(ConstNameVal constNameVal) {
		String constName = constNameVal.getConstName();

		if (Tab.currentScope().findSymbol(constName) != null) {
			report_error("Ime " + constName + " se vec koristi u trenutnom opsegu.", constNameVal);
		} else {
			Const c = constNameVal.getConst();
			int val = -1;
			if (currentVarType.equals(c.obj.getType())) {
				val = c.obj.getAdr();
			} else {
				report_error("Vrednost konstante " + constName + " nije kompatbilna sa svojim tipom.", constNameVal);
			}
			Obj constObj = Tab.insert(Obj.Con, constName, currentVarType);
			constObj.setAdr(val);
		}
	}

	public void visit(VarType varType) {
		Struct typeStruct = varType.getType().obj.getType();
		currentVarType = typeStruct;
	}

	private void analyzeVarNameOfSingle(String varName, SyntaxNode node, boolean isField) {
		if (Tab.currentScope().findSymbol(varName) != null) {
			report_error("Ime " + varName + " se vec koristi u trenutnom opsegu.", node);
		} else {
			Obj obj = Tab.insert(isField ? Obj.Fld : Obj.Var, varName, currentVarType);
			if (obj.getKind() == Obj.Fld) {
				obj.setAdr(obj.getAdr() + 1);
			}
		}
	}

	private void analyzeVarNameOfArray(String varName, SyntaxNode node, boolean isField) {
		if (Tab.currentScope().findSymbol(varName) != null) {
			report_error("Ime " + varName + " se vec koristi u trenutnom opsegu.", node);
		} else {
			Obj obj = Tab.insert(isField ? Obj.Fld : Obj.Var, varName, new Struct(Struct.Array, currentVarType));
			if (obj.getKind() == Obj.Fld) {
				obj.setAdr(obj.getAdr() + 1);
			}
		}
	}

	public void visit(GlobalVarNameOfSingle varName) {
		analyzeVarNameOfSingle(varName.getVarName(), varName, false);
	}

	public void visit(GlobalVarNameOfArray varName) {
		analyzeVarNameOfArray(varName.getVarName(), varName, false);
	}

	public void visit(VarNameOfSingle varName) {
		analyzeVarNameOfSingle(varName.getVarName(), varName, false);
	}

	public void visit(VarNameOfArray varName) {
		analyzeVarNameOfArray(varName.getVarName(), varName, false);
	}

	public void visit(RecordName recordName) {
		if (Tab.currentScope().findSymbol(recordName.getRecordName()) != null) {
			report_error("Ime " + recordName.getRecordName() + " se vec koristi u trenutnom opsegu.", recordName);
			recordName.obj = Tab.noObj;
		} else {
			recordName.obj = Tab.insert(Obj.Type, recordName.getRecordName(), new Struct(Struct.Class));
		}
		Tab.openScope();
	}

	public void visit(RecordDecl recordDecl) {
		Obj recordObj = recordDecl.getRecordName().obj;
		if (recordObj != Tab.noObj) {
			Tab.chainLocalSymbols(recordObj.getType());
			records.put(recordObj.getName(), recordObj.getType());
		}
		Tab.closeScope();
	}

	public void visit(FieldType fieldType) {
		Struct typeStruct = fieldType.getType().obj.getType();
		currentVarType = typeStruct;
	}

	public void visit(FieldNameOfSingle fieldName) {
		analyzeVarNameOfSingle(fieldName.getVarName(), fieldName, true);
	}

	public void visit(FieldNameOfArray fieldName) {
		analyzeVarNameOfArray(fieldName.getVarName(), fieldName, true);
	}

	public void visit(MethodReturnsValue retVal) {
		methRetType = retVal.getType().obj.getType();
	}

	public void visit(MethodReturnsVoid retVal) {
		methRetType = ExtendedTab.voidType;
	}

	private boolean isOverridingMethod(MethodSignature methodSig) {
		if (currentSuperclassNode == null) {
			return false;
		}

		if (overriddenMethods.containsKey(methodSig.getMethodName())) {
			return false;
		}

		Obj superMethodObj = null;
		for (Obj obj : currentSuperclass.getType().getMembers()) {
			if (obj.getName().equals(methodSig.getMethodName())) {
				superMethodObj = obj;
				break;
			}
		}

		if (superMethodObj == null || superMethodObj.getKind() != Obj.Meth) {
			return false;
		}

		if (classConstructors.get(currentSuperclass.getName()) == superMethodObj) {
			return false;
		}

		int parIndex = 0;
		for (Obj superFormPar : superMethodObj.getLocalSymbols()) {
			if (superFormPar.getFpPos() == 0) {
				continue;
			} else if (parIndex < formPars.size()) {
				Obj newFormPar = formPars.get(parIndex++);
				if (!superFormPar.getType().equals(newFormPar.getType())) {
					return false;
				}
			} else {
				return false;
			}
		}

		return parIndex == formPars.size();
	}

	public void visit(MethodSignature methodSig) {
		Obj existingMet = Tab.currentScope().findSymbol(methodSig.getMethodName());
		if (existingMet != null) {
			if (existingMet.getKind() == Obj.Meth && existingMet.getType().equals(methRetType)
					&& isOverridingMethod(methodSig)) {
				existingMet.setLocals(new HashTableDataStructure());
				methodSig.obj = existingMet;
				methName = methodSig.getMethodName();
				overriddenMethods.put(methName, true);
			} else {
				report_error("Ime " + methodSig.getMethodName() + " se vec koristi u trenutnom opsegu.", methodSig);
				methodSig.obj = Tab.noObj;
			}
		} else {
			methodSig.obj = Tab.insert(Obj.Meth, methodSig.getMethodName(), methRetType);
			methName = methodSig.getMethodName();
		}
		Tab.openScope();

		if (declaringClass) {
			Tab.insert(Obj.Var, "this", currentClass != null ? currentClass.getType() : Tab.noType);
		} else {
			if (methRetType == ExtendedTab.voidType && formPars.size() == 0
					&& methodSig.getMethodName().equals("main")) {
				mainFound = true;
			}
		}
		int parsAdded = 1;

		for (Obj formPar : formPars) {

			if (Tab.currentScope().findSymbol(formPar.getName()) != null) {
				report_error("Ime " + formPar.getName() + " se vec koristi u trenutnom opsegu.", methodSig);
			} else {
				Obj parObj = Tab.insert(Obj.Var, formPar.getName(), formPar.getType());
				parObj.setFpPos(parsAdded++);
			}

		}

		formPars.clear();
	}

	public void visit(MethodDecl methodDecl) {
		Obj methodObj = methodDecl.getMethodSignature().obj;
		if (methodObj != Tab.noObj) {
			Tab.chainLocalSymbols(methodObj);
		}
		Tab.closeScope();
		methName = null;
	}

	public void visit(FormalParamOfSingle formPar) {
		formPars.add(new Obj(Obj.Var, formPar.getParName(), formPar.getType().obj.getType()));
	}

	public void visit(FormalParamOfArray formPar) {
		formPars.add(new Obj(Obj.Var, formPar.getParName(), new Struct(Struct.Array, formPar.getType().obj.getType())));
	}

	public void visit(ClassName className) {
		if (Tab.currentScope().findSymbol(className.getClassName()) != null) {
			report_error("Ime " + className.getClassName() + " se vec koristi u trenutnom opsegu.", className);
			className.obj = Tab.noObj;
		} else {
			className.obj = Tab.insert(Obj.Type, className.getClassName(), new Struct(Struct.Class));
			currentClass = className.obj;
		}
		declaringClass = true;
		Tab.openScope();
	}

	public void visit(DoesNotExtend e) {
		if (currentClass != null) {
			currentClassNode = classTree.insertChild(currentClass.getName(), currentClass.getType());
		}
	}

	public void visit(DoesExtend doesExtend) {
		Struct superType = doesExtend.getType().obj.getType();
		if (superType != Tab.noType) {
			if (superType.getKind() != Struct.Class) {
				report_error("Tip " + doesExtend.getType().getTypeName() + " iz kojeg se izvodi mora biti klasa.",
						doesExtend);
			} else if (records.containsKey(doesExtend.getType().getTypeName())) {
				report_error("Tip " + doesExtend.getType().getTypeName() + " iz kojeg se izvodi mora biti klasa.",
						doesExtend);
			} else {
				currentSuperclass = Tab.find(doesExtend.getType().getTypeName());
				currentSuperclassNode = classTree.find(currentSuperclass.getName());
				if (currentClass != null) {
					currentClassNode = currentSuperclassNode.insertChild(currentClass.getName(),
							currentClass.getType());
				}
			}
		} else {
			report_error("Tip " + doesExtend.getType().getTypeName() + " iz kojeg se izvodi ne postoji.", doesExtend);
		}
	}

	public void visit(ClassBodyStart bodyStart) {
		if (currentSuperclass != null) {
			Struct superType = currentSuperclass.getType();
			for (Obj obj : superType.getMembers()) {
				if (obj.getKind() == Obj.Fld) {
					Obj newObj = Tab.insert(Obj.Fld, obj.getName(), obj.getType());
					newObj.setAdr(newObj.getAdr() + 1);
				}
			}
		}
	}

	private void copyMethodsFromSuperclass(SyntaxNode node) {
		if (currentSuperclass != null) {
			boolean lookingForConstructor = classConstructors.containsKey(currentSuperclass.getName());
			boolean foundConstructor = false;
			Struct superType = currentSuperclass.getType();
			for (Obj obj : superType.getMembers()) {
				if (obj.getKind() == Obj.Meth) {
					String newMethName = obj.getName();
					if (lookingForConstructor && newMethName.equals(currentSuperclass.getName())) {
						lookingForConstructor = false;

						if (currentClass == null) {
							continue;
						}

						newMethName = currentClass.getName();
						foundConstructor = true;
					}
					Obj existingObj = Tab.currentScope().findSymbol(newMethName);
					if (existingObj != null) {
						report_error("Ime " + newMethName + " se vec koristi u trenutnom opsegu.", node);
					} else {
						Obj newMethObj = Tab.insert(Obj.Meth, newMethName, obj.getType());
						Tab.openScope();
						for (Obj locSym : obj.getLocalSymbols()) {
							if (locSym.getName().equals("this")) {
								Tab.insert(locSym.getKind(), "this",
										currentClass != null ? currentClass.getType() : Tab.noType);
							} else {
								Obj paramObj = Tab.insert(locSym.getKind(), locSym.getName(), locSym.getType());
								paramObj.setFpPos(locSym.getFpPos());
							}
						}
						Tab.chainLocalSymbols(newMethObj);
						Tab.closeScope();
						if (foundConstructor) {
							foundConstructor = false;
							classConstructors.put(currentClass.getName(), newMethObj);
						}
					}
				}
			}

		}
	}

	public void visit(ClassMethodsStart metStart) {
		copyMethodsFromSuperclass(metStart);
	}

	public void visit(ClassNoMethods noMets) {
		copyMethodsFromSuperclass(noMets);
	}

	public void visit(ConstructorName constName) {
		if (currentClass != null) {
			if (constName.getConstName().equals(currentClass.getName())) {
				Obj existingMet = Tab.currentScope().findSymbol(constName.getConstName());
				if (existingMet != null) {
					// superclass constructor
					if (classConstructors.containsKey(currentClass.getName())
							&& classConstructors.get(currentClass.getName()).equals(existingMet)) {
						existingMet.setLocals(new HashTableDataStructure());
						constName.obj = existingMet;
					} else {
						report_error("Ime " + constName.getConstName() + " se vec koristi u trenutnom opsegu.",
								constName);
						constName.obj = Tab.noObj;
					}

				} else {
					constName.obj = Tab.insert(Obj.Meth, constName.getConstName(), Tab.noType);
					classConstructors.put(currentClass.getName(), constName.obj);
				}
			} else {
				report_error("Konstruktor mora biti istog imena kao klasa.", constName);
				constName.obj = Tab.noObj;
			}
		} else {
			constName.obj = Tab.noObj;
		}
		Tab.openScope();

		Tab.insert(Obj.Var, "this", currentClass != null ? currentClass.getType() : Tab.noType);
		inConstructor = true;
	}

	public void visit(ConstructorDecl constDecl) {
		if (constDecl.getConstructorName().obj != Tab.noObj) {
			Tab.chainLocalSymbols(constDecl.getConstructorName().obj);
		}
		Tab.closeScope();
		inConstructor = false;
	}

	public void visit(ClassDecl classDecl) {
		Obj classObj = classDecl.getClassName().obj;
		if (classObj != Tab.noObj) {
			Tab.chainLocalSymbols(classObj.getType());
		}
		declaringClass = false;
		currentClass = null;
		currentSuperclass = null;
		currentClassNode = null;
		currentSuperclassNode = null;
		overriddenMethods.clear();
		Tab.closeScope();
	}

	public void visit(Type type) {
		// TODO Pretraga samo tipova
		Obj typeObj = Tab.find(type.getTypeName());
		if (typeObj == Tab.noObj) {
			report_error("Tip " + type.getTypeName() + " ne postoji.", type);
			type.obj = Tab.noObj;
		} else if (typeObj.getKind() != Obj.Type) {
			report_error("Ime " + type.getTypeName() + " ne oznacava tip.", type);
			type.obj = Tab.noObj;
		} else {
			type.obj = typeObj;
		}
	}

	public void visit(NamedDesignator design) {
		Obj designated = Tab.find(design.getDesignName());
		if (designated == Tab.noObj) {
			report_error("Ime " + design.getDesignName() + " ne postoji.", design);
			design.obj = Tab.noObj;
		} else {
			if (designated.getKind() == Obj.Fld) {
				report_info("Detektovan pristup polju klase", design);
				report_info("\t" + ExtendedTab.printObj(designated), null);
			} else if (designated.getKind() == Obj.Var) {
				if (designated.getLevel() == 0) {
					report_info("Detektovan pristup globalnoj promenljivoj", design);
					report_info("\t" + ExtendedTab.printObj(designated), null);
				} else if (designated.getFpPos() != 0) {
					report_info("Detektovan pristup formalnom parametru metode", design);
					report_info("\t" + ExtendedTab.printObj(designated), null);
				} else {
					report_info("Detektovan pristup lokalnoj promenljivoj", design);
					report_info("\t" + ExtendedTab.printObj(designated), null);
				}
			}
			design.obj = designated;
		}
	}

	public void visit(ThisDesignator design) {
		if (declaringClass) {
			Obj thisObj = Tab.currentScope().findSymbol("this");
			if (thisObj != null) {
				report_info("Detektovan pristup formalnom parametru metode", design);
				report_info("\t" + ExtendedTab.printObj(thisObj), null);
				design.obj = thisObj;
			} else {
				report_error("This ne postoji, a trebalo bi. Ne znam kako.", design);
				design.obj = Tab.noObj;
			}
		} else {
			report_error("Koriscenje this izvan metode klase.", design);
			design.obj = Tab.noObj;
		}
	}

	public void visit(AddressingMember member) {
		Obj leftObj = member.getDesignator().obj;
		if ((leftObj.getKind() != Obj.Var && leftObj.getKind() != Obj.Fld && leftObj.getKind() != Obj.Elem)
				|| leftObj.getType().getKind() != Struct.Class) {
			report_error("Pristup clanovima moze se raditi samo nad objektim klase.", member);
			member.obj = Tab.noObj;
		} else {
			Obj memberObj = null;
			if (member.getDesignator() instanceof ThisDesignator) {
				Scope classScope = Tab.currentScope().getOuter();
				memberObj = classScope.findSymbol(member.getMemberName());
			} else {
				memberObj = leftObj.getType().getMembersTable().searchKey(member.getMemberName());
			}
			if (memberObj != null) {
				if (memberObj.getKind() == Obj.Fld) {
					report_info("Detektovan pristup polju klase", member);
					report_info("\t" + ExtendedTab.printObj(memberObj), null);
				} else if (memberObj.getKind() == Obj.Var) {
					if (memberObj.getLevel() == 0) {
						report_info("Detektovan pristup globalnoj promenljivoj", member);
						report_info("\t" + ExtendedTab.printObj(memberObj), null);
					} else if (memberObj.getFpPos() != 0) {
						report_info("Detektovan pristup formalnom parametru metode", member);
						report_info("\t" + ExtendedTab.printObj(memberObj), null);
					} else {
						report_info("Detektovan pristup lokalnoj promenljivoj", member);
						report_info("\t" + ExtendedTab.printObj(memberObj), null);
					}
				}
				member.obj = memberObj;
			} else {
				report_error(
						"Polje " + member.getMemberName() + " ne postoji u klasi objekta " + leftObj.getName() + ".",
						member);
				member.obj = Tab.noObj;
			}
		}
	}

	public void visit(AddressingElem member) {
		Obj leftObj = member.getDesignator().obj;
		if (leftObj.getType().getKind() != Struct.Array) {
			report_error("Pristup elementima moze se raditi samo nad nizovima.", member);
			member.obj = Tab.noObj;
		} else {
			Expr expr = member.getExpr();
			if (expr.struct.equals(Tab.intType)) {
				report_info("Detektovan pristup elementu niza", member);
				report_info("\t" + ExtendedTab.printObj(leftObj), null);
				member.obj = new Obj(Obj.Elem, "#", leftObj.getType().getElemType());
			} else {
				report_error("Indeks pri pristupu elementima mora biti int.", member);
				member.obj = Tab.noObj;
			}
		}
	}

	public void visit(DesignOfVarFactor factor) {
		Obj factorObj = factor.getDesignator().obj;
		if (factorObj.getKind() != Obj.Con && factorObj.getKind() != Obj.Elem && factorObj.getKind() != Obj.Fld
				&& factorObj.getKind() != Obj.Var) {
			report_error("Ime " + (factorObj == Tab.noObj ? "" : factorObj.getName())
					+ " ne predstavlja promenljivu, konstantu ili element niza.", factor);
			factor.struct = Tab.noType;
		} else {
			factor.struct = factorObj.getType();
		}
	}

	public void visit(ActualParameters param) {
		actPars.add(param.getExpr().struct);
	}

	public void visit(SingleActualParam param) {
		actPars.add(param.getExpr().struct);
	}

	private boolean equivTypes(Struct to, Struct from) {
		if (to == from) {
			return true;
		}
		if (to.getKind() == Struct.Array && from.getKind() == Struct.Array
				&& equivTypes(to.getElemType(), from.getElemType())) {
			return true;
		}

		return false;
	}

	private boolean compatTypes(Struct to, Struct from) {
		if (equivTypes(to, from)) {
			return true;
		}

		if ((to.isRefType() && from == Tab.nullType) || (to == Tab.nullType && from.isRefType())) {
			return true;
		}

		return false;
	}

	private boolean compatAssignTypes(Struct to, Struct from) {
		if (equivTypes(to, from)) {
			return true;
		}

		if (to.isRefType() && from == Tab.nullType) {
			return true;
		}

		if (to.getKind() == Struct.Class && from.getKind() == Struct.Class) {
			ClassTree toNode = classTree.find(to);
			if (toNode != null && toNode.find(from) != null) {
				return true;
			}
		}

		return false;
	}

	private boolean checkActParams(Obj meth) {
		if (meth == Tab.lenObj && actPars.size() == 1 && actPars.get(0).getKind() == Struct.Array) {
			return true;
		}

		int parIndex = 0;
		for (Obj methFormPar : meth.getLocalSymbols()) {
			if (methFormPar.getFpPos() == 0) {
				continue;
			} else if (parIndex < actPars.size()) {
				Struct actPar = actPars.get(parIndex++);
				if (!compatAssignTypes(methFormPar.getType(), actPar)) {
					return false;
				}
			} else {
				return false;
			}
		}

		return parIndex == actPars.size();
	}

	public void visit(DesignOfMethodFactor factor) {
		Obj factorObj = factor.getDesignator().obj;
		if (factorObj.getKind() != Obj.Meth) {
			report_error("Ime " + (factorObj == Tab.noObj ? "" : factorObj.getName()) + " ne predstavlja metodu.",
					factor);
			factor.struct = Tab.noType;
		} else {
			if (factorObj.getLevel() == 0) {
				report_info("Detektovan pristup globalnoj metodi", factor);
				report_info("\t" + ExtendedTab.printObj(factorObj), null);
			} else {
				report_info("Detektovan pristup metodi klase", factor);
				report_info("\t" + ExtendedTab.printObj(factorObj), null);
			}
			if (checkActParams(factorObj)) {
				factor.struct = factorObj.getType();
			} else {
				report_error("Neispravni stvarni argumenti.", factor);
				factor.struct = Tab.noType;
			}
		}
		actPars.clear();
	}

	public void visit(SuperMethodFactor factor) {
		if (declaringClass) {
			if (currentSuperclass != null) {
				if (inConstructor) {
					if (classConstructors.containsKey(currentSuperclass.getName())) {
						Obj superMeth = classConstructors.get(currentSuperclass.getName());
						report_info("Detektovan pristup konstruktoru nadklase preko super", factor);
						report_info("\t" + ExtendedTab.printObj(superMeth), null);
						if (actPars.size() == 0) {
							factor.struct = superMeth.getType();
						} else {
							report_error("Neispravni stvarni argumenti.", factor);
							factor.struct = Tab.noType;
						}
					} else {
						report_error("Nadklasa ne sadrzi trenutnu metodu.", factor);
						factor.struct = Tab.noType;
					}
				} else {
					if (methName != null) {
						Obj superMethodObj = null;
						for (Obj obj : currentSuperclass.getType().getMembers()) {
							if (obj.getName().equals(methName)) {
								superMethodObj = obj;
								break;
							}
						}

						if (superMethodObj == null || superMethodObj.getKind() != Obj.Meth) {
							report_error("Nadklasa ne sadrzi trenutnu metodu.", factor);
							factor.struct = Tab.noType;
						}

						if (checkActParams(superMethodObj)) {
							factor.struct = superMethodObj.getType();
						} else {
							report_error("Neispravni stvarni argumenti.", factor);
							factor.struct = Tab.noType;
						}
					} else {
						report_error("Nadklasa ne sadrzi trenutnu metodu.", factor);
						factor.struct = Tab.noType;
					}
				}
			} else {
				report_error("Koriscenje super bez postojanja nadklase.", factor);
				factor.struct = Tab.noType;
			}
		} else {
			report_error("Koriscenje super izvan metode klase.", factor);
			factor.struct = Tab.noType;
		}
		actPars.clear();
	}

	public void visit(ConstFactor factor) {
		factor.struct = factor.getConst().obj.getType();
	}

	public void visit(NewObjectFactor factor) {
		if (factor.getType().obj.getType().getKind() == Struct.Class) {
			report_info("Detektovano kreiranje objekta klase", factor);
			report_info("\t" + ExtendedTab.printObj(Tab.find(factor.getType().getTypeName())), null);
			factor.struct = factor.getType().obj.getType();
		} else {
			report_error("Tip " + factor.getType().getTypeName() + " ne predstavlja klasu.", factor);
			factor.struct = Tab.noType;
		}
	}

	public void visit(NewArrayFactor factor) {
		Expr expr = factor.getExpr();
		if (expr.struct.equals(Tab.intType)) {
			factor.struct = new Struct(Struct.Array, factor.getType().obj.getType());
		} else {
			report_error("Velicina niza pri kreiranju mora biti int.", factor);
			factor.struct = Tab.noType;
		}
	}

	public void visit(ParenFactor factor) {
		factor.struct = factor.getExpr().struct;
	}

	public void visit(TermFactor term) {
		term.struct = term.getFactor().struct;
	}

	public void visit(TermWithMulop term) {
		if (term.getTerm().struct.equals(Tab.intType) && term.getFactor().struct.equals(Tab.intType)) {
			term.struct = Tab.intType;
		} else {
			report_error("Operandi za *, /, i % moraju biti int.", term);
			term.struct = Tab.noType;
		}
	}

	public void visit(ExprTerm expr) {
		expr.struct = expr.getTerm().struct;
	}

	public void visit(ExprWithAddop expr) {
		if (expr.getExpression().struct.equals(Tab.intType) && expr.getTerm().struct.equals(Tab.intType)) {
			expr.struct = Tab.intType;
		} else {
			report_error("Operandi za + i - moraju biti int.", expr);
			expr.struct = Tab.noType;
		}
	}

	public void visit(PositiveExpr expr) {
		expr.struct = expr.getExpression().struct;
	}

	public void visit(NegativeExpr expr) {
		if (expr.getExpression().struct.equals(Tab.intType)) {
			expr.struct = Tab.intType;
		} else {
			report_error("Negacija se moze raditi samo nad int.", expr);
			expr.struct = Tab.noType;
		}
	}

	public void visit(CondFactExpr fact) {
		if (fact.getExpr().struct != ExtendedTab.boolType) {
			report_error("Operandi u uslovnom iskazu moraju biti bool.", fact);
		}
	}

	public void visit(InstanceofCondFact fact) {
		if (fact.getDesignator().obj.getType().getKind() != Struct.Class
				|| fact.getType().obj.getType().getKind() != Struct.Class
				|| records.containsKey(fact.getType().obj.getName())) {
			report_error("Operacija instanceof se moze raditi samo nad klasama.", fact);
		}
	}

	public void visit(RelopCondFact fact) {
		if (!compatTypes(fact.getExpr().struct, fact.getExpr1().struct)) {
			report_error("Operandi u uslovnom iskazu moraju biti kompatibilni.", fact);
		} else if (fact.getExpr().struct.isRefType() && !(fact.getRelop() instanceof RelopEq)
				&& !(fact.getRelop() instanceof RelopNeq)) {
			report_error("Uz promenljive tipa klase ili niza, od relacionih operatora, mogu se koristiti samo != i ==.",
					fact);
		}
	}

	public void visit(DoWhileLoopStart loop) {
		++nestedLoops;
	}

	public void visit(DoWhileStatement loop) {
		--nestedLoops;
	}

	public void visit(BreakStatement stmt) {
		if (nestedLoops == 0) {
			report_error("Koriscenje break je dozvoljeno samo unutar petlji.", stmt);
		}
	}

	public void visit(ContinueStatement stmt) {
		if (nestedLoops == 0) {
			report_error("Koriscenje continue je dozvoljeno samo unutar petlji.", stmt);
		}
	}

	public void visit(MethodCall call) {
		Obj methObj = call.getDesignator().obj;
		if (methObj.getKind() != Obj.Meth) {
			report_error("Ime " + (methObj == Tab.noObj ? "" : methObj.getName()) + " ne predstavlja metodu.", call);
		} else {
			if (methObj.getLevel() == 0) {
				report_info("Detektovan pristup globalnoj metodi", call);
				report_info("\t" + ExtendedTab.printObj(methObj), null);
			} else {
				report_info("Detektovan pristup metodi klase", call);
				report_info("\t" + ExtendedTab.printObj(methObj), null);
			}
			if (!checkActParams(methObj)) {
				report_error("Neispravni stvarni argumenti.", call);
			}
		}
		actPars.clear();
	}

	public void visit(SuperMethodCall factor) {
		if (declaringClass) {
			if (currentSuperclass != null) {
				if (inConstructor) {
					if (classConstructors.containsKey(currentSuperclass.getName())) {
						report_info("Detektovan pristup konstruktoru nadklase preko super", factor);
						report_info("\t" + ExtendedTab.printObj(classConstructors.get(currentSuperclass.getName())),
								null);
						if (actPars.size() != 0) {
							report_error("Neispravni stvarni argumenti.", factor);
						}
					} else {
						report_error("Nadklasa ne sadrzi trenutnu metodu.", factor);
					}
				} else {
					if (methName != null) {
						Obj superMethodObj = null;
						for (Obj obj : currentSuperclass.getType().getMembers()) {
							if (obj.getName().equals(methName)) {
								superMethodObj = obj;
								break;
							}
						}

						if (superMethodObj == null || superMethodObj.getKind() != Obj.Meth) {
							report_error("Nadklasa ne sadrzi trenutnu metodu.", factor);
						}

						if (!checkActParams(superMethodObj)) {
							report_error("Neispravni stvarni argumenti.", factor);
						}
					} else {
						report_error("Nadklasa ne sadrzi trenutnu metodu.", factor);
					}
				}
			} else {
				report_error("Koriscenje super bez postojanja nadklase.", factor);
			}
		} else {
			report_error("Koriscenje super izvan metode klase.", factor);
		}
		actPars.clear();
	}

	public void visit(IntConst intConst) {
		intConst.obj = new Obj(Obj.Con, "#", Tab.intType);
		intConst.obj.setAdr(intConst.getVal());
	}

	public void visit(CharConst charConst) {
		charConst.obj = new Obj(Obj.Con, "#", Tab.charType);
		charConst.obj.setAdr(charConst.getVal());
	}

	public void visit(BoolConst boolConst) {
		boolConst.obj = new Obj(Obj.Con, "#", ExtendedTab.boolType);
		boolConst.obj.setAdr(boolConst.getVal() ? 1 : 0);
	}

	public void visit(AssStatement ass) {
		Obj leftObj = ass.getDesignator().obj;
		if (leftObj.getKind() != Obj.Var && leftObj.getKind() != Obj.Elem && leftObj.getKind() != Obj.Fld) {
			report_error("Leva strana pri dodeli mora biti promenljiva, element niza ili polje klase.", ass);
		} else if (!compatAssignTypes(leftObj.getType(), ass.getExpr().struct)) {
			report_error("Tipovi moraju biti kompatibilni pri dodeli.", ass);
		}
	}

	public void visit(IncStatement inc) {
		Obj leftObj = inc.getDesignator().obj;
		if (leftObj.getKind() != Obj.Var && leftObj.getKind() != Obj.Elem && leftObj.getKind() != Obj.Fld) {
			report_error("Leva strana pri inkrementiranju mora biti promenljiva, element niza ili polje klase.", inc);
		} else if (!leftObj.getType().equals(Tab.intType)) {
			report_error("Tip pri inkrementiranju mora biti int.", inc);
		}
	}

	public void visit(DecStatement dec) {
		Obj leftObj = dec.getDesignator().obj;
		if (leftObj.getKind() != Obj.Var && leftObj.getKind() != Obj.Elem && leftObj.getKind() != Obj.Fld) {
			report_error("Leva strana pri dekrementiranju mora biti promenljiva, element niza ili polje klase.", dec);
		} else if (!leftObj.getType().equals(Tab.intType)) {
			report_error("Tip pri dekrementiranju mora biti int.", dec);
		}
	}

	public void visit(ReturnVoidStatement ret) {
		if (inConstructor) {
			report_error("Koriscenje return u konstruktoru nije dozvoljeno.", ret);
		} else if (methRetType != ExtendedTab.voidType) {
			report_error("Tip povratne vrednosti nije ekvivalentan sa tipom metode.", ret);
		}
	}

	public void visit(ReturnValStatement ret) {
		if (inConstructor) {
			report_error("Koriscenje return u konstruktoru nije dozvoljeno.", ret);
		} else if (!equivTypes(methRetType, ret.getExpr().struct)) {
			report_error("Tip povratne vrednosti nije ekvivalentan sa tipom metode.", ret);
		}
	}

	public void visit(ReadStatement stmt) {
		Obj leftObj = stmt.getDesignator().obj;
		if (leftObj.getKind() != Obj.Var && leftObj.getKind() != Obj.Elem && leftObj.getKind() != Obj.Fld) {
			report_error("Citanje se moze vrsiti samo u promenljivu, element niza ili polje klase.", stmt);
		} else if (leftObj.getType() != Tab.charType && leftObj.getType() != Tab.intType
				&& leftObj.getType() != ExtendedTab.boolType) {
			report_error("Mogu se citati samo int, char i bool.", stmt);
		}
	}

	public void visit(PrintStatement stmt) {
		Struct exprStruct = stmt.getExpr().struct;
		if (exprStruct != Tab.charType && exprStruct != Tab.intType && exprStruct != ExtendedTab.boolType) {
			report_error("Mogu se ispisivati samo int, char i bool.", stmt);
		}
	}

	public void visit(PrintWithPadStatement stmt) {
		Struct exprStruct = stmt.getExpr().struct;
		if (exprStruct != Tab.charType && exprStruct != Tab.intType && exprStruct != ExtendedTab.boolType) {
			report_error("Mogu se ispisivati samo int, char i bool.", stmt);
		} else if (stmt.getConst().obj.getType() != Tab.intType) {
			report_error("Padding pri ispisu mora biti int.", stmt);
		}
	}

	public boolean passed() {
		return !errorDetected;
	}
}
