package rs.ac.bg.etf.pp1;

import java.util.LinkedList;
import java.util.List;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class ClassTree {

	private String name;
	private Obj type;
	private ClassTree parent;
	
	private List<ClassTree> children = new LinkedList<>();
	private ClassTree lastSought = null;

	public ClassTree(String name, Obj type, ClassTree parent) {
		this.name = name;
		this.type = type;
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public Obj getType() {
		return type;
	}
	
	public ClassTree getParent() {
		return parent;
	}

	public List<ClassTree> getChildren() {
		return children;
	}

	public ClassTree insertChild(String name, Obj type) {
		ClassTree newNode = new ClassTree(name, type, this);
		children.add(newNode);
		return newNode;
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

	public ClassTree find(Struct type) {
		if (lastSought != null && lastSought.getType() != null && lastSought.getType().getType() == type) {
			return lastSought;
		}
		if (this.type != null && this.type.getType() == type) {
			lastSought = this;
			return this;
		}
		for (ClassTree child : children) {
			ClassTree childRetVal = child.find(type);
			if (childRetVal != null) {
				lastSought = childRetVal;
				return childRetVal;
			}
		}
		return null;
	}
	
	public ClassTree find(Obj type) {
		if (lastSought != null && lastSought.getType() == type) {
			return lastSought;
		}
		if (this.type == type) {
			lastSought = this;
			return this;
		}
		for (ClassTree child : children) {
			ClassTree childRetVal = child.find(type);
			if (childRetVal != null) {
				lastSought = childRetVal;
				return childRetVal;
			}
		}
		return null;
	}
}
