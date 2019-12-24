package org.ip.hashtable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// EPI 2018: 12.6
public class SmallestSubArraySequential {
	public static void main(String[] s) {
		String string = "My paramount object in this struggle is to save the Union and is not either to save or to destroy slavery. If I could save the Union without feeing any slave I would do it, and if I could save it by freeing all the slaves I would do it; and if I could save it by freeing some and leaving others alone I would also do that.";
		System.out.println(new SmallestSubArraySequential().solve(string, "save Union"));
	}
	public Pair solve(String s1, String s2) {
		String[] array1 = s1.split(" ");
		String[] array2 = s2.split(" ");
		Map<String,Integer> map = new HashMap<String,Integer>();
		for (int i = 0; i < array2.length; i++) {
			map.put(array2[i], i);
		}
		int[] array = new int[array2.length];
		Arrays.fill(array, -1);
		int minDistance = Integer.MAX_VALUE;
		int minIndex = -1;
		for (int i = 0; i < array1.length; i++) {
			if (map.containsKey(array1[i])) {
				int pos = map.get(array1[i]);
				if ((pos == 0 || array[pos - 1] != -1) && array[pos] == -1) {
					array[pos] = i;
				}
				if (array[array.length - 1] != -1) {
					int distance = array[array.length - 1] - array[0];
					if (distance < minDistance) {
						minDistance = distance;
						minIndex = array[0];
					}
					Arrays.fill(array, -1);
				}
			}
		}
		if (minDistance == -1) {
			return new Pair(-1,-1);
		}
		return new Pair(minIndex, minIndex + minDistance);
	}
	public static class Pair {
		int left;
		int length;
		public Pair(int left, int length) {this.left=left;this.length=length;}
		@Override
		public String toString() {
			return left + "," + length;
		}
	}
}
