package rs.ac.bg.etf.pp1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ClassTree {
	class Method {
		private String name;
		private int formPars = 0;

		public Method(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setFormPars(int formPars) {
			this.formPars = formPars;
		}

		public int getFormPars() {
			return formPars;
		}

		public void incFormPars() {
			++formPars;
		}
	}

	private String name;

	private List<ClassTree> children = new LinkedList<>();
	private ClassTree lastSought = null;

	private Map<String, Method> methods = new HashMap<>();
	private Method lastSoughtMethod = null;

	public ClassTree(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public ClassTree insertChild(String name) {
		ClassTree newNode = new ClassTree(name);
		children.add(newNode);
		return newNode;
	}

	public Method insertMethod(String name) {
		Method newMethod = new Method(name);
		methods.put(name, newMethod);
		return newMethod;
	}

	public ClassTree find(String name) {
		if (lastSought != null && lastSought.getName().equals(name)) {
			return lastSought;
		}
		if (this.name.equals(name)) {
			lastSought = this;
			return this;
		}
		for (ClassTree child : children) {
			ClassTree childRetVal = child.find(name);
			if (childRetVal != null) {
				lastSought = childRetVal;
				return childRetVal;
			}
		}
		return null;
	}

	public Method findMethod(String name) {
		if (lastSoughtMethod != null && lastSoughtMethod.getName().equals(name)) {
			return lastSoughtMethod;
		}
		if (methods.containsKey(name)) {
			Method retVal = methods.get(name);
			lastSoughtMethod = retVal;
			return retVal;
		}
		return null;
	}
}
