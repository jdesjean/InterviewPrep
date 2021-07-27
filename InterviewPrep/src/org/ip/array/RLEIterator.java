package org.ip.array;

import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/rle-iterator/">LC: 900</a>
 */
public class RLEIterator {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new Integer[] {8,8,5,-1}, new TestCase1()};
		Object[][] tcs = new Object[][] {tc1};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2() };
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {
		private int[] a;
		int index = 0;
		int q = 0;

		public Solver2() {}
		public Solver2(int[] a) {
			this.a = a;
		}

		@Override
		public int[] apply(Function<Problem, int[]> t) {
			return t.apply(this);
		}

		@Override
		public Problem problem(int[] n) {
			return new Solver(n);
		}

		@Override
		public int next(int n) {
			for (; index < a.length - 1; index += 2, q = 0) {
				int min = Math.min(n, a[index] - q);
				q += min;
				n -= min;
				if (n == 0) break;
			}
			return index < a.length ? a[index + 1] : -1;
		}
		
	}
	/**
	 * Modifies input
	 */
	static class Solver implements Problem {
		private int[] a;
		int index = 0;

		public Solver() {}
		public Solver(int[] a) {
			this.a = a;
		}

		@Override
		public int[] apply(Function<Problem, int[]> t) {
			return t.apply(this);
		}

		@Override
		public Problem problem(int[] n) {
			return new Solver(n);
		}

		@Override
		public int next(int n) {
			for (; index < a.length - 1; index += 2) {
				int min = Math.min(n, a[index]);
				a[index] -= min;
				n -= min;
				if (n == 0) break;
			}
			return index < a.length ? a[index + 1] : -1;
		}
		
	}
	interface Problem extends Function<Function<Problem, int[]>, int[]> {
		public Problem problem(int[] n);
		public int next(int n);
	}
	static class TestCase1 implements Function<Problem, int[]> {

		@Override
		public int[] apply(Problem t) {
			// TODO Auto-generated method stub
			Problem copy = t.problem(new int[] {3,8,0,9,2,5});
			return new int[] {
					copy.next(2),
					copy.next(1),
					copy.next(1),
					copy.next(2)
			};
		}
		
	}
}
