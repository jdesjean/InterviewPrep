package org.ip.array;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/problems/best-time-to-buy-and-sell-stock/">LC: 121</a>
 */
public class MaxProfit {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				MaxProfit::tc1,
				MaxProfit::tc2);
		Solver[] solvers = new Solver[] {new DP()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		System.out.println(solver.solve(new int[] {7,1,5,3,6,4}));
	}
	public static void tc2(Solver solver) {
		System.out.println(solver.solve(new int[] {7,6,4,3,1}));
	}
	public static class DP implements Solver {

		@Override
		public int solve(int[] prices) {
			if (prices == null || prices.length == 0) return 0;
			int min = prices[0];
			int max = 0;
			for (int i = 1; i < prices.length; i++) {
				int diff = prices[i] - min;
				if (diff > max) {
					max = diff;
				}
				if (prices[i] < min) {
					min = prices[i];
				}
			}
			return max;
		}
		
	}
	public interface Solver {
		int solve(int[] prices);
	}
}
