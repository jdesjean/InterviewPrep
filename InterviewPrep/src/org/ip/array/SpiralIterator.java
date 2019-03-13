package org.ip.array;

// EPI: 6.17
public class SpiralIterator {
	public static void main(String[] s) {
		for (int size = 3; size <= 5; size++) {
			int[][] array = new int[size][size];
			for (int i = 0; i < size * size; i++) {
				int l = i / size;
				int c = i % size;
				array[l][c] = i + 1;
			}
			SpiralIterator iterator = new SpiralIterator(array);
			iterator.println();
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
	public SpiralIterator(int[][] array) {
		this.array = array;
	}
	public void println() {
		for (int iteration = 0; iteration < Math.ceil(array.length / 2.0); iteration++) {
			int end = array.length - 1 - iteration;
			if (iteration == end) {
				region(iteration, iteration, iteration, end);
			} else {
				region(iteration, iteration, iteration, end - 1);
				region(iteration, end, end - 1, end);
				region(end, end, end, iteration + 1);
				region(end, iteration, iteration + 1, iteration);
			}
		}
		System.out.println("");
	}
	public void region(int sl, int sc, int el, int ec) {
		if (sl > el || sc > ec) {
			for (int l = sl; l >= el; l--) {
				for (int c = sc; c >= ec; c--) { 
					System.out.print("," + array[l][c]);
				}
			}
		} else {
			for (int l = sl; l <= el; l++) {
				for (int c = sc; c <= ec; c++) { 
					System.out.print("," + array[l][c]);
				}
			}
		}
	}
}
