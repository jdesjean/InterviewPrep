package org.ip.array;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/discuss/interview-question/352459/">OA 2019</a>    
 */
public class LargestSubarrayLengthK {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				LargestSubarrayLengthK::tc1,
				LargestSubarrayLengthK::tc2,
				LargestSubarrayLengthK::tc3,
				LargestSubarrayLengthK::tc4,
				LargestSubarrayLengthK::tc5,
				LargestSubarrayLengthK::tc6);
		Solver[] solvers = new Solver[] {new Distinct(), new Iterative(), new Iterative2()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		System.out.println(solver.solve(new int[] {1,4,3,2,5}, 4));
	}
	public static void tc2(Solver solver) {
		System.out.println(solver.solve(new int[] {4,1,3,2,5}, 4));
	}
	public static void tc3(Solver solver) {
		System.out.println(solver.solve(new int[] {4,4,3,2,5}, 4));
	}
	public static void tc4(Solver solver) {
		System.out.println(solver.solve(new int[] {4,4,5,2,5}, 4));
	}
	public static void tc5(Solver solver) {
		System.out.println(solver.solve(new int[] {1,4,4,3,2,5}, 3));
	}
	public static void tc6(Solver solver) {
		System.out.println(solver.solve(new int[] {5, 5, 3, 5, 5, 4}, 3));
	}
	public interface Solver {
		public int solve(int[] a, int k);
	}
	/**
	 * O(N)
	 */
	public static class Distinct implements Solver {

		@Override
		public int solve(int[] a, int k) {
			if (a.length < k) return -1;
			int largest = 0;
			for (int i = 1; i <= a.length - k; i++) {
				if (a[i] <= a[largest]) continue;
				largest = i;
			}
			return largest;
		}
	}
	/**
	 * O(N)
	 */
	public static class Iterative2 implements Solver {

		@Override
		public int solve(int[] a, int k) {
			if (a.length < k) return -1;
			int largest = 0;
			int largest2 = -1;
			for (int i = 1; i <= a.length - k; i++) {
				if (a[i] <= a[largest]) continue;
				largest2 = largest = i;
			}
			for (int i = 0; i <= a.length - k; i++) {
				if (a[i] != a[largest]) continue;
				int j = i + 1;
				for (; j < a.length; j++) {
					if (a[j] == a[largest]) continue;
					if (largest2 == -1 || a[j] > a[largest2]) {
						largest2 = j;
						largest = i;
						break;
					}
				}
				i = j;
			}
			return largest;
		}
		boolean larger(int[] a, int largest, int i, int k) {
			for (int j = 0; j < k; j++) {
				if (a[i + j] == a[largest + j]) continue;
				else return a[i + j] > a[largest + j];
			}
			return false;
		}
	}
	/**
	 * O(NK)
	 */
	public static class Iterative implements Solver {

		@Override
		public int solve(int[] a, int k) {
			if (a.length < k) return -1;
			int largest = 0;
			for (int i = 1; i <= a.length - k; i++) {
				if (!larger(a, largest, i, k)) continue;
				largest = i;
			}
			return largest;
		}
		boolean larger(int[] a, int largest, int i, int k) {
			for (int j = 0; j < k; j++) {
				if (a[i + j] == a[largest + j]) continue;
				else return a[i + j] > a[largest + j];
			}
			return false;
		}
	}
}
