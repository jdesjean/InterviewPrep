package org.ip.array;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/daily-temperatures/">LC: 739</a>
 */
public class DailyTemperature {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {1,1,4,2,1,1,0,0}, new int[] {73,74,75,71,69,72,76,73}};
		Object[] tc2 = new Object[] {new int[] {8,1,5,4,3,2,1,1,0,0}, new int[] {89,62,70,58,47,47,46,76,100,70}};
		Object[] tc3 = new Object[] {new int[] {1,0,0,2,1,0,0,0,0,0}, new int[] {34,80,80,34,34,80,80,80,80,34}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public int[] apply(int[] t) {
			int[] res = new int[t.length];
			int hotest = 0;
			for (int i = t.length - 1; i >= 0; i--) {
				if (t[i] >= hotest) {
					hotest = t[i];
				} else {
					int j = i + 1;
					for (; j < t.length && t[i] >= t[j]; j += res[j]) {
					}
					res[i] = j - i;
				}
			}
			return res;
		}
	}
	static class Solver implements Problem {

		@Override
		public int[] apply(int[] t) {
			Deque<Integer> deque = new ArrayDeque<>();
			int[] res = new int[t.length];
			for (int i = t.length - 1; i >= 0; i--) {
				while (!deque.isEmpty() && t[i] >= t[deque.peek()]) {
					deque.pop();
				}
				res[i] = deque.isEmpty() ? 0 : deque.peek() - i;
				deque.push(i);
			}
			return res;
		}
		
	}
	interface Problem extends Function<int[], int[]> {
		
	}
}
