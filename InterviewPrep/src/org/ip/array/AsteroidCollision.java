package org.ip.array;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;

import org.ip.Test;

public class AsteroidCollision {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new int[] {5,10}, new int[] {5,10,-5} };
		Object[] tc2 = new Object[] { new int[] {}, new int[] {8,-8} };
		Object[] tc3 = new Object[] { new int[] {10}, new int[] {10,2,-5} };
		Object[] tc4 = new Object[] { new int[] {-2,1}, new int[] {-2,1,1,-1} };
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		private static final String Deque = null;

		@Override
		public int[] apply(int[] t) {
			Deque<Integer> deque = new ArrayDeque<>();
			List<Integer> res = new ArrayList<>();
			for (int i = 0; i < t.length; i++) {
				if (t[i] >= 0) {
					deque.add(t[i]);
				} else {
					int v = Math.abs(t[i]);
					while (!deque.isEmpty() && deque.peekLast() < v) {
						deque.removeLast();
					}
					boolean add = true;
					if (!deque.isEmpty() && deque.peekLast() == v) {
						deque.removeLast();
						add = false;
					}
					if (deque.isEmpty() && add) {
						res.add(t[i]);
					}
				}
			}
			while (!deque.isEmpty()) {
				res.add(deque.removeFirst());
			}
			return res.stream().mapToInt(Integer::intValue).toArray();
		}
		
	}
	interface Problem extends Function<int[], int[]> {
		
	}
}
