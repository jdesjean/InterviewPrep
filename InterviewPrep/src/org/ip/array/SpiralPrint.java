package org.ip.array;

public class SpiralPrint {
	public static void main(String[] s) {
		/*X Y A

		M B C

		P Q R

		Output: XYACRQPMB*/
		System.out.println(solve(new char[][]{{'X','Y','A'},{'M','B','C'},{'P','Q','R'}}) + "=XYACRQPMB");
		/*X Y A

		M B C

		P Q R

		Z W D
		Output: XYACRDWZPMBQ*/
		System.out.println(solve(new char[][]{{'X','Y','A'},{'M','B','C'},{'P','Q','R'}, {'Z','W','D'}}) + "=XYACRDWZPMBQ");
		
		/*X Y A B

		M B C B

		P Q R B

		Z W D B
		Output: XYABBBBDWZPMBCRQ*/
		System.out.println(solve(new char[][]{{'X','Y','A','B'},{'M','B','C','B'},{'P','Q','R','B'}, {'Z','W','D','B'}}) + "=XYABBBBDWZPMBCRQ");
	}
	public static String solve(char[][] matrix) {
		StringBuilder sb = new StringBuilder();
		for (int h = matrix.length-1, w = matrix[0].length-1, k = 0; w > 0; w-=1, h-=1, k++) {
			for (int i = k; i < w; i++) {
				sb.append(matrix[k][i]);
			}
			for (int i = k; i < h; i++) {
				sb.append(matrix[i][w]);
			}
			for (int i = w; i > k; i--) {
				sb.append(matrix[h][i]);
			}
			for (int i = h; i > k; i--) {
				sb.append(matrix[i][k]);
			}
			if (k == w && k == h) {
				sb.append(matrix[k][k]);
			}
		}
		return sb.toString();
	}
}
