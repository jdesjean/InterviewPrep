package org.ip;

// Google 10/03/19
// Remove every second element in circular list
public class CircularList {
	public static void main(String[] s) {
		CircularList list = new CircularList();
		Node t1 = t1();
		list.remove(t1);
		print(t1);
		Node t2 = t2();
		list.remove(t2);
		print(t2);
		Node t3 = t3();
		list.remove(t3);
		print(t3);
	}
	public static void print(Node h) {
		Node c = h;
		if (h != null) {
			System.out.print(h.value + ",");
			c = h.next;
		}
		for (; c != null && c != h; c = c.next) {
			System.out.print(c.value+ ",");
		}
		System.out.println();
	}
	public void remove(Node h) {
		for (Node c = h; c != null; ) {
			Node next = c.next;
			if (next == null || next == h) return;
			c.next = next.next;
			c = next.next;
			if (c == h) return;
		}
	}
	public static Node t1() {
		Node h = node(1, node(2));
		h.next.next = h;
		return h;
	}
	public static Node t2() {
		Node h = node(1, node(2, node(3)));
		h.next.next.next = h;
		return h;
	}
	public static Node t3() {
		Node h = node(1, node(2, node(3, node(4))));
		h.next.next.next = h;
		return h;
	}
	public static Node node(int v) {
		return new Node(v, null);
	}
	public static Node node(int v, Node next) {
		return new Node(v, next);
	}
	public static class Node {
		Node next;
		int value;
		public Node(int value, Node next) 
		{
			this.value=value;
			this.next=next;
		}
	}
}
