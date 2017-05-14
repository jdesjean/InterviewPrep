package org.ip.recursion;

public class Robbery {
	public static void main(String[] s) {
		RoberyMaximizer[] maximizers = new RoberyMaximizer[]{new RecursiveRoberyMaximizer(), new DPRoberyMaximizer()};
		for (RoberyMaximizer maximizer : maximizers) {
			System.out.println(maximizer.maximize(new int[]{6,1,2,7}) + "==13");
		}
		
	}
	public interface RoberyMaximizer {
		public int maximize(int[] houses);
	}
	public static class RecursiveRoberyMaximizer implements RoberyMaximizer{

		@Override
		public int maximize(int[] houses) {
			return max(houses,0,houses.length-1);
		}
		private static int max(int[] houses, int left, int right) {
			if (left > right) return 0;
			int max = 0;
			for (int i = left; i <= right; i++) {
				int maxLeft = max(houses,left,i-2);
				int maxRight = max(houses,i+2,right);
				max = Math.max(max, houses[i]+maxLeft+maxRight);
			}
			return max;
		}
	}
	public static class DPRoberyMaximizer implements RoberyMaximizer{

		@Override
		public int maximize(int[] houses) {
			int[] cache = new int[houses.length];
			int max = 0;
			for (int i = 0; i < houses.length; i++) {
				cache[i] = houses[i];
				for (int j = 0; j <= i-2; j++) {
					cache[i] = Math.max(cache[i], houses[i] + cache[j]);
				}
				max = Math.max(max, cache[i]);
			}
			return max;
		}
		
	}
}
