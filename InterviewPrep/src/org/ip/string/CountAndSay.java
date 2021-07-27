package org.ip.string;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/problems/count-and-say/">LC: 38</a>
 */
public class CountAndSay {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				CountAndSay::tc1,
				CountAndSay::tc2
				);
		Solver[] solvers = new Solver[] {new Iterative()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		System.out.println(solver.solve(1));
	}
	public static void tc2(Solver solver) {
		System.out.println(solver.solve(4));
	}
	public static class Iterative implements Solver {

		@Override
		public String solve(int n) {
			if (n < 1) return "";
			if (n == 1) return "1";
			StringBuilder prev;
			StringBuilder next = new StringBuilder();
			next.append("1");
			for (int i = 2; i <= n; i++) {
				int count = 0;
				prev = next;
				next = new StringBuilder(n);
				for (int j = 0; j < prev.length(); j++) {
					if (j == 0 || prev.charAt(j) == prev.charAt(j - 1)) {
						count++;
					} else {
						next.append(count);
						next.append(prev.charAt(j - 1));
						count = 1;
					}
				}
				if (count > 0) {
					next.append(count);
					next.append(prev.charAt(prev.length() - 1));
				}
			}
			return next.toString();
		}
		
	}
	public interface Solver {
		public String solve(int n);
	}
}
