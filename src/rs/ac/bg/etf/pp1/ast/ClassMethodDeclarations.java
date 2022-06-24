// generated with ast extension for cup
// version 0.8
// 24/5/2022 11:35:27


package rs.ac.bg.etf.pp1.ast;

public class ClassMethodDeclarations extends ClassMethodDecls {

    private ClassFirstMethod ClassFirstMethod;
    private MethodDeclList MethodDeclList;

    public ClassMethodDeclarations (ClassFirstMethod ClassFirstMethod, MethodDeclList MethodDeclList) {
        this.ClassFirstMethod=ClassFirstMethod;
        if(ClassFirstMethod!=null) ClassFirstMethod.setParent(this);
        this.MethodDeclList=MethodDeclList;
        if(MethodDeclList!=null) MethodDeclList.setParent(this);
    }

    public ClassFirstMethod getClassFirstMethod() {
        return ClassFirstMethod;
    }

    public void setClassFirstMethod(ClassFirstMethod ClassFirstMethod) {
        this.ClassFirstMethod=ClassFirstMethod;
    }

    public MethodDeclList getMethodDeclList() {
        return MethodDeclList;
    }

    public void setMethodDeclList(MethodDeclList MethodDeclList) {
        this.MethodDeclList=MethodDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassFirstMethod!=null) ClassFirstMethod.accept(visitor);
        if(MethodDeclList!=null) MethodDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassFirstMethod!=null) ClassFirstMethod.traverseTopDown(visitor);
        if(MethodDeclList!=null) MethodDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassFirstMethod!=null) ClassFirstMethod.traverseBottomUp(visitor);
        if(MethodDeclList!=null) MethodDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassMethodDeclarations(\n");

        if(ClassFirstMethod!=null)
            buffer.append(ClassFirstMethod.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclList!=null)
            buffer.append(MethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassMethodDeclarations]");
        return buffer.toString();
    }
}
