package org.ip.greedy;

import java.util.Arrays;

import org.ip.array.Utils;

public class Scheduler2 {
	public static void main(String[] s) {
		int[] tasks = new int[] {5,2,1,6,4,4};
		Scheduler2 scheduler = new Scheduler2();
		System.out.println(scheduler.solve(tasks, 3)); // 6,1 + 5,2 + 4,4 = 8
		System.out.println(scheduler.solve(tasks, 2)); // 6,1,4 + 5,2,4 = 11
		System.out.println(scheduler.solve(new int[] {5,3,4,2,2}, 2)); // 5,3 + 4,2,2 = 8
	}
	public int solve(int[] tasks, int thread) {
		int sum = Utils.sum(tasks, tasks.length);
		int avg = sum / thread;
		Arrays.sort(tasks);
		int max = 0;
		for (int i = 0, j = tasks.length - 1, t = 0; i <= j;) {
			if (Math.abs(t + tasks[j] - avg) < Math.abs(t - avg)) {
				t+=tasks[j--];
				max = Math.max(max, t);
			} else if (Math.abs(t + tasks[i] - avg) < Math.abs(t - avg)) {
				t+=tasks[i++];
				max = Math.max(max, t);
			} else {
				t = 0;
			}
		}
		return max;
	}
}
