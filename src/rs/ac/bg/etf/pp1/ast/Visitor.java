// generated with ast extension for cup
// version 0.8
// 23/5/2022 21:21:5


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(FieldNameList FieldNameList);
    public void visit(Mulop Mulop);
    public void visit(Relop Relop);
    public void visit(FieldDeclList FieldDeclList);
    public void visit(ConstNameValList ConstNameValList);
    public void visit(ProgDeclList ProgDeclList);
    public void visit(VarName VarName);
    public void visit(ActualParamsList ActualParamsList);
    public void visit(StatementList StatementList);
    public void visit(AddressingList AddressingList);
    public void visit(Addop Addop);
    public void visit(FormalParamsList FormalParamsList);
    public void visit(Factor Factor);
    public void visit(CondTerm CondTerm);
    public void visit(ClassFirstMethod ClassFirstMethod);
    public void visit(GlobalVarDecl GlobalVarDecl);
    public void visit(ProgDecl ProgDecl);
    public void visit(FieldDecl FieldDecl);
    public void visit(Term Term);
    public void visit(Condition Condition);
    public void visit(AssignStatement AssignStatement);
    public void visit(VarNameList VarNameList);
    public void visit(DesignatorName DesignatorName);
    public void visit(FormalParam FormalParam);
    public void visit(ClassExtends ClassExtends);
    public void visit(VarDeclList VarDeclList);
    public void visit(Expr Expr);
    public void visit(ActPars ActPars);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(ClassMethodDecls ClassMethodDecls);
    public void visit(Const Const);
    public void visit(IfCon IfCon);
    public void visit(MethodReturnType MethodReturnType);
    public void visit(GlobalVarName GlobalVarName);
    public void visit(FieldName FieldName);
    public void visit(GlobalVarNameList GlobalVarNameList);
    public void visit(Statement Statement);
    public void visit(Expression Expression);
    public void visit(CondFact CondFact);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(Addressing Addressing);
    public void visit(SingleStatement SingleStatement);
    public void visit(FormPars FormPars);
    public void visit(ClassMethods ClassMethods);
    public void visit(Type Type);
    public void visit(BoolConst BoolConst);
    public void visit(CharConst CharConst);
    public void visit(IntConst IntConst);
    public void visit(MulopMod MulopMod);
    public void visit(MulopDiv MulopDiv);
    public void visit(MulopMul MulopMul);
    public void visit(AddopMinus AddopMinus);
    public void visit(AddopPlus AddopPlus);
    public void visit(Relople Relople);
    public void visit(RelopLt RelopLt);
    public void visit(RelopGe RelopGe);
    public void visit(RelopGt RelopGt);
    public void visit(RelopNeq RelopNeq);
    public void visit(RelopEq RelopEq);
    public void visit(Assignop Assignop);
    public void visit(AddressingElem AddressingElem);
    public void visit(AddressingMember AddressingMember);
    public void visit(NoAddressing NoAddressing);
    public void visit(Addressings Addressings);
    public void visit(SuperDesignator SuperDesignator);
    public void visit(ThisDesignator ThisDesignator);
    public void visit(NamedDesignator NamedDesignator);
    public void visit(Designator Designator);
    public void visit(ParenFactor ParenFactor);
    public void visit(NewArrayFactor NewArrayFactor);
    public void visit(NewObjectFactor NewObjectFactor);
    public void visit(ConstFactor ConstFactor);
    public void visit(DesignOfMethodFactor DesignOfMethodFactor);
    public void visit(DesignOfVarFactor DesignOfVarFactor);
    public void visit(TermFactor TermFactor);
    public void visit(TermWithMulop TermWithMulop);
    public void visit(ExprTerm ExprTerm);
    public void visit(ExprWithAddop ExprWithAddop);
    public void visit(NegativeExpr NegativeExpr);
    public void visit(PositiveExpr PositiveExpr);
    public void visit(CondFactExpr CondFactExpr);
    public void visit(InstanceofCondFact InstanceofCondFact);
    public void visit(RelopCondFact RelopCondFact);
    public void visit(CondTermFact CondTermFact);
    public void visit(AndCondTerm AndCondTerm);
    public void visit(ConditionTerm ConditionTerm);
    public void visit(OrCondition OrCondition);
    public void visit(SingleActualParam SingleActualParam);
    public void visit(ActualParameters ActualParameters);
    public void visit(AssignError AssignError);
    public void visit(AssStatement AssStatement);
    public void visit(DecStatement DecStatement);
    public void visit(IncStatement IncStatement);
    public void visit(MethodCall MethodCall);
    public void visit(DesignAssign DesignAssign);
    public void visit(Statements Statements);
    public void visit(IfConError IfConError);
    public void visit(IfCondition IfCondition);
    public void visit(PrintWithPadStatement PrintWithPadStatement);
    public void visit(PrintStatement PrintStatement);
    public void visit(ReadStatement ReadStatement);
    public void visit(ReturnValStatement ReturnValStatement);
    public void visit(ReturnVoidStatement ReturnVoidStatement);
    public void visit(ContinueStatement ContinueStatement);
    public void visit(BreakStatement BreakStatement);
    public void visit(DoWhileStatement DoWhileStatement);
    public void visit(IfElseStatement IfElseStatement);
    public void visit(IfStatement IfStatement);
    public void visit(DesignStatement DesignStatement);
    public void visit(BlockStatement BlockStatement);
    public void visit(OneStatement OneStatement);
    public void visit(NoStatement NoStatement);
    public void visit(ManyStatements ManyStatements);
    public void visit(NoActualParams NoActualParams);
    public void visit(HasActualParams HasActualParams);
    public void visit(FormalParamOfArray FormalParamOfArray);
    public void visit(FormalParamOfSingle FormalParamOfSingle);
    public void visit(FormParamError FormParamError);
    public void visit(SingleFormalParam SingleFormalParam);
    public void visit(FormalParameters FormalParameters);
    public void visit(NoFormalParams NoFormalParams);
    public void visit(HasFormalParams HasFormalParams);
    public void visit(MethodReturnsVoid MethodReturnsVoid);
    public void visit(MethodReturnsValue MethodReturnsValue);
    public void visit(MethodName MethodName);
    public void visit(MethodDecl MethodDecl);
    public void visit(NoMethodDecl NoMethodDecl);
    public void visit(MethodDeclarations MethodDeclarations);
    public void visit(RecordName RecordName);
    public void visit(RecordDecl RecordDecl);
    public void visit(ConstructorName ConstructorName);
    public void visit(ConstructorDecl ConstructorDecl);
    public void visit(ClassFirstMethodNotConstr ClassFirstMethodNotConstr);
    public void visit(ClassFirstMethodConstr ClassFirstMethodConstr);
    public void visit(ClassNoMethodDecl ClassNoMethodDecl);
    public void visit(ClassMethodDeclarations ClassMethodDeclarations);
    public void visit(ClassMethodsStart ClassMethodsStart);
    public void visit(ClassNoMethods ClassNoMethods);
    public void visit(ClassHasMethods ClassHasMethods);
    public void visit(FieldNameOfArray FieldNameOfArray);
    public void visit(FieldNameOfSingle FieldNameOfSingle);
    public void visit(SingleFieldName SingleFieldName);
    public void visit(FieldNames FieldNames);
    public void visit(FieldType FieldType);
    public void visit(FieldDeclError FieldDeclError);
    public void visit(FieldDeclaration FieldDeclaration);
    public void visit(NoFieldDecl NoFieldDecl);
    public void visit(FieldDeclarations FieldDeclarations);
    public void visit(ClassBodyStart ClassBodyStart);
    public void visit(ClassBody ClassBody);
    public void visit(ExtendsError ExtendsError);
    public void visit(DoesNotExtend DoesNotExtend);
    public void visit(DoesExtend DoesExtend);
    public void visit(ClassName ClassName);
    public void visit(ClassDecl ClassDecl);
    public void visit(VarNameOfArray VarNameOfArray);
    public void visit(VarNameOfSingle VarNameOfSingle);
    public void visit(SingleVarName SingleVarName);
    public void visit(VarNames VarNames);
    public void visit(VarDecl VarDecl);
    public void visit(NoVarDecl NoVarDecl);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(GlobalVarNameOfArray GlobalVarNameOfArray);
    public void visit(GlobalVarNameOfSingle GlobalVarNameOfSingle);
    public void visit(GlobalVarNameListError GlobalVarNameListError);
    public void visit(SingleGlobalVarName SingleGlobalVarName);
    public void visit(GlobalVarNames GlobalVarNames);
    public void visit(VarType VarType);
    public void visit(GlobalValDeclError GlobalValDeclError);
    public void visit(GlobalVarDeclaration GlobalVarDeclaration);
    public void visit(ConstNameVal ConstNameVal);
    public void visit(SingleConstNameVal SingleConstNameVal);
    public void visit(ConstNameVals ConstNameVals);
    public void visit(ConstType ConstType);
    public void visit(ConstDecl ConstDecl);
    public void visit(ProgRecordDecl ProgRecordDecl);
    public void visit(ProgClassDecl ProgClassDecl);
    public void visit(ProgVarDecl ProgVarDecl);
    public void visit(ProgConstDecl ProgConstDecl);
    public void visit(NoProgDecl NoProgDecl);
    public void visit(ProgDeclarations ProgDeclarations);
    public void visit(ProgramName ProgramName);
    public void visit(Program Program);

}
