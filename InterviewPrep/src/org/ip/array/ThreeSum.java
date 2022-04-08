package org.ip.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/3sum/">LC: 15</a>
 */
public class ThreeSum {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[][] {{-1,-1,2}, {-1,0,1}}, new int[] {-1,0,1,2,-1,-4}};
		Object[] tc2 = new Object[] {new int[][] {}, new int[] {}};
		Object[] tc3 = new Object[] {new int[][] {}, new int[] {0}};
		Object[] tc4 = new Object[] {new int[][] {}, new int[] {0,0,0}};
		Object[] tc5 = new Object[] {new int[][] {{-1,0,1}}, new int[] {1,-1,-1,0}};
		
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5};
		Solver[] solvers = new Solver[] {new MapSolver(), new SortSolver()};
		Test.apply(solvers, tcs);
	}
	public static class SortSolver implements Solver {
		@Override
		public List<List<Integer>> apply(int[] nums) {
			List<List<Integer>> res = new ArrayList<>();
			Arrays.sort(nums);
			for (int i = 0; i < nums.length; i++) {
				if (i == 0 || nums[i - 1] != nums[i]) { // Avoid dups
					_solve(nums, res, i);
				}
			}
			return res;
		}
		
		void _solve(int[] nums, List<List<Integer>> res, int index) {
			for (int i = index + 1, j = nums.length - 1; i < j;) {
				int sum = nums[index] + nums[i] + nums[j];
				if (sum == 0) {
					res.add(Arrays.asList(nums[index], nums[i++], nums[j--]));
					while (i < j && nums[i] == nums[i - 1]) { // Avoid dups
						++i;
					}
				} else if (sum < 0) {
					i++;
				} else {
					j--;
				}
			}
		}
		
	}
	public static class MapSolver implements Solver {

		@Override
		public List<List<Integer>> apply(int[] nums) {
			Map<Integer, Set<Integer>> map = map(nums);
			Set<Triplet> set = new LinkedHashSet<>();
			//Arrays.sort(nums);
			for (int i = 0; i < nums.length; i++) {
				Set<Triplet> current = _solve(nums, map, i);
				set.addAll(current);
			}
			return toList(set);
		}
		List<List<Integer>> toList(Set<Triplet> set) {
			List<List<Integer>> res = new ArrayList<>();
			for (Triplet triplet : set) {
				res.add(Arrays.asList(triplet.a, triplet.b, triplet.c));
			}
			return res;
		}
		Map<Integer, Set<Integer>> map(int[] nums) {
			Map<Integer, Set<Integer>> map = new HashMap<>();
			for (int i = 0; i < nums.length; i++) {
				Set<Integer> list = map.computeIfAbsent(nums[i], (v) -> new HashSet<>());
				list.add(i);
			}
			return map;
		}
		Set<Triplet> _solve(int[] nums, Map<Integer, Set<Integer>> map, int index) {
			Set<Triplet> res = new LinkedHashSet<>();
			int target = -nums[index];
			for (int i = index + 1; i < nums.length; i++) {
				int val = target - nums[i];
				Set<Integer> indexes = map.get(val);
				if (indexes == null) continue;
				boolean containsIndex = indexes.contains(index);
				boolean containsCurrent = indexes.contains(i);
				if (containsIndex && containsCurrent) {
					if (indexes.size() <= 2) break;
				} else if (containsIndex || containsCurrent) {
					if (indexes.size() <= 1) break;
				}
				res.add(new Triplet(nums[index], nums[i], val));
				while (i < nums.length -1 && nums[i] == nums[i + 1]) {
					i++;
				}
			}
			return res;
		}
		
	}
	public interface Solver extends Function<int[], List<List<Integer>>>{
		
	}
	private static class Triplet {
		private final int a;
		private final int b;
		private final int c;
		private final int[] sorted;
		public Triplet(int a, int b, int c) {
			this.a = a;
			this.b = b;
			this.c = c;
			sorted = sort(a,b,c);
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + sorted[0];
			result = prime * result + sorted[1];
			result = prime * result + sorted[2];
			return result;
		}
		int[] sort(int a, int b, int c) {
			int[] res = new int[] {a, b, c};
			Arrays.sort(res);
			return res;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Triplet other = (Triplet) obj;
			if (sorted[0] != other.sorted[0])
				return false;
			if (sorted[1] != other.sorted[1])
				return false;
			if (sorted[2] != other.sorted[2])
				return false;
			return true;
		}
		
	}
}
