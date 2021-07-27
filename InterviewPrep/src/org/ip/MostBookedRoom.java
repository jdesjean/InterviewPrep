package org.ip;

/**
 * <a href="https://leetcode.com/discuss/interview-question/421787/">OA 2019</a>
 */
public class MostBookedRoom {
	public static void main(String[] s) {
		//Input: ["+1A", "+3E", "-1A", "+4F", "+1A", "-3E"]
		//Output: "1A"
		System.out.println(new MostBookedRoom().solve(new String[] {"+1A", "+3E", "-1A", "+4F", "+1A", "-3E"}));
	}
	public String solve(String[] s) {
		int[] cache = new int[26 * 10];
		int max = -1;
		for (int i = 0; i < s.length; i++) {
			if (s[i].charAt(0) == '-') continue;
			int index = 26 * (s[i].charAt(1) - '0') + s[i].charAt(2) - 'A';
			cache[index]++;
			if (max == -1) {
				max = index;
			} else if (cache[index] > cache[max]) {
				max = index;
			}
		}
		return max == -1 ? "" : Integer.toString(max / 26) + (char)(max % 26 + 'A'); 
	}
}
