package org.ip.array;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays/">LC: 689</a>
 */
public class MaximumSum3Subarray {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new int[] {0,3,5}, new int[] {1,2,1,2,6,7,5,1}, 2};
		
		Object[][] tcs = new Object[][] { tc1};
		Problem[] solvers = new Problem[] { new Solver(), new DP() };
		Test.apply(solvers, tcs);
	}
	static class DP implements Problem {

		@Override
		public int[] apply(int[] t, Integer u) {
			int k = u;
			int[] cache = window(t, k);
	        int[] left = maxLeft(cache);
	        int[] right = maxRight(cache);
	        
	        int[] res = new int[]{-1, -1, -1};
	        for (int j = k; j < cache.length - k; j++) {
	            int i = left[j - k], l = right[j + k];
	            if (res[0] == -1 || cache[i] + cache[j] + cache[l] > cache[res[0]] + cache[res[1]] + cache[res[2]]) {
	                res[0] = i;
	                res[1] = j;
	                res[2] = l;
	            }
	        }
			return res;
		}
		int[] maxRight(int[] cache) {
			int[] cumSum = new int[cache.length];
			int best = cache.length - 1;
	        for (int i = cache.length - 1; i >= 0; i --) {
	            if (cache[i] >= cache[best]) {
	                best = i;
	            }
	            cumSum[i] = best;
	        }
	        return cumSum;
		}
		int[] maxLeft(int[] cache) {
			int[] cumSum = new int[cache.length];
			int best = 0;
	        for (int i = 0; i < cache.length; i ++) {
	            if (cache[i] > cache[best]) {
	                best = i;
	            }
	            cumSum[i] = best;
	        }
	        return cumSum;
		}
		int[] window(int[] t, int k) {
			int d = k - 1;
			int[] cache = new int[t.length - d];
			
			int sum = 0;
			for (int i = 0; i < t.length; i++) {
				sum += t[i];
				if (i > d) sum -= t[i - k];
				if (i >= d) cache[i - d] = sum;
			}
			return cache;
		}
	}
	static class Solver implements Problem {

		@Override
		public int[] apply(int[] t, Integer u) {
			int k = u;
			int[] cache = cache(t, k);
			int[] buffer = new int[3];
			int[] res = new int[3];
			AtomicInteger resSum = new AtomicInteger();
			_apply((out, sum) -> {
				if (sum > resSum.get()) {
					resSum.set(sum);
					System.arraycopy(out, 0, res, 0, 3);
				} else if (sum == resSum.get()) { // Don't need to compare lexicographically, because recursion explore smallest first
					//skip
				}
				
			}, t, buffer, cache, k, 0, 0, 0);
			return res;
		}
		
		void _apply(BiConsumer<int[], Integer> consumer, int[] t, int[] res, int[] cache, int k, int resi, int ti, int sum) {
			if (resi == res.length) {
				consumer.accept(res, sum);
				return;
			}
			res[resi] = ti;
			_apply(consumer, t, res, cache, k, resi + 1, ti + k, sum + cache[ti]);
			for (int j = ti + 1; j < Math.min(t.length, t.length - ((res.length - resi) * k) + 1); j++) {
				_apply(consumer, t, res, cache, k, resi, j, sum);
			}
		}
		int[] cache(int[] t, int k) {
			int d = k - 1;
			int[] cache = new int[t.length - d];
			
			int sum = 0;
			for (int i = 0; i < t.length; i++) {
				sum += t[i];
				if (i > d) sum -= t[i - k];
				if (i >= d) cache[i - d] = sum;
			}
			return cache;
		}
	}
	interface Problem extends BiFunction<int[], Integer, int[]> {
		
	}
}
