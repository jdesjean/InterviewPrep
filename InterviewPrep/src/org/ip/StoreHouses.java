package org.ip;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.ip.array.Utils;

/**
 * <a href="https://leetcode.com/discuss/interview-question/350248/Google-or-Summer-Intern-OA-2019-or-Stores-and-Houses">OA 2019</a> 
 */
public class StoreHouses {
	public static void main(String[] s) {
		tc1(new StoreHouses());
		tc2(new StoreHouses());
		tc3(new StoreHouses());
	}
	public static void tc1(StoreHouses solver) {
		int[] houses = new int[]{5, 10, 17};
		int[] stores = new int[] {1, 5, 20, 11, 16};
		//[5, 11, 16]
		Utils.println(solver.solve(houses, stores));
	}
	public static void tc2(StoreHouses solver) {
		int[] houses = new int[] {2, 4, 2};
		int[] stores = new int[] {5, 1, 2, 3};
		// [2, 3, 2]
		Utils.println(solver.solve(houses, stores));
	}
	public static void tc3(StoreHouses solver) {
		int[] houses = new int[] {4, 8, 1, 1};
		int[] stores = new int[] {5, 3, 1, 2, 6};
		// [3, 6, 1, 1]
		Utils.println(solver.solve(houses, stores));
	}
	public int[] solve(int[] houses, int[] stores) {
		Arrays.sort(stores);
		return IntStream.of(houses)
		.map((x) -> {
			if (stores.length == 0) {
				return -1;
			}
			int idx = Arrays.binarySearch(stores, x);
			if (idx >= 0) {
				return stores[idx];
			}
			idx = (-1)*(idx+1);
			
			int idxA = idx - 1;
			int idxB = idx;
			int diffA = idxA >= 0 ? Math.abs(x - stores[idxA]) : Integer.MAX_VALUE;
			int diffB = idxB < stores.length ? Math.abs(x - stores[idxB]) : Integer.MAX_VALUE;
			if (diffA <= diffB) {
				return stores[idxA];
			} else {
				return stores[idxB];
			}
		})
		.toArray();
	}
	
}
