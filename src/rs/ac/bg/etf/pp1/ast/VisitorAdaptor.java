// generated with ast extension for cup
// version 0.8
// 22/5/2022 19:17:48


package rs.ac.bg.etf.pp1.ast;

public abstract class VisitorAdaptor implements Visitor { 

    public void visit(FieldNameList FieldNameList) { }
    public void visit(Mulop Mulop) { }
    public void visit(Relop Relop) { }
    public void visit(FieldDeclList FieldDeclList) { }
    public void visit(ConstNameValList ConstNameValList) { }
    public void visit(ProgDeclList ProgDeclList) { }
    public void visit(VarName VarName) { }
    public void visit(ActualParamsList ActualParamsList) { }
    public void visit(StatementList StatementList) { }
    public void visit(AddressingList AddressingList) { }
    public void visit(Addop Addop) { }
    public void visit(FormalParamsList FormalParamsList) { }
    public void visit(Factor Factor) { }
    public void visit(CondTerm CondTerm) { }
    public void visit(ClassFirstMethod ClassFirstMethod) { }
    public void visit(GlobalVarDecl GlobalVarDecl) { }
    public void visit(ProgDecl ProgDecl) { }
    public void visit(FieldDecl FieldDecl) { }
    public void visit(Term Term) { }
    public void visit(Condition Condition) { }
    public void visit(AssignStatement AssignStatement) { }
    public void visit(VarNameList VarNameList) { }
    public void visit(DesignatorName DesignatorName) { }
    public void visit(FormalParam FormalParam) { }
    public void visit(ClassExtends ClassExtends) { }
    public void visit(VarDeclList VarDeclList) { }
    public void visit(Expr Expr) { }
    public void visit(ActPars ActPars) { }
    public void visit(DesignatorStatement DesignatorStatement) { }
    public void visit(ClassMethodDecls ClassMethodDecls) { }
    public void visit(Const Const) { }
    public void visit(IfCon IfCon) { }
    public void visit(MethodReturnType MethodReturnType) { }
    public void visit(GlobalVarName GlobalVarName) { }
    public void visit(FieldName FieldName) { }
    public void visit(GlobalVarNameList GlobalVarNameList) { }
    public void visit(Statement Statement) { }
    public void visit(Expression Expression) { }
    public void visit(CondFact CondFact) { }
    public void visit(MethodDeclList MethodDeclList) { }
    public void visit(Addressing Addressing) { }
    public void visit(SingleStatement SingleStatement) { }
    public void visit(FormPars FormPars) { }
    public void visit(ClassMethods ClassMethods) { }
    public void visit(Type Type) { visit(); }
    public void visit(BoolConst BoolConst) { visit(); }
    public void visit(CharConst CharConst) { visit(); }
    public void visit(IntConst IntConst) { visit(); }
    public void visit(MulopMod MulopMod) { visit(); }
    public void visit(MulopDiv MulopDiv) { visit(); }
    public void visit(MulopMul MulopMul) { visit(); }
    public void visit(AddopMinus AddopMinus) { visit(); }
    public void visit(AddopPlus AddopPlus) { visit(); }
    public void visit(Relople Relople) { visit(); }
    public void visit(RelopLt RelopLt) { visit(); }
    public void visit(RelopGe RelopGe) { visit(); }
    public void visit(RelopGt RelopGt) { visit(); }
    public void visit(RelopNeq RelopNeq) { visit(); }
    public void visit(RelopEq RelopEq) { visit(); }
    public void visit(Assignop Assignop) { visit(); }
    public void visit(AddressingElem AddressingElem) { visit(); }
    public void visit(AddressingMember AddressingMember) { visit(); }
    public void visit(NoAddressing NoAddressing) { visit(); }
    public void visit(Addressings Addressings) { visit(); }
    public void visit(SuperDesignator SuperDesignator) { visit(); }
    public void visit(ThisDesignator ThisDesignator) { visit(); }
    public void visit(NamedDesignator NamedDesignator) { visit(); }
    public void visit(Designator Designator) { visit(); }
    public void visit(ParenFactor ParenFactor) { visit(); }
    public void visit(NewArrayFactor NewArrayFactor) { visit(); }
    public void visit(NewObjectFactor NewObjectFactor) { visit(); }
    public void visit(ConstFactor ConstFactor) { visit(); }
    public void visit(DesignOfMethodFactor DesignOfMethodFactor) { visit(); }
    public void visit(DesignOfVarFactor DesignOfVarFactor) { visit(); }
    public void visit(TermFactor TermFactor) { visit(); }
    public void visit(TermWithMulop TermWithMulop) { visit(); }
    public void visit(ExprTerm ExprTerm) { visit(); }
    public void visit(ExprWithAddop ExprWithAddop) { visit(); }
    public void visit(NegativeExpr NegativeExpr) { visit(); }
    public void visit(PositiveExpr PositiveExpr) { visit(); }
    public void visit(CondFactExpr CondFactExpr) { visit(); }
    public void visit(InstanceofCondFact InstanceofCondFact) { visit(); }
    public void visit(RelopCondFact RelopCondFact) { visit(); }
    public void visit(CondTermFact CondTermFact) { visit(); }
    public void visit(AndCondTerm AndCondTerm) { visit(); }
    public void visit(ConditionTerm ConditionTerm) { visit(); }
    public void visit(OrCondition OrCondition) { visit(); }
    public void visit(SingleActualParam SingleActualParam) { visit(); }
    public void visit(ActualParameters ActualParameters) { visit(); }
    public void visit(AssignError AssignError) { visit(); }
    public void visit(AssStatement AssStatement) { visit(); }
    public void visit(DecStatement DecStatement) { visit(); }
    public void visit(IncStatement IncStatement) { visit(); }
    public void visit(MethodCall MethodCall) { visit(); }
    public void visit(DesignAssign DesignAssign) { visit(); }
    public void visit(Statements Statements) { visit(); }
    public void visit(IfConError IfConError) { visit(); }
    public void visit(IfCondition IfCondition) { visit(); }
    public void visit(PrintWithPadStatement PrintWithPadStatement) { visit(); }
    public void visit(PrintStatement PrintStatement) { visit(); }
    public void visit(ReadStatement ReadStatement) { visit(); }
    public void visit(ReturnValStatement ReturnValStatement) { visit(); }
    public void visit(ReturnVoidStatement ReturnVoidStatement) { visit(); }
    public void visit(ContinueStatement ContinueStatement) { visit(); }
    public void visit(BreakStatement BreakStatement) { visit(); }
    public void visit(DoWhileStatement DoWhileStatement) { visit(); }
    public void visit(IfElseStatement IfElseStatement) { visit(); }
    public void visit(IfStatement IfStatement) { visit(); }
    public void visit(DesignStatement DesignStatement) { visit(); }
    public void visit(BlockStatement BlockStatement) { visit(); }
    public void visit(OneStatement OneStatement) { visit(); }
    public void visit(NoStatement NoStatement) { visit(); }
    public void visit(ManyStatements ManyStatements) { visit(); }
    public void visit(NoActualParams NoActualParams) { visit(); }
    public void visit(HasActualParams HasActualParams) { visit(); }
    public void visit(FormalParamOfArray FormalParamOfArray) { visit(); }
    public void visit(FormalParamOfSingle FormalParamOfSingle) { visit(); }
    public void visit(FormParamError FormParamError) { visit(); }
    public void visit(SingleFormalParam SingleFormalParam) { visit(); }
    public void visit(FormalParameters FormalParameters) { visit(); }
    public void visit(NoFormalParams NoFormalParams) { visit(); }
    public void visit(HasFormalParams HasFormalParams) { visit(); }
    public void visit(MethodReturnsVoid MethodReturnsVoid) { visit(); }
    public void visit(MethodReturnsValue MethodReturnsValue) { visit(); }
    public void visit(MethodDecl MethodDecl) { visit(); }
    public void visit(NoMethodDecl NoMethodDecl) { visit(); }
    public void visit(MethodDeclarations MethodDeclarations) { visit(); }
    public void visit(RecordName RecordName) { visit(); }
    public void visit(RecordDecl RecordDecl) { visit(); }
    public void visit(ConstructorDecl ConstructorDecl) { visit(); }
    public void visit(ClassFirstMethodNotConstr ClassFirstMethodNotConstr) { visit(); }
    public void visit(ClassFirstMethodConstr ClassFirstMethodConstr) { visit(); }
    public void visit(ClassNoMethodDecl ClassNoMethodDecl) { visit(); }
    public void visit(ClassMethodDeclarations ClassMethodDeclarations) { visit(); }
    public void visit(ClassNoMethods ClassNoMethods) { visit(); }
    public void visit(ClassHasMethods ClassHasMethods) { visit(); }
    public void visit(FieldNameOfArray FieldNameOfArray) { visit(); }
    public void visit(FieldNameOfSingle FieldNameOfSingle) { visit(); }
    public void visit(SingleFieldName SingleFieldName) { visit(); }
    public void visit(FieldNames FieldNames) { visit(); }
    public void visit(FieldDeclError FieldDeclError) { visit(); }
    public void visit(FieldDeclaration FieldDeclaration) { visit(); }
    public void visit(NoFieldDecl NoFieldDecl) { visit(); }
    public void visit(FieldDeclarations FieldDeclarations) { visit(); }
    public void visit(ClassBody ClassBody) { visit(); }
    public void visit(ExtendsError ExtendsError) { visit(); }
    public void visit(DoesNotExtend DoesNotExtend) { visit(); }
    public void visit(DoesExtend DoesExtend) { visit(); }
    public void visit(ClassDecl ClassDecl) { visit(); }
    public void visit(VarNameOfArray VarNameOfArray) { visit(); }
    public void visit(VarNameOfSingle VarNameOfSingle) { visit(); }
    public void visit(SingleVarName SingleVarName) { visit(); }
    public void visit(VarNames VarNames) { visit(); }
    public void visit(VarDecl VarDecl) { visit(); }
    public void visit(NoVarDecl NoVarDecl) { visit(); }
    public void visit(VarDeclarations VarDeclarations) { visit(); }
    public void visit(GlobalVarNameOfArray GlobalVarNameOfArray) { visit(); }
    public void visit(GlobalVarNameOfSingle GlobalVarNameOfSingle) { visit(); }
    public void visit(GlobalVarNameListError GlobalVarNameListError) { visit(); }
    public void visit(SingleGlobalVarName SingleGlobalVarName) { visit(); }
    public void visit(GlobalVarNames GlobalVarNames) { visit(); }
    public void visit(VarType VarType) { visit(); }
    public void visit(GlobalValDeclError GlobalValDeclError) { visit(); }
    public void visit(GlobalVarDeclaration GlobalVarDeclaration) { visit(); }
    public void visit(ConstNameVal ConstNameVal) { visit(); }
    public void visit(SingleConstNameVal SingleConstNameVal) { visit(); }
    public void visit(ConstNameVals ConstNameVals) { visit(); }
    public void visit(ConstType ConstType) { visit(); }
    public void visit(ConstDecl ConstDecl) { visit(); }
    public void visit(ProgRecordDecl ProgRecordDecl) { visit(); }
    public void visit(ProgClassDecl ProgClassDecl) { visit(); }
    public void visit(ProgVarDecl ProgVarDecl) { visit(); }
    public void visit(ProgConstDecl ProgConstDecl) { visit(); }
    public void visit(NoProgDecl NoProgDecl) { visit(); }
    public void visit(ProgDeclarations ProgDeclarations) { visit(); }
    public void visit(ProgramName ProgramName) { visit(); }
    public void visit(Program Program) { visit(); }


    public void visit() { }
}
