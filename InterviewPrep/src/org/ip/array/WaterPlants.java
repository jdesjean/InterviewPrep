package org.ip.array;

/**
 * <a href="https://leetcode.com/discuss/interview-question/394347/">OA 2019</a>
 */
public class WaterPlants {
	public static void main(String[] s) {
		System.out.println(new WaterPlants().solve(new int[] {2,4,5,1,2}, 5, 7));
	}
	public int solve(int[] plants, int m1, int m2) {
		int i = 0, j = plants.length - 1, c1 = 0, c2 = 0, count = 0;
		for (; i < j; i++, j--) {
			if (c1 < plants[i]) {
				c1 = m1;
				count++;
			}
			if (c2 < plants[j]) {
				c2 = m2;
				count++;
			}
			c1 -= plants[i];
			c2 -= plants[j];
		}
		if (i == j && c1 + c2 < plants[i]) {
			count++;
		}
		return count;
	}
	
}
