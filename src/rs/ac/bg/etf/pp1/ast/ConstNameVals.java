// generated with ast extension for cup
// version 0.8
// 23/5/2022 13:46:21


package rs.ac.bg.etf.pp1.ast;

public class ConstNameVals extends ConstNameValList {

    private ConstNameValList ConstNameValList;
    private ConstNameVal ConstNameVal;

    public ConstNameVals (ConstNameValList ConstNameValList, ConstNameVal ConstNameVal) {
        this.ConstNameValList=ConstNameValList;
        if(ConstNameValList!=null) ConstNameValList.setParent(this);
        this.ConstNameVal=ConstNameVal;
        if(ConstNameVal!=null) ConstNameVal.setParent(this);
    }

    public ConstNameValList getConstNameValList() {
        return ConstNameValList;
    }

    public void setConstNameValList(ConstNameValList ConstNameValList) {
        this.ConstNameValList=ConstNameValList;
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
        if(ConstNameValList!=null) ConstNameValList.accept(visitor);
        if(ConstNameVal!=null) ConstNameVal.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstNameValList!=null) ConstNameValList.traverseTopDown(visitor);
        if(ConstNameVal!=null) ConstNameVal.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstNameValList!=null) ConstNameValList.traverseBottomUp(visitor);
        if(ConstNameVal!=null) ConstNameVal.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstNameVals(\n");

        if(ConstNameValList!=null)
            buffer.append(ConstNameValList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstNameVal!=null)
            buffer.append(ConstNameVal.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstNameVals]");
        return buffer.toString();
    }
}
