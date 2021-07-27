package org.ip.linkedlist;

/**
 * <a href="https://leetcode.com/problems/copy-list-with-random-pointer/">LC: 138</a>
 */
public class CopyListRandom {
	public static void main(String[] s) {
		CopyListRandom copyListRandom = new CopyListRandom();
		Node seven = new Node(7);
		Node thirteen = new Node(13);
		Node eleven = new Node(11);
		Node ten = new Node(10);
		Node one = new Node(1);
		seven.next = thirteen;
		thirteen.next = eleven;
		eleven.next = ten;
		ten.next = one;
		thirteen.random = seven;
		eleven.random = one;
		ten.random = eleven;
		one.random = seven;
		Node copy = copyListRandom.solve(seven);
		print(copy);
		print(seven);
	}
	
	static void print(Node node) {
		for (Node head = node; head != null; head = head.next) {
			if (head.random != null) {
				System.out.print(head.val + ":" + head.random.val);
			} else {
				System.out.print(head.val + ":null");
			}
			System.out.print(", ");
		}
		System.out.println();
	}
	
	public Node solve(Node head) {
		if (head == null) return head;
		for (Node c1 = head; c1 != null; ) {
			Node c1Next = c1.next;
			Node c2Next = new Node(c1.val);
			c2Next.random = c1.random;
			c2Next.next = c1.next;
			c1.next = c2Next;
			c1 = c1Next;
		}
		Node copyHead = head.next;
		for (Node c1 = head, c2 = copyHead; c1 != null; ) {
			Node c1Next = c1.next.next;
			Node c2Next = c1Next != null ? c1Next.next : null;
			Node c1Random = c2.random;
			Node c2Random = c1Random != null ? c1Random.next : null;
			c1.random = c1Random;
			c2.random = c2Random;
			c1 = c1Next;
			c2 = c2Next;
		}
		for (Node c1 = head, c2 = copyHead; c1 != null; ) {
			Node c1Next = c1.next.next;
			Node c2Next = c1Next != null ? c1Next.next : null;
			c1.next = c1Next;
			c2.next = c2Next;
			c1 = c1Next;
			c2 = c2Next;
		}
		return copyHead;
    }
	
	static class Node {
	    int val;
	    Node next;
	    Node random;

	    public Node(int val) {
	        this.val = val;
	        this.next = null;
	        this.random = null;
	    }
	}
}
