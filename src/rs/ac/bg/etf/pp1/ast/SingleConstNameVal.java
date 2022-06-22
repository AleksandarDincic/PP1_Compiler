// generated with ast extension for cup
// version 0.8
// 22/5/2022 19:17:48


package rs.ac.bg.etf.pp1.ast;

public class SingleConstNameVal extends ConstNameValList {

    private ConstNameVal ConstNameVal;

    public SingleConstNameVal (ConstNameVal ConstNameVal) {
        this.ConstNameVal=ConstNameVal;
        if(ConstNameVal!=null) ConstNameVal.setParent(this);
    }

    public ConstNameVal getConstNameVal() {
        return ConstNameVal;
    }

    public void setConstNameVal(ConstNameVal ConstNameVal) {
        this.ConstNameVal=ConstNameVal;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstNameVal!=null) ConstNameVal.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstNameVal!=null) ConstNameVal.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstNameVal!=null) ConstNameVal.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleConstNameVal(\n");

        if(ConstNameVal!=null)
            buffer.append(ConstNameVal.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleConstNameVal]");
        return buffer.toString();
    }
}
