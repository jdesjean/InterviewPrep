package org.ip.recursion;

// EPI: 17.6 
public class Knapsack {
	public static void main(String[] s) {
		Item[] items = new Item[] {item(65,20), item(35, 8), item(245, 60), item(195, 55), item(65, 40), item(150, 70), item(275, 85), item(155, 25), item(120, 30), item(320, 65), item(75,75), item(40,10), item(200,95), item(100,50), item(220,40),item(99,10)};
		System.out.println(new RecursiveSolve().solve(items, 130));
		System.out.println(new DPSolve().solve(items, 130));
	}
	public static class DPSolve implements Solve {

		@Override
		public int solve(Item[] items, int weight) {
			int sumValue = 0;
			for (int i = 0; i < items.length; i++) {
				sumValue += items[i].value;
			}
			if (sumValue < weight) {
				int[] cache = new int[sumValue + 1];
				return solveValue(items, weight, cache);
			} else {
				int[] cache = new int[weight + 1];
				return solveWeight(items, weight, cache);
			}
		}
		public int solveValue(Item[] items, int weight, int[] cache) {
			int max = 0;
			for (int i = 0; i < items.length; i++) {
				for (int j = cache.length-1; j>= items[i].value; j--) {
					int value = j - items[i].value;
					if (cache[value] == 0 && value != 0) continue;
					int w = cache[value] + items[i].weight;
					if (w <= weight) {
						if (cache[j] == 0) {
							cache[j] = w;
						} else {
							cache[j] = Math.min(cache[j], w);
						}
						max = Math.max(j, max);
					}
				}
			}
			return max;
		}
		public int solveWeight(Item[] items, int weight, int[] cache) {
			int max = 0;
			for (int i = 0; i < items.length; i++) {
				for (int j = cache.length-1; j>= items[i].weight; j--) {
					int ww = j - items[i].weight;
					if (cache[ww] == 0 && ww != 0) continue;
					int value = cache[ww] + items[i].value;
					cache[j] = Math.max(cache[j], value);
					max = Math.max(value, max);
				}
			}
			return max;
		}
	}
	public static class RecursiveSolve implements Solve {

		@Override
		public int solve(Item[] items, int weight) {
			int max = 0;
			Item[] buffer = new Item[items.length];
			for (int i = 0; i < items.length; i++) {
				max = Math.max(max, solve(items, buffer, 0, i, weight));
			}
			return max;
		}
		public int solve(Item[] items, Item[] buffer, int w, int r, int weight) {
			if (r == items.length) {
				int sum = 0;
				for (int i = 0; i < w; i++) {
					sum += buffer[i].value;
				}
				return sum;
			}
			int max = solve(items, buffer, w, r + 1, weight);
			if (items[r].weight <= weight) {
				buffer[w] = items[r];
				max = Math.max(max, solve(items, buffer, w + 1, r + 1, weight - items[r].weight));
			}
			return max;
		}
	}
	public interface Solve {
		public int solve(Item[] items, int weight);
	}
	public static Item item(int value, int cost) {
		return new Item(value, cost);
	}
	public static class Item {
		public int value;
		public int weight;
		public Item(int value, int weight) { this.value=value; this.weight=weight; }
		@Override
		public String toString() { return value + ":" + weight; }
	}
}
