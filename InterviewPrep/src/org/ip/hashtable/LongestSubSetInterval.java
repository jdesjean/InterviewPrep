package org.ip.hashtable;

import java.util.HashSet;
import java.util.Set;

// EPI 2018: 12.9
public class LongestSubSetInterval {
	public static void main(String[] s) {
		int[] array = new int[] {3,-2,7,0,8,1,2,0,-1,5,8};
		System.out.println(new LongestSubSetInterval().solve(array));
	}
	public Set<Integer> solve(int[] array) {
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < array.length; i++) {
			set.add(array[i]);
		}
		Set<Integer> longest = new HashSet<Integer>();
		while (!set.isEmpty()) {
			Set<Integer> candidate = new HashSet<Integer>();
			Integer value = set.iterator().next();
			set.remove(value);
			candidate.add(value);
			for (int i = value + 1; i < Integer.MAX_VALUE; i++) {
				if (!set.remove(i)) break;
				candidate.add(i);
			}
			for (int i = value - 1; i > Integer.MIN_VALUE; i--) {
				if (!set.remove(i)) break;
				candidate.add(i);
			}
			if (candidate.size() > longest.size()) {
				longest = candidate;
			}
		}
		return longest;
	}
}
