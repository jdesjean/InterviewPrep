package org.ip.tree;

import java.util.Arrays;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>>{
	public final T value;
	public final Node<T>[] childs;
	public Node(T value){this.value=value;childs = new Node[2];}
	public Node(T value, Node<T> left, Node<T> right){this.value=value;childs=new Node[]{left,right};}
	public Node<T> getLeft(){return childs[0];}
	public Node<T> getRight(){return childs[1];}
	public boolean isLeaf() {
		return childs[0] == null && childs[1] == null;
	}
	@Override
	public String toString() {
		return "Node [value=" + value + ", childs=" + Arrays.toString(childs) + "]";
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
		if (this == obj) return 0;
		if (obj == null) return -1;
		if (this.value == null) {
			if (obj.value == null) return 0;
			return 1;
		}
		return this.value.compareTo(obj.value);
	}
	
}
