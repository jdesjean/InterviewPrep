package org.ip.dp;

import org.ip.recursion.Knapsack.Item;
import static org.ip.recursion.Knapsack.item;

// EPI 2018: 16.6
public class Knapsack3 {
	public static void main(String[] s) {
		Item[] items = new Item[] {item(65,20), item(35, 8), item(245, 60), item(195, 55), item(65, 40), item(150, 70), item(275, 85), item(155, 25), item(120, 30), item(320, 65), item(75,75), item(40,10), item(200,95), item(100,50), item(220,40),item(99,10)};
		System.out.println(new Knapsack3().solve(items, 130));
	}
	public int solve(Item[] items, int maxTotalWeight) {
		int[] cache = new int[maxTotalWeight + 1];
		for (int i = 0; i < items.length; i++) {
			int weight = items[i].weight;
			int value = items[i].value;
			for (int j = maxTotalWeight; j >= weight; j--) {
				int prev = j - weight;
				cache[j] = Math.max(cache[j], cache[prev] + value);
			}
		}
		return cache[maxTotalWeight];
	}
}
