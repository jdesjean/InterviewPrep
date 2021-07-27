package org.ip.linkedlist;

// EPI 2018: 7.3 
public class CycleDetector2 {
	public static void main(String[] s) {
		Node one = new Node(1);
		one.next = new Node(2, new Node(3, one)); 
		Node node = new Node(0, one);
		CycleDetector2 detection = new CycleDetector2();
		Node root = detection.hasCycle(node);
		System.out.println(root.val);
	}
	public Node hasCycle(Node h) {
		Node found = null;
		for (Node slow = h, fast = h; fast != null && fast.next != null;) {
			fast = fast.next;
			fast = fast.next;
			slow = slow.next;
			if (fast == slow) {
				found = fast;
				break;
			}
		}
		if (found == null) {
			return null;
		}
		for (Node slow = h; slow != found; slow = slow.next, found = found.next) {}
		return found;
	}
}
