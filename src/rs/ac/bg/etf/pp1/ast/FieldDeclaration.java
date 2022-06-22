// generated with ast extension for cup
// version 0.8
// 22/5/2022 19:17:48


package rs.ac.bg.etf.pp1.ast;

public class FieldDeclaration extends FieldDecl {

    private Type Type;
    private FieldNameList FieldNameList;

    public FieldDeclaration (Type Type, FieldNameList FieldNameList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.FieldNameList=FieldNameList;
        if(FieldNameList!=null) FieldNameList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
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
        if(Type!=null) Type.accept(visitor);
        if(FieldNameList!=null) FieldNameList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(FieldNameList!=null) FieldNameList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(FieldNameList!=null) FieldNameList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FieldDeclaration(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
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
