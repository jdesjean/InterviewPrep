package org.ip.linkedlist;

import java.util.Arrays;

public class ListNode {
	public int val;
	public ListNode next;
	public ListNode() {}
	public ListNode(int val) { this.val = val; }
	public ListNode(int val, ListNode next) { this.val = val; this.next = next; }
	public static ListNode toList(int[] is) {
		if (is == null || is.length == 0) return null;
		ListNode head = new ListNode(is[0]);
		ListNode tail = head;
		for (int i = 1; i < is.length; i++) {
			tail.next = new ListNode(is[i]);
			tail = tail.next;
		}
		return head;
	}
	public static int len(ListNode node) {
		int i = 0;
		for (ListNode current = node; current != null; current = current.next) {
			i++;
		}
		return i;
	}
	public static int[] toInt(ListNode node) {
		int[] values = new int[len(node)];
		int i = 0;
		for (ListNode current = node; current != null; current = current.next) {
			values[i++] = current.val;
		}
		return values;
	}
	@Override
	public String toString() {
		return Arrays.toString(toInt(this));
	}
}
