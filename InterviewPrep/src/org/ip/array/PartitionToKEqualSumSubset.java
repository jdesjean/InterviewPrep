package org.ip.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/partition-to-k-equal-sum-subsets/">LC: 698</a>
 */
public class PartitionToKEqualSumSubset {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {true, new int[] {4, 3, 2, 3, 5, 2, 1}, 4};
		Object[] tc2 = new Object[] {false, new int[] {7,2,2,2,2,2,2,2,3}, 3};
		Object[] tc3 = new Object[] {true, new int[] {10,10,10,7,7,7,7,7,7,6,6,6}, 3};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] {new Solver2(), new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public boolean test(int[] t, Integer u) {
			if (t == null|| t.length == 0) return false;
			int sum = Arrays.stream(t).reduce(0, Integer::sum);
			int k = u;
			if (sum % k != 0) return false;
			int avg = sum / k;
			
			Arrays.sort(t);
	        int i = t.length - 1;
	        if (t[i] > avg) return false;
	        while (i >= 0 && t[i] == avg) {
	            i--;
	            k--;
	        }
	        int[] cache = new int[k];
	        if (_test(cache, t, i, avg)) {
				return true;
			}
			return false;
		}
		boolean _test(int[] partitions, int[] nums, int i, int avg) {
			if (i < 0) {
				return true;
			}
			for (int j = 0; j < partitions.length; j++) {
				if (partitions[j] + nums[i] > avg) continue;
				partitions[j] += nums[i];
				if (_test(partitions, nums, i - 1, avg)) return true;
				partitions[j] -= nums[i];
				if (partitions[j] == 0) break; //we don't need to try multiple start from 0
			}
			return false;
		}
	}
	static class Solver implements Problem {

		@Override
		public boolean test(int[] t, Integer u) {
			int sum = Arrays.stream(t).reduce(0, Integer::sum);
			int k = u;
			if (sum % k != 0) return false;
			int avg = sum / k;
			Arrays.sort(t);
	        int last = t.length - 1;
	        if (t[last] > avg) return false;
	        while (last >= 0 && t[last] == avg) {
	        	last--;
	            k--;
	        }
	        
			List<Integer>[] cache = new ArrayList[avg + 1];
			for (int i = 0; i < cache.length; i++) {
				cache[i] = new ArrayList<>();
			}
			cache[0].add(1);
			for (int i = 0; i <= last; i++) {
				for (int j = cache.length - 1; j >= t[i]; j--) {
					if (cache[j - t[i]].size() == 0) continue;
					cache[j].add(i);
				}
			}
			if (cache[cache.length - 1].size() < k) return false;
			return visit(cache, t);
		}
		boolean visit(List<Integer>[] cache, int[] t) {
			//visit all subsets of cache[t.length - 1]
			//visit(cache, t, new boolean[t.length], cache.length - 1);
			return false;
		}
		boolean visit(List<Integer>[] cache, int[] t, boolean[] visited, int i) {
			if (i == 0) {
				return allVisited(visited);
			}
			for (Integer v : cache[i]) {
				if (visited[v]) continue;
				visited[v] = true;
				if (visit(cache, t, visited, i - t[v])) {
					return true;
				}
				visited[v] = false;
			}
			return false;
		}
		boolean allVisited(boolean[] visited) {
			for (int i = 0; i < visited.length; i++) {
				if (!visited[i]) return false;
			}
			return true;
		}
	}
	interface Problem extends BiPredicate<int[], Integer> {
		
	}
}
