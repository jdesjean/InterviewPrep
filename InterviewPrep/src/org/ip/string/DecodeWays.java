package org.ip.string;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.ip.string.IntegerToEnglish.Iterative;
import org.ip.string.IntegerToEnglish.Solver;

/**
 * <a href="https://leetcode.com/problems/decode-ways/">LC: 91</a>
 */
public class DecodeWays {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				DecodeWays::tc7,
				DecodeWays::tc1,
				DecodeWays::tc2,
				DecodeWays::tc3,
				DecodeWays::tc4,
				DecodeWays::tc5,
				DecodeWays::tc6
				);
		Solver[] solvers = new Solver[] {new Recursive(), new DP()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		System.out.println(solver.solve("12"));
	}
	public static void tc2(Solver solver) {
		System.out.println(solver.solve("226"));
	}
	public static void tc3(Solver solver) {
		System.out.println(solver.solve("0"));
	}
	public static void tc4(Solver solver) {
		System.out.println(solver.solve("1"));
	}
	public static void tc5(Solver solver) {
		System.out.println(solver.solve("2101"));
	}
	public static void tc6(Solver solver) {
		System.out.println(solver.solve("1201234"));
	}
	public static void tc7(Solver solver) {
		System.out.println(solver.solve("1123"));
	}
	
	public static class DP implements Solver {

		@Override
		public int solve(String num) {
			int[] cache = new int[num.length() + 1];
			cache[num.length()] = 1;
			for (int i = num.length() - 1; i >= 0; i--) {
				int digit = Character.digit(num.charAt(i), 10);
				if (digit == 0) continue;
				cache[i] = cache[i + 1];
				if (i < num.length() - 1) {
					int next = Character.digit(num.charAt(i + 1), 10);
					int v = digit * 10 + next;
					if (v <= 26 && v > 0) {
						cache[i] += cache[i + 2];
					}
				}
			}
			return cache[0];
		}
		
	}
	public static class Recursive implements Solver {

		@Override
		public int solve(String num) {
			if (num == null) return 0;
			AtomicInteger ref = new AtomicInteger();
			solve(ref, num, 0);
			return ref.get();
		}
		void solve(AtomicInteger ref, String num, int index) {
			if (index == num.length()) {
				ref.incrementAndGet();
				return;
			}
			int digit = Character.digit(num.charAt(index), 10);
			if (digit == 0) return;
			solve(ref, num, index + 1);
			if (index < num.length() - 1) {
				int next = Character.digit(num.charAt(index + 1), 10);
				int v = digit * 10 + next;
				if (v <= 26 && v > 0) {
					solve(ref, num, index + 2);
				}
			}
		}
		
	}
	public interface Solver {
		public int solve(String num);
	}
}
