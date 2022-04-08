package org.ip.stream;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/sliding-window-maximum/">LC: 239</a>
 * EPI 24.12
 */
public class SlidingWindowMax {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {3,3,5,5,6,7}, new int[] {1,3,-1,-3,5,3,6,7}, 3};
		Object[][] tcs = new Object[][] {tc1};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public int[] apply(int[] t, Integer u) {
			if (t == null || t.length == 0) return t;
			int[] left = new int[t.length];
			int[] right = new int[t.length];
			int k = u;
			for (int i = 0; i <= t.length / k; i++) {
				int max = 0;
				for (int j = 0; j < k; j++) {
					int l = i * k + j;
					if (l >= t.length) continue;
					left[l] = max = Math.max(max, t[l]);
				}
			}
			for (int i = t.length / k; i >= 0; i--) {
				int max = 0;
				for (int j = k - 1; j >= 0; j--) {
					int l = i * k + j;
					if (l >= t.length) continue;
					right[l] = max = Math.max(max, t[l]);
				}
			}
			int[] res = new int[t.length - k + 1];
			for (int i = k - 1; i < t.length; i++) {
				res[i - k + 1] = Math.max(left[i], right[i - k + 1]); 
			}
			return res;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public int[] apply(int[] t, Integer u) {
			if (t == null || t.length == 0) return t;
			int k = u;
			int[] res = new int[t.length - k + 1];
			Deque<Integer> deque = new ArrayDeque<>();
			for (int i = 0; i < k; i++) {
				while (!deque.isEmpty() && deque.peekLast() < t[i]) {
					deque.removeLast();
				}
				deque.add(t[i]);
			}
			for (int i = k; i < t.length; i++) {
				res[i - k] = deque.peekFirst();
				if (!deque.isEmpty() && deque.peekFirst() == t[i - k]) {
					deque.removeFirst();
				}
				while (!deque.isEmpty() && deque.peekLast() < t[i]) {
					deque.removeLast();
				}
				deque.add(t[i]);
			}
			res[res.length - 1] = deque.peekFirst();
			return res;
		}
		
	}
	interface Problem extends BiFunction<int[], Integer, int[]> {
		
	}
}
