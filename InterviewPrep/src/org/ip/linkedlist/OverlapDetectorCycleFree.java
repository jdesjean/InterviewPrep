package org.ip.linkedlist;

// EPI: 8.4
public class OverlapDetectorCycleFree {
	public static void main(String[] s) {
		Node five = new Node(5, new Node(6));
		Node four = new Node(4, five);
		Node one = new Node(1, new Node(2, new Node(3, five)));
		OverlapDetectorCycleFree detector = new OverlapDetectorCycleFree();
		Node overlap = detector.hasOverlap(one, four);
		System.out.println(overlap);
	}
	public Node hasOverlap(Node n1, Node n2) {
		int l1 = LList.length(n1);
		int l2 = LList.length(n2);
		int diff = l1 - l2;
		Node c1 = n1;
		Node c2 = n2;
		if (diff < 0) {
			c2 = LList.advance(n2, -1*diff);
		} else {
			c1 = LList.advance(n1, diff);
		}
		for (; c1 != null && c2 != null && c1 != c2; c1 = c1.next, c2 = c2.next) {}
		return c1 == c2 ? c1 : null;
	}
}
