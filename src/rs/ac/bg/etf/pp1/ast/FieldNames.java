// generated with ast extension for cup
// version 0.8
// 27/5/2022 16:20:49


package rs.ac.bg.etf.pp1.ast;

public class FieldNames extends FieldNameList {

    private FieldNameList FieldNameList;
    private FieldName FieldName;

    public FieldNames (FieldNameList FieldNameList, FieldName FieldName) {
        this.FieldNameList=FieldNameList;
        if(FieldNameList!=null) FieldNameList.setParent(this);
        this.FieldName=FieldName;
        if(FieldName!=null) FieldName.setParent(this);
    }

    public FieldNameList getFieldNameList() {
        return FieldNameList;
    }

    public void setFieldNameList(FieldNameList FieldNameList) {
        this.FieldNameList=FieldNameList;
    }

    public FieldName getFieldName() {
        return FieldName;
    }

    public void setFieldName(FieldName FieldName) {
        this.FieldName=FieldName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FieldNameList!=null) FieldNameList.accept(visitor);
        if(FieldName!=null) FieldName.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FieldNameList!=null) FieldNameList.traverseTopDown(visitor);
        if(FieldName!=null) FieldName.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FieldNameList!=null) FieldNameList.traverseBottomUp(visitor);
        if(FieldName!=null) FieldName.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FieldNames(\n");

        if(FieldNameList!=null)
            buffer.append(FieldNameList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FieldName!=null)
            buffer.append(FieldName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FieldNames]");
        return buffer.toString();
    }
}
