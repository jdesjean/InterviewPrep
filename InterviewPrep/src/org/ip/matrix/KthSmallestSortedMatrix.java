package org.ip.matrix;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/solution/">LC: 378</a>
 */
public class KthSmallestSortedMatrix {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 13, new int[][] {
			{1,5,9},{10,11,13},{12,13,15}
		}, 8};
		Object[] tc2 = new Object[] { 1, new int[][] {
			{1,5,9},{10,11,13},{12,13,15}
		}, 1};
		Object[] tc3 = new Object[] { 15, new int[][] {
			{1,5,9},{10,11,13},{12,13,15}
		}, 9};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new PQSolver(), new BSSolver() };
		Test.apply(solvers, tcs);
	}
	static class BSSolver implements Problem {

		@Override
		public int applyAsInt(int[][] t, Integer u) {
			int[] pos = new int[2];
			int k = u;
			int l = t[0][0], r = t[t.length - 1][t[t.length - 1].length - 1];
			for (; l < r; ) {
				int m = l + (r - l) / 2;
				pos[0] = t[0][0];
				pos[1] = t[t.length - 1][t[t.length - 1].length - 1];
				int count = find(t, m, pos);
				if (count == k) return pos[0];
				else if (count < k) {
					l = pos[1];
				} else {
					r = pos[0];
				}
			}
			return l;
		}
		int find(int[][] t, int m, int[] pos) {
			int l = t.length - 1, c = 0;
			int count = 0;
			for (; l >= 0 && c < t[l].length; ) {
				if (t[l][c] > m) {
					pos[1] = Math.min(pos[1], t[l][c]);
					l--;
				} else {
					pos[0] = Math.max(pos[0], t[l][c]);
					c++;
					count += l + 1;
				}
			}
			return count;
		}
		
	}
	static class PQSolver implements Problem {

		@Override
		public int applyAsInt(int[][] t, Integer u) {
			PriorityQueue<int[]> pq = new PriorityQueue<>(new MatrixComparator(t));
			for (int l = 0; l < t.length; l++) {
				pq.add(new int[] {l, 0});
			}
			for (int k = u - 1; k > 0; k--) {
				int[] pos = pq.remove();
				if (pos[1] < t[pos[0]].length - 1) {
					pq.add(new int[] {pos[0], pos[1] + 1});
				}
			}
			return t[pq.peek()[0]][pq.peek()[1]];
		}
		static class MatrixComparator implements Comparator<int[]> {
			private int[][] m;

			public MatrixComparator(int[][] m) {
				this.m = m;
			}

			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(m[o1[0]][o1[1]], m[o2[0]][o2[1]]);
			}
			
		}
	}
	interface Problem extends ToIntBiFunction<int[][], Integer> {
		
	}
}
