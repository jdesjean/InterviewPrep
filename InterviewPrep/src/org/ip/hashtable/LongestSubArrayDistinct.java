package org.ip.hashtable;

import java.util.HashMap;
import java.util.Map;

// EPI 2018: 12.6
public class LongestSubArrayDistinct {
	public static void main(String[] s) {
		char[] array = new char[] {'f','s','f','e','t','w','e','n','w','e'};
		System.out.println(new LongestSubArrayDistinct().solve(array));
	}
	public Pair solve(char[] array) {
		Map<Character,Integer> map = new HashMap<Character,Integer>();
		int index = -1;
		int maxLength = 0;
		int prev = -1;
		for (int i = 0; i < array.length; i++) {
			if (map.get(array[i]) != null) {
				int length = i - (prev + 1);
				prev = map.getOrDefault(array[i], 0);
				if (length > maxLength) {
					maxLength = length;
					index = i - maxLength;
				}
			}
			map.put(array[i], i);
		}
		int length = array.length - (prev + 1);
		if (length > maxLength) {
			maxLength = length;
			index = array.length - maxLength;
		}
		return new Pair(index,maxLength);
	}
	public static class Pair {
		int start;
		int length;
		public Pair(int start, int length) {this.start=start;this.length=length;}
		@Override
		public String toString() {
			return start + "," + length;
		}
	}
}
