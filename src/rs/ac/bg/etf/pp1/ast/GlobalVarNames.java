// generated with ast extension for cup
// version 0.8
// 24/5/2022 23:55:3


package rs.ac.bg.etf.pp1.ast;

public class GlobalVarNames extends GlobalVarNameList {

    private GlobalVarNameList GlobalVarNameList;
    private GlobalVarName GlobalVarName;

    public GlobalVarNames (GlobalVarNameList GlobalVarNameList, GlobalVarName GlobalVarName) {
        this.GlobalVarNameList=GlobalVarNameList;
        if(GlobalVarNameList!=null) GlobalVarNameList.setParent(this);
        this.GlobalVarName=GlobalVarName;
        if(GlobalVarName!=null) GlobalVarName.setParent(this);
    }

    public GlobalVarNameList getGlobalVarNameList() {
        return GlobalVarNameList;
    }

    public void setGlobalVarNameList(GlobalVarNameList GlobalVarNameList) {
        this.GlobalVarNameList=GlobalVarNameList;
    }

    public GlobalVarName getGlobalVarName() {
        return GlobalVarName;
    }

    public void setGlobalVarName(GlobalVarName GlobalVarName) {
        this.GlobalVarName=GlobalVarName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(GlobalVarNameList!=null) GlobalVarNameList.accept(visitor);
        if(GlobalVarName!=null) GlobalVarName.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobalVarNameList!=null) GlobalVarNameList.traverseTopDown(visitor);
        if(GlobalVarName!=null) GlobalVarName.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobalVarNameList!=null) GlobalVarNameList.traverseBottomUp(visitor);
        if(GlobalVarName!=null) GlobalVarName.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalVarNames(\n");

        if(GlobalVarNameList!=null)
            buffer.append(GlobalVarNameList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(GlobalVarName!=null)
            buffer.append(GlobalVarName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobalVarNames]");
        return buffer.toString();
    }
}
