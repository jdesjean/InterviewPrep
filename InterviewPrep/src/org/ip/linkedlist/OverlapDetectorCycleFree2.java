package org.ip.linkedlist;

// EPI 2018: 7.4 
public class OverlapDetectorCycleFree2 {
	public static void main(String[] s) {
		Node five = new Node(5, new Node(6));
		Node four = new Node(4, five);
		Node one = new Node(1, new Node(2, new Node(3, five)));
		OverlapDetectorCycleFree2 detector = new OverlapDetectorCycleFree2();
		Node overlap = detector.hasOverlap(one, four);
		System.out.println(overlap);
	}
	public Node hasOverlap(Node h1, Node h2) {
		int l1 = length(h1);
		int l2 = length(h2);
		int d = l2 - l1;
		if (d > 0) {
			h2 = advance(h2, d);
		} else if (d < 0) {
			h1 = advance(h1, -d);
		}
		for (Node n1 = h1, n2 = h2; n1 != null && n2 != null; n1 = n1.next, n2 = n2.next) {
			if (n1 == n2) return n1;
		}
		return null;
	}
	public int length(Node h) {
		int length = 0;
		for (Node n = h; n != null; n = n.next) {
			length++;
		}
		return length;
	}
	public Node advance(Node h, int l) {
		while (l > 0) {
			l--;
			h = h.next;
		}
		return h;
	}
}
