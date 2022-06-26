// generated with ast extension for cup
// version 0.8
// 26/5/2022 12:52:40


package rs.ac.bg.etf.pp1.ast;

public class AddressingMember extends Designator {

    private Designator Designator;
    private String memberName;

    public AddressingMember (Designator Designator, String memberName) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.memberName=memberName;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName=memberName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AddressingMember(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+memberName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AddressingMember]");
        return buffer.toString();
    }
}
