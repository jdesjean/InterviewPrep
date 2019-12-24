package org.ip.honor;

// EPI 2018: 24.10
public class PostingList {
	public static void main(String[] s) {
		Node n = node(0, node(1, node(2, node(3))));
		n.other = n.next.next;
		n.next.other = n.next.next.next;
		n.next.next.other = n.next;
		n.next.next.next.other = n.next.next.next;
		Node c = new PostingList().clone(n);
		for (Node h = c; h != null; h = h.next) {
			System.out.println(h.value + " "  + h.other.value);
		}
		for (Node h = n; h != null; h = h.next) {
			System.out.println(h.value + " "  + h.other.value);
		}
	}
	public Node clone(Node h) {
		Node copy = new Node(-1,null,null);
		for (Node n = h; n != null; ) {
			Node next = n.next;
			
			Node c = new Node(n.value, n.next, null);
			
			n.next = c;
			
			n = next;
		}
		copy.next = h.next;
		for (Node n = h; n != null;) {
			Node other = n.other;
			if (other != null) {
				n.next.other = other.next;
			}
			if (n.next != null) {
				n = n.next.next;
			} else {
				n = null;
			}
		}
		for (Node n = h; n != null; ) {
			Node next = n.next;
			if (next != null) {
				Node next2 = next.next;
				n.next = next2;
			}
			n = next;
		}
		return copy.next;
	}
	public static Node node(int value) {
		return new Node(value, null, null);
	}
	public static Node node(int value, Node next) {
		return new Node(value, next, null);
	}
	public static class Node {
		int value;
		Node next;
		Node other;
		public Node(int value, Node next, Node other) {
			super();
			this.value = value;
			this.next = next;
			this.other = other;
		}
		
	}
}
