package org.ip.array;

// EPI 2018: 5.20
public class PascalTriangle2 {
	public static void main(String[] s) {
		int h = 5;
		int[][] m = new int[h][h];
		new PascalTriangle2().fill(m);
		Utils.println(m);
	}
	public void fill(int[][] m) {
		m[0][0] = 1;
		for (int l = 1; l < m.length; l++) {
			for (int c = 0; c < m.length; c++) {
				int prev = c > 0 ? m[l - 1][c - 1] : 0;
				m[l][c] = prev + m[l - 1][c]; 
			}
		}
	}
}
