package org.ip.linkedlist;

//EPI 2018: 7.5
public class OverlapDetector2 {
	public static void main(String[] s) {
		Node five = new Node(5, new Node(6));
		Node four = new Node(4, five);
		Node one = new Node(1, new Node(2, new Node(3, five)));
		

		OverlapDetector2 detector = new OverlapDetector2();
		Node overlap = detector.hasOverlap(one, four);
		System.out.println(overlap.val);
		
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
	CycleDetector cycleDetector = new CycleDetector();
	OverlapDetectorCycleFree overlapDetector = new OverlapDetectorCycleFree();
	public Node hasOverlap(Node h1, Node h2) {
		Node c1 = cycleDetector.hasCycle(h1);
		Node c2 = cycleDetector.hasCycle(h2);
		if (c1 == null && c2 == null) {
			return overlapDetector.hasOverlap(h1, h2);
		} else if (c1 != null && c2 != null) {
			if (c1 == c2) return c1;
			else if (c1.next != null) {
				for (Node c3 = c1.next; c3 != c1; c3 = c3.next) {
					if (c3 == c2) return c1;
				}
				return null;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
