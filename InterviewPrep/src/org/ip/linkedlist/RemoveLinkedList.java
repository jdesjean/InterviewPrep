package org.ip.linkedlist;

import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/remove-linked-list-elements/>LC: 203</a>
 */
public class RemoveLinkedList {
	public static void main(String[] s) {
		var tc1 = new Object[] {new int[] {1,2,3,4,5}, new int[] {1,2,6,3,4,5,6}, 6};
		var tc2 = new Object[] {new int[0], new int[0] , 1};
		var tc3 = new Object[] {new int[0], new int[] {7,7,7,7} , 7};
		var tcs = new Object[][] {tc1, tc2, tc3};
		for (var tc : tcs) {
			tc[1] = ListNode.toList((int[]) tc[1]);
		}
		var solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public ListNode apply(ListNode t, Integer u) {
			int target = u;
			ListNode head = new ListNode(0);
			head.next = t;
			for (ListNode current = head.next, prev = head; current != null; current = current.next) {
				if (current.val == target) {
					prev.next = current.next;
				} else {
					prev = current;
				}
			}
			return head.next;
		}
		
	}
	interface Problem extends BiFunction<ListNode, Integer, ListNode> {
		
	}
}
