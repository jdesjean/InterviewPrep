package org.ip.array;

import java.util.Arrays;
import java.util.BitSet;
import java.util.function.Predicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/partition-equal-subset-sum/">LC: 416</a>
 */
public class PartitionEqualSubsetSum {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {true, new int[] {1,5,11,5}};
		Object[] tc2 = new Object[] {false, new int[] {1,2,3,5}};
		Object[] tc3 = new Object[] {true, new int[] {14,9,8,4,3,2}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2(), new Solver3(), new RecursivePartitionBalanced(), new DPPartitionBalanced()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public boolean test(int[] t) {
			int sum = Arrays.stream(t).sum();
			if (sum % 2 != 0) return false;
			int target = sum / 2;
			boolean[] set = new boolean[target + 1];
			set[0] = true;
			for (int i = 0; i < t.length && !set[target]; i++) {
				for (int j = target - t[i]; j >= 0; j--) {
					set[j + t[i]] |= set[j];
				}
			}
			return set[target];
		}
		
	}
	static class Solver2 implements Problem {

		@Override
		public boolean test(int[] t) {
			int sum = Arrays.stream(t).sum();
			if (sum % 2 != 0) return false;
			int target = sum / 2;
			BitSet set = new BitSet();
			set.set(0);
			for (int i = 0; i < t.length && !set.get(target); i++) {
				for (int j = target - t[i]; j >= 0; j--) {
					if (!set.get(j)) continue;
					set.set(j + t[i]);
				}
			}
			return set.get(target);
		}
		
	}
	static class Solver3 implements Problem {

		@Override
		public boolean test(int[] t) {
			int sum = Arrays.stream(t).sum();
			if (sum % 2 != 0) return false;
			int target = sum / 2;
			BitSet set = new BitSet();
			set.set(target);
			for (int i = 0; i < t.length && !set.get(0); i++) {
				BitSet next = shift(set, t[i]);
				set.or(next);
			}
			return set.get(0);
		}
		BitSet shift(BitSet bs, int n) {
			return bs.get(n, Math.max(n, bs.length()));
		}
	}
	public static class RecursivePartitionBalanced implements Problem {

		@Override
		public boolean test(int[] array) {
			int[] buffer = new int[array.length];
			int sum = Arrays.stream(array).sum();
			if (sum % 2 == 1) return false;
			int target = sum / 2;
			Boolean[][] memo = new Boolean[array.length + 1][target + 1];
			return partition(array, buffer, sum / 2, 0, 0, 0, memo);
		}
		public boolean partition(int[] array, int[] buffer, int target, int current, int w, int i, Boolean[][] memo) {
			if (current == target) {
				return true;
			}
			if (i >= array.length) {
				return false;
			}
			if (current > target) {
				return false;
			}
			if (memo[i][current] != null) {
				return memo[i][current];
			}
			buffer[w] = array[i];
			boolean res = partition(array,buffer,target,current+array[i],w + 1,i + 1,memo) || 
			partition(array,buffer,target,current,w,i + 1,memo);
			memo[i][current] = res;
			return res;
		}
		
	}
	public static class DPPartitionBalanced implements Problem {

		@Override
		public boolean test(int[] array) {
			int sum = Arrays.stream(array).sum();
			if (sum % 2 == 1) return false;
			int target = sum / 2;
			boolean[][] cache = new boolean[target + 1][array.length + 1];
			for (int i = 0; i <= array.length; i++) {
				cache[0][i] = true;
			}
			for (int l = 1; l < cache.length; l++) {
				for (int c = 1; c < cache[l].length; c++) {
					if (l < array[c - 1]) {
						cache[l][c] = cache[l][c - 1];
					} else {
						cache[l][c] = cache[l][c - 1] || cache[l - array[c - 1]][c - 1];
					}
				}
			}
			return cache[target][array.length];
		}
		
	}
	interface Problem extends Predicate<int[]> {
		
	}
}
