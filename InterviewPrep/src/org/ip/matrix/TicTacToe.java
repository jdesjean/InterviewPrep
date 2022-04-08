package org.ip.matrix;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/design-tic-tac-toe/">LC: 348</a>
 */
public class TicTacToe {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {0, 0, 0, 0, 0, 0, 1}, new TestCase1()};
		Object[] tc2 = new Object[] {new int[] {0, 0, 1}, new TestCase2()};
		Object[] tc3 = new Object[] {new int[] {0, 0, 2}, new TestCase3()};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		TestCaseSolver[] solvers = new TestCaseSolver[] { new SolverAbstractProblem(Solver.class) };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		private final static int[] VALUES = new int[]{0,1,-1};
		private int[] rows;
		private int[] cols;
		private int diag;
		private int antidiag;

		private int n;

		public Solver(int n) {
			this.n = n;
			this.rows = new int[n];
			this.cols = new int[n];
			this.diag = 0;
			this.antidiag = 0;
		}

		@Override
		public int move(int r, int c, int p) {
			int value = VALUES[p]; 
			if (Math.abs(rows[r] += value) == n) {
				return p;
			}
			if (Math.abs(cols[c] += value) == n) {
				return p;
			}
			if (r == c) { //diag
				if (Math.abs(diag += value) == n) {
					return p;
				}
			}
			if (n - 1 - c == r) { //antidiag
				if (Math.abs(antidiag += value) == n) {
					return p;
				}
			}
			return 0;
		}
		
	}
	interface Problem {
		int move(int r, int c, int p);
	}
	static class SolverAbstractProblem implements TestCaseSolver {
		private Class<? extends Problem> _clazz;

		public SolverAbstractProblem(Class<? extends Problem> clazz) {
			_clazz = clazz;
		}

		@Override
		public int[] apply(TestCase t) {
			return t.apply(_clazz);
		}
		
	}
    interface TestCaseSolver extends Function<TestCase, int[]> {
    	
    }
    static class TestCase1 implements TestCase {

		@Override
		public int[] apply(Class<? extends Problem> clazz) {
			Problem t;
			try {
				t = clazz.getConstructor(Integer.TYPE).newInstance(3);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(e);
			}
			return new int[] {
				t.move(0, 0, 1),
				t.move(0, 2, 2),
				t.move(2, 2, 1),
				t.move(1, 1, 2),
				t.move(2, 0, 1),
				t.move(1, 0, 2),
				t.move(2, 1, 1)
			};
		}
		
	}
    static class TestCase2 implements TestCase {

		@Override
		public int[] apply(Class<? extends Problem> clazz) {
			Problem t;
			try {
				t = clazz.getConstructor(Integer.TYPE).newInstance(2);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(e);
			}
			return new int[] {
				t.move(0, 1, 1),
				t.move(1, 1, 2),
				t.move(1, 0, 1)
			};
		}
		
	}
    static class TestCase3 implements TestCase {

		@Override
		public int[] apply(Class<? extends Problem> clazz) {
			Problem t;
			try {
				t = clazz.getConstructor(Integer.TYPE).newInstance(2);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(e);
			}
			return new int[] {
				t.move(0, 1, 2),
				t.move(1, 0, 1),
				t.move(1, 1, 2)
			};
		}
		
	}
    interface TestCase extends Function<Class<? extends Problem>, int[]> {
    	
    }
}
