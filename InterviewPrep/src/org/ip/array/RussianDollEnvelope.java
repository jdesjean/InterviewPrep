package org.ip.array;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/russian-doll-envelopes/">LC: 354</a>
 */
public class RussianDollEnvelope {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 3, new int[][] {{5,4},{6,4},{6,7},{2,3}} };
		Object[] tc2 = new Object[] { 1, new int[][] {{1,1},{1,1},{1,1}} };
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new RecursiveSolver(), new DPSolver(), new DPBinarySearchSolver() };
		Test.apply(solvers, tcs);
	}
	// nlogn
	static class DPBinarySearchSolver implements Problem {

		@Override
		public int applyAsInt(int[][] value) {
			int[] cache = new int[value.length];
			Arrays.sort(value, new SmallLargeEnvelopeComparator()); // smallest to largest, equal is reversed
			for (int i = 0; i < value.length; i++) {
				cache[i] = value[i][1];
			}
			
			return lis(cache);
		}
		/*
		 * Similar to insertion sort, but if insertion inside the list, don't shuffle the rest of the values
		 */
		int lis(int[] a) {
			int max = 0;
			for (int i = 0; i < a.length; i++) {
				int index = Arrays.binarySearch(a, 0, max, a[i]);
				if (index < 0) {
					index = -(index + 1);
				}
				a[index] = a[i];
				if (index == max) {
					max++;
				}
			}
			return max;
		}
		static class SmallLargeEnvelopeComparator implements Comparator<int[]> {

			@Override
			public int compare(int[] o1, int[] o2) {
				int cmp = Integer.compare(o1[0], o2[0]);
				if (cmp != 0) return cmp;
				return Integer.compare(o2[1], o1[1]); //reversed
			}
			
		}
	}
	// N^2
	static class DPSolver implements Problem {

		@Override
		public int applyAsInt(int[][] value) {
			int[] cache = new int[value.length];
			Arrays.sort(value, Collections.reverseOrder(new EnvelopeComparator())); // largest to smallest
			Arrays.fill(cache, 1);
			int max = 1;
			for (int i = 0; i < cache.length; i++) {
				for (int j = 0; j < i; j++) {
					if (compare(value[i], value[j]) >= 0) continue;
					cache[i] = Math.max(cache[i], cache[j] + 1);
					max = Math.max(cache[i], max);
				}
			}
			return max;
		}
		
	}
	// 2^n
	static class RecursiveSolver implements Problem {

		@Override
		public int applyAsInt(int[][] value) {
			if (value.length == 0) return 0;
			Arrays.sort(value, Collections.reverseOrder(new EnvelopeComparator())); // largest to smallest
			return _apply(value, 0, new int[value.length][value[0].length], -1) + 1;
		}
		int _apply(int[][] value, int vIndex, int[][] pile, int pIndex) {
			if (vIndex == value.length) {
				return pIndex;
			}
			// either use current or not
			int max = _apply(value, vIndex + 1, pile, pIndex);
			if (pIndex == -1 || compare(value[vIndex], pile[pIndex]) < 0) {
				pile[pIndex + 1] = value[vIndex];
				max = Math.max(max, _apply(value, vIndex + 1, pile, pIndex + 1));
			}
			return max;
		}
		
	}
	static int compare(int[] o1, int[] o2) {
		int cmp0 = Integer.compare(o1[0], o2[0]);
		int cmp1 = Integer.compare(o1[1], o2[1]);
		return cmp0 < 0 && cmp1 < 0 ? -1 : 1;
	}
	static class EnvelopeComparator implements Comparator<int[]> {

		@Override
		public int compare(int[] o1, int[] o2) {
			int cmp = Integer.compare(o1[0], o2[0]);
			if (cmp != 0) return cmp;
			return Integer.compare(o1[1], o2[1]);
		}
		
	}
	interface Problem extends ToIntFunction<int[][]> {
		
	}
}
