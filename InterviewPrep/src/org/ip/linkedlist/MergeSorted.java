package org.ip.linkedlist;

// EPI: 8.1
public class MergeSorted {
	public static void main(String[] s) {
		Node h1 = LList.testList1();
		Node h2 = LList.testList11();;
		Node h3 = LList.merge(h1,h2);
		LList.println(h3);
	}
	public Node merge(Node n1, Node n2) {
		Node head, tail;
		if (n1.value <= n2.value) {
			head = tail = n1;
			n1 = n1.next;
		} else {
			head = tail = n2;
			n2 = n2.next;
		}
		for (; n1 != null && n2 != null;) {
			if (n1.value <= n2.value) {
				Node next = n1.next;
				tail.next = n1;
				tail = n1;
				n1 = next;
			} else {
				Node next = n2.next;
				tail.next = n2;
				tail = n2;
				n2 = next;
			}
		}
		tail.next = n1 != null ? n1 : n2;
		return head;
	}
}
