package org.ip.matrix;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/find-the-kth-smallest-sum-of-a-matrix-with-sorted-rows/">LC: 1439</a>
 */
public class KthSmallestSumSortedMatrix {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 7, new int[][] {{1,3,11},{2,4,6}}, 5 };
		Object[] tc2 = new Object[] { 17, new int[][] {{1,3,11},{2,4,6}}, 9 };
		Object[] tc3 = new Object[] { 9, new int[][] {{1,10,10},{1,4,5},{2,3,6}}, 7 };
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(int[][] t, Integer u) {
			int k = u;
			PriorityQueue<int[]> pq = new PriorityQueue<>(new MatrixComparator(t));
			Set<String> visited = new HashSet<>();
			pq.add(new int[t.length]);
			for (int i = 1; i < k; i++) {
				int[] pos = pq.remove();
				for (int j = 0; j < t.length; j++) {
					if (pos[j] < t[j].length - 1) {
						int[] next = Arrays.copyOf(pos, pos.length);
						next[j]++;
						String sNext = Arrays.toString(next);
						if (visited.add(sNext)) {
							pq.add(next);
						}
					}
				}
			}
			
			return sum(t, pq.peek());
		}
		static class MatrixComparator implements Comparator<int[]> {
			private int[][] m;

			public MatrixComparator(int[][] m) {
				this.m = m;
			}

			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(sum(m, o1), sum(m, o2));
			}
			
		}
		static int sum(int[][] m, int[] o) {
			int sum = 0;
			for (int i = 0; i < o.length; i++) {
				sum += m[i][o[i]];
			}
			return sum;
		}
	}
	interface Problem extends ToIntBiFunction<int[][], Integer> {
		
	}
}
