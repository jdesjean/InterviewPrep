package org.ip.search;

// EPI 2018: 11.6
public class Sorted2D {
	public static void main(String[] s) {
		int[][] m = new int[][] {
			{-1,2,4,4,6},
			{1,5,5,9,21},
			{3,6,6,9,22},
			{3,5,8,10,24},
			{3,6,8,10,24},
			{6,8,9,12,25},
			{8,10,12,13,40}
			};
		System.out.println(new Sorted2D().find(m, 7));
		System.out.println(new Sorted2D().find(m, 8));
	}
	public int find(int[][] m, int value) {
		int result = -1;
		int h = m.length - 1;
		int w = m[h].length - 1;
		if (value < m[0][0] || value > m[h][w]) return result;
		for (int c = w, l = 0; c >= 0 && l <= h;) {
			if (value == m[l][c]) {
				return l * (w + 1) + c;
			} else if (value < m[l][c]) {
				c--;
			} else {
				l++;
			}
		}
		return -1;
	}
}
