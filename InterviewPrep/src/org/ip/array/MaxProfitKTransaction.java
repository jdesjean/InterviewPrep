package org.ip.array;

import java.util.Arrays;

//EPI 2018: 5.7
public class MaxProfitKTransaction {
	public static void main(String[] s) {
		MaxProfitKTransaction1[] algos = new MaxProfitKTransaction1[] {new MaxProfitKTransaction1()};
		for (MaxProfitKTransaction1 algo : algos) {
			int[] array = new int[] {310,315,275,295,260,270,290,230,255,250};
			System.out.println(algo.solve(array, 2));
			array = new int[] {1,2,3,4};
			System.out.println(algo.solve(array, 2));
		}
	}
	public static class MaxProfitKTransaction1 {

		public int solve(int[] array, int k) {
			int[] min = new int[k];
			Arrays.fill(min, Integer.MAX_VALUE);
			int[] max = new int[k];
 			for (int i = 0; i < array.length; i++) {
				for (int j = k - 1; j >= 0; j--) {
					max[j] = Math.max(max[j], array[i] - min[j]);
					min[j] = Math.min(min[j], array[i] - (j > 0 ? max[j - 1] : 0));
				}
			}
			return max[k - 1];
		}
	}
}
