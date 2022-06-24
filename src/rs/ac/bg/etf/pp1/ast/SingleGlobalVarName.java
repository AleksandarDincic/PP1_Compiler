// generated with ast extension for cup
// version 0.8
// 24/5/2022 11:35:27


package rs.ac.bg.etf.pp1.ast;

public class SingleGlobalVarName extends GlobalVarNameList {

    private GlobalVarName GlobalVarName;

    public SingleGlobalVarName (GlobalVarName GlobalVarName) {
        this.GlobalVarName=GlobalVarName;
        if(GlobalVarName!=null) GlobalVarName.setParent(this);
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
        if(GlobalVarName!=null) GlobalVarName.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobalVarName!=null) GlobalVarName.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobalVarName!=null) GlobalVarName.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleGlobalVarName(\n");

        if(GlobalVarName!=null)
            buffer.append(GlobalVarName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleGlobalVarName]");
        return buffer.toString();
    }
}
