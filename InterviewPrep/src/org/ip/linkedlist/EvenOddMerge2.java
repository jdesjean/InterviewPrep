package org.ip.linkedlist;

// EPI 2018: 7.10
public class EvenOddMerge2 {
	public static void main(String[] s) {
		EvenOddMerge2 merge = new EvenOddMerge2();
		System.out.println(merge.merge(LList.testList5()));
		System.out.println(merge.merge(LList.testList55()));
	}
	public Node merge(Node h) {
		if (h == null) return h;
		List l1 = new List();
		List l2 = new List();
		for (Node n1 = h, n2 = h.next; n1 != null || n2 != null;) {
			l1.add(n1);
			l2.add(n2);
			if (n1 != null) {
				Node t1 = n1.next != null ? n1.next.next : null;
				n1.next = t1;
				n1 = t1;
			}
			if (n2 != null) {
				Node t2 = n2.next != null ? n2.next.next : null;
				n2.next = t2;
				n2 = t2;
			}
		}
		l1.add(l2);
		
		return l1.head;
	}
	public static class List {
		Node head;
		Node tail;
		public void add(Node node) {
			if (node == null) {return;}
			if (head == null) {
				head = tail = node;
			} else {
				tail.next = node;
				tail = node;
			}
		}
		public void add(List list) {
			if (head == null) {
				head = list.head;
				tail = list.tail;
			} else {
				tail.next = list.head;
				if (list.tail != null) {
					tail = list.tail;
				}
			}
		}
	}
}
