package org.ip.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/problems/intersection-of-two-arrays-ii/">LC: 350</a>
 */
public class IntersectionArrays {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				IntersectionArrays::tc1,
				IntersectionArrays::tc2,
				IntersectionArrays::tc3);
		Solver[] solvers = new Solver[] {new MapSolver()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		Utils.println(solver.intersect(new int[] {1,2,2,1}, new int[] {2,2}));
	}
	public static void tc2(Solver solver) {
		Utils.println(solver.intersect(new int[] {4,9,5}, new int[] {9,4,9,8,4}));
	}
	public static void tc3(Solver solver) {
		Utils.println(solver.intersect(new int[] {}, new int[] {}));
	}
	/**
	 * Solution when both arrays are unsorted and fit in memory
	 * 1) If arrays are sorted, 1 pointer through each array
	 * 2a) If one array is much smaller and sorted then binary search
	 * 2b) If one array is much smaller, but not sorted then 1 pointer
	 * 3) Partition data. 
	 * 3a) Can either use map solution, but must keep track of what was found in nums1
	 * 3b) Sort then 1 pointer through each. 
	 */
	public static class MapSolver implements Solver {

		@Override
		public int[] intersect(int[] nums1, int[] nums2) {
			if (nums1 == null || nums2 == null) return new int[0];
			Map<Integer, Integer> map1 = map(nums1);
			Map<Integer, Integer> map2 = map(nums2);
			Map<Integer, Integer> res = new HashMap<>();
			int count = 0;
			for (Entry<Integer, Integer> entry : map1.entrySet()) {
				if (!map2.containsKey(entry.getKey())) continue;
				int min = Math.min(map2.get(entry.getKey()), entry.getValue());
				res.put(entry.getKey(), min);
				count += min;
			}
			int[] nums = new int[count];
			int index = 0;
			for (Entry<Integer, Integer> entry : res.entrySet()) {
				for (int i = 0; i < entry.getValue(); i++) {
					nums[index++] = entry.getKey();
				}
			}
			return nums;
		}
		
		Map<Integer, Integer> map(int[] nums) {
			Map<Integer, Integer> map = new HashMap<>(); 
			for (int i = 0; i < nums.length; i++) {
				map.compute(nums[i], (key, value) -> value == null ? 1 : value + 1);
			}
			return map;
		}
		
	}
	public interface Solver {
		public int[] intersect(int[] nums1, int[] nums2);
	}
}
