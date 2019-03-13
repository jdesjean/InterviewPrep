package org.ip.array;

// EPI: 6.19
public class PascalTriangle {
	public static void main(String[] s) {
		PascalTriangle triangle = new PascalTriangle(9);
		triangle.println();
	}
	private int[] array;
	private int height;
	public PascalTriangle(int height) {
		this.height = height;
		this.array = new int[--height * 2 + 1];
	}
	public void println() {
		int prevTmp = 0;
		int nextTmp = 0;
		for (int i = 0; i < height; i++) {
			int size = i + 1;
			for (int j = 0; j < size; j++) {
				nextTmp = array[j];
				if (j == 0 || j == size - 1) array[j] = 1;
				else array[j] = array[j] + prevTmp;
				System.out.print("," + array[j]);
				prevTmp = nextTmp;
			}
			System.out.println("");
		}
	}
}
