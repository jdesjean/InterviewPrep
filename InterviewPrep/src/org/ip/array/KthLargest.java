package org.ip.array;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * <a href="https://leetcode.com/problems/kth-largest-element-in-an-array/">LC: 215</a>
 */
public class KthLargest {
	public static void main(String[] s) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object[] tc1 = new Object[] {5, new int[] {3,2,1,5,6,4}, 2};
		Object[] tc2 = new Object[] {4, new int[] {3,2,3,1,2,4,5,5,6}, 4};
		Object[] tc3 = new Object[] {2, new int[] {2,2,2,2,1}, 2};
		Object[] tc4 = new Object[] {1, new int[] {1,1,1,1,1,2}, 2};
		Object[] tc5 = new Object[] {1, new int[] {1,1}, 3};
		List<Object[]> tcs = Arrays.asList(tc1, tc2, tc3, tc4, tc5);
		Class<Function<Integer, Integer>>[] solvers = new Class[] {Solver.class};
		for (Object[] tc : tcs) {
			System.out.print(tc[0]);
			for (Class<Function<Integer, Integer>> solver : solvers) {
				Function<Integer, Integer> function = solver.getConstructor(int[].class).newInstance(tc[1]);
				System.out.print(":" + function.apply((Integer) tc[2]));
			}
			System.out.println();
		}
	}
	public static class Solver implements Function<Integer, Integer> {
		private int[] array;
		public Solver(int[] array) {
			this.array = array;
		}

		@Override
		public Integer apply(Integer u) {
			int index = u - 1;
			int l = 0;
			for (int h = array.length - 1; l < h; ) {
				int pivot = l + (h - l) / 2;
				pivot = partition(pivot, l, h);
				if (pivot == index) {
					l = pivot;
					break;
				} else if (pivot < index) {
					l = pivot + 1;
				} else {
					h = pivot - 1;
				}
			}
			return array[l];
		}
		int partition(int pivot, int l, int r) {
			int vPivot = array[pivot];
			for (int m = l; m <= r; ) {
				int v = array[m];
				if (v > vPivot) {
					swap(l++, m++);
				} else if (v == vPivot) {
					m++;
				} else {
					swap(m, r--);
				}
			}
			return l;
		}
		public void swap(int i, int j) {
			int tmp = array[i];
			array[i] = array[j];
			array[j] = tmp;
		}
	}
}
