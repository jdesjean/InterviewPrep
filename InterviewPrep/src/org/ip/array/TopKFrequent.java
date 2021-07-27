package org.ip.array;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/top-k-frequent-elements/">LC: 347</a>
 */
public class TopKFrequent {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new int[] {1,2}, new int[]{1,1,1,2,2,3}, 2};
		Object[] tc2 = new Object[] { new int[] {1}, new int[] {1}, 1};
		Object[] tc3 = new Object[] { new int[] {-1,2}, new int[] {4,1,-1,2,-1,2,3}, 2};
		
		Object[][] tcs = new Object[][] { tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public int[] apply(int[] t, Integer u) {
			Map<Integer, Integer> freqs = new HashMap<>();
			for (int i = 0; i < t.length; i++) {
				freqs.compute(t[i], (k, v) -> v == null ? 1 : 1 + v);
			}
			Pair pair = new Pair(freqs.size());
			int j = 0;
			for (Map.Entry<Integer, Integer> freq : freqs.entrySet()) {
				pair.freq[j] = freq.getValue();
				pair.val[j++] = freq.getKey();
			}
			partition(pair, u);
			int[] res = new int[u];
			for (int i = 0; i < u; i++) {
				res[i] = pair.val[i];
			}
			return res;
		}
		void partition(Pair pair, int k) {
			int left = 0;
			int right = pair.freq.length - 1;
			while (left <= right) {
				int pivot = left + (right - left) / 2;
				pivot = partition(pair, left, right, pivot);
				if (pivot == k) return;
				else if (pivot < k) {
					left = pivot + 1;
				} else if (pivot > k) {
					right = pivot - 1;
				}
			}
		}
		int partition(Pair pair, int left, int right, int pivot) {
			int val = pair.freq[pivot];
			int l = left, mid = left, r = right;
			for (; mid <= r; ) {
				if (pair.freq[mid] > val) {
					swap(pair, l++, mid++);
				} else if (pair.freq[mid] < val) {
					swap(pair, mid, r--);
				} else {
					mid++;
				}
			}
			return l; 
		}
		static void swap(Pair pair, int i, int j) {
			Utils.swap(pair.freq, i, j);
			Utils.swap(pair.val, i, j);
		}
		class Pair {
			int[] freq;
			int[] val;
			public Pair(int size) {
				freq = new int[size];
				val = new int[size];
			}
		}
	}
	interface Problem extends BiFunction<int[], Integer, int[]> {
		
	}
}
