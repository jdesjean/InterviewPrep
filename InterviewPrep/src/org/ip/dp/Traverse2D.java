package org.ip.dp;

import java.util.Arrays;

// EPI 2018: 16.3
public class Traverse2D {
	public static void main(String[] s) {
		System.out.println(new Traverse2D().count(5, 5));
	}
	public int count(int w, int h) {
		int[] buffer = new int[w];
		Arrays.fill(buffer, 1);
		for (int l = 1; l < h; l++) {
			int prev = 0;
			for (int c = 0; c < w; c++) {
				buffer[c] = prev + buffer[c];
				prev = buffer[c];
			}
		}
		return buffer[w - 1];
	}
}
