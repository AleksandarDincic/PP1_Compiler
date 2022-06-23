package rs.ac.bg.etf.pp1;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


import rs.ac.bg.etf.pp1.ast.BoolConst;
import rs.ac.bg.etf.pp1.ast.CharConst;
import rs.ac.bg.etf.pp1.ast.ClassDecl;
import rs.ac.bg.etf.pp1.ast.ClassName;
import rs.ac.bg.etf.pp1.ast.Const;
import rs.ac.bg.etf.pp1.ast.ConstNameVal;
import rs.ac.bg.etf.pp1.ast.ConstType;
import rs.ac.bg.etf.pp1.ast.DoesExtend;
import rs.ac.bg.etf.pp1.ast.FieldNameOfArray;
import rs.ac.bg.etf.pp1.ast.FieldNameOfSingle;
import rs.ac.bg.etf.pp1.ast.FieldType;
import rs.ac.bg.etf.pp1.ast.GlobalVarNameOfArray;
import rs.ac.bg.etf.pp1.ast.GlobalVarNameOfSingle;
import rs.ac.bg.etf.pp1.ast.IntConst;
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

public class SemanticAnalyzer extends VisitorAdaptor {
	boolean errorDetected = false;
	int nVars;

	Struct currentVarType = null;

	Logger log = Logger.getLogger(getClass());
	
	Map<String, Struct> records = new HashMap<>();

	//Collection<Obj> superclassMembers = null;
	
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
			Tab.insert(isField? Obj.Fld : Obj.Var, varName, currentVarType);
		}
	}
	
	private void analyzeVarNameOfArray(String varName, SyntaxNode node, boolean isField) {
		if (Tab.currentScope().findSymbol(varName) != null) {
			report_error("Ime " + varName + " se vec koristi u trenutnom opsegu.", node);
		} else {
			Tab.insert(isField? Obj.Fld : Obj.Var, varName, new Struct(Struct.Array, currentVarType));
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
		}
		else {
			recordName.obj = Tab.insert(Obj.Type, recordName.getRecordName(), new Struct(Struct.Class));
		}
		Tab.openScope();
	}
	
	public void visit(RecordDecl recordDecl) {
		Obj recordObj = recordDecl.getRecordName().obj;
		if(recordObj != Tab.noObj) {
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
	
	/*public void visit(ClassName className) {
		if (Tab.currentScope().findSymbol(className.getClassName()) != null) {
			report_error("Ime " + className.getClassName() + " se vec koristi u trenutnom opsegu.", className);
			className.obj = Tab.noObj;
		}
		else {
			className.obj = Tab.insert(Obj.Type, className.getClassName(), new Struct(Struct.Class));
		}
		Tab.openScope();
	}
	
	public void visit(DoesExtend doesExtend) {
		Struct superType = doesExtend.getType().struct;
		if(superType != Tab.noType) {
			if(superType.getKind() != Struct.Class) {
				//mora biti klasa
			}
			else if (records.containsKey(doesExtend.getType().getTypeName())) {
				//ne moze se izvoditi is record
			}
			else {
				superclassMembers = superType.getMembers();
				for(Obj obj : superclassMembers) {
					if(obj.getKind() == Obj.Fld) {
						Tab.insert(Obj.Fld, obj.getName(), obj.getType());
					}
				}
			}
		}
	}
	
	public void visit(ClassDecl classDecl) {
		Obj classObj = classDecl.getClassName().obj;
		if(classObj != Tab.noObj) {
			//dodaj preostale metode!!
			
			
			Tab.chainLocalSymbols(classObj.getType());
		}
		Tab.closeScope();
		superclassMembers = null;
	}*/
	
	public void visit(Type type) {
		Obj typeObj = Tab.find(type.getTypeName());
		if (typeObj == Tab.noObj) {
			// Tip ne postoji
			report_error("Tip " + type.getTypeName() + " ne postoji.", type);
			type.struct = Tab.noType;
		} else if (typeObj.getKind() != typeObj.Type) {
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
