// generated with ast extension for cup
// version 0.8
// 23/5/2022 21:21:5


package rs.ac.bg.etf.pp1.ast;

public class DoesNotExtend extends ClassExtends {

    public DoesNotExtend () {
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
        buffer.append("DoesNotExtend(\n");

        buffer.append(tab);
        buffer.append(") [DoesNotExtend]");
        return buffer.toString();
    }
}
