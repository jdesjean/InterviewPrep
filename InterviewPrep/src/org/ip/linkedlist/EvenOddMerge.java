package org.ip.linkedlist;

// EPI: 8.10
public class EvenOddMerge {
	public static void main(String[] s) {
		EvenOddMerge merge = new EvenOddMerge();
		System.out.println(merge.merge(LList.testList5()));
		System.out.println(merge.merge(LList.testList55()));
	}
	public Node merge(Node node) {
		Node head = node.next;
		Node tail = null;
		for (Node even = node, odd = node.next; even != null && odd != null;) {
			Node next = odd.next;
			even.next = next;
			if (next != null) {
				odd.next = next.next;
				odd = next.next;
				tail = next;
			} else {
				tail = even;
			}
			even = next;
		}
		tail.next = head;
		return node;
	}
}
