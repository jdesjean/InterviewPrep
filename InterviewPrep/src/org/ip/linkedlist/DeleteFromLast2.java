package org.ip.linkedlist;

//EPI 2018: 7.6
public class DeleteFromLast2 {
	public static void main(String[] s) {
		DeleteFromLast2 delete = new DeleteFromLast2();
		System.out.println(delete.delete(LList.testList1(),0));
		System.out.println(delete.delete(LList.testList1(),1));
		System.out.println(delete.delete(LList.testList1(),2));
	}
	public Node delete(Node h, int k) {
		Node last = h;
		for (int i = 0; i < k; i++) {
			last = last.next;
			if (last == null) {
				return h;
			}
		}
		Node prev = null;
		Node first = h;
		for (; last.next != null; last = last.next, prev = first, first = first.next) {}
		if (prev == null) {
			Node tmp = first.next;
			first.next = null;
			return tmp;
		} else {
			prev.next = first.next;
			first.next = null;
			return h;
		}
	}
}
