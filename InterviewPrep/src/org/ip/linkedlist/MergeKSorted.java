package org.ip.linkedlist;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/merge-k-sorted-lists/">LC: 23</a>
 */
public class MergeKSorted {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new int[] {1,1,2,3,4,4,5,6}, new ListNode[] {ListNode.toList(new int[] {1,4,5}), ListNode.toList(new int[] {1,3,4}), ListNode.toList(new int[] {2,6})}};
		Object[] tc2 = new Object[] { new int[] {}, new ListNode[] {}};
		Object[] tc3 = new Object[] { new int[] {}, new ListNode[] {ListNode.toList(new int[0])}};
		
		Object[][] tcs = new Object[][] { tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public ListNode apply(ListNode[] t) {
			PriorityQueue<Integer> pq = new PriorityQueue<>(new ListComparator(t));
			for (int i = 0; i < t.length; i++) {
				if (t[i] != null) pq.add(i);
			}
			ListNode head = new ListNode(0);
			ListNode tail = head;
			while (!pq.isEmpty()) {
				int index = pq.remove();
				tail.next = t[index];
				tail = t[index];
				t[index] = t[index].next;
				if (t[index] != null) {
					pq.add(index);
				}
			}
			return head.next;
		}
		
	}
	static class ListComparator implements Comparator<Integer> {
		private ListNode[] listNodes;

		public ListComparator(ListNode[] listNodes) {
			this.listNodes = listNodes;
		}

		@Override
		public int compare(Integer o1, Integer o2) {
			return Integer.compare(listNodes[o1].val, listNodes[o2].val);
		}
		
	}
	static class List {
		ListNode head = null;
		ListNode tail = null;
		void appendToTail(ListNode node) {
			if (tail == null) {
				head = tail = node;
			} else {
				tail.next = node;
				tail = node;
			}
			tail.next = null;
		}
	}
	interface Problem extends Function<ListNode[], ListNode> {
		
	}
}
