package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.AddressingElem;
import rs.ac.bg.etf.pp1.ast.AddressingMember;
import rs.ac.bg.etf.pp1.ast.BoolConst;
import rs.ac.bg.etf.pp1.ast.CharConst;
import rs.ac.bg.etf.pp1.ast.ClassBodyStart;
import rs.ac.bg.etf.pp1.ast.ClassDecl;
import rs.ac.bg.etf.pp1.ast.ClassMethodsStart;
import rs.ac.bg.etf.pp1.ast.ClassName;
import rs.ac.bg.etf.pp1.ast.ClassNoMethods;
import rs.ac.bg.etf.pp1.ast.Const;
import rs.ac.bg.etf.pp1.ast.ConstFactor;
import rs.ac.bg.etf.pp1.ast.ConstNameVal;
import rs.ac.bg.etf.pp1.ast.ConstType;
import rs.ac.bg.etf.pp1.ast.ConstructorDecl;
import rs.ac.bg.etf.pp1.ast.ConstructorName;
import rs.ac.bg.etf.pp1.ast.DesignOfMethodFactor;
import rs.ac.bg.etf.pp1.ast.DesignOfVarFactor;
import rs.ac.bg.etf.pp1.ast.DoesExtend;
import rs.ac.bg.etf.pp1.ast.DoesNotExtend;
import rs.ac.bg.etf.pp1.ast.Expr;
import rs.ac.bg.etf.pp1.ast.ExprTerm;
import rs.ac.bg.etf.pp1.ast.ExprWithAddop;
import rs.ac.bg.etf.pp1.ast.ExtendsError;
import rs.ac.bg.etf.pp1.ast.FieldNameOfArray;
import rs.ac.bg.etf.pp1.ast.FieldNameOfSingle;
import rs.ac.bg.etf.pp1.ast.FieldType;
import rs.ac.bg.etf.pp1.ast.FormalParamOfArray;
import rs.ac.bg.etf.pp1.ast.FormalParamOfSingle;
import rs.ac.bg.etf.pp1.ast.GlobalVarNameOfArray;
import rs.ac.bg.etf.pp1.ast.GlobalVarNameOfSingle;
import rs.ac.bg.etf.pp1.ast.IntConst;
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
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.ast.ProgramName;
import rs.ac.bg.etf.pp1.ast.RecordDecl;
import rs.ac.bg.etf.pp1.ast.RecordName;
import rs.ac.bg.etf.pp1.ast.SingleActualParam;
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
	int nVars;

	
	Struct currentVarType = null;

	Logger log = Logger.getLogger(getClass());

	Map<String, Struct> records = new HashMap<>();

	String methName = null;
	Struct methRetType = null;

	ClassTree classTree = new ClassTree("0", null);
	boolean declaringClass = false;

	Obj currentClass = null;
	Obj currentSuperclass = null;

	ClassTree currentClassNode = null;
	ClassTree currentSuperclassNode = null;

	boolean inConstructor = false;

	Map<String, Obj> classConstructors = new HashMap<String, Obj>();
	List<Obj> formPars = new ArrayList<>();
	List<Struct> actPars = new ArrayList<>();

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
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgramName().obj);
		Tab.closeScope();
	}

	public void visit(ProgramName programName) {
		programName.obj = Tab.insert(Obj.Prog, programName.getProgName(), Tab.noType);
		Tab.openScope();
	}

	public void visit(ConstType constType) {
		Struct typeStruct = constType.getType().struct;
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
			if (currentVarType.equals(c.struct)) {
				if (c instanceof IntConst) {
					val = ((IntConst) c).getVal();
				} else if (c instanceof CharConst) {
					val = ((CharConst) c).getVal();
				} else if (c instanceof BoolConst) {
					val = ((BoolConst) c).getVal() ? 1 : 0;
				}
			} else {
				report_error("Vrednost konstante " + constName + " nije kompatbilna sa svojim tipom.", constNameVal);
			}
			Obj constObj = Tab.insert(Obj.Con, constName, currentVarType);
			constObj.setAdr(val);
		}
	}

	public void visit(VarType varType) {
		Struct typeStruct = varType.getType().struct;
		currentVarType = typeStruct;
	}

	private void analyzeVarNameOfSingle(String varName, SyntaxNode node, boolean isField) {
		if (Tab.currentScope().findSymbol(varName) != null) {
			report_error("Ime " + varName + " se vec koristi u trenutnom opsegu.", node);
		} else {
			Tab.insert(isField ? Obj.Fld : Obj.Var, varName, currentVarType);
		}
	}

	private void analyzeVarNameOfArray(String varName, SyntaxNode node, boolean isField) {
		if (Tab.currentScope().findSymbol(varName) != null) {
			report_error("Ime " + varName + " se vec koristi u trenutnom opsegu.", node);
		} else {
			Tab.insert(isField ? Obj.Fld : Obj.Var, varName, new Struct(Struct.Array, currentVarType));
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
		Struct typeStruct = fieldType.getType().struct;
		currentVarType = typeStruct;
	}

	public void visit(FieldNameOfSingle fieldName) {
		analyzeVarNameOfSingle(fieldName.getVarName(), fieldName, true);
	}

	public void visit(FieldNameOfArray fieldName) {
		analyzeVarNameOfArray(fieldName.getVarName(), fieldName, true);
	}

	public void visit(MethodReturnsValue retVal) {
		methRetType = retVal.getType().struct;
	}

	public void visit(MethodReturnsVoid retVal) {
		methRetType = Tab.noType;
	}

	private boolean isOverridingMethod(MethodSignature methodSig) {
		if (currentSuperclassNode == null) {
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
		formPars.add(new Obj(Obj.Var, formPar.getParName(), formPar.getType().struct));
	}

	public void visit(FormalParamOfArray formPar) {
		formPars.add(new Obj(Obj.Var, formPar.getParName(), new Struct(Struct.Array, formPar.getType().struct)));
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
		Struct superType = doesExtend.getType().struct;
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
					Tab.insert(Obj.Fld, obj.getName(), obj.getType());
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
		Tab.closeScope();
	}

	public void visit(Type type) {
		// TODO Pretraga samo tipova
		Obj typeObj = Tab.find(type.getTypeName());
		if (typeObj == Tab.noObj) {
			report_error("Tip " + type.getTypeName() + " ne postoji.", type);
			type.struct = Tab.noType;
		} else if (typeObj.getKind() != Obj.Type) {
			report_error("Ime " + type.getTypeName() + " ne oznacava tip.", type);
			type.struct = Tab.noType;
		} else {
			type.struct = typeObj.getType();
		}
	}

	public void visit(NamedDesignator design) {
		Obj designated = Tab.find(design.getDesignName());
		if (designated == Tab.noObj) {
			report_error("Ime " + design.getDesignName() + " ne postoji.", design);
			design.obj = Tab.noObj;
		} else {
			design.obj = designated;
		}
	}

	public void visit(ThisDesignator design) {
		if (declaringClass) {
			Obj thisObj = Tab.currentScope().findSymbol("this");
			if (thisObj != null) {
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
		if (leftObj.getType().getKind() != Struct.Class) {
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

	public void visit(SingleActualParam param) {
		actPars.add(param.getExpr().struct);
	}

	private boolean equivAssignTypes(Struct to, Struct from) {
		if (to == from) {
			return true;
		}
		if (to.getKind() == Struct.Array && from.getKind() == Struct.Array && to.getElemType() == from.getElemType()) {
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
		int parIndex = 1;
		for (Obj methFormPar : meth.getLocalSymbols()) {
			if (methFormPar.getFpPos() == 0) {
				continue;
			} else if (parIndex < actPars.size()) {
				Struct actPar = actPars.get(parIndex++);
				if (!equivAssignTypes(methFormPar.getType(), actPar)) {
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
					if(classConstructors.containsKey(currentSuperclass.getName())) {
						Obj superMeth = classConstructors.get(currentSuperclass.getName());
						if(actPars.size() == 0) {
							factor.struct = superMeth.getType();
						}
						else {
							report_error("Neispravni stvarni argumenti.", factor);
							factor.struct = Tab.noType;
						}
					}
					else {
						report_error("Nadklasa ne sadrzi trenutnu metodu.", factor);
						factor.struct = Tab.noType;
					}
				} else {
					if(methName != null) {
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
						
						if(checkActParams(superMethodObj)) {
							factor.struct = superMethodObj.getType();
						}
						else {
							report_error("Neispravni stvarni argumenti.", factor);
							factor.struct = Tab.noType;
						}
					}
					else {
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
		factor.struct = factor.getConst().struct;
	}

	public void visit(NewObjectFactor factor) {
		if (factor.getType().struct.getKind() == Struct.Class) {
			factor.struct = factor.getType().struct;
		} else {
			report_error("Tip " + factor.getType().getTypeName() + " ne predstavlja klasu.", factor);
			factor.struct = Tab.noType;
		}
	}

	public void visit(NewArrayFactor factor) {
		Expr expr = factor.getExpr();
		if (expr.struct.equals(Tab.intType)) {
			factor.struct = factor.getType().struct;
		} else {
			report_error("Indeks pri pristupu elementima mora biti int.", factor);
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

	public void visit(IntConst intConst) {
		intConst.struct = Tab.intType;
	}

	public void visit(CharConst charConst) {
		charConst.struct = Tab.charType;
	}

	public void visit(BoolConst boolConst) {
		boolConst.struct = ExtendedTab.boolType;
	}

	public boolean passed() {
		return !errorDetected;
	}
}
