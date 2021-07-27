package org.ip.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/find-k-closest-elements/">LC: 658</a>
 */
public class KClosestSorted {
	/**
	 * 1) Max heap of size k
	 * 2) binary search, then use 2 pointers
	 * sorted output? 
	 * a) output (minDiff_i - i + k) % k then rotate by min_i
	 * b) increase 2 pointers until r - l == k then copy to output
	 */
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {1,2,3,4}, new int[] {1,2,3,4,5}, 4, 3};
		Object[] tc2 = new Object[] {new int[] {1,2,3,4}, new int[] {1,2,3,4,5}, 4, -1};
		Object[] tc3 = new Object[] {new int[] {1}, new int[] {1}, 4, -1};
		Object[] tc4 = new Object[] {new int[] {2,3,5}, new int[] {1,2,3,5}, 3, 4};
		Object[] tc5 = new Object[] {new int[] {3,3,4}, new int[] {0,0,1,2,3,3,4,7,7,8}, 3, 5};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		@Override
		public List<Integer> apply(int[] a, Integer k, Integer x) {
			if (k <= 0) return new ArrayList<>();
			int idx = Arrays.binarySearch(a, x);
			int left = idx < 0 ? Math.abs(idx + 1) - 1 : idx;
			int right = idx < 0 ? Math.abs(idx + 1) : idx + 1;
			for (; right - left - 1 < k && (right < a.length || left >= 0); ) {
				int vl = left >= 0 ? a[left] : Integer.MAX_VALUE;
				int vr = right < a.length ? a[right] : Integer.MAX_VALUE;
				int dl = left >= 0 ? vl - x : Integer.MAX_VALUE;
				int dr = right < a.length ? vr - x : Integer.MAX_VALUE;
				int compare = Integer.compare(Math.abs(dl), Math.abs(dr));
				if (compare == 0) {
					if (vl <= vr) {
						left--;
					} else {
						right++;
					}
				} else if (compare < 0) {
					left--;
				} else {
					right++;
				}
			}
			
			List<Integer> res = new ArrayList<>(right - left - 1);
			for (int i = left + 1; i < right; i++) {
				res.add(a[i]);
			}
			return res;
		}
	}
	public interface Problem extends TriFunction<int[], Integer, Integer, List<Integer>> {
		
	}
}
