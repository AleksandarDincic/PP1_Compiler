package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.AddressingElem;
import rs.ac.bg.etf.pp1.ast.Designator;
import rs.ac.bg.etf.pp1.ast.FormalParamOfArray;
import rs.ac.bg.etf.pp1.ast.FormalParamOfSingle;
import rs.ac.bg.etf.pp1.ast.VarNameOfArray;
import rs.ac.bg.etf.pp1.ast.VarNameOfSingle;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;

class CounterVisitor extends VisitorAdaptor {

	protected int count;

	public int getCount() {
		return count;
	}

	public static class FormParamCounter extends CounterVisitor {

		@Override
		public void visit(FormalParamOfSingle formParamDecl1) {
			count++;
		}

		@Override
		public void visit(FormalParamOfArray formParamDecl1) {
			count++;
		}
	}

	public static class VarCounter extends CounterVisitor {
		@Override
		public void visit(VarNameOfSingle VarDecl) {
			count++;
		}

		@Override
		public void visit(VarNameOfArray VarDecl) {
			count++;
		}
	}

}