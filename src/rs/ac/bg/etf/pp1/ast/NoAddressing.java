// generated with ast extension for cup
// version 0.8
// 23/5/2022 21:21:5


package rs.ac.bg.etf.pp1.ast;

public class NoAddressing extends AddressingList {

    public NoAddressing () {
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
        buffer.append("NoAddressing(\n");

        buffer.append(tab);
        buffer.append(") [NoAddressing]");
        return buffer.toString();
    }
}
