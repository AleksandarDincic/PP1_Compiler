// generated with ast extension for cup
// version 0.8
// 24/5/2022 11:35:27


package rs.ac.bg.etf.pp1.ast;

public class AddressingMember extends Addressing {

    private String mozdanovo;

    public AddressingMember (String mozdanovo) {
        this.mozdanovo=mozdanovo;
    }

    public String getMozdanovo() {
        return mozdanovo;
    }

    public void setMozdanovo(String mozdanovo) {
        this.mozdanovo=mozdanovo;
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
        buffer.append("AddressingMember(\n");

        buffer.append(" "+tab+mozdanovo);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AddressingMember]");
        return buffer.toString();
    }
}
