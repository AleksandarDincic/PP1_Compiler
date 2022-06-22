// generated with ast extension for cup
// version 0.8
// 22/5/2022 19:17:48


package rs.ac.bg.etf.pp1.ast;

public class ClassHasMethods extends ClassMethods {

    private ClassMethodDecls ClassMethodDecls;

    public ClassHasMethods (ClassMethodDecls ClassMethodDecls) {
        this.ClassMethodDecls=ClassMethodDecls;
        if(ClassMethodDecls!=null) ClassMethodDecls.setParent(this);
    }

    public ClassMethodDecls getClassMethodDecls() {
        return ClassMethodDecls;
    }

    public void setClassMethodDecls(ClassMethodDecls ClassMethodDecls) {
        this.ClassMethodDecls=ClassMethodDecls;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassMethodDecls!=null) ClassMethodDecls.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassMethodDecls!=null) ClassMethodDecls.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassMethodDecls!=null) ClassMethodDecls.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassHasMethods(\n");

        if(ClassMethodDecls!=null)
            buffer.append(ClassMethodDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassHasMethods]");
        return buffer.toString();
    }
}
