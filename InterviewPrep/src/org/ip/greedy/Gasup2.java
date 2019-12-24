package org.ip.greedy;

// EPI: 18.6
public class Gasup2 {
	public static void main(String[] s) {
		int[][] segments = new int[][] {{50,900},{20,600},{5,200},{30,400},{25,600},{10,200},{10,100}};
		int index = solve(segments);
		System.out.println(index);
		for (int i = 0, total = 0; i < segments.length; i++) {
			total += segments[(i + index) % segments.length][0] * 20;
			total -= segments[(i + index) % segments.length][1];
			System.out.println(total);
		}
	}
	public static int solve(int[][] segments) {
		int total = 0;
		int min = 0;
		int minIndex = 0;
		for (int i = 0; i < segments.length; i++) {
			total += segments[i][0] * 20;
			total -= segments[i][1];
			if (total < min) {
				min = total;
				minIndex = (i + 1) % segments.length;
			}
		}
		return minIndex;
	}
}
