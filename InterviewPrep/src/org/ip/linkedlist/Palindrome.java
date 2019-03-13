package org.ip.linkedlist;


// EPI: 8.11
public class Palindrome {
	public static void main(String[] s) {
		Palindrome palidrome = new Palindrome();
		System.out.println(palidrome.isPalindrome(LList.testList6()));
		System.out.println(palidrome.isPalindrome(LList.testList7()));
		System.out.println(palidrome.isPalindrome(LList.testList1()));
	}
	Reverser reverser = new Reverser();
	public boolean isPalindrome(Node node) {
		Node slow = node;
		Node prev = null;
		for (Node fast = node; fast != null;) {
			if (fast.next != null) {
				fast = fast.next.next;
			} else {
				fast = null;
			}
			prev = slow;
			slow = slow.next;
		}
		slow = reverser.reverse(slow, 0, Integer.MAX_VALUE);
		prev.next = slow;
		for (Node p1 = node, p2 = slow; p2 != null; p1 = p1.next, p2 = p2.next) {
			if (p1.value != p2.value) return false;
		}
		return true;
	}
}
