package org.ip.bst;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

import org.ip.Test;
import org.ip.tree.TreeNode;

/**
 * <a href="https://leetcode.com/problems/closest-binary-search-tree-value/">LC: 270</a>
 */
public class BSTClosestValue {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {4, new Integer[] {4,2,5,1,3}, 3.714286};
		Object[] tc2 = new Object[] {Integer.MAX_VALUE, new Integer[] {Integer.MAX_VALUE}, -Double.MAX_VALUE};
		Object[] tc3 = new Object[] {Integer.MIN_VALUE, new Integer[] {Integer.MIN_VALUE}, Double.MAX_VALUE};
		Object[] tc4 = new Object[] {0, new Integer[] {}, Double.MAX_VALUE};
		Object[] tc5 = new Object[] {2, new Integer[] {5,3,6,2,4}, 1d};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] {new Solver(), new Solver2(), new Solver3()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public Integer apply(TreeNode t, Double u) {
			if (t == null) return 0;
			TreeNode pt = t;
			while (t != null) {
				int cmp = u.compareTo((double) t.val);
				if (cmp == 0) {
					return t.val;
				}
				double diff = Math.abs(Double.sum(t.val, -u));
				double prev = Math.abs(Double.sum(pt.val, -u));
				if (diff < prev) {
					pt = t;
				}
				t = cmp > 0 ? t.right : t.left;
			}
			return pt.val;
		}
	}
	static class Solver3 implements Problem {

		@Override
		public Integer apply(TreeNode t, Double u) {
			if (t == null) return 0;
			return _apply(t, u);
		}
		Integer _apply(TreeNode t, Double u) {
			if (t == null) return null;
			
			int cmp = u.compareTo((double) t.val);
			if (cmp == 0) {
				return t.val;
			}
			Integer child = cmp > 0 ? _apply(t.right, u) : _apply(t.left, u);
			if (child == null) return t.val;
			double diff = Math.abs(Double.sum(t.val, -u));
			double prev = Math.abs(Double.sum(child, -u));
			return Double.compare(diff, prev) < 0 ? t.val : child; 
		}
		
	}
	public static class Solver implements Problem {
		@Override
		public Integer apply(TreeNode root, Double t) {
			AtomicInteger closest = new AtomicInteger();
			AtomicBoolean set = new AtomicBoolean(false);
			_solve(closest, set, root, t);
			return closest.get();
		}
		
		void _solve(AtomicInteger closest, AtomicBoolean set, TreeNode current, Double target) {
			if (current == null) return;
			if (!set.get()) {
				set.set(true);
				closest.set(current.val);
			} else {
				double diff = Math.abs(Double.sum(current.val, -target));
				double prev = Math.abs(Double.sum(closest.get(), -target));
				if (Double.compare(diff, prev) < 0) {
					closest.set(current.val);
				}
			}
			int compare = Double.compare(current.val, target);
			if (compare == 0) {
				return;
			} else if (compare < 0) {
				_solve(closest, set, current.right, target);
			} else if (compare > 0) {
				_solve(closest, set, current.left, target);
			}
		}
		
	}
	interface Problem extends BiFunction<TreeNode, Double, Integer> {
		
	}
}
