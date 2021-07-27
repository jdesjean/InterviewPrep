package org.ip.linkedlist;

// EPI: 8.7
public class DeleteFromLast {
	public static void main(String[] s) {
		DeleteFromLast delete = new DeleteFromLast();
		System.out.println(delete.delete(LList.testList1(),0));
		System.out.println(delete.delete(LList.testList1(),1));
		System.out.println(delete.delete(LList.testList1(),2));
	}
	public Node delete(Node node, int k) {
		Node knode = LList.advance(node, k);
		Node current = node;
		Node prev = null;
		for (Node tail = knode; tail != null && tail.next != null; prev = current, tail = tail.next, current = current.next) {}
		if (current.next == null) {
			prev.next = current.next;
		} else {
			current.val = current.next.val;
			current.next = current.next.next;
		}
		return node;
	}
}
