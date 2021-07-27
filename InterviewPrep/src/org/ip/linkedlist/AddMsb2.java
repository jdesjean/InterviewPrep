package org.ip.linkedlist;

// EPI 2018: 7.13
public class AddMsb2 {
	public static void main(String[] s) {
		Node n1 = new Node(4, new Node(1, new Node(3)));
		Node n2 = new Node(9, new Node(0, new Node(7)));
		Node n3 = new Node(9, new Node(7));
		AddMsb2 add = new AddMsb2();
		System.out.println(add.add(n1, n2));
		System.out.println(add.add(n1, n3));
	}
	public Node add(Node h1, Node h2) {
		int l1 = length(h1);
		int l2 = length(h2);
		NodeCarry nc = _add(h1, h2, l1, l2);
		if (nc.carry > 0) {
			return new Node(nc.carry, nc.node);
		} else {
			return nc.node;
		}
	}
	public NodeCarry _add(Node h1, Node h2, int l1, int l2) {
		NodeCarry nc;
		int n1 = 0, n2 = 0;
		
		if (l1 < l2) {
			nc = _add(h1, h2.next, l1, l2 - 1);
			n1 = h2.val;
		} else if (l2 < l1) {
			nc = _add(h1.next, h2, l1 - 1, l2);
			n1 = h1.val;
		} else if (l1 > 0 && l2 > 0) {
			nc = _add(h1.next, h2.next, l1 - 1, l2 - 1);
			n1 = h1.val;
			n2 = h2.val;
		} else {
			return null;
		}
		if (nc != null) {
			int v = n1 + n2 + nc.carry;
			return new NodeCarry(new Node(v % 10, nc.node), v / 10);
		} else {
			int v = n1 + n2;
			return new NodeCarry(new Node(v % 10), v / 10);
		}
		
	}
	public static class NodeCarry {
		Node node;
		int carry;
		public NodeCarry(Node node, int carry) { this.node=node; this.carry=carry; }
	}
	public int length(Node h) {
		int length = 0;
		for (Node n = h; n != null; n = n.next) {
			length++;
		}
		return length;
	}
}
