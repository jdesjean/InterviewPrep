package org.ip.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/problems/task-scheduler/">LC: 621</a>
 */
public class TaskScheduler {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				TaskScheduler::tc1,
				TaskScheduler::tc2,
				TaskScheduler::tc3,
				TaskScheduler::tc4,
				TaskScheduler::tc5);
		Solver[] solvers = new Solver[] {new Recursive(), new Greedy()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		System.out.println(solver.solve(new char[] {'A','A','A','B','B','B'}, 2));
	}
	public static void tc2(Solver solver) {
		System.out.println(solver.solve(new char[] {'A','A','A','B','B','B'}, 0));
	}
	public static void tc3(Solver solver) {
		System.out.println(solver.solve(new char[] {'A','A','A','A','A','A','B','C','D','E','F','G'}, 2));
	}
	public static void tc4(Solver solver) {
		System.out.println(solver.solve(new char[] {'A','A','A','B','B','B','C','C','C'}, 1));
	}
	public static void tc5(Solver solver) {
		System.out.println(solver.solve(new char[] {'A','A','A','B','B','B','C','C','C','D','D','E'}, 2));
	}
	private static class Greedy implements Solver {

		@Override
		public int solve(char[] tasks, int n) {
			if (tasks == null) return 0;
			if (n == 0) return tasks.length;
			int[] freqs = new int[26];
			int fmax = 0;
			for (int i = 0; i < tasks.length; i++) {
				int value = ++freqs[tasks[i] - 'A'];
				if (value > freqs[fmax]) {
					fmax = tasks[i] - 'A';
				}
			}
			int idle = (freqs[fmax] - 1) * n;
			for (int i = 25; i >= 0 && idle > 0; i--) {
				if (i == fmax) continue;
				idle -= Math.min(freqs[fmax] - 1, freqs[i]);
			}
			idle = Math.max(idle, 0);
			return idle + tasks.length;
		}
	}
	private static class Recursive implements Solver {

		@Override
		public int solve(char[] tasks, int n) {
			if (tasks == null) return 0;
			if (n == 0) return tasks.length;
			Map<Character, Integer> count = new HashMap<>();
			Map<Character, Integer> lastIndex = new HashMap<>();
			Set<Character> remaining = new HashSet<>();
			for (int i = 0; i < tasks.length; i++) {
				count.compute(tasks[i], (k, v) -> v == null ? 1 : v + 1);
				remaining.add(tasks[i]);
			}
			return solve(count, remaining, n, lastIndex, 0);
		}
		
		private int solve(Map<Character, Integer> tasks, Set<Character> remaining, int n, Map<Character, Integer> lastIndex, int index) {
			if (remaining.isEmpty()) {
				return index;
			}
			int min = Integer.MAX_VALUE;
			boolean skipped = true;
			
			for (Entry<Character, Integer> task : tasks.entrySet()) {
				if (task.getValue() == 0) continue;
				Character key = task.getKey();
				int prevIndex = lastIndex.getOrDefault(key, -n - 1);
				if (index - prevIndex > n) {
					skipped = false;
					lastIndex.compute(key, (k, v) -> index);
					int value = tasks.compute(key, (k, v) -> v - 1);
					if (value == 0) {
						remaining.remove(key);
					}
					min = Math.min(min, solve(tasks, remaining, n, lastIndex, index + 1));
					if (value == 0) {
						remaining.add(key);
					}
					lastIndex.put(key, prevIndex);
					tasks.compute(key, (k, v) -> value + 1);
				}
			}
			if (skipped) {
				min = Math.min(min, solve(tasks, remaining, n, lastIndex, index + 1));
			}
			return min;
		}
	}
	private interface Solver {
		public int solve(char[] tasks, int n);
	}
}
