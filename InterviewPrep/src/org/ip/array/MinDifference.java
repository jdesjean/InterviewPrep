package org.ip.array;

import org.ip.array.Utils;

/**
 * <a href="https://leetcode.com/discuss/interview-question/356433/">OA 2019</a>
 */
public class MinDifference {
	public static void main(String[] s) {
		tc1(new Recursive());
		tc1(new DP());
		tc2(new Recursive());
		tc2(new DP());
	}
	public static void tc1(Solution solution) {
		//Input: [1, 2, 3, 4, 5]
		//Output: 1
		System.out.println(solution.solve(new int[] {1,2,3,4,5}));
	}
	public static void tc2(Solution solution) {
		//Input; [10, 10, 9, 9, 2]
		//Output: 0
		System.out.println(solution.solve(new int[] {10, 10, 9, 9, 2}));
	}
	public interface Solution {
		public int solve(int[] a);
	}
	public static class DP implements Solution {

		@Override
		public int solve(int[] a) {
			int sum = Utils.sum(a);
			int[] cache = new int[sum + 1];
			cache[0] = 1;
			int min = sum;
			for (int i = 0; i < a.length; i++) {
				for (int j = cache.length - 1; j >= a[i]; j--) {
					if (cache[j - a[i]] > 0) {
						cache[j] = 1;
						min = Math.min(min, Math.abs(sum - j - j));
					}
				}
			}
			return min;
		}
	}
	public static class Recursive implements Solution {
		public int solve(int[] a) {
			int sum = Utils.sum(a);
			return solve(a, sum, 0, 0);
		}
		public int solve(int[] a, int max, int sum, int index) {
			if (index == a.length) {
				return Math.abs(max - sum - sum);
			}
			int s1 = solve(a, max, sum, index + 1);
			int s2 = solve(a, max, sum + a[index], index + 1);
			return Math.min(s1, s2);
		}
	}
	
}
