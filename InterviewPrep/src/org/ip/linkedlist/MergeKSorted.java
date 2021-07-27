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
			if (t.length == 0) return null;
			List list = new List();
			PriorityQueue<Integer> pq = new PriorityQueue<>(new ListNodeComparator(t));
			for (int i = 0; i < t.length; i++) {
				if (t[i] == null) continue;
				pq.add(i);
			}
			while (!pq.isEmpty()) {
				int index = pq.remove();
				ListNode next = t[index].next;
				list.appendToTail(t[index]);
				t[index] = next;
				if (t[index] != null) {
					pq.add(index);
				}
			}
			return list.head;
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
	static class ListNodeComparator implements Comparator<Integer> {
		private ListNode[] t;

		public ListNodeComparator(ListNode[] t) {
			this.t = t;
		}

		@Override
		public int compare(Integer o1, Integer o2) {
			return Integer.compare(t[o1].val, t[o2].val);
		}
		
	}
	interface Problem extends Function<ListNode[], ListNode> {
		
	}
}
