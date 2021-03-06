package org.ip.tree;

public class Node<T> implements Comparable<Node<T>>{
	public final T value;
	public Node<T> parent;
	public final Node<T>[] childs;
	public Node<T> sibling;
	public Node<T> left;
	public Node<T> right;
	public Node(T value){this.value=value;childs = new Node[2];}
	public Node(T value, Node<T> left, Node<T> right){this.value=value;childs=new Node[]{this.left = left,this.right = right};}
	public Node(T value, Node<T>[] childs){this.value=value;this.childs=childs;}
	public Node<T> getLeft(){return childs[0];}
	public Node<T> getRight(){return childs[1];}
	public int lockCount = 0;
	public boolean locked = false;
	public boolean isLeaf() {
		return childs[0] == null && childs[1] == null;
	}
	@Override
	public String toString() {
		return "Node [value=" + value + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	@Override
	public int compareTo(Node<T> obj) {
		if (!(this.value instanceof Comparable)) return 0;
		if (this == obj) return 0;
		if (obj == null) return -1;
		if (this.value == null) {
			if (obj.value == null) return 0;
			return 1;
		}
		return ((Comparable<T>)this.value).compareTo(obj.value);
	}
	public static <T> Node<T> node(T value) {
		return new Node<T>(value);
	}
	public static <T> Node<T> node(T value, Node<T> left, Node<T> right) {
		return new Node<T>(value, left, right);
	}
	public static <T> Node<T> node(T value, Node<T> left) {
		return new Node<T>(value, left, null);
	}
}
