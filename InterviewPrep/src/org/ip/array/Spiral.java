package org.ip.array;

// EPI 2018: 5.18
public class Spiral {
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
	public static String solve(char[][] m) {
		StringBuilder sb = new StringBuilder();
		for (int l = 0, b = m.length - 1, r = m[0].length - 1; l <= r && l <= b; l++, b--, r--) {
			for (int i = l; i <= r; i++) {
				sb.append(m[l][i]);
			}
			for (int i = l + 1; i <= b; i++) {
				sb.append(m[i][r]);
			}
			for (int i = r - 1; i >= l; i--) {
				sb.append(m[b][i]);
			}
			for (int i = b - 1; i > l; i--) {
				sb.append(m[i][l]);
			}
		}
		return sb.toString();
	}
}
