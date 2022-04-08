package org.ip.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/find-largest-value-in-each-tree-row/">LC: 515</a>
 */
public class LargestInEachRow {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new int[] {1,3,9}, new Integer[] {1,3,2,5,3,null,9}};
		Object[] tc2 = new Object[] { new int[] {1,3}, new Integer[] {1,2,3}};	
		Object[] tc3 = new Object[] { new int[] {1}, new Integer[] {1}};
		Object[] tc4 = new Object[] { new int[] {1,2}, new Integer[] {1,null,2}};
		Object[] tc5 = new Object[] { new int[] {}, new Integer[] {}};
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4, tc5};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[])tc[1]);
		}
		Problem[] solvers = new Problem[] { new Solver(), new Solver2() };
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public List<Integer> apply(TreeNode t) {
			Deque<TreeNode> deque = new ArrayDeque<>(t != null ? Arrays.asList(t) : Arrays.asList());
			List<Integer> res = new ArrayList<>();
			Consumer<TreeNode> addNonNull = (child) -> {
				if (child != null) deque.add(child);
			};
			for (int max = Integer.MIN_VALUE; !deque.isEmpty(); max = Integer.MIN_VALUE) {
				for (int size = deque.size(); size > 0; size--) {
					TreeNode current = deque.remove();
					max = Math.max(max, current.val);
					addNonNull.accept(current.left);
					addNonNull.accept(current.right);
				}
				res.add(max);
			}
			return res;
		}
	}
	static class Solver implements Problem {

		private final static TreeNode SENTINEL = new TreeNode(0);
		
		@Override
		public List<Integer> apply(TreeNode t) {
			if (t == null) return new ArrayList<>();
			List<Integer> res = new ArrayList<>();
			Deque<TreeNode> queue = new LinkedList<>();
			queue.addLast(t);
			queue.addLast(SENTINEL);
			int max = Integer.MIN_VALUE;
			while (!queue.isEmpty()) {
				TreeNode current = queue.removeFirst();
				if (current == SENTINEL) {
					if (!queue.isEmpty()) {
						queue.addLast(SENTINEL);
					}
					res.add(max);
					max = Integer.MIN_VALUE;
					continue;
				}
				max = Math.max(current.val, max);
				if (current.left != null) {
					queue.addLast(current.left);
				}
				if (current.right != null) {
					queue.addLast(current.right);
				}
			}
			return res;
		}
		
	}
	interface Problem extends Function<TreeNode, List<Integer>> {
		
	}
}
