package org.ip.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/print-binary-tree/">LC: 655</a>
 */
public class PrintBinaryTree {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new String[][] {
			new String[] {"","1",""},
			new String[] {"2","",""}
		}, new Integer[] {1,2}};
		Object[] tc2 = new Object[] {new String[][] {
			new String[]{"","","","1","","",""},
			new String[]{"","2","","","","3",""},
			new String[]{"","","4","","","",""}
		}, new Integer[] {1,2,3,null,4}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] { new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public List<List<String>> apply(TreeNode t) {
			List<List<String>> res = new ArrayList<>();
			int height = height(t);
			int n = (int)Math.pow(2, height + 1) - 1;
			for (int i = 0; i <= height; i++) {
				String[] array = new String[n];
				Arrays.fill(array, "");
				res.add(Arrays.asList(array));
			}
			_apply(res, t, 0, (n - 1) / 2, height);
			return res;
		}
		int height(TreeNode node) {
			if (node == null) {
				return -1;
			}
			return Math.max(height(node.left), height(node.right)) + 1;
		}
		void _apply(List<List<String>> res, TreeNode current, int r, int c, int height) {
			if (current == null) {
				return;
			}
			res.get(r).set(c, String.valueOf(current.val));
			int pow = (int)Math.pow(2, height - r - 1);
			_apply(res, current.left, r + 1, c - pow, height);
			_apply(res, current.right, r + 1, c + pow, height);
		}
		
	}
	interface Problem extends Function<TreeNode, List<List<String>>> {
		
	}
}
