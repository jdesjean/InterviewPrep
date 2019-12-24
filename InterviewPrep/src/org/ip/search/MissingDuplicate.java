package org.ip.search;

// EPI 2018: 11.10
public class MissingDuplicate {
	public static void main(String[] s) {
		int[] array = new int[] {5,3,0,3,1,2};
		System.out.println(new MissingDuplicate().find(array));
	}
	public Pair find(int[] array) {
		Pair pair = new Pair();
		int cxor = 0;
		int txor = 0;
		for (int i = 0; i < array.length; i++) {
			cxor ^= array[i];
			txor ^= i;
		}
		int xor = cxor ^ txor;
		int bitdiff = xor & (~(xor - 1));
		int sxor = 0;
		for (int i = 0; i < array.length; i++) {
			if ((array[i] & bitdiff) != 0) {
				sxor ^= array[i];
			}
		}
		boolean dup = false;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == sxor) {
				dup = true;
			}
		}
		int exor = sxor;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != sxor) {
				exor ^= array[i]; 
			}
		}
		if (dup) {
			pair.dup = sxor;
			pair.missing = exor ^ txor;
		} else {
			pair.missing = sxor;
			pair.dup = exor ^ txor;
		}
		return pair;
	}
	public static class Pair {
		int dup;
		int missing;
		public String toString() {
			return "duplicate:" + dup + ", missing: "+missing;
		}
	}
}
