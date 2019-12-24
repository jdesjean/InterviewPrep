package org.ip.sort;

import java.util.Arrays;

// EPI 2018: 13.5
public class SmallestNonConstructibleValue {
	public static void main(String[] s) {
		System.out.println(new SmallestNonConstructibleValue().solve(new int[] {1,1,1,1,1,5,10,25}));
		System.out.println(new SmallestNonConstructibleValue().solve(new int[] {1,2,2,4,12,15}));
	}
	public int solve(int[] array) {
		Arrays.sort(array);
		if (array[0] > 1) return 1;
		int smallest = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > smallest + 1) break;
			smallest += array[i];
		}
		return smallest + 1;
	}
}
