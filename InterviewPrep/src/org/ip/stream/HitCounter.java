package org.ip.stream;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/design-hit-counter/">LC: 362</a>
 */
public class HitCounter {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {3, 7, 9, 15, 20}, new TestCase1()};
		Object[][] tcs = new Object[][] {tc1};
		TestCaseSolver[] solvers = new TestCaseSolver[] { new SolverAbstractProblem(Solver.class) };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {
		static class Pair {
			int timestamp;
			int count;
			public Pair(int timestamp, int count) {
				this.timestamp = timestamp;
				this.count = count;
			}
		}
		Deque<Pair> deque = new ArrayDeque<>(300);
		int sum = 0;
		
		@Override
		public void hit(int timestamp) {
			if (deque.isEmpty() || deque.peekLast().timestamp != timestamp) {
				deque.add(new Pair(timestamp, 1));
			} else {
				deque.peekLast().count++;
			}
			sum++;
			while (timestamp - deque.peek().timestamp >= 300) {
				sum -= deque.remove().count;
			}
		}

		@Override
		public int getHits(int timestamp) {
			while (!deque.isEmpty() && timestamp - deque.peek().timestamp >= 300) {
				sum -= deque.remove().count;
			}
			return sum;
		}
		
	}
	interface Problem {
		void hit(int timestamp);
		int getHits(int timestamp);
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
				t = clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
				throw new RuntimeException(e);
			}
			IntUnaryOperator f = (v) -> {
				t.hit(v);
				return -1;
			};
			return new int[] {
				f.applyAsInt(1),       // hit at timestamp 1.
				f.applyAsInt(2),       // hit at timestamp 2.
				f.applyAsInt(3),       // hit at timestamp 3.
				t.getHits(4),          // get hits at timestamp 4, return 3.
				f.applyAsInt(300),     // hit at timestamp 300.
				t.getHits(300),        // get hits at timestamp 300, return 4.
				t.getHits(301)
			};
		}
		
		
	}
    interface TestCase extends Function<Class<? extends Problem>, int[]> {
    	
    }
}
