package org.ip.linkedlist;

public class List {
	Node head;
	Node tail;
	public void append(Node node) {
		if (head == null) {
			head = tail = node;
		} else {
			tail.next = node;
			tail = node;
		}
	}
	public void append(List list) {
		if (list.head == null) return;
		if (tail != null) {
			tail.next = list.head;
			tail = list.tail;
		} else {
			head = list.head;
			tail = list.tail;
		}
	}
}
