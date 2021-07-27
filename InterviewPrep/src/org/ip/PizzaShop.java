package org.ip;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.ip.array.Utils;


/**
 * <a href="https://leetcode.com/discuss/interview-question/356935/">OA 2019</a>
 */
public class PizzaShop {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				PizzaShop::tc1,
				PizzaShop::tc2,
				PizzaShop::tc3,
				PizzaShop::tc4);
		Solver[] solvers = new Solver[] {new Binary(), new DP(), new Recursive()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		System.out.println(solver.solve(new int[] {800, 850, 900}, new int[] {100, 150}, 1000, 2));
	}
	public static void tc2(Solver solver) {
		System.out.println(solver.solve(new int[] {850, 900}, new int[] {200, 250}, 1000, 2));
	}
	public static void tc3(Solver solver) {
		System.out.println(solver.solve(new int[] {1100, 900}, new int[] {200}, 1000, 2));
	}
	public static void tc4(Solver solver) {
		System.out.println(solver.solve(new int[] {800, 800, 800, 800}, new int[] {100}, 1000, 2));
	}
	public static class Binary implements Solver {

		@Override
		public int solve(int[] pizzas, int[] toppings, int x, int t) {
			int combo = (int) Math.pow(2, toppings.length);
			int min = Integer.MAX_VALUE;
			for (int j = 0; j < combo; j++) {
				if (parity(j) > t) continue;
				int sum = sum(toppings, j);
				for (int i = 0; i < pizzas.length; i++) {
					int diff = x - pizzas[i] - sum;
					int compareDiffMin = Math.abs(diff) - Math.abs(min); 
					if (compareDiffMin < 0) {
						min = diff;
					} else if (compareDiffMin == 0) {
						min = Math.max(diff, min);
					}
				}
			}
			return x - min;
		}
		int parity(int combo) {
			int count = 0;
			while (combo > 0) {
				combo &= (combo - 1);
				count++;
			}
			return count;
		}
		int sum(int[] toppings, int combo) {
			int sum = 0;
			for (int i = 0; i < toppings.length; i++) {
				if ((combo & 1) == 1) {
					sum += toppings[i];
				}
				combo >>= 1;
			}
			return sum;
		}
		
	}
	public static class DP implements Solver {

		@Override
		public int solve(int[] pizzas, int[] toppings, int x, int t) {
			int max = Utils.max(pizzas) + Utils.sum(toppings);
			int minPizza = Utils.min(pizzas);
			int[] cache = new int[max - minPizza + 1];
			Arrays.fill(cache, Integer.MAX_VALUE);
			int min = max - x;
			for (int i = 0; i < pizzas.length; i++) {
				cache[pizzas[i] - minPizza] = 0;
				int diff = x - pizzas[i];
				int compareDiffMin = Math.abs(diff) - Math.abs(min); 
				if (compareDiffMin < 0) {
					min = diff;
				} else if (compareDiffMin == 0) {
					min = Math.max(diff, min);
				}
			}
			for (int i = 0; i < toppings.length; i++) {
				for (int j = cache.length; j >= toppings[i]; j--) {
					if (cache[j - toppings[i]] < t) {
						cache[j] = Math.min(cache[j], cache[j - toppings[i]] + 1);
						int diff = x - j - minPizza;
						int compareDiffMin = Math.abs(diff) - Math.abs(min);
						if (compareDiffMin < 0) {
							min = diff;
						} else if (compareDiffMin == 0) {
							min = diff > min ? diff : min;
						}
					}
				}
			}
			return x - min;
		}
		
	}
	public static class Recursive implements Solver {

		@Override
		public int solve(int[] pizzas, int[] toppings, int x, int t) {
			int min = x;
			for (int i = 0; i < pizzas.length; i++) {
				int solve = solve(toppings, x - pizzas[i], 0, t);
				int compareSolveMin = Math.abs(solve) - Math.abs(min); 
				if (compareSolveMin < 0) {
					min = solve;
				} else if (compareSolveMin == 0) {
					min = Math.max(solve, min); 
				}
			}
			return x - min;
		}
		public int solve(int[] toppings, int x, int j, int t) {
			if (j == toppings.length || t == 0) {
				return x;
			}
			int a = solve(toppings, x - toppings[j], j + 1, t - 1);
			int b = solve(toppings, x, j + 1, t);
			int compareAB = Math.abs(a) - Math.abs(b);
			if (compareAB < 0) {
				return a;
			} else if (compareAB == 0) {
				return Math.max(a,  b); 
			} else {
				return b;
			}
		}
		
	}
	public interface Solver {
		public int solve(int[] pizzas, int[] toppings, int x, int t);
	}
}
