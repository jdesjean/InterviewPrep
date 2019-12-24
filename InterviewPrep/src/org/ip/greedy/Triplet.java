package org.ip.greedy;

import java.util.Arrays;

import org.ip.array.Utils;

// EPI: 18.4
public class Triplet {
	public static void main(String[] s) {
		int[] result = solve(new int[] {11,2,5,7,3}, 21);
		Utils.println(result, result.length);
	}
	public static int[] solve(int[] array, int target) {
		Arrays.sort(array);
		for (int i = 0; i < array.length; i++) {
			int[] doublet = Doublet.solve(array, target - array[i], 0, array.length-1);
			if (doublet != null) {
				return new int[] {array[i], array[doublet[0]], array[doublet[1]]};
			}
		}
		return null;
	}
	
}
