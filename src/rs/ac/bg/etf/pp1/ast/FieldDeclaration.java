// generated with ast extension for cup
// version 0.8
// 24/5/2022 20:27:25


package rs.ac.bg.etf.pp1.ast;

public class FieldDeclaration extends FieldDecl {

    private FieldType FieldType;
    private FieldNameList FieldNameList;

    public FieldDeclaration (FieldType FieldType, FieldNameList FieldNameList) {
        this.FieldType=FieldType;
        if(FieldType!=null) FieldType.setParent(this);
        this.FieldNameList=FieldNameList;
        if(FieldNameList!=null) FieldNameList.setParent(this);
    }

    public FieldType getFieldType() {
        return FieldType;
    }

    public void setFieldType(FieldType FieldType) {
        this.FieldType=FieldType;
    }

    public FieldNameList getFieldNameList() {
        return FieldNameList;
    }

    public void setFieldNameList(FieldNameList FieldNameList) {
        this.FieldNameList=FieldNameList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FieldType!=null) FieldType.accept(visitor);
        if(FieldNameList!=null) FieldNameList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FieldType!=null) FieldType.traverseTopDown(visitor);
        if(FieldNameList!=null) FieldNameList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FieldType!=null) FieldType.traverseBottomUp(visitor);
        if(FieldNameList!=null) FieldNameList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FieldDeclaration(\n");

        if(FieldType!=null)
            buffer.append(FieldType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FieldNameList!=null)
            buffer.append(FieldNameList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FieldDeclaration]");
        return buffer.toString();
    }
}
