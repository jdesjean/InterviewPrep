package org.ip.array;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/combination-sum/">LC: 39</a>
 */
public class CombinationSum {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new int[][] {{2,2,3}, {7}}, new int[] {2,3,6,7}, 7};
		Object[] tc2 = new Object[] { new int[][] {{2,2,2,2}, {2,3,3}, {3,5}}, new int[] {2,3,5}, 8};
		
		Object[][] tcs = new Object[][] { tc1, tc2};
		Problem[] solvers = new Problem[] { new DPSolver(), new BacktrackSolver() };
		Test.apply(solvers, tcs);
	}
	static class BacktrackSolver implements Problem {

		@Override
		public List<List<Integer>> apply(int[] t, Integer u) {
			int[] cache = new int[u];
			List<List<Integer>> res = new ArrayList<>();
			visit(list -> res.add(list), cache, 0, t, u, 0, 0);
			return res;
		}
		void visit(Consumer<List<Integer>> consumer, int[] cache, int cacheIndex, int[] t, int target, int sum, int prev) {
			if (sum > target) {
				return;
			} else if (sum == target) {
				List<Integer> res = new ArrayList<>(cacheIndex);
				for (int i = 0; i < cacheIndex; i++) {
					res.add(cache[i]);
				}
				consumer.accept(res);
				return;
			}
			for (int i = 0; i < t.length; i++) {
				if (t[i] < prev) continue;
				cache[cacheIndex] = t[i];
				visit(consumer, cache, cacheIndex + 1, t, target, sum + t[i], t[i]);
			}
		}
		
	}
	static class DPSolver implements Problem {

		@Override
		public List<List<Integer>> apply(int[] t, Integer u) {
			int k = u;
			ArrayList<Integer>[] cache = new ArrayList[k + 1];
			cache[0] = new ArrayList<Integer>();
			cache[0].add(0);
			for (int i = 1; i <= k; i++) {
				for (int j = 0; j < t.length; j++) {
					int p = i - t[j];
					if (p < 0) continue;
					if (cache[p] != null) {
						if (cache[i] == null) {
							cache[i] = new ArrayList<>();
						}
						cache[i].add(t[j]);
					}
				}
			}
			List<List<Integer>> res = new ArrayList<>();
			if (cache[k] == null) return res;
			visit(list -> {
				res.add(list);
			}, cache, k, new int[k], 0);
			return res;
		}
		void visit(Consumer<List<Integer>> consumer, ArrayList<Integer>[] cache, int cacheIndex, int[] buffer, int bufferIndex) {
			if (cacheIndex == 0) {
				List<Integer> res = new ArrayList<>(bufferIndex);
				for (int i = 0; i < bufferIndex; i++) {
					res.add(buffer[i]);
				}
				consumer.accept(res);
				return;
			}
			for (Integer value : cache[cacheIndex]) {
				if (bufferIndex > 0 && value < buffer[bufferIndex - 1]) continue;
				buffer[bufferIndex] = value;
				visit(consumer, cache, cacheIndex - value, buffer, bufferIndex + 1);
			}
		}
	}
	interface Problem extends BiFunction<int[], Integer, List<List<Integer>>> {
		
	}
}
