package org.ip.recursion;

public class Robbery2 {
	public static void main(String[] s) {
		RoberyMaximizer[] maximizers = new RoberyMaximizer[]{new RecursiveRoberyMaximizer(), new DPRoberyMaximizer()};
		for (RoberyMaximizer maximizer : maximizers) {
			System.out.println(maximizer.maximize(new int[]{6,1,2,7}) + "==13");
		}
		
	}
	public interface RoberyMaximizer {
		public int maximize(int[] houses);
	}
	public static class RecursiveRoberyMaximizer implements RoberyMaximizer {
		@Override
		public int maximize(int[] houses) {
			return maximize(houses, 0, houses.length - 1);
		}
		public int maximize(int[] houses, int l, int r) {
			if (l < 0 || r < 0 || l >= houses.length || r >= houses.length || l > r) return 0;
			int max = 0;
			for (int i = l; i <= r; i++) {
				int ll = maximize(houses, l, i - 2);
				int rr = maximize(houses, i + 2, r);
				max = Math.max(max, ll + rr + houses[i]);
			}
			return max;
		}
	}
	public static class DPRoberyMaximizer implements RoberyMaximizer {

		@Override
		public int maximize(int[] houses) {
			int[] cache = new int[houses.length];
			for (int i = 0; i < houses.length; i++) {
				int ll = i >= 2 ? cache[i - 2] : 0;
				int l = i >= 1 ? cache[i - 1] : 0;
				cache[i] = Math.max(ll + houses[i], l);
			}
			return cache[houses.length - 1];
		}
		
	}
}
