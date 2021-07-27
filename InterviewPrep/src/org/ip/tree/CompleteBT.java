package org.ip.tree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Predicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/check-completeness-of-a-binary-tree/">LC: 958</a>

 */
public class CompleteBT {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { true, new Integer[]{1,2,3,4,5,6}};
		Object[] tc2 = new Object[] { false, new Integer[] {1,2,3,4,5,null,7}};
		Object[] tc3 = new Object[] { true, new Integer[] {1,2,3,5,6}};
		Object[] tc4 = new Object[] { false, new Integer[] {1,2,3,5,null,7,8}};
		Object[] tc5 = new Object[] { false, new Integer[] {1,2,3,5,null,7,null}};
		
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4, tc5};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		
		@Override
		public boolean test(TreeNode t) {
			if (t == null) return true;
			Deque<TreeNode> deque = new LinkedList<>();
			deque.add(t);
			boolean isNull = false;
			while (!deque.isEmpty()) {
				TreeNode current = deque.remove();
				if (current == null) {
					isNull = true;
					continue;
				}
				if (isNull) {
					return false;
				}
				deque.add(current.left);
				deque.add(current.right);
			}
			return true;
		}
		
	}
	interface Problem extends Predicate<TreeNode> {
		
	}
}
