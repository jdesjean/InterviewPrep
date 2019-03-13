package org.ip.linkedlist;

// EPI: 8.3
public class CycleDetector {
	public static void main(String[] s) {
		Node one = new Node(1);
		one.next = new Node(2, new Node(3, one)); 
		Node node = new Node(0, one);
		CycleDetector detection = new CycleDetector();
		Node root = detection.hasCycle(node);
		System.out.println(root.value);
	}
	public Node hasCycle(Node node) {
		boolean hasCycle = false;
		Node fast = node;
		for (Node slow = node; fast != null && fast.next != null && node != null;) {
			if (fast.next != null) {
				fast = fast.next.next;
			}
			slow = slow.next;
			if (fast == slow) {
				hasCycle = true;
				break;
			}
		}
		if (!hasCycle) return null;
		for (Node slow = node; slow != fast; slow = slow.next, fast = fast.next) {}
		return fast;
	}
}
