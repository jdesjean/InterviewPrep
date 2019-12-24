package org.ip.recursion;

// EPI: 17.1
public class Score {
	public static void main(String[] s) {
		System.out.println(new DPSolve().solve(new int[] {2,3,7},12));
		System.out.println(new RecursiveSolve().solve(new int[] {2,3,7},12));
	}
	public interface Solve {
		public int solve(int[] points, int score);
	}
	public static class RecursiveSolve implements Solve {
		public int solve(int[] points, int score) {
			return solve(points, score, points.length - 1);
		}
		public int solve(int[] points, int score, int point) {
			if (score < 0) {
				return 0;
			} else if (score == 0) {
				return 1;
			}
			int sum = 0;
			for (int i = 0; i <= point; i++) {
				sum += solve(points, score - points[i], i);
			}
			return sum;
		}
	}
	public static class DPSolve implements Solve {
		public int solve(int[] points, int score) {
			int[] cache = new int[score + 1];
			cache[0] = 1;
			for (int i = 0; i < points.length; i++) {
				for (int j = points[i]; j < cache.length; j++) {
					cache[j] = cache[j - points[i]] + cache[j]; 
				}
			}
			return cache[score];
		}
	}
	
	
}
