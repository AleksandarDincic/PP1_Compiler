package rs.ac.bg.etf.pp1;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ClassTree.Method;
import rs.ac.bg.etf.pp1.ast.BoolConst;
import rs.ac.bg.etf.pp1.ast.CharConst;
import rs.ac.bg.etf.pp1.ast.ClassBodyStart;
import rs.ac.bg.etf.pp1.ast.ClassDecl;
import rs.ac.bg.etf.pp1.ast.ClassMethodsStart;
import rs.ac.bg.etf.pp1.ast.ClassName;
import rs.ac.bg.etf.pp1.ast.ClassNoMethods;
import rs.ac.bg.etf.pp1.ast.Const;
import rs.ac.bg.etf.pp1.ast.ConstNameVal;
import rs.ac.bg.etf.pp1.ast.ConstType;
import rs.ac.bg.etf.pp1.ast.ConstructorDecl;
import rs.ac.bg.etf.pp1.ast.ConstructorName;
import rs.ac.bg.etf.pp1.ast.DoesExtend;
import rs.ac.bg.etf.pp1.ast.DoesNotExtend;
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
import rs.ac.bg.etf.pp1.ast.MethodName;
import rs.ac.bg.etf.pp1.ast.MethodReturnsValue;
import rs.ac.bg.etf.pp1.ast.MethodReturnsVoid;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.ast.ProgramName;
import rs.ac.bg.etf.pp1.ast.RecordDecl;
import rs.ac.bg.etf.pp1.ast.RecordName;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.Type;
import rs.ac.bg.etf.pp1.ast.VarNameOfArray;
import rs.ac.bg.etf.pp1.ast.VarNameOfSingle;
import rs.ac.bg.etf.pp1.ast.VarType;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.HashTableDataStructure;

public class SemanticAnalyzer extends VisitorAdaptor {
	boolean errorDetected = false;
	int nVars;

	Struct currentVarType = null;

	Logger log = Logger.getLogger(getClass());

	Map<String, Struct> records = new HashMap<>();

	Struct methRetType = null;

	// Collection<Obj> superclassMembers = null;

	ClassTree classTree = new ClassTree("0");
	Obj currentClass = null;
	Obj currentSuperclass = null;
	Map<String, Obj> classConstructors = new HashMap<String, Obj>();

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

	public void visit(MethodName methodName) {
		Obj existingMet = Tab.currentScope().findSymbol(methodName.getMethodName());
		if (existingMet != null) {
			if (currentSuperclass != null && existingMet.getKind() == Obj.Meth
					&& existingMet.getType().equals(methRetType)) {
				existingMet.setLocals(new HashTableDataStructure());
			} else {
				report_error("Ime " + methodName.getMethodName() + " se vec koristi u trenutnom opsegu.", methodName);
				methodName.obj = Tab.noObj;
			}
		} else {
			methodName.obj = Tab.insert(Obj.Meth, methodName.getMethodName(), methRetType);
		}
		Tab.openScope();

		if (currentClass != null) {
			Tab.insert(Obj.Var, "this", currentClass.getType());
		}
	}

	public void visit(MethodDecl methodDecl) {
		Obj methodObj = methodDecl.getMethodName().obj;
		if (methodObj != Tab.noObj) {
			Tab.chainLocalSymbols(methodObj);
		}
		Tab.closeScope();
	}

	public void visit(FormalParamOfSingle formPar) {
		if (Tab.currentScope().findSymbol(formPar.getParName()) != null) {
			report_error("Ime " + formPar.getParName() + " se vec koristi u trenutnom opsegu.", formPar);
		} else {
			Tab.insert(Obj.Var, formPar.getParName(), formPar.getType().struct);
		}
	}

	public void visit(FormalParamOfArray formPar) {
		if (Tab.currentScope().findSymbol(formPar.getParName()) != null) {
			report_error("Ime " + formPar.getParName() + " se vec koristi u trenutnom opsegu.", formPar);
		} else {
			Tab.insert(Obj.Var, formPar.getParName(), new Struct(Struct.Array, formPar.getType().struct));
		}
	}

	public void visit(ClassName className) {
		if (Tab.currentScope().findSymbol(className.getClassName()) != null) {
			report_error("Ime " + className.getClassName() + " se vec koristi u trenutnom opsegu.", className);
			className.obj = Tab.noObj;
		} else {
			className.obj = Tab.insert(Obj.Type, className.getClassName(), new Struct(Struct.Class));
			currentClass = className.obj;
		}
		Tab.openScope();
	}

	public void visit(DoesNotExtend e) {
		if (currentClass != null) {
			classTree.insertChild(currentClass.getName());
		}
	}

	public void visit(ExtendsError e) {
		if (currentClass != null) {
			classTree.insertChild(currentClass.getName());
		}
	}

	public void visit(DoesExtend doesExtend) {
		Struct superType = doesExtend.getType().struct;
		if (superType != Tab.noType) {
			if (superType.getKind() != Struct.Class) {
				// mora biti klasa
			} else if (records.containsKey(doesExtend.getType().getTypeName())) {
				// ne moze se izvoditi is record
			} else {
				currentSuperclass = Tab.find(doesExtend.getType().getTypeName());
				if (currentClass != null) {
					ClassTree superclassTreeNode = classTree.find(currentSuperclass.getName());
					superclassTreeNode.insertChild(currentClass.getName());
				}
			}
		} else {
			// Tip natklase ne postoji
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

	private void copyMethodsFromSuperclass() {
		if (currentSuperclass != null) {
			ClassTree superclassNode = classTree.find(currentSuperclass.getName());
			ClassTree classNode = null;
			if (currentClass != null) {
				classNode = superclassNode.find(currentClass.getName());
			}

			boolean lookingForConstructor = classConstructors.containsKey(currentSuperclass.getName());
			Struct superType = currentSuperclass.getType();
			for (Obj obj : superType.getMembers()) {
				if (obj.getKind() == Obj.Meth) {
					String newMethName = obj.getName();
					if (lookingForConstructor && obj.getName().equals(currentSuperclass.getName())) {
						newMethName = currentClass.getName();
						lookingForConstructor = false;
					}
					Obj newMethObj = Tab.insert(Obj.Meth, newMethName, obj.getType());
					Tab.openScope();
					for (Obj locSym : obj.getLocalSymbols()) {
						if (locSym.getName().equals("this")) {
							Tab.insert(locSym.getKind(), "this", currentClass.getType());
						} else {
							Tab.insert(locSym.getKind(), locSym.getName(), locSym.getType());
						}
					}
					Tab.chainLocalSymbols(newMethObj);
					Tab.closeScope();
					if (classNode != null) {
						Method newMethodNode = classNode.insertMethod(newMethName);
						Method copiedMethodNode = superclassNode.findMethod(obj.getName());
						newMethodNode.setFormPars(copiedMethodNode.getFormPars());
					}
				}
			}

		}
	}

	public void visit(ClassMethodsStart metStart) {
		copyMethodsFromSuperclass();
	}

	public void visit(ClassNoMethods noMets) {
		copyMethodsFromSuperclass();
	}

	public void visit(ConstructorName constName) {
		if (currentClass != null) {
			if (constName.getConstName().equals(currentClass.getName())) {
				if (currentSuperclass != null && classConstructors.containsKey(currentSuperclass.getName())) {
					Obj existingMet = Tab.currentScope().findSymbol(currentClass.getName());
					existingMet.setLocals(new HashTableDataStructure());
					constName.obj = existingMet;
				} else {
					constName.obj = Tab.insert(Obj.Meth, constName.getConstName(), Tab.noType);
				}
				classConstructors.put(currentClass.getName(), constName.obj);
			} else {
				report_error("Konstruktor nije istog imena kao klasa.", constName);
				constName.obj = Tab.noObj;
			}
		} else {
			constName.obj = Tab.noObj;
		}
		Tab.openScope();

		Tab.insert(Obj.Var, "this", currentClass != null ? currentClass.getType() : Tab.noType);
	}

	public void visit(ConstructorDecl constDecl) {
		if (constDecl.getConstructorName().obj != Tab.noObj) {
			Tab.chainLocalSymbols(constDecl.getConstructorName().obj);
		}
		Tab.closeScope();
	}

	public void visit(ClassDecl classDecl) {
		Obj classObj = classDecl.getClassName().obj;
		if (classObj != Tab.noObj) {
			Tab.chainLocalSymbols(classObj.getType());
		}
		currentClass = null;
		currentSuperclass = null;
		Tab.closeScope();
	}

	public void visit(Type type) {
		Obj typeObj = Tab.find(type.getTypeName());
		if (typeObj == Tab.noObj) {
			// Tip ne postoji
			report_error("Tip " + type.getTypeName() + " ne postoji.", type);
			type.struct = Tab.noType;
		} else if (typeObj.getKind() != Obj.Type) {
			// Ime nije tip
			report_error("Ime" + type.getTypeName() + " ne oznacava tip.", type);
			type.struct = Tab.noType;
		} else {
			type.struct = typeObj.getType();
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
