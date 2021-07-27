package org.ip.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * <a href="https://leetcode.com/problems/subarray-sum-equals-k/">LC: 560</a>
 */
public class SubArraySumK {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {1,1,1}, 2, 2};
		Object[] tc2 = new Object[] {new int[] {1,2,3}, 3, 2};
		Object[] tc3 = new Object[] {new int[] {1,0,1}, 2, 1};
		Object[] tc4 = new Object[] {new int[] {1,1,2,1}, 3, 2};
		Object[] tc5 = new Object[] {new int[] {-1,-1,1}, 0, 1};
		Object[] tc6 = new Object[] {new int[] {-1,-1,-1}, -2, 2};
		Object[] tc7 = new Object[] {new int[] {1,-1,0}, 0, 3};
		Object[] tc8 = new Object[] {new int[] {0,0,0}, 0, 6};
		List<Object[]> tcs = Arrays.asList(tc1, tc2, tc3, tc4, tc5, tc6, tc7, tc8);
		BiFunction<int[], Integer, Integer>[] solvers = new BiFunction[] {new Linear(), new Linear2(), new LinearMonotonic(), new Polynomial()};
		for (Object[] tc : tcs) {
			for (BiFunction<int[], Integer, Integer> solver : solvers) {
				System.out.print(","  + String.valueOf(solver.apply((int[]) tc[0], (Integer) tc[1])));
			}
			System.out.print("," + String.valueOf(tc[2]));
			System.out.println();
		}
	}
	public static class Linear implements BiFunction<int[], Integer, Integer> {
		@Override
		public Integer apply(int[] t, Integer u) {
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
	public static class Linear2 implements BiFunction<int[], Integer, Integer> {
		@Override
		public Integer apply(int[] t, Integer u) {
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
	public static class LinearMonotonic implements BiFunction<int[], Integer, Integer> {

		@Override
		public Integer apply(int[] t, Integer u) {
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
	
	public static class Polynomial implements BiFunction<int[], Integer, Integer> {

		@Override
		public Integer apply(int[] t, Integer u) {
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
}
