package org.ip.array;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/number-of-visible-people-in-a-queue/">LC: 1944</a>
 */
public class VisiblePeopleInQueue {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {3,1,2,1,1,0}, new int[] {10,6,8,5,11,9}};
		Object[] tc2 = new Object[] {new int[] {4,1,1,1,0}, new int[] {5,1,2,3,10}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public int[] apply(int[] t) {
			Deque<Integer> stack = new ArrayDeque<Integer>();
			int[] res = new int[t.length];
			for (int i = t.length - 1; i >= 0; i--) {
				while (!stack.isEmpty() && stack.peek() < t[i]) {
					stack.pop();
					res[i]++;
				}
				if (!stack.isEmpty()) {
					res[i]++;
				}
				stack.push(t[i]);
			}
			return res;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public int[] apply(int[] t) {
			int[] res = new int[t.length];
			for (int i = 0; i < t.length; i++) {
				int max = 0;
				for (int j = i + 1; j < t.length; j++) {
					if (t[j] > max) {
						max = t[j];
						res[i]++;
					}
					if (t[j] > t[i]) break;
				}
			}
			return res;
		}
		
	}
	interface Problem extends Function<int[], int[]> {
		
	}
}
