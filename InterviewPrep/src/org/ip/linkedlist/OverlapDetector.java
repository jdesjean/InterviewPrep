package org.ip.linkedlist;

// EPI: 8.5
public class OverlapDetector {
	public static void main(String[] s) {
		Node five = new Node(5, new Node(6));
		Node four = new Node(4, five);
		Node one = new Node(1, new Node(2, new Node(3, five)));
		

		OverlapDetector detector = new OverlapDetector();
		Node overlap = detector.hasOverlap(one, four);
		System.out.println(overlap);
		
		Node one1 = new Node(1);
		Node three = new Node(3, one1);
		one1.next = new Node(2, three); 
		Node zero = new Node(0, one1);
		
		Node four4 = new Node(4, three);
		
		Node five5 = new Node(5);
		Node six6 = new Node(6);
		five5.next = six6;
		six6.next = five5;
		
		Node overlap2 = detector.hasOverlap(zero, four4);
		System.out.println(overlap2.val);
		
		Node overlap3 = detector.hasOverlap(zero, five5);
		System.out.println(overlap3 != null ? overlap3.val : null);
	}
	CycleDetector detector = new CycleDetector();
	public Node hasOverlap(Node n1, Node n2) {
		Node y1 = detector.hasCycle(n1);
		Node y2 = detector.hasCycle(n2);
		if (y1 == null && y2 == null) {
			return new OverlapDetectorCycleFree().hasOverlap(n1, n2);
		} else if ((y1 != null && y2 == null) || (y1 == null && y2 != null)) {
			return null;
		} else if (y1 != null && y1 == y2) {
			return y1;
		}
		for (Node c1 = y1.next; c1 != y1 && c1 != null; c1 = c1.next) {
			if (c1 == y2) return c1;
		}
		return null;
	}
}
