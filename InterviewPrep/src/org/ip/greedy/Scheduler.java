package org.ip.greedy;

import java.util.Arrays;

import org.ip.array.Utils;

// EPI: 18.1
// EPI 2018: 17.1
public class Scheduler {
	public static void main(String[] s) {
		int[] tasks = new int[] {5,2,1,6,4,4};
		System.out.println(solve(tasks, 3)); // 6,1 + 5,2 + 4,4 = 8
		System.out.println(solve(tasks, 2)); // 6,1,4 + 5,2,4 = 11
		System.out.println(solve(new int[] {5,3,4,2,2}, 2)); // 5,3 + 4,2,2 = 8
	}
	public static int solve(int[] tasks, int workers) {
		int sum = Utils.sum(tasks, tasks.length);
		int div = sum / workers;
		Arrays.sort(tasks);
		int max = 0;
		for (int w = 0, ti = 0, tj = tasks.length - 1; w < workers; w++) {
			int time = 0;
			while (time < div) {
				int diff = div - time;
				int index = Math.abs(Utils.binarySearch(tasks, ti, tj, diff));
				time += tasks[index];
				if (index == ti) {
					ti++;
				} else if (index == tj){
					tj--;
				} else {
					if (Math.abs(index - ti) < Math.abs(index - tj)) {
						Utils.shift(tasks, ti++, index);
					} else {
						Utils.shiftBackward(tasks, index, tj--);
					}
				}
			}
			max = Math.max(max, time);
		}
		return max;
	}
}
