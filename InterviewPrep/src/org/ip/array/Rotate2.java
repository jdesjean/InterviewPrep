package org.ip.array;

// EPI 2018: 5.19
public class Rotate2 {
	public static void main(String[] s) {
		for (int size = 4; size <= 5; size++) {
			int[][] array = new int[size][size];
			for (int i = 0; i < size * size; i++) {
				int l = i / size;
				int c = i % size;
				array[l][c] = i + 1;
			}
			Rotate2 rotate = new Rotate2();
			rotate.rotate(array);
			Utils.println(array);
			/*
			 * 01,02,03,04,05
			 * 06,07,08,09,10
			 * 11,12,13,14,15
			 * 16,17,18,19,20
			 * 21,22,23,24,25
			 */
			
			/*
			 * 21,16,11,06,01
			 * 22,17,12,07,02
			 * 23,18,13,08,03
			 * 24,19,14,09,04
			 * 25,20,15,10,05
			 * 
			 */
		}
	}
	public void rotate(int[][] m) {
		//assume m is square; otherwise requires resizing m
		int it = m.length / 2;
		for (int i = 0; i < it; i++) {
			int n = m.length - i - 1;
			for (int j = i; j < n; j++) {
				int tmp = m[i][j + i];
				m[i][j + i] = m[n - j][i];
				m[n - j][i] = m[n][n - j];
				m[n][n - j] = m[j + i][n];
				m[j + i][n] = tmp;
			}
		}
	}
}
