package org.ip.tree;

public class BasicNode {
	public BasicNode left;
	public BasicNode right;
	int value;
	public BasicNode(int value) {this.value=value;}
	public BasicNode(int value, BasicNode right) {this.value=value;this.right=right;}
	public BasicNode(int value, BasicNode left, BasicNode right) {
		this.value=value;this.left=left;this.right=right;
	}
	@Override
	public String toString() {
		return String.valueOf(value);
	}
	public boolean isLeaf() {
		return left == null && right == null;
	}
}
