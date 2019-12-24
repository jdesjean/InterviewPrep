package org.ip.linkedlist;

// EPI 2018: 7.9
public class CycleShift2 {
	public static void main(String[] s) {
		CycleShift2 shift = new CycleShift2();
		System.out.println(LList.testList6());
		System.out.println(shift.shift(LList.testList6(), 3));
	}
	public Node shift(Node h, int size) {
		int length = LList.length(h);
		int shift = size % length;
		int skip = length - shift;
		if (skip == 0) return h;
		Node prev = null;
		Node node = h;
		for (int i = 0; i < skip; i++) {
			prev = node;
			node = node.next;
		}
		prev.next = null; //Create new tail
		Node head = node;
		for (int i = skip; i < length - 1; i++) {
			node = node.next;
		}
		node.next = h; // Create cycle from tail to head
		return head;
	}
}
