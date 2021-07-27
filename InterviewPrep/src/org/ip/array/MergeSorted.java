package org.ip.array;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * <a href="https://leetcode.com/problems/merge-sorted-array/">LC: 88</a>
 */
public class MergeSorted {
	public static void main(String[] s) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object[] tc1 = new Object[] {new int[] {1,2,2,3,5,6}, new int[] {1,2,3,0,0,0}, 3, new int[] {2,5,6}, 3};
		Object[] tc2 = new Object[] {new int[] {1}, new int[] {1}, 1, new int[] {}, 0};
		Object[] tc3 = new Object[] {new int[] {1,1,1}, new int[] {1,1,0}, 2, new int[] {1}, 1};
		Object[] tc4 = new Object[] {new int[] {1}, new int[] {0}, 0, new int[] {1}, 1};
		List<Object[]> tcs = Arrays.asList(tc1, tc2, tc3, tc4);
		Class<? extends Problem>[] solvers = new Class[] {Solver.class};
		for (Object[] tc : tcs) {
			Utils.print((int[])tc[0]);
			System.out.println();
			for (Class<? extends Problem> solver : solvers) {
				Problem function = solver.getConstructor(int[].class, int.class).newInstance((int[])tc[1], tc[2]);
				function.accept((int[]) tc[3], (Integer) tc[4]);
				Utils.print((int[]) tc[1]);
				System.out.println();
			}
			System.out.println();
		}
	}
	public static class Solver implements Problem {
		private int[] nums1;
		private int n;

		public Solver(int[] nums, int n) {
			this.nums1 = nums;
			this.n = n;
		}
		
		@Override
		public void accept(int[] nums2, Integer u) {
			for (int r1 = n - 1, r2 = nums2.length - 1, i = nums1.length - 1; i >= 0; i--) {
				if (r1 >= 0 && r2 >= 0) {
					if (nums1[r1] >= nums2[r2]) {
						nums1[i] = nums1[r1--];
					} else {
						nums1[i] = nums2[r2--];
					}
				} else {
					int[] nums = r1 >= 0 ? nums1 : nums2;
					int r = r1 >= 0 ? r1-- : r2--;
					nums1[i] = nums[r];
				}
			}
		}
		
	}
	public interface Problem extends BiConsumer<int[], Integer>{
		
	}
}
