package org.ip.matrix;

import java.util.BitSet;
import java.util.function.Predicate;

import org.ip.Test;

/**
 * <a href='https://leetcode.com/problems/valid-sudoku/'>LC: 36</a>
 */
public class ValidSudoku {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {true, new char[][] {
			{'5','3','.','.','7','.','.','.','.'},
			{'6','.','.','1','9','5','.','.','.'},
			{'.','9','8','.','.','.','.','6','.'},
			{'8','.','.','.','6','.','.','.','3'},
			{'4','.','.','8','.','3','.','.','1'},
			{'7','.','.','.','2','.','.','.','6'},
			{'.','6','.','.','.','.','2','8','.'},
			{'.','.','.','4','1','9','.','.','5'},
			{'.','.','.','.','8','.','.','7','9'}
		}};
		Object[] tc2 = new Object[] {false, new char[][] {
			{'8','3','.','.','7','.','.','.','.'},
			{'6','.','.','1','9','5','.','.','.'},
			{'.','9','8','.','.','.','.','6','.'},
			{'8','.','.','.','6','.','.','.','3'},
			{'4','.','.','8','.','3','.','.','1'},
			{'7','.','.','.','2','.','.','.','6'},
			{'.','6','.','.','.','.','2','8','.'},
			{'.','.','.','4','1','9','.','.','5'},
			{'.','.','.','.','8','.','.','7','9'}
		}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		/*
		 * 3 checks (rows, cols, 3x3), 9 units [0,8], 10 numbers [0,9]
		 */
		private final static int NUMBERS = 10;
		private final static int UNITS = 9;
		private final static int UNITS_NUMBERS = UNITS * NUMBERS;
		private final static int CHECKS = 3;
		private final static int CHECKS_UNITS = CHECKS * UNITS;
		
		@Override
		public boolean test(char[][] t) {
			BitSet sets = new BitSet();
			for (int l = 0; l < 9; l++) {
				for (int c = 0; c < 9; c++) {
					if (t[l][c] == '.') continue;
					int v = Character.digit(t[l][c], 10) * CHECKS_UNITS;
					for (int[] pair : new int[][] {{0, l},{1, c},{2, l / 3 * 3 + c / 3}}) {
						/*
						 * Simpler
						 * int vv = v + pair[1] * NUMBERS + pair[0] * UNITS_NUMBERS;
						 * However below:
						 * 1) better data locality if 3 checks are colocated in memory
						 * 2) 1 less multiplication since v is now static
						 */
						int vv = pair[0] + pair[1] * CHECKS + v;
						if (sets.get(vv)) {
							return false;
						}
						sets.set(vv);
					}
				}
			}
			return true;
		}
	}
	static class Solver implements Problem {

		@Override
		public boolean test(char[][] t) {
			BitSet[][] sets = new BitSet[3][9];
			for (int l = 0; l < 3; l++) {
				for (int c = 0; c < 9; c++) {
					sets[l][c] = new BitSet();
				}
			}
			for (int l = 0; l < 9; l++) {
				for (int c = 0; c < 9; c++) {
					if (t[l][c] == '.') continue;
					int v = Character.digit(t[l][c], 10);
					for (int[] pair : new int[][] {{0, l},{1, c},{2, l / 3 * 3 + c / 3}}) {
						if (sets[pair[0]][pair[1]].get(v)) return false;
						sets[pair[0]][pair[1]].set(v);
					}
				}
			}
			return true;
		}
	}
	interface Problem extends Predicate<char[][]> {
		
	}
}
