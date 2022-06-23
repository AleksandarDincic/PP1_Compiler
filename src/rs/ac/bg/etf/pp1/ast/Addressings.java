// generated with ast extension for cup
// version 0.8
// 23/5/2022 21:21:5


package rs.ac.bg.etf.pp1.ast;

public class Addressings extends AddressingList {

    private AddressingList AddressingList;
    private Addressing Addressing;

    public Addressings (AddressingList AddressingList, Addressing Addressing) {
        this.AddressingList=AddressingList;
        if(AddressingList!=null) AddressingList.setParent(this);
        this.Addressing=Addressing;
        if(Addressing!=null) Addressing.setParent(this);
    }

    public AddressingList getAddressingList() {
        return AddressingList;
    }

    public void setAddressingList(AddressingList AddressingList) {
        this.AddressingList=AddressingList;
    }

    public Addressing getAddressing() {
        return Addressing;
    }

    public void setAddressing(Addressing Addressing) {
        this.Addressing=Addressing;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AddressingList!=null) AddressingList.accept(visitor);
        if(Addressing!=null) Addressing.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AddressingList!=null) AddressingList.traverseTopDown(visitor);
        if(Addressing!=null) Addressing.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AddressingList!=null) AddressingList.traverseBottomUp(visitor);
        if(Addressing!=null) Addressing.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Addressings(\n");

        if(AddressingList!=null)
            buffer.append(AddressingList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Addressing!=null)
            buffer.append(Addressing.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Addressings]");
        return buffer.toString();
    }
}
