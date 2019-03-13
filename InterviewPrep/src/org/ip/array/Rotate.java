package org.ip.array;

// EPI: 6.18
public class Rotate {
	public static void main(String[] s) {
		for (int size = 4; size <= 5; size++) {
			int[][] array = new int[size][size];
			for (int i = 0; i < size * size; i++) {
				int l = i / size;
				int c = i % size;
				array[l][c] = i + 1;
			}
			Rotate rotate = new Rotate(array);
			rotate.rotate();
			Utils.println(array);
			/*
			 * 01,02,03,04,05
			 * 06,07,08,09,10
			 * 11,12,13,14,15
			 * 16,17,18,19,20
			 * 21,22,23,24,25
			 */
		}
	}
	private int[][] array;
	public Rotate(int[][] array) {
		this.array=array;
	}
	public void rotate() {
		for (int iteration = 0; iteration < array.length / 2; iteration++) {
			int end = array.length - iteration - 1;
			for (int i = iteration; i < end; i++) {
				swap(iteration, iteration, end, end, i);
			}
		}
	}
	public void swap(int sl, int sc, int el, int ec, int i) {
		int tmp = array[sl][sc + i];
		array[sl][sc + i] = array[el - i][sc];
		array[el - i][sc] = array[el][ec - i];
		array[el][ec - i] = array[sl + i][ec];
		array[sl + i][ec] = tmp;
	}
}
