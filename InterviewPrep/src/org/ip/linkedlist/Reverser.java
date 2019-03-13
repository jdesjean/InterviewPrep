package org.ip.linkedlist;

public class Reverser {
	public static void main(String[] s) {
		Reverser reverse = new Reverser();
		Node reversed = reverse.reverse(LList.testList2(), 1, 3);
		LList.println(reversed);
	}
	public Node reverse(Node node, int l, int r) {
		Node dummy = new Node(0, node);
		Node head = dummy;
		for (int i = 0; i < l; i++)  { 
			head = head.next;
		}
		for (Node current = head.next; l < r && current.next != null; l++) {
			Node next = current.next;
			current.next = next.next;
			next.next = head.next;
			head.next = next;
			
		}
		return dummy.next;
	}
}
