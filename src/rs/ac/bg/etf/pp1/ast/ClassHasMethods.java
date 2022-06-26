// generated with ast extension for cup
// version 0.8
// 26/5/2022 12:52:40


package rs.ac.bg.etf.pp1.ast;

public class ClassHasMethods extends ClassMethods {

    private ClassMethodsStart ClassMethodsStart;
    private ClassMethodDecls ClassMethodDecls;

    public ClassHasMethods (ClassMethodsStart ClassMethodsStart, ClassMethodDecls ClassMethodDecls) {
        this.ClassMethodsStart=ClassMethodsStart;
        if(ClassMethodsStart!=null) ClassMethodsStart.setParent(this);
        this.ClassMethodDecls=ClassMethodDecls;
        if(ClassMethodDecls!=null) ClassMethodDecls.setParent(this);
    }

    public ClassMethodsStart getClassMethodsStart() {
        return ClassMethodsStart;
    }

    public void setClassMethodsStart(ClassMethodsStart ClassMethodsStart) {
        this.ClassMethodsStart=ClassMethodsStart;
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
        if(ClassMethodsStart!=null) ClassMethodsStart.accept(visitor);
        if(ClassMethodDecls!=null) ClassMethodDecls.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassMethodsStart!=null) ClassMethodsStart.traverseTopDown(visitor);
        if(ClassMethodDecls!=null) ClassMethodDecls.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassMethodsStart!=null) ClassMethodsStart.traverseBottomUp(visitor);
        if(ClassMethodDecls!=null) ClassMethodDecls.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassHasMethods(\n");

        if(ClassMethodsStart!=null)
            buffer.append(ClassMethodsStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

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
