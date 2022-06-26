// generated with ast extension for cup
// version 0.8
// 26/5/2022 21:48:52


package rs.ac.bg.etf.pp1.ast;

public class ClassBody implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ClassBodyStart ClassBodyStart;
    private FieldDeclList FieldDeclList;
    private ClassMethods ClassMethods;

    public ClassBody (ClassBodyStart ClassBodyStart, FieldDeclList FieldDeclList, ClassMethods ClassMethods) {
        this.ClassBodyStart=ClassBodyStart;
        if(ClassBodyStart!=null) ClassBodyStart.setParent(this);
        this.FieldDeclList=FieldDeclList;
        if(FieldDeclList!=null) FieldDeclList.setParent(this);
        this.ClassMethods=ClassMethods;
        if(ClassMethods!=null) ClassMethods.setParent(this);
    }

    public ClassBodyStart getClassBodyStart() {
        return ClassBodyStart;
    }

    public void setClassBodyStart(ClassBodyStart ClassBodyStart) {
        this.ClassBodyStart=ClassBodyStart;
    }

    public FieldDeclList getFieldDeclList() {
        return FieldDeclList;
    }

    public void setFieldDeclList(FieldDeclList FieldDeclList) {
        this.FieldDeclList=FieldDeclList;
    }

    public ClassMethods getClassMethods() {
        return ClassMethods;
    }

    public void setClassMethods(ClassMethods ClassMethods) {
        this.ClassMethods=ClassMethods;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassBodyStart!=null) ClassBodyStart.accept(visitor);
        if(FieldDeclList!=null) FieldDeclList.accept(visitor);
        if(ClassMethods!=null) ClassMethods.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassBodyStart!=null) ClassBodyStart.traverseTopDown(visitor);
        if(FieldDeclList!=null) FieldDeclList.traverseTopDown(visitor);
        if(ClassMethods!=null) ClassMethods.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassBodyStart!=null) ClassBodyStart.traverseBottomUp(visitor);
        if(FieldDeclList!=null) FieldDeclList.traverseBottomUp(visitor);
        if(ClassMethods!=null) ClassMethods.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassBody(\n");

        if(ClassBodyStart!=null)
            buffer.append(ClassBodyStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FieldDeclList!=null)
            buffer.append(FieldDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassMethods!=null)
            buffer.append(ClassMethods.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassBody]");
        return buffer.toString();
    }
}
