package org.ip.linkedlist;

// EPI 2018: 7.13
public class AddLsb2 {
	public static void main(String[] s) {
		Node n1 = new Node(3, new Node(1, new Node(4)));
		Node n2 = new Node(7, new Node(0, new Node(9)));
		AddLsb2 add = new AddLsb2();
		System.out.println(add.add(n1, n2));
	}
	public Node add(Node h1, Node h2) {
		Node n1 = h1, n2 = h2;
		List l1 = new List();
		int carry = 0;
		for (; n1 != null && n2 != null; n1 = n1.next, n2 = n2.next) {
			int v = n1.value + n2.value + carry;
			l1.append(new Node(v % 10));
			carry = v / 10;
		}
		Node n = n1 != null ? n1 : n2;
		for (; n != null; n = n.next) {
			int v = n.value + carry;
			l1.append(new Node(v % 10));
			carry = v / 10;
		}
		if (carry > 0) {
			l1.append(new Node(carry));
		}
		return l1.head;
	}
}
