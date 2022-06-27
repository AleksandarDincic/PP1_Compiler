// generated with ast extension for cup
// version 0.8
// 27/5/2022 16:20:49


package rs.ac.bg.etf.pp1.ast;

public class DesignAssign extends DesignatorStatement {

    private AssignStatement AssignStatement;

    public DesignAssign (AssignStatement AssignStatement) {
        this.AssignStatement=AssignStatement;
        if(AssignStatement!=null) AssignStatement.setParent(this);
    }

    public AssignStatement getAssignStatement() {
        return AssignStatement;
    }

    public void setAssignStatement(AssignStatement AssignStatement) {
        this.AssignStatement=AssignStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AssignStatement!=null) AssignStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AssignStatement!=null) AssignStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AssignStatement!=null) AssignStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignAssign(\n");

        if(AssignStatement!=null)
            buffer.append(AssignStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignAssign]");
        return buffer.toString();
    }
}
