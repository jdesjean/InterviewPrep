package org.ip.array;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/buildings-with-an-ocean-view/">LC: 1762</a>
 */
public class BuildingsOceanView {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {0,2,3}, new int[] {4,2,3,1}};
		Object[] tc2 = new Object[] {new int[] {0,1,2,3}, new int[] {4,3,2,1}};
		Object[] tc3 = new Object[] {new int[] {3}, new int[] {1,3,2,4}};
		Object[] tc4 = new Object[] {new int[] {3}, new int[] {2,2,2,2}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int[] apply(int[] t) {
			Deque<Integer> deque = new LinkedList<>();
			for (int i = 0; i < t.length; i++) {
				while (!deque.isEmpty() && t[i] >= t[deque.peekLast()]) {
					deque.removeLast();
				}
				deque.addLast(i);
			}
			int[] res = new int[deque.size()];
			for (int i = 0; i < res.length; i++) {
				res[i] = deque.removeFirst();
			}
			return res;
		}
		
	}
	interface Problem extends Function<int[], int[]> {
		
	}
}
