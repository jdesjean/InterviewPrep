package org.ip.linkedlist;

// EPI: 8.6
// leetcode 237
public class Delete {
	public static void main(String[] s) {
		Delete delete = new Delete();
		System.out.println(delete.delete(LList.testList1(), 2));
		System.out.println(delete.delete(LList.testList1(), 5));
		System.out.println(delete.delete(LList.testList1(), 7));
	}
	public Node delete(Node node, int value) {
		for (Node current = node, prev = null; current != null; prev = current, current = current.next) {
			if(current.val == value) {
				if (prev == null && current.next != null) { // root
					current.val = current.next.val;
					current.next = current.next.next;
				} else if (prev != null) {
					prev.next = current.next;
				}
			}
		}
		return node;
	}
}
