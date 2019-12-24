package org.ip.dp;

import org.ip.recursion.Knapsack.Item;
import static org.ip.recursion.Knapsack.item;

// EPI 2018: 16.6
public class Knapsack2 {
	public static void main(String[] s) {
		Item[] items = new Item[] {item(65,20), item(35, 8), item(245, 60), item(195, 55), item(65, 40), item(150, 70), item(275, 85), item(155, 25), item(120, 30), item(320, 65), item(75,75), item(40,10), item(200,95), item(100,50), item(220,40),item(99,10)};
		System.out.println(new Knapsack2().solve(items, 130));
	}
	public int solve(Item[] items, int maxTotalWeight) {
		return solve(items, maxTotalWeight, 0, 0);
	}
	private int solve(Item[] items, int maxTotalWeight, int index, int value) {
		if (index == items.length) {
			return value;
		}
		
		int max = solve(items, maxTotalWeight, index + 1, value);
		if (maxTotalWeight >= items[index].weight) {
			max = Math.max(max, solve(items, maxTotalWeight - items[index].weight, index + 1, value + items[index].value));
		}
		return max;
	}
}
