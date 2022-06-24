// generated with ast extension for cup
// version 0.8
// 24/5/2022 11:35:27


package rs.ac.bg.etf.pp1.ast;

public class IfStatement extends SingleStatement {

    private IfCon IfCon;
    private Statement Statement;

    public IfStatement (IfCon IfCon, Statement Statement) {
        this.IfCon=IfCon;
        if(IfCon!=null) IfCon.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public IfCon getIfCon() {
        return IfCon;
    }

    public void setIfCon(IfCon IfCon) {
        this.IfCon=IfCon;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfCon!=null) IfCon.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfCon!=null) IfCon.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfCon!=null) IfCon.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfStatement(\n");

        if(IfCon!=null)
            buffer.append(IfCon.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfStatement]");
        return buffer.toString();
    }
}
