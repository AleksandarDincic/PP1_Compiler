// generated with ast extension for cup
// version 0.8
// 27/5/2022 16:20:49


package rs.ac.bg.etf.pp1.ast;

public class DoWhileStatement extends SingleStatement {

    private DoWhileLoopStart DoWhileLoopStart;
    private Statement Statement;
    private DoWhileCondStart DoWhileCondStart;
    private Condition Condition;

    public DoWhileStatement (DoWhileLoopStart DoWhileLoopStart, Statement Statement, DoWhileCondStart DoWhileCondStart, Condition Condition) {
        this.DoWhileLoopStart=DoWhileLoopStart;
        if(DoWhileLoopStart!=null) DoWhileLoopStart.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.DoWhileCondStart=DoWhileCondStart;
        if(DoWhileCondStart!=null) DoWhileCondStart.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
    }

    public DoWhileLoopStart getDoWhileLoopStart() {
        return DoWhileLoopStart;
    }

    public void setDoWhileLoopStart(DoWhileLoopStart DoWhileLoopStart) {
        this.DoWhileLoopStart=DoWhileLoopStart;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public DoWhileCondStart getDoWhileCondStart() {
        return DoWhileCondStart;
    }

    public void setDoWhileCondStart(DoWhileCondStart DoWhileCondStart) {
        this.DoWhileCondStart=DoWhileCondStart;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DoWhileLoopStart!=null) DoWhileLoopStart.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(DoWhileCondStart!=null) DoWhileCondStart.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DoWhileLoopStart!=null) DoWhileLoopStart.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(DoWhileCondStart!=null) DoWhileCondStart.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DoWhileLoopStart!=null) DoWhileLoopStart.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(DoWhileCondStart!=null) DoWhileCondStart.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DoWhileStatement(\n");

        if(DoWhileLoopStart!=null)
            buffer.append(DoWhileLoopStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DoWhileCondStart!=null)
            buffer.append(DoWhileCondStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DoWhileStatement]");
        return buffer.toString();
    }
}
