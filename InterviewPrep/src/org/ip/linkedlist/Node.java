package org.ip.linkedlist;

public class Node {
	int value;
	Node next;
	public Node(int value){this.value=value;}
	@Override
	public String toString() {
		return "Node [value=" + value + ", next=" + next + "]";
	}
	public Node(int value, Node next){this.value=value;this.next=next;}
}
