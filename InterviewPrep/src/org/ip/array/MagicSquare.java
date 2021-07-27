package org.ip.array;

import java.util.BitSet;

/**
 * OA 2019
 * <a href="https://leetcode.com/discuss/interview-question/341295/Google-or-Online-Assessment-2019-or-Fill-Matrix">OA 2019</a>
 * <br> Related: <a href="https://en.wikipedia.org/wiki/Magic_square">Wiki</a>
 */
public class MagicSquare {
	public static void main(String[] s) {
		MagicSquare square = new MagicSquare();
		Utils.println(square.solve(3));
	}
	public int[][] solve(int n) {
		int nn = n * n;
		int sum = nn * (nn + 1) / 2 / n;
		int[][] result = new int[n][n];
		BitSet set = new BitSet();
		boolean solved = solve(result, 0, 0, set, sum);
		System.out.println(solved);
		return result;
	}
	public boolean solve(int[][] result, int l, int c, BitSet set, int sum) {
		if (c >= result.length) {
			if (!isMagicLine(result, l, sum)) {
				return false;
			}
			l++;
			c = 0;
		}
		if (l == result.length - 1 && c > 0) {
			if (!isMagicColumn(result, c - 1, sum)) {
				return false;
			}
		}
		if (l >= result.length) {
			return isMagicColumn(result, result.length - 1, sum) 
					&& isMagicDiagonalTopLeft(result, sum)
					&& isMagicDiagonalTopRight(result, sum);
		}
		for (int i = 0; i < result.length * result.length; i++) {
			if (!set.get(i)) {
				result[l][c] = i + 1;
				set.set(i);
				if (solve(result, l, c + 1, set, sum)) {
					return true;
				}
				set.clear(i);
			}
		}
		return false;
	}
	public boolean isMagicLine(int[][] result, int l, int sum) {
		return sum(result, l, 0, 0, 1) == sum;
	}
	public boolean isMagicColumn(int[][] result, int c, int sum) {
		return sum(result, 0, 1, c, 0) == sum;
	}
	public boolean isMagicDiagonalTopLeft(int[][] result, int sum) {
		return sum(result, 0, 1, 0, 1) == sum;
	}
	public boolean isMagicDiagonalTopRight(int[][] result, int sum) {
		return sum(result, 0, 1, result.length - 1, -1) == sum;
	}
	public int sum(int[][] result, int l1, int dl, int c1, int dc) {
		int sum = 0;
		for (int c = c1, l = l1; c < result.length && l < result.length && c >= 0 && l >= 0; c+=dc, l+=dl) {
			sum += result[l][c];
		}
		return sum;
	}
}
