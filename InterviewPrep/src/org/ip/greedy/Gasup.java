package org.ip.greedy;

// EPI: 18.6
public class Gasup {
	public static void main(String[] s) {
		int[][] segments = new int[][] {{50,900},{20,600},{5,200},{30,400},{25,600},{10,200},{10,100}};
		System.out.println(solve(segments));
	}
	public static int solve(int[][] segments) {
		int current = 0;
		int j = 0;
		for (int i = 0; i < segments.length; i++) {
			current += segments[i][0] - segments[i][1] / 20;
			while (current < 0 && i < segments.length) {
				current = segments[i][0] - segments[i][1] / 20;
				j = i++;
			}
		}
		return j;
	}
}
