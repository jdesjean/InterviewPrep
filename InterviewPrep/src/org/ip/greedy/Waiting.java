package org.ip.greedy;

import java.util.Arrays;

// EPI: 18.2
// EPI 2018: 17.2
public class Waiting {
	public static void main(String[] s) {
		int[] tasks = new int[] {2,5,1,3};
		System.out.println(solve(tasks));
	}
	public static int solve(int[] tasks) {
		Arrays.sort(tasks);
		int sum = 0;
		for (int i = 1; i < tasks.length; i++) {
			sum += tasks[i-1] * (tasks.length - i);
		}
		return sum;
	}
}
