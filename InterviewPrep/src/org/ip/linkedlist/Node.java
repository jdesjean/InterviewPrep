package org.ip.linkedlist;

public class Node {
	int val;
	Node next;
	public Node(int value){this.val=value;}
	@Override
	public String toString() {
		return "Node [value=" + val + ", next=" + next + "]";
	}
	public Node(int value, Node next){this.val=value;this.next=next;}
}
