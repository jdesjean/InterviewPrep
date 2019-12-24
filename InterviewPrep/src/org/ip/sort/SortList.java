package org.ip.sort;

// EPI 2018: 13.11
public class SortList {
	public static void main(String[] s) {
		Node head = node(5,node(3,node(10,node(2,node(12,node(1, null))))));
		head = new SortList().sort(head);
		for (Node h = head; h != null; h = h.next) {
			System.out.print(h.value + ",");
		}
	}
	public Node sort(Node head) {
		if (head == null || head.next == null) return head;
		Node slow = head;
		Node prev = null;
		for (Node fast = head; fast != null && fast.next != null; fast = fast.next.next, slow = slow.next) {
			prev = slow;
		}
		if (prev != null) {
			prev.next = null;
		}
		head = sort(head);
		slow = sort(slow);
		return merge(head, slow);
	}
	private Node merge(Node h1, Node h2) {
		Node h = null, t = null;
		Node n1 = h1, n2 = h2;
		for (; n1 != null && n2 != null;) {
			if (n1.value <= n2.value) {
				Node next = n1.next;
				if (h == null) {
					h = t = n1;
				} else {
					t.next = n1;
					t = n1;
				}
				n1 = next;
			} else {
				Node next = n2.next;
				if (h == null) {
					h = t = n2;
				} else {
					t.next = n2;
					t = n2;
				}
				n2 = next;
			}
		}
		if (n1 != null) {
			if (t == null) {
				h = t = n1;
			} else {
				t.next = n1;
			}
		} else if (n2 != null) {
			if (t == null) {
				h = t = n2;
			} else {
				t.next = n2;
			}
		}
		return h;
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
