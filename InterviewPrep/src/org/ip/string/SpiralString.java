package org.ip.string;

public class SpiralString {
	public static void main(String[] s) {
		/*X Y A

		M B C

		P Q R

		Output: XYACRQPMB*/
		System.out.println(solve(new char[][]{{'X','Y','A'},{'M','B','C'},{'P','Q','R'}}));
		/*X Y A

		M B C

		P Q R

		Z W D
		Output: XYACRDWZPMBQ*/
		System.out.println(solve(new char[][]{{'X','Y','A'},{'M','B','C'},{'P','Q','R'}, {'Z','W','D'}}));
	}
	public static String solve(char[][] matrix) {
		StringBuilder sb = new StringBuilder();
		for (int h = matrix.length, w = matrix[0].length, k = 0; w > 0; w-=2, h-=2, k++) {
			if (h > 1) {
				for (int i = 0; i < w-1; i++) {
					sb.append(matrix[k][k+i]);
				}
			}
			if (w > 1) {
				for (int i = 0; i < h-1; i++) {
					sb.append(matrix[k+i][w-k-1]);
				}
			}
			for (int i = w-1; i > 0; i--) {
				sb.append(matrix[h-k-1][k+i]);
			}
			for (int i = h-1; i > 0; i--) {
				sb.append(matrix[k+i][k]);
			}
			if (w == 1 && h == 1) {
				sb.append(matrix[k][k]);
			}
		}
		return sb.toString();
	}
}
