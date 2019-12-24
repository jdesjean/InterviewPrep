package org.ip.array;

//EPI 2018: 5.2
public class BigInteger {
	public static void main(String[] s) {
		LL list = new LL();
		list.add(1);
		list.add(2);
		list.add(9);
		BigInteger integer = new BigInteger(list);
		integer.increment();
		System.out.println(integer.list.toString());
	}
	private LL list;
	public BigInteger(LL list) {
		this.list = list;
	}
	public void increment() {
		int carry = 1;
		for (Node tail = this.list.tail; tail != null && carry > 0;tail = tail.previous) {
			int value = tail.value;
			value += carry;
			tail.value = value % 10;
			carry = value / 10;
		}
		if (carry > 0) {
			list.prepend(carry);
		}
	}
	public static class LL {
		Node head;
		Node tail;
		public void add(int value) {
			add(new Node(value));
		}
		public void prepend(int value) {
			prepend(new Node(value));
		}
		public void prepend(Node value) {
			if (head == null) { head = tail = value; }
			else {
				value.next = head;
				head.previous = value;
				head = value;
			}
		}
		public void add(Node node) {
			if (head == null) {
				head = tail = node;
			} else {
				node.previous = tail;
				tail.next = node;
				tail = node;
			}
		}
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (Node current = head; current != null; current = current.next) {
				sb.append(current.value + ",");
			}
			return sb.toString();
		}
	}
	public static class Node {
		int value;
		Node previous;
		Node next;
		public Node(int value) { this.value = value; }
	}
}
