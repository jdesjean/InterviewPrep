package org.ip.array;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * OA 2019
 * <a href="https://leetcode.com/problems/minimum-number-of-days-to-make-m-bouquets/">LC: 1482</a>
 * <br>Similar problems:
 * <a href="https://leetcode.com/problems/k-empty-slots/solution/">k-empty-slots</a>
 * <a href="https://leetcode.com/problems/sliding-window-maximum/">LC: 239</a> 
 * <a href="https://leetcode.com/problems/split-array-largest-sum/">LC: 410</a> 
 * <a href="https://leetcode.com/problems/koko-eating-bananas/">LC: 875</a> 
 */
public class MinDaysBloom {
	public static void main(String[] s) {
		// [1, 2, 4, 9, 3, 4, 1], k = 2, n = 2
		// Output: 4
		int[] array = new int[] {1,2,4,9,3,4,1};
		//int[] array = new int[] {1,10,3,10,2};
		//int[] array = new int[] {1000000000,1000000000};
		//int[] array = new int[] {1,10,2,9,3,8,4,7,5,6};
		int k = 2;
		int n = 2;
		System.out.println(new MinDaysBloomNaieve().min(array, k, n));
		System.out.println(new MinDaysBloomBinarySearch().min(array, k, n));
		System.out.println(new MinDaysBloomDP().min(array, k, n));
		System.out.println(new MinDaysBloomRecursive().min(array, k, n));
	}
	//O(NL), where L is length(roses)
	public static class MinDaysBloomDP {
		int min(int[] roses, int k, int n) {
	        int[] windowKmax = new int[roses.length - k + 1];
	        fillMax(windowKmax,roses,k);
	        int[][] dp = new int[2][roses.length + 1];
	        for (int i = 1; i <= n; i++) {
	            Arrays.fill(dp[i % 2],Integer.MAX_VALUE);
	            for (int j = k; j <= roses.length; j++) {
	                dp[i % 2][j] = Math.min(dp[i % 2][j - 1], Math.max(dp[(i - 1) % 2][j - k],windowKmax[j - k]));
	            }
	        }
	        int min = dp[n % 2][roses.length];
            return min == Integer.MAX_VALUE ? -1 : min;
	    }
	}
	public static class MinDaysBloomRecursive {
		int min(int[] roses, int k, int n) {
	        int[] windowKmax = new int[roses.length - k + 1];
	        fillMax(windowKmax,roses,k);
	        int min = min(windowKmax, roses, k, n, n, roses.length);
	        return min == Integer.MAX_VALUE ? -1 : min;
	    }
		int min(int[] windowKmax, int[] roses, int k, int n, int i, int j) {
			if (i == 0) {
				return 0;
			} else if (j < k) {
				return Integer.MAX_VALUE;
			}
			int prev = min(windowKmax, roses, k, n, i, j - 1);
			int prevprev = min(windowKmax, roses, k, n, i - 1, j - k);
			return Math.min(prev, Math.max(prevprev, windowKmax[j - k]));
		}
	}
	//O(nlogk), where k is max(roses) - min(roses)
	public static class MinDaysBloomBinarySearch {
		public int min(int[] roses, int k, int n) {
			int[] cache = new int[roses.length - k + 1];
			fillMax(cache, roses, k);
			int max = org.ip.array.Utils.max(roses);
			int last = Integer.MAX_VALUE;
			for (int l = 1, h = max; l <= h; ) {
				int mid = l + (h - l) / 2;
				int bloom = solveCache(cache, k, mid);
				if (bloom >= n) {
					last = mid;
					h = mid - 1;
				} else if (bloom < n) {
					l = mid + 1;
				}
			}
			return last == Integer.MAX_VALUE ? -1 : last;
		}
	}
	//O(NK), where k is max(roses) - min(roses)
	public static class MinDaysBloomNaieve {
		public int min(int[] roses, int k, int n) {
			int max = org.ip.array.Utils.max(roses);
			for (int min = 1; min <= max; min++) {
				int bloom = solve(roses, k, min);
				if (bloom >= n) {
					return min;
				}
			}
			return -1;
		}
	}
	
	public static int solve(int[] roses, int k, int n) {
		int bloom = 0;
		for (int i = 0; i < roses.length;) {
			if (isBloom(roses, i, k, n)) {
				bloom++;
				i+=k;
			} else {
				i++;
			}
		}
		return bloom;
	}
	private static void fillMax(int[] windowKmax, int[] r, int k) {
        Deque<Integer> dq = new LinkedList<>();
        for (int i = 0; i < r.length; i++) {
            if (i >= k && r[i - k] == dq.peekFirst()) dq.pollFirst();
            while (!dq.isEmpty() && r[i] > dq.peekLast()) dq.pollLast();
            dq.offerLast(r[i]);
            if (i >= k - 1) windowKmax[i - k + 1] = dq.peekFirst();
        }
    }
	public static int solveCache(int[] cache, int k, int n) {
		int bloom = 0;
		for (int i = 0; i < cache.length;) {
			if (cache[i] <= n) {
				bloom++;
				i+=k;
			} else {
				i++;
			}
		}
		return bloom;
	}
	public static boolean isBloom(int[] roses, int i, int k, int n) {
		for (int j = i; j < i + k; j++) {
			if (j >= roses.length || roses[j] > n) {
				return false;
			}
		}
		return true;
	}
}
