package org.ip.honor;

// EPI 2018: 24.5
public class LCIS {
	public static void main(String[] s) {
		int[] a = new int[] {2,11,3,5,13,7,19,17,23};
		System.out.println(new LCIS().solve(a));
	}
	public Pair solve(int[] a) {
		int maxI = 0;
		int maxL = 1;
		for (int i = 1, length = 1, index = 0; i < a.length - (maxL - length); i++) {
			if (a[i] > a[i-1]) {
				length++;
				if (length > maxL) {
					maxL = length;
					maxI = index;
				}
			} else { // can improve heuristically on this, by skipping forward maxL. If elements ordered at that index backtrack else skip again
				index = i;
				length = 1;
			}
		}
		return new Pair(maxI, maxL);
	}
	public static class Pair {
		int index;
		int length;
		public Pair(int index, int length) {
			super();
			this.index = index;
			this.length = length;
		}
		@Override
		public String toString() {
			return "Pair [index=" + index + ", length=" + length + "]";
		}
		
	}
}
