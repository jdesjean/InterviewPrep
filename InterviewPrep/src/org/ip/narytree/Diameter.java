package org.ip.narytree;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/diameter-of-n-ary-tree//">LC: 1522</a>
 */
public class Diameter {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {3, new Integer[] {1,null,3,2,4,null,5,6}};
		Object[] tc2 = new Object[] {4, new Integer[] {1,null,2,null,3,4,null,5,null,6}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		for (Object[] tc : tcs) {
			tc[1] = Node.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		@Override
		public int applyAsInt(Node t) {
			AtomicInteger max = new AtomicInteger();
			_solve(t, max);
			return max.get();
		}
		
		int _solve(Node node, AtomicInteger max) {
			if (node == null) {
				return 0;
			}
			int childMax = 0;
			int childMax2 = 0;
			for (Node child : node.children) {
				int value = _solve(child, max);
				if (value > childMax2) {
					if (value > childMax) {
						childMax2 = childMax;
						childMax = value;
					} else {
						childMax2 = value;
					}
				}
			}
			int sum = childMax + childMax2;
			if (sum > max.get()) {
				max.set(sum);
			}
			return childMax + 1;
		}

	}
	public interface Problem extends ToIntFunction<Node> {
		
	}
}
