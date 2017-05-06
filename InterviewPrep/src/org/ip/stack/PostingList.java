package org.ip.stack;

import java.util.Deque;
import java.util.LinkedList;

public class PostingList {
	public static void main(String[] s) {
		JumpOrder[] orders = new JumpOrder[]{new JumpOrderRecursive(), new JumpOrderIterative()};
		for (int i = 0; i < orders.length; i++) {
			Node head = testList();
			orders[i].execute(head);
			println(head);
		}
	}
	public static Node testList() {
		Node head = node(-1,node(-1,node(-1,node(-1))));
		head.jump = head.next.next;
		head.next.jump = head.next.next.next;
		head.next.next.jump = head.next;
		head.next.next.next.jump = head.next.next.next;
		return head;
	}
	public static Node node(int value) {
		return new Node(value);
	}
	public static Node node(int value, Node next) {
		return new Node(value,next);
	}
	public static Node node(int value, Node next, Node jump) {
		return new Node(value,next,jump);
	}
	public static void println(Node h1) {
		for (Node current = h1; current != null; current = current.next) {
			if (current != h1) System.out.print(',');
			System.out.print(current.value);
		}
		System.out.println();
	}
	public static class Node {
		int value;
		Node next;
		Node jump;
		public Node(int value, Node next, Node jump) {
			super();
			this.value = value;
			this.next = next;
			this.jump = jump;
		}
		public Node(int value, Node next) {
			super();
			this.value = value;
			this.next = next;
		}
		public Node(int value) {
			super();
			this.value = value;
		}
	}
	//EPI 9.5
	public interface JumpOrder {
		public void execute(Node head);
	}
	public static class JumpOrderRecursive implements JumpOrder {
		@Override
		public void execute(Node head) {
			jump(head,0);
		}
		private static int jump(Node head, int index) {
			if (head == null || head.value >= 0) return index;
			head.value = index;
			
			int index1 = jump(head.jump,index+1);
			int index2 = jump(head.next,index+1);
			
			return Math.max(index1, index2);
		}
	}
	public static class JumpOrderIterative implements JumpOrder {

		@Override
		public void execute(Node head) {
			Deque<Node> stack = new LinkedList<Node>();
			stack.push(head);
			int index = 0;
			while (!stack.isEmpty()) {
				Node current = stack.pop();
				if (current.value >= 0) continue;
				current.value = index++;
				if (current.next != null) stack.push(current.next);
				if (current.jump != null) stack.push(current.jump);
			}
		}
		
	}
	
}
