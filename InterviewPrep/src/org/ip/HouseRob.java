package org.ip;

/**
 * <a href="https://leetcode.com/problems/house-robber/">LC: 198</a>
 */
public class HouseRob {
	public static void main(String[] s) {
		tc0(new DP());
		tc1(new DP());
		tc2(new DP());
	}
	public static void tc0(Solver solver) {
		System.out.println(solver.solve(new int[] {1,2,3,4}));
	}
	public static void tc1(Solver solver) {
		System.out.println(solver.solve(new int[] {1,2,3,1}));
	}
	public static void tc2(Solver solver) {
		System.out.println(solver.solve(new int[] {2,7,9,3,1}));
	}
	public interface Solver {
		public int solve(int[] a);
	}
	public static class DP implements Solver {

		@Override
		public int solve(int[] a) {
			int p1 = 0, p2 = 0;
			for (int i = 0; i < a.length; i++) {
				int p3 = p1 + a[i];
				p1 = Math.max(p1, p2);
				p2 = p3;
			}
			return Math.max(p1, p2);
		}
		
	}
}
