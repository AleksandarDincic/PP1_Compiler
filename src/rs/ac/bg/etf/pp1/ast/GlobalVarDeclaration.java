// generated with ast extension for cup
// version 0.8
// 25/5/2022 20:53:53


package rs.ac.bg.etf.pp1.ast;

public class GlobalVarDeclaration extends GlobalVarDecl {

    private VarType VarType;
    private GlobalVarNameList GlobalVarNameList;

    public GlobalVarDeclaration (VarType VarType, GlobalVarNameList GlobalVarNameList) {
        this.VarType=VarType;
        if(VarType!=null) VarType.setParent(this);
        this.GlobalVarNameList=GlobalVarNameList;
        if(GlobalVarNameList!=null) GlobalVarNameList.setParent(this);
    }

    public VarType getVarType() {
        return VarType;
    }

    public void setVarType(VarType VarType) {
        this.VarType=VarType;
    }

    public GlobalVarNameList getGlobalVarNameList() {
        return GlobalVarNameList;
    }

    public void setGlobalVarNameList(GlobalVarNameList GlobalVarNameList) {
        this.GlobalVarNameList=GlobalVarNameList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarType!=null) VarType.accept(visitor);
        if(GlobalVarNameList!=null) GlobalVarNameList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarType!=null) VarType.traverseTopDown(visitor);
        if(GlobalVarNameList!=null) GlobalVarNameList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarType!=null) VarType.traverseBottomUp(visitor);
        if(GlobalVarNameList!=null) GlobalVarNameList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalVarDeclaration(\n");

        if(VarType!=null)
            buffer.append(VarType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(GlobalVarNameList!=null)
            buffer.append(GlobalVarNameList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobalVarDeclaration]");
        return buffer.toString();
    }
}
