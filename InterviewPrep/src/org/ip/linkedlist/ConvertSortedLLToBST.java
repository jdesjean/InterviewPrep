package org.ip.linkedlist;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import org.ip.Test;
import org.ip.tree.TreeNode;

/**
 * <a href="https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/">LC: 109</a>
 */
public class ConvertSortedLLToBST {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new Integer[] {0,-3,9,-10,null,5}, ListNode.toList(new int[] {-10,-3,0,5,9})};
		Object[] tc2 = new Object[] { new Integer[] {3,1,5,0,2,4}, ListNode.toList(new int[] {0,1,2,3,4,5})};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public TreeNode apply(ListNode t) {
			int len = len(t);
			return _solve(new AtomicReference<>(t), 0, len - 1);
		}
		TreeNode _solve(AtomicReference<ListNode> n, int l, int r) {
			if (l > r) return null;
			int mid = l + (r - l) / 2;
			TreeNode left = _solve(n, l, mid - 1);
			TreeNode root = new TreeNode(n.get().val);
			n.set(n.get().next);
			TreeNode right = _solve(n, mid + 1, r);
			root.left = left;
			root.right = right;
			return root;
		}
		int len(ListNode t) {
			int len = 0;
			for (ListNode n = t; n != null; n = n.next) {
				len++;
			}
			return len;
		}
		
	}
	
	interface Problem extends Function<ListNode, TreeNode> {
		
	}
}
