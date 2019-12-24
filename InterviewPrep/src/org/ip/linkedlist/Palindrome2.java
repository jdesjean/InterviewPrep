package org.ip.linkedlist;


// EPI 2018: 7.11
public class Palindrome2 {
	public static void main(String[] s) {
		Palindrome2 palidrome = new Palindrome2();
		System.out.println(palidrome.isPalindrome(LList.testList6()));
		System.out.println(palidrome.isPalindrome(LList.testList7()));
		System.out.println(palidrome.isPalindrome(LList.testList1()));
	}
	Reverser reverser = new Reverser();
	public boolean isPalindrome(Node node) {
		Node mid = mid(node);
		Node tail = reverse(mid);
		//mid - prev.next = tail;
		for (; tail != null; node = node.next, tail = tail.next) {
  			if (node.value != tail.value) {
				return false;
			}
		}
		return true;
	}
	public Node mid(Node h) {
		Node slow = h;
		for (; h != null && h.next != null; slow = slow.next, h = h.next.next) {
			
		}
		return slow;
	}
	public Node reverse(Node h) {
		Node p = null;
		for (Node n = h; n != null; ) {
			Node next = n.next;
			n.next = p;
			p = n;
			n = next;
		}
		return p;
	}
}
