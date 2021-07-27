package org.ip.matrix;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/discuss/interview-question/549128/">OA 2020</a>
 * <a href="https://leetcode.com/problems/number-of-ways-of-cutting-a-pizza/">LC: 1444</a>
 */
public class CutTheCake {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				CutTheCake::tc1,
				CutTheCake::tc2,
				CutTheCake::tc3,
				CutTheCake::tc4,
				CutTheCake::tc5,
				CutTheCake::tc6,
				CutTheCake::tc7,
				CutTheCake::tc8,
				CutTheCake::tc9);
		Solver[] solvers = new Solver[] {new DP(), new Recursive()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		System.out.println(solver.solve(new Strawberry[][] {
			{V, V}, 
			{V, V}}, 3));
	}
	public static void tc2(Solver solver) {
		System.out.println(solver.solve(new Strawberry[][] {
			{V, V, V}, 
			{V, D, D},
			{V, D, D}}, 3));
	}
	public static void tc3(Solver solver) {
		System.out.println(solver.solve(new Strawberry[][] {
			{D, D, D, V}, 
			{D, D, D, V},
			{D, D, D, V},
			{D, D, D, V}}, 4));
	}
	public static void tc4(Solver solver) {
		System.out.println(solver.solve(new Strawberry[][] {
			{D, D, D, V}, 
			{D, D, D, V},
			{D, D, D, V},
			{D, D, D, V}}, 2));
	}
	public static void tc5(Solver solver) {
		System.out.println(solver.solve(new Strawberry[][] {
			{D, D, D, V}, 
			{D, D, D, D},
			{D, D, D, D},
			{D, D, D, V}}, 2));
	}
	public static void tc6(Solver solver) {
		System.out.println(solver.solve(new Strawberry[][] {
			{V, D, D, D}, 
			{D, V, D, D},
			{D, D, V, D},
			{D, D, D, V}}, 4));
	}
	public static void tc7(Solver solver) {
		System.out.println(solver.solve(new Strawberry[][] {
			{D, D, D, V}, 
			{D, D, V, D},
			{D, V, D, D},
			{V, D, D, D}}, 4));
	}
	public static void tc8(Solver solver) {
		System.out.println(solver.solve(new Strawberry[][] {
			{V, V, V, V, V, V, V, V, V, V}}, 5));
	}
	public static void tc9(Solver solver) {
		System.out.println(solver.solve(new Strawberry[][] {
			{V, V, V, V, V, V, V, V, V, V},
			{V, V, V, V, V, V, V, V, V, V},
			{V, V, V, V, V, V, V, V, V, V},
			{V, V, V, V, V, V, V, V, V, V},
			{V, V, V, V, V, V, V, V, V, V},
			{V, V, V, V, V, V, V, V, V, V},
			{V, V, V, V, V, V, V, V, V, V},
			{V, V, V, V, V, V, V, V, V, V},
			{V, V, V, V, V, V, V, V, V, V},
			{V, V, V, V, V, V, V, V, V, V}}, 5));
	}
	public static class DP implements Solver {

		@Override
		public int solve(Strawberry[][] cake, int k) {
			int[][] prev = new int[cake.length][cake[0].length];
			for (int l = 0; l < cake.length; l++) {
				for (int c = 0; c < cake[0].length; c++) {
					if (canCut(cake, l, c, cake.length, cake[0].length)) {
						prev[l][c] = 1;
					}
				}
			}
			for (int i = 2; i <= k; i++) {
				int[][] next = new int[cake.length][cake[0].length];
				for (int l = 0; l < cake.length; l++) {
					for (int c = 0; c < cake[0].length; c++) {
						int sum = 0;
						for (int ll = l + 1; ll < cake.length; ll++) { // horizontal
							if (canCut(cake, l, c, ll, cake[0].length)) {
								sum = add(sum, prev[ll][c]);
							}
						}
						for (int cc = c + 1; cc < cake[0].length; cc++) { //vertical
							if (canCut(cake, l, c, cake.length, cc)) {
								sum = add(sum, prev[l][cc]);
							}
						}
						next[l][c] = sum;
					}
				}
				prev = next;
			}
			return prev[0][0];
		}
		private int add(int a, int b) {
			return (a + b) % MOD;
		}
	}
	private final static int MOD = (int)Math.pow(10, 9) + 7; 
	public static class Recursive implements Solver {

		@Override
		public int solve(Strawberry[][] cake, int k) {
			return solve(cake, k, 0, 0);
		}
		public int solve(Strawberry[][] cake, int k, int l, int c) {
			if (k == 1) {
				if (canCut(cake, l, c, cake.length, cake[0].length)) {
					return 1;
				} else {
					return 0;
				}
			}
			int sum = 0;
			for (int ll = l + 1; ll < cake.length; ll++) { // horizontal
				if (canCut(cake, l, c, ll, cake[0].length)) {
					sum += solve(cake, k - 1, ll, c);
				}
			}
			for (int cc = c + 1; cc < cake[0].length; cc++) { // vertical
				if (canCut(cake, l, c, cake.length, cc)) {
					sum += solve(cake, k - 1, l, cc);
				}
			}
			return sum;
		}
	}
	private static boolean canCut(Strawberry[][] cake, int l, int c, int ll, int cc) {
		for (int lll = l; lll < ll; lll++) {
			for (int ccc = c; ccc < cc; ccc++) {
				if (cake[lll][ccc] == V) return true;
			}
		}
		return false;
	}
	public final static Strawberry V = Strawberry.V;
	public final static Strawberry D = Strawberry.D;
	public enum Strawberry {
		V, D
	}
	public interface Solver {
		public int solve(Strawberry[][] cake, int k);
	}
}
