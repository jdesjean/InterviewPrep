package org.ip.array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/subarray-sum-equals-k/">LC: 560</a>
 */
public class SubArraySumK {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {2, new int[] {1,1,1}, 2};
		Object[] tc2 = new Object[] {2, new int[] {1,2,3}, 3};
		Object[] tc3 = new Object[] {1, new int[] {1,0,1}, 2};
		Object[] tc4 = new Object[] {2, new int[] {1,1,2,1}, 3};
		Object[] tc5 = new Object[] {1, new int[] {-1,-1,1}, 0};
		Object[] tc6 = new Object[] {2, new int[] {-1,-1,-1}, -2};
		Object[] tc7 = new Object[] {3, new int[] {1,-1,0}, 0};
		Object[] tc8 = new Object[] {6, new int[] {0,0,0}, 0};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5, tc6, tc7, tc8};
		Problem[] solvers = new Problem[] {new Linear(), new Linear2(), new LinearMonotonic(), new Polynomial()};
		Test.apply(solvers, tcs);
	}
	public static class Linear implements Problem {
		@Override
		public int applyAsInt(int[] t, Integer u) {
			Map<Integer,List<Integer>> sums = new HashMap<>();
			int sum = 0;
			for (int i = 0; i < t.length; i++) {
				sum += t[i];
				List<Integer> list = sums.computeIfAbsent(sum, x -> new ArrayList<>());
				list.add(i);
			}
			sum = 0;
			int res = 0;
			for (int i = 0; i < t.length; i++) {
				int target = sum + u;
				List<Integer> index = sums.get(target);
				if (index != null) {
					for (int j = 0; j < index.size(); j++) {
						if (index.get(j) < i) continue;
						res += index.size() - j;
						break;
					}
				}
				sum += t[i];
			}
			return res;
		}
	}
	public static class Linear2 implements Problem {
		@Override
		public int applyAsInt(int[] t, Integer u) {
			int count = 0, sum = 0;
	        HashMap <Integer, Integer> map = new HashMap <>();
	        map.put(0, 1);
	        for (int i = 0; i < t.length; i++) {
	            sum += t[i];
	            count += map.getOrDefault(sum - u, 0);
	            map.compute(sum, (k, v) -> v == null ? 1 : v + 1);
	        }
	        return count;
		}
	}
	/**
	 * Only works when all numbers positives or negatives
	 */
	public static class LinearMonotonic implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			Set<Integer> sums = new HashSet<Integer>();
			int sum = 0;
			for (int i = 0; i < t.length; i++) {
				
				sums.add(sum);
			}
			sum = 0;
			int res = 0;
			for (int i = 0; i < t.length; i++) {
				sum += t[i];
				if (sums.contains(sum - u)) {
					res++;
				}
				sums.add(sum);
			}
			return res;
		}
		
	}
	
	public static class Polynomial implements Problem {

		@Override
		public int applyAsInt(int[] t, Integer u) {
			int res = 0;
			for (int i = 0; i < t.length; i++) {
				int sum = 0;
				for (int j = i; j < t.length; j++) {
					sum += t[j];
					if (sum == u) res++;
				}
			}
			return res;
		}
		
	}
	interface Problem extends ToIntBiFunction<int[], Integer> {
		
	}
}
