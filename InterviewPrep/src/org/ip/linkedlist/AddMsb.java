package org.ip.linkedlist;

// EPI: 8.13
public class AddMsb {
	public static void main(String[] s) {
		Node n1 = new Node(4, new Node(1, new Node(3)));
		Node n2 = new Node(9, new Node(0, new Node(7)));
		AddMsb add = new AddMsb();
		System.out.println(add.add(n1, n2));
	}
	public Node add(Node n1, Node n2) {
		int l1 = LList.length(n1);
		int l2 = LList.length(n2);
		int diff = l1 - l2;
		MutableInteger carry = new MutableInteger();
		Node head = add(n1, n2, diff, carry);
		if (carry.value > 0) {
			return new Node(carry.value, head);
		} else {
			return head;
		}
	}
	private static class MutableInteger {
		int value;
	}
	public Node add(Node n1, Node n2, int diff, MutableInteger carry) {
		int value;
		if (diff < 0) {
			value = n2.value;
			diff++;
		} else if (diff > 0) {
			value = n1.value;
			diff--;
		} else {
			int i1 = n1 != null ? n1.value : 0;
			int i2 = n2 != null ? n2.value : 0;
			value = i1 + i2;
		}
		Node next = null;
		if ((n1 != null && n1.next != null) || (n2 != null && n2.next != null)) {
			next = add(n1 != null ? n1.next : null, n2 != null ? n2.next : null, diff, carry);
		}
		value += carry.value;
		Node head = new Node(value % 10, next);
		carry.value = value / 10;
		return head;
	}
}
