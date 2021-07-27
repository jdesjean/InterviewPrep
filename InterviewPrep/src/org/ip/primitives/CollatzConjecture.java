package org.ip.primitives;

import java.util.HashSet;
import java.util.Set;

// EPI 2018: 12.11
public class CollatzConjecture {
	public static void main(String[] s) {
		int max = 100;
		System.out.println(new CollatzConjecture().test(max));
	}
	public boolean test(int max) {
		Set<Integer> cache = new HashSet<Integer>();
		cache.add(1);
		for (int i = 3; i <= max; i+=2) {
			if (cache.contains(i)) continue;
			if (!_test(i, cache)) return false;
		}
		return true;
	}
	private boolean _test(int value, Set<Integer> cache) {
		Set<Integer> sequence = new HashSet<Integer>();
		while (true) {
			if (!sequence.add(value)) { // loop
				return false;
			}
			if (!cache.add(value)) {
				return true;
			}
			if (value % 2 == 1) {
				int next = value * 3 + 1;
				if (next < value) {
					throw new ArithmeticException("overflow");
				}
				value = next;
			} else {
				value /= 2;
			}
		}
	}
}
