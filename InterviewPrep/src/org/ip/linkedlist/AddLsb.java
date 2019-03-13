package org.ip.linkedlist;

// EPI: 8.13
public class AddLsb {
	public static void main(String[] s) {
		Node n1 = new Node(3, new Node(1, new Node(4)));
		Node n2 = new Node(7, new Node(0, new Node(9)));
		AddLsb add = new AddLsb();
		System.out.println(add.add(n1, n2));
	}
	public Node add(Node n1, Node n2) {
		int carry = 0;
		List list = new List();
		for (Node c1 = n1, c2 = n2; c1 != null || c2 != null;) {
			int i1 = c1 != null ? c1.value : 0;
			int i2 = c2 != null ? c2.value : 0;
			int value = i1 + i2 + carry;
			list.append(new Node(value % 10));
			carry = value / 10;
			
			if (c1 != null) {
				c1 = c1.next;
			}
			if (c2 != null) {
				c2 = c2.next;
			}
		}
		if (carry > 0) {
			list.append(new Node(carry));
		}
		return list.head;
	}
}
