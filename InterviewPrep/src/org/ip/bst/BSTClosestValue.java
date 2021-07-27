package org.ip.bst;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.ip.tree.TreeNode;

/**
 * <a href="https://leetcode.com/problems/closest-binary-search-tree-value/">LC: 270</a>
 */
public class BSTClosestValue {
	public static void main(String[] s) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object[] tc1 = new Object[] {4, new Integer[] {4,2,5,1,3}, 3.714286};
		Object[] tc2 = new Object[] {Integer.MAX_VALUE, new Integer[] {Integer.MAX_VALUE}, -Double.MAX_VALUE};
		Object[] tc3 = new Object[] {Integer.MIN_VALUE, new Integer[] {Integer.MIN_VALUE}, Double.MAX_VALUE};
		Object[] tc4 = new Object[] {0, new Integer[] {}, Double.MAX_VALUE};
		Object[] tc5 = new Object[] {2, new Integer[] {5,3,6,2,4}, 1d};
		List<Object[]> tcs = Arrays.asList(tc1, tc2, tc3, tc4, tc5);
		Class<? extends Problem>[] solvers = new Class[] {Solver.class};
		for (Object[] tc : tcs) {
			System.out.print(tc[0]);
			System.out.println();
			for (Class<? extends Problem> solver : solvers) {
				Problem function = solver.getConstructor(TreeNode.class).newInstance(TreeNode.fromBfs((Integer[]) tc[1]));
				System.out.print(function.apply((Double) tc[2]));
				System.out.println();
			}
			System.out.println();
		}
	}
	public static class Solver implements Problem {
		private TreeNode root;

		public Solver(TreeNode root) {
			this.root = root;
		}

		@Override
		public Integer apply(Double t) {
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
	public interface Problem extends Function<Double, Integer> {
		
	}
}
