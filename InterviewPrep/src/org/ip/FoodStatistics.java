package org.ip;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <a href="https://leetcode.com/discuss/interview-question/399386/">OA 2020</a>
 */
public class FoodStatistics {
	public static void main(String[] s) {
		tc1(new Iterative());
		tc2(new Iterative());
		tc3(new Iterative());
		tc4(new Iterative());
	}
	private static void tc1(Solver solver) {
		solver.solve(Arrays.asList(new Pair("orange", 10), new Pair("apple", 120), new Pair("banana", 200), new Pair("apple", 140), new Pair("apple", 130))).forEach((ele) -> {
	        System.out.print(ele + ",");
	    });
		System.out.println();
	}
	private static void tc2(Solver solver) {
		solver.solve(Arrays.asList(new Pair("banana", 90), new Pair("apple", 100), new Pair("apple", 260))).forEach((ele) -> {
	        System.out.print(ele + ",");
	    });
		System.out.println();
	}
	private static void tc3(Solver solver) {
		solver.solve(Arrays.asList(new Pair("grapefruit", 120), new Pair("grape", 200), new Pair("grapefruit", 150), new Pair("grapefruit", 150), new Pair("grape", 180))).forEach((ele) -> {
	        System.out.print(ele + ",");
	    });
		System.out.println();
	}
	private static void tc4(Solver solver) {
		solver.solve(Arrays.asList(new Pair("apple", 100), new Pair("apple", 101))).forEach((ele) -> {
	        System.out.print(ele + ",");
	    });
		System.out.println();
	}
	public static class Iterative implements Solver {

		@Override
		public Collection<Statistics> solve(List<Pair> foods) {
			Map<String, Statistics> stats = new TreeMap<>();
			for (Pair food : foods) {
				Statistics stat = stats.computeIfAbsent(food.name, ingnored -> new Statistics(food.name));
				stat.count++;
				stat.min = Math.min(stat.min, food.cost);
				stat.max = Math.max(stat.max, food.cost);
				stat.average = stat.average + (food.cost - stat.average) / stat.count;  
			}
			return stats.values();
		}
		
	}
			
	public interface Solver {
		public Collection<Statistics> solve(List<Pair> foods);
	}
	private static class Pair {
		final String name;
		final int cost;
		public Pair(String name, int cost) {
			super();
			this.name = name;
			this.cost = cost;
		}
	}
	private static class Statistics {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		int average;
		int count;
		final String name;
		public Statistics(String name) {
			this.name = name;
		}
		@Override
		public String toString() {
			return "Statistics [min=" + min + ", max=" + max + ", average=" + average + ", count=" + count + ", name="
					+ name + "]";
		}
		
	}
	
}
