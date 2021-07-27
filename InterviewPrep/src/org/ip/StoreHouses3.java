package org.ip;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.IntStream;

import org.ip.array.Utils;

/**
 * <a href="https://leetcode.com/discuss/interview-question/350248/Google-or-Summer-Intern-OA-2019-or-Stores-and-Houses">OA 2019</a> 
 */
public class StoreHouses3 {
	public static void main(String[] s) {
		tc1(new StoreHouses3());
		tc2(new StoreHouses3());
		tc3(new StoreHouses3());
	}
	public static void tc1(StoreHouses3 solver) {
		int[] houses = new int[]{5, 10, 17};
		int[] stores = new int[] {1, 5, 20, 11, 16};
		//[5, 11, 16]
		Utils.println(solver.solve(houses, stores));
	}
	public static void tc2(StoreHouses3 solver) {
		int[] houses = new int[] {2, 4, 2};
		int[] stores = new int[] {5, 1, 2, 3};
		// [2, 3, 2]
		Utils.println(solver.solve(houses, stores));
	}
	public static void tc3(StoreHouses3 solver) {
		int[] houses = new int[] {4, 8, 1, 1};
		int[] stores = new int[] {5, 3, 1, 2, 6};
		// [3, 6, 1, 1]
		Utils.println(solver.solve(houses, stores));
	}
	public int[] solve(int[] houses, int[] stores) {
		TreeSet<Integer> set = new TreeSet<>();
		for (int i = 0; i < stores.length; i++) {
			set.add(stores[i]);
		}
		
		return IntStream.of(houses)
		.map((x) -> {
			NavigableSet<Integer> smaller = set.headSet(x, true);
			NavigableSet<Integer> bigger = set.tailSet(x, false);
			Iterator<Integer> it = smaller.descendingIterator();
			Iterator<Integer> it2 = bigger.iterator();
			int diffA = Integer.MAX_VALUE;
			int diffB = Integer.MAX_VALUE;
			int va = set.ceiling(x);
			int vb = set.floor(x);
			if (it.hasNext()) {
				va = it.next();
				diffA = Math.abs(x - va);
			}
			if (it2.hasNext()) {
				vb = it2.next();
				diffB = Math.abs(x - vb);
			}
			if (diffA <= diffB) {
				return va;
			} else {
				return vb;
			}
		})
		.toArray();
	}
	
}
