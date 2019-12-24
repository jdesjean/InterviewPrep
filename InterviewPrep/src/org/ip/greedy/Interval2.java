package org.ip.greedy;

import java.util.Arrays;
import java.util.Comparator;

// EPI: 18.3
public class Interval2 {
	public static void main(String[] s) {
		Task[] tasks = new Task[] {new Task(1,2),new Task(2,3),new Task(3,4),new Task(2,3),new Task(3,4),new Task(4,5)};
		System.out.println(solve(tasks));
	}
	public static int solve(Task[] tasks) {
		Arrays.sort(tasks, new TaskComparator());
		int min = 1;
		for (int i = 1, end = tasks[0].end; i < tasks.length; i++) {
			if (tasks[i].start > end){
				end = tasks[i].end;
				min++;
			} 
		}
		return min;
	}
	public static class TaskComparator implements Comparator<Task> {

		@Override
		public int compare(Task o1, Task o2) {
			return o1.end - o2.end;
		}
		
	}
	public static class Task {
		public int start;
		public int end;
		public Task(int start, int end) {this.start=start;this.end=end;}
	}
}
