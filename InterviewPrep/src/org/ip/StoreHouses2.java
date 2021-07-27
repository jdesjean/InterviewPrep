package org.ip;

import java.util.Arrays;

import org.ip.array.Utils;

/**
 * <a href="https://leetcode.com/discuss/interview-question/350248/Google-or-Summer-Intern-OA-2019-or-Stores-and-Houses">OA 2019</a> 
 */
public class StoreHouses2 {
	public static void main(String[] s) {
		tc1(new StoreHouses2());
		tc2(new StoreHouses2());
		tc3(new StoreHouses2());
	}
	public static void tc1(StoreHouses2 solver) {
		int[] houses = new int[]{5, 10, 17};
		int[] stores = new int[] {1, 5, 20, 11, 16};
		//[5, 11, 16]
		Utils.println(solver.solve(houses, stores));
	}
	public static void tc2(StoreHouses2 solver) {
		int[] houses = new int[] {2, 4, 2};
		int[] stores = new int[] {5, 1, 2, 3};
		// [2, 3, 2]
		Utils.println(solver.solve(houses, stores));
	}
	public static void tc3(StoreHouses2 solver) {
		int[] houses = new int[] {4, 8, 1, 1};
		int[] stores = new int[] {5, 3, 1, 2, 6};
		// [3, 6, 1, 1]
		Utils.println(solver.solve(houses, stores));
	}
	public int[] solve(int[] houses, int[] stores) {
		int max = Math.max(Utils.max(houses), Utils.max(stores)) + 1;
		int[] buffer = new int[max];
		Arrays.fill(buffer, Integer.MAX_VALUE);
  		for (int i = 0; i < stores.length; i++) {
			buffer[stores[i]] = 0;
		}
		for (int i = 0, prev = -1; i < max; i++) {
			if (buffer[i] == 0) {
				prev = i;
			} else if (prev != -1) {
				buffer[i] = prev - i;
			}
		}
		for (int i = max - 1, prev = -1; i >= 0; i--) {
			if (buffer[i] == 0) {
				prev = i;
			} else if (prev != -1) { 
				if (prev - i < Math.abs(buffer[i])) {
					buffer[i] = prev - i;
				}
			}
		}
		for (int i = 0; i < houses.length; i++) {
			houses[i] = houses[i] + buffer[houses[i]];
		}
		return houses;
	}
	
}
