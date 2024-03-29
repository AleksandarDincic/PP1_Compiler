// generated with ast extension for cup
// version 0.8
// 27/5/2022 16:20:49


package rs.ac.bg.etf.pp1.ast;

public class HasActualParams extends ActPars {

    private ActualParamsList ActualParamsList;

    public HasActualParams (ActualParamsList ActualParamsList) {
        this.ActualParamsList=ActualParamsList;
        if(ActualParamsList!=null) ActualParamsList.setParent(this);
    }

    public ActualParamsList getActualParamsList() {
        return ActualParamsList;
    }

    public void setActualParamsList(ActualParamsList ActualParamsList) {
        this.ActualParamsList=ActualParamsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActualParamsList!=null) ActualParamsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActualParamsList!=null) ActualParamsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActualParamsList!=null) ActualParamsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("HasActualParams(\n");

        if(ActualParamsList!=null)
            buffer.append(ActualParamsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [HasActualParams]");
        return buffer.toString();
    }
}
