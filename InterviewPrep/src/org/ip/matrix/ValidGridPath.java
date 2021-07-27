package org.ip.matrix;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/check-if-there-is-a-valid-path-in-a-grid/">LC: 1391</a>
 */
public class ValidGridPath {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {true, new int[][] {{2,4,3},{6,5,2}}};
		Object[] tc2 = new Object[] {false, new int[][] {{1,2,1},{1,2,1}}};
		Object[] tc3 = new Object[] {false, new int[][] {{1,1,2}}};
		Object[] tc4 = new Object[] {true, new int[][] {{1,1,1,1,1,1,3}}};
		Object[] tc5 = new Object[] {true, new int[][] {{2},{2},{2},{2},{2},{2},{6}}};
		Object[] tc6 = new Object[] {false, new int[][] {{4,3,3},{6,5,2}}};
		
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5, tc6};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2() };
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {
		private final static int[][] PIECE_DIR = new int[][] {{1,3},{0,2},{2,3},{1,2},{0,3},{0,1}};
		private final static int[][] DIR_XY = new int[][] {{-1,0},{0,1},{1,0},{0,-1}};
		@Override
		public boolean test(int[][] t) {
			return _test(t, new int[] {0,0}, PIECE_DIR[t[0][0] - 1][0], new HashSet<>())
					|| _test(t, new int[] {0,0}, PIECE_DIR[t[0][0] - 1][1], new HashSet<>());
		}
		boolean _test(int[][] t, int[] pos, int dir, Set<String> visited) {
			if (!visited.add(pos[0] + "," + pos[1])) return false;
			if (pos[0] == t.length - 1 && pos[1] == t[t.length-1].length - 1) return true;
			int[] nextPos = new int[] {pos[0] + DIR_XY[dir][0], pos[1] + DIR_XY[dir][1]};
			if (nextPos[0] < 0
					|| nextPos[1] < 0
					|| nextPos[0] >= t.length
					|| nextPos[1] >= t[nextPos[0]].length) {
				return false;
			}
			int nextPiece = t[nextPos[0]][nextPos[1]] - 1;
			int[] nextPos1 = new int[] {nextPos[0] + DIR_XY[PIECE_DIR[nextPiece][0]][0], nextPos[1] + DIR_XY[PIECE_DIR[nextPiece][0]][1]};
			int[] nextPos2 = new int[] {nextPos[0] + DIR_XY[PIECE_DIR[nextPiece][1]][0], nextPos[1] + DIR_XY[PIECE_DIR[nextPiece][1]][1]};
			if (equals(pos, nextPos1)) {
				return _test(t, nextPos, PIECE_DIR[nextPiece][1], visited);
			} else if (equals(pos, nextPos2)) {
				return _test(t, nextPos, PIECE_DIR[nextPiece][0], visited);
			} else {
				return false;
			}
		}
		boolean equals(int[] p1, int[] p2) {
			return p1[0] == p2[0] && p1[1] == p2[1];
		}
	}
	static class Solver implements Problem {
		private final static int[][] DIR_PIECE = new int[][] {{2,3,4},{1,3,5},{2,5,6},{1,4,6}};
		private final static int[][] DIR_NEXT = new int[][] {{0,3,1},{1,2,0},{2,3,1},{3,2,0}};
		private final static int[][] PIECE_DIR = new int[][] {{1,3},{0,2},{2,3},{1,2},{0,3},{0,1}};
		private final static int[][] DIR_XY = new int[][] {{-1,0},{0,1},{1,0},{0,-1}};
		@Override
		public boolean test(int[][] t) {
			return _test(t, new int[] {0,0}, PIECE_DIR[t[0][0] - 1][0], new HashSet<>())
					|| _test(t, new int[] {0,0}, PIECE_DIR[t[0][0] - 1][1], new HashSet<>());
		}
		boolean _test(int[][] t, int[] pos, int dir, Set<String> visited) {
			if (!visited.add(pos[0] + "," + pos[1])) return false;
			if (pos[0] == t.length - 1 && pos[1] == t[t.length-1].length - 1) return true;
			int[] nextPos = new int[] {pos[0] + DIR_XY[dir][0], pos[1] + DIR_XY[dir][1]};
			int idx = -1;
			return nextPos[0] >= 0
					&& nextPos[1] >= 0
					&& nextPos[0] < t.length
					&& nextPos[1] < t[nextPos[0]].length
					&& (idx = index(dir, t[nextPos[0]][nextPos[1]])) != -1
					&& _test(t, nextPos, DIR_NEXT[dir][idx], visited);
		}
		int index(int dir, int piece) {
			int[] pieces = DIR_PIECE[dir];
			for (int i = 0; i < pieces.length; i++) {
				if (pieces[i] != piece) continue;
				return i;
			}
			return -1;
		}
		
	}
	interface Problem extends Predicate<int[][]> {
		
	}
}
