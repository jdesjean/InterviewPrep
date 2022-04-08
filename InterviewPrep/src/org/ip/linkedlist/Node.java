package org.ip.linkedlist;

import java.util.ArrayList;
import java.util.List;

public class Node {
	int val;
	Node next;
	public Node(int value){this.val=value;}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(val);
		for (Node current = this.next; current != null && current != this; current = current.next) {
			sb.append(", ");
			sb.append(current.val);
		}
		sb.append("]");
		return sb.toString();
	}
	public Node(int value, Node next){this.val=value;this.next=next;}
	static Node from(int[] v) {
		Node head = new Node(0);
		Node current = head;
		for (int i = 0; i < v.length; i++) {
			Node next = new Node(v[i]);
			current.next = next;
			current = next;
		}
		current.next = head.next;
		return head.next;
	}
	static int[] to(Node node) {
		List<Integer> list = new ArrayList<>();
		for (Node current = node; current != null; current = current.next) {
			list.add(current.val);
			if (current.next == node) break;
		}
		return list.stream().mapToInt(Integer::intValue).toArray();
	}
}
