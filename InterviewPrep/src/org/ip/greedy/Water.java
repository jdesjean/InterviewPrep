package org.ip.greedy;

// EPI: 18.7
public class Water {
	public static void main(String[] s) {
		int[] heights = new int[] {1,2,1,3,4,4,5,6,2,1,3,1,3,2,1,2,4,1};
		System.out.println(solve(heights));
	}
	public static int solve(int[] heights) {
		int max = 0;
		for (int i = 0, j = heights.length - 1; i < j;) {
			int volume = Math.min(heights[i], heights[j]) * (j - i);
			max = Math.max(max, volume);
			if (heights[i] < heights[j]) {
				i++;
			} else if (heights[i] > heights[j]) {
				j--;
			} else {
				i++;
				j--;
			}
		}
		return max;
	}
}
