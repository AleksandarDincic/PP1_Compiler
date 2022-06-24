// generated with ast extension for cup
// version 0.8
// 24/5/2022 23:55:3


package rs.ac.bg.etf.pp1.ast;

public class DoWhileLoopStart implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public DoWhileLoopStart () {
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
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DoWhileLoopStart(\n");

        buffer.append(tab);
        buffer.append(") [DoWhileLoopStart]");
        return buffer.toString();
    }
}
