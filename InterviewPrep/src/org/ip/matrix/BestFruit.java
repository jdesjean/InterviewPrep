package org.ip.matrix;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/discuss/interview-question/549128/">OA 2020</a> 
 */
public class BestFruit {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				BestFruit::tc1,
				BestFruit::tc2,
				BestFruit::tc3,
				BestFruit::tc4);
		Solver[] solvers = new Solver[] {new PQ()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		System.out.println(solver.solve(new int[][] {
			{1, 2, 3},
			{2, 3, 1}
		}));
	}
	public static void tc2(Solver solver) {
		System.out.println(solver.solve(new int[][] {
			{1, 2, 3, 4},
			{1, 2, 3, 4},
			{2, 4, 3, 1},
			{3, 4, 2, 1},
			{4, 3, 2, 1}
		}));
	}
	public static void tc3(Solver solver) {
		System.out.println(solver.solve(new int[][] {
			{2, 1},
			{2, 1}
		}));
	}
	public static void tc4(Solver solver) {
		System.out.println(solver.solve(new int[][] {
			{1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
			{2, 3, 4, 5, 6, 7, 8, 9, 10, 1},
			{3, 4, 5, 6, 7, 8, 9, 10, 1, 2},
			{4, 5, 6, 7, 8, 9, 10, 1, 2, 3},
			{5, 6, 7, 8, 9, 10, 1, 2, 3, 4},
			{6, 7, 8, 9, 10, 1, 2, 3, 4, 5},
			{7, 8, 9, 10, 1, 2, 3, 4, 5, 6},
			{8, 9, 10, 1, 2, 3, 4, 5, 6, 7},
			{9, 10, 1, 2, 3, 4, 5, 6, 7, 8},
			{10, 1, 2, 3, 4, 5, 6, 7, 8, 9}
		}));
	}
	public static class PQ implements Solver {

		@Override
		public int solve(int[][] preferences) {
			Set<Integer> removed = new HashSet<>();
			Map<Integer, Fruit> freq = new HashMap<>();
			int[] votes = new int[preferences.length];
			for (int i = 0; i < preferences.length; i++) {
				final int kind = preferences[i][0];
				Fruit fruit = freq.computeIfAbsent(kind, ignored -> new Fruit(kind));
				fruit.add(i);
			}
			while (majority(freq, preferences.length) == null) {
				Fruit min = min(freq);
				removed.add(min.kind);
				freq.remove(min.kind);
				for (int index : min.index) {
					while (removed.contains(preferences[index][++votes[index]])) {}
					final int kind = preferences[index][votes[index]];
					Fruit fruit = freq.computeIfAbsent(kind, ignored -> new Fruit(kind));
					fruit.add(index);
				}
			}
			return majority(freq, preferences.length).kind;
		}
		private Fruit majority(Map<Integer, Fruit> freq, int k) {
			int majority = k % 2 == 0 ? k / 2 + 1 : (k + 1) / 2;
			for (Fruit fruit : freq.values()) {
				if (fruit.index.size() >= majority) {
					return fruit;
				}
			}
			return null;
		}
		private Fruit min(Map<Integer, Fruit> freq) {
			Fruit min = null;
			for (Fruit fruit : freq.values()) {
				if (min == null || fruit.compareTo(min) < 0) {
					min = fruit;
				}
			}
			return min;
		}
	}
	public static class Fruit implements Comparable<Fruit> {
		final int kind;
		final Set<Integer> index = new HashSet<>();
		public Fruit(int kind) {
			this.kind = kind;
		}
		public void add(int index) {
			this.index.add(index);
		}
		
		@Override
		public int hashCode() {
			return kind;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Fruit other = (Fruit) obj;
			return kind == other.kind;
		}
		@Override
		public int compareTo(Fruit o) {
			int size = Integer.compare(index.size(), o.index.size());
			return size != 0 ? size : Integer.compare(kind, o.kind);
		}
	}
	public interface Solver {
		public int solve(int[][] preferences);
	}
}
