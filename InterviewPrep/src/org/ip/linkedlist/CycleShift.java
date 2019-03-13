package org.ip.linkedlist;

// EPI: 8.9
public class CycleShift {
	public static void main(String[] s) {
		CycleShift shift = new CycleShift();
		System.out.println(shift.shift(LList.testList6(), 3));
	}
	public Node shift(Node node, int size) {
		int length = LList.length(node);
		size = size % length;
		if (size == 0) return node;
		Node head = LList.advance(node, length - size - 1);
		Node tail = LList.advance(head, size);
		tail.next = node;
		Node newHead = head.next;
		head.next = null;
		return newHead;
	}
}
