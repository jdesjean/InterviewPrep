package org.ip.linkedlist;

// EPI 2018: 7.1
public class MergeSorted2 {
	public static void main(String[] s) {
		Node h1 = LList.testList1();
		Node h2 = LList.testList11();
		Node h3 = new MergeSorted().merge(h1,h2);
		LList.println(h3);
	}
	public Node merge(Node h1, Node h2) {
		if (h1 == null) return h2;
		else if (h2 == null) return h1;
		Node h = h1.val < h2.val ? h1 : h2;
		Node n = h;
		Node n1 = h == h1 ? h1.next : h1, n2 = h == h2 ? h2.next : h;
		for (; n1 != null && n2 != null; ) {
			if (n1.val < n2.val) {
				Node tmp = n1.next;
				n.next = tmp;
				n1 = tmp;
				n= n1;
			} else {
				Node tmp = n2.next;
				n.next = tmp;
				n2 = tmp;
				n = n2;
			}
		}
		n.next = n1 != null ? n1 : n2;
		return h;
	}
}
