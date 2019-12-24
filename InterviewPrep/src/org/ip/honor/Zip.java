package org.ip.honor;

// EPI 2018: 24.9
public class Zip {
	public static void main(String[] s) {
		Node head = node(0, node(1, node(2, node(3, node(4)))));
		for (Node h = new Zip().zip(head); h != null; h = h.next) {
			System.out.println(h);
		}
		head = node(0, node(1, node(2, node(3))));
		for (Node h = new Zip().zip(head); h != null; h = h.next) {
			System.out.println(h);
		}
		head = node(0, node(1, node(2, node(3, node(4, node(5))))));
		for (Node h = new Zip().zip(head); h != null; h = h.next) {
			System.out.println(h);
		}
	}
	public Node zip(Node head) {
		Node mid = mid(head);
		reverse(mid);
		
		Node tail = new Node(-1, null);
		Node list = tail;
		Node h = head;
		for (Node m = mid.next; m != null; ) {
			Node nh = h.next;
			Node nm = m.next;
			tail.next = h;
			tail = tail.next;
			
			tail.next = m;
			tail = m;
			
			h = nh;
			m = nm;
		}
		if (h == mid) {
			tail.next = h;
			h.next = null;
			tail = h;
		}
		return list.next;
	}
	public Node reverse(Node head) {
		Node prev = null;
		for (Node node = head.next; node != null; ) {
			Node next = node.next;
			node.next = prev;
			
			prev = node;
			node = next;
		}
		head.next = prev;
		return head;
	}
	public Node mid(Node head) {
		Node slow = head;
		for (Node fast = head; fast != null && fast.next != null && fast.next.next != null; ) {
			slow = slow.next;
			fast = fast.next.next;
		}
		return slow;
	}
	
	public static Node node(int value) {
		return new Node(value, null);
	}
	public static Node node(int value, Node next) {
		return new Node(value, next);
	}
	public static class Node {
		int value;
		Node next;
		public Node(int value, Node next) {
			super();
			this.value = value;
			this.next = next;
		}
		@Override
		public String toString() {
			return "Node [value=" + value + "]";
		}
	}
}
