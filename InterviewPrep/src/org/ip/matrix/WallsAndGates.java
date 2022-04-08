package org.ip.matrix;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Consumer;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/walls-and-gates/">LC: 286</a>
 */
public class WallsAndGates {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[][] {{3,-1,0,1},{2,2,1,-1},{1,-1,2,-1},{0,-1,3,4}}, new int[][] {{2147483647,-1,0,2147483647},{2147483647,2147483647,2147483647,-1},{2147483647,-1,2147483647,-1},{0,-1,2147483647,2147483647}}};
		Object[] tc2 = new Object[] {new int[][] {{-1}}, new int[][] {{-1}}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		private final static int[][] DIR = new int[][] {{0,1},{0,-1},{-1,0},{1,0}}; 
		
		@Override
		public void accept(int[][] t) {
			Deque<int[]> deque = new LinkedList<>();
			for (int l = 0; l < t.length; l++) {
				for (int c = 0; c < t[l].length; c++) {
					if (t[l][c] != 0) continue;
					deque.add(new int[] {l, c});
				}
			}
			while (!deque.isEmpty()) {
				for (int size = deque.size(); size > 0; size--) {
					int[] pt = deque.removeFirst();
					int l = pt[0];
					int c = pt[1];
					for (int i = 0; i < DIR.length; i++) {
						int ll = l + DIR[i][0];
						int cc = c + DIR[i][1];
						if (ll < 0 || cc < 0 || ll >= t.length || cc >= t[ll].length) continue;
						else if (t[ll][cc] < Integer.MAX_VALUE) continue;
						t[ll][cc] = t[l][c] + 1;
						deque.addLast(new int[] {ll,cc});
					}
				}
			}
		}
	}
	interface Problem extends Consumer<int[][]> {
		
	}
}
