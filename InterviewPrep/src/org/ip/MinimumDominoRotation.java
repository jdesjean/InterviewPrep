package org.ip;

public class MinimumDominoRotation {
	public static void main(String[] s) {
		System.out.println(new MinimumDominoRotation().solve(new int[] {2,1,2,4,2,2}, new int[] {5,2,6,2,3,2}));
		System.out.println(new MinimumDominoRotation().solve(new int[] {5,1,2,4,2,2}, new int[] {2,2,6,2,3,2}));
	}
	public int solve(int[] a, int[] b) {
		if (a == null || b == null || a.length <= 1 || a.length != b.length) return 0;
		
		int count1 = _solve(a, b);
		int count2 = _solve(b, a);
		
		int min = Math.min(count1, count2);
		return min != Integer.MAX_VALUE ? min : -1;
	}
	public int _solve(int[] a, int[] b) {
		int count = _solve(a, b, a[0]);
		if (count == Integer.MAX_VALUE) {
			count = _solve(a, b, b[0]);
			if (count != Integer.MAX_VALUE) {
				count++; // rotate 0
			}
		}
		return count;
	}
	public int _solve(int[] a, int[] b, int x) {
		int count = 0;
		for (int i = 1; i < a.length; i++) {
			if (a[i] != x) {
				if (b[i] == x) {
					count++;
				} else {
					count = Integer.MAX_VALUE;
					break;
				}
			}
		}
		return count;
	}
}
