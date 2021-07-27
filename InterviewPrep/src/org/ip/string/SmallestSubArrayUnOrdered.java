package org.ip.string;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

// EPI 2018: 12.6
public class SmallestSubArrayUnOrdered {
	public static void main(String[] s) {
		String string = "My paramount object in this struggle is to save the Union and is not either to save or to destroy slavery. If I could save the Union without feeing any slave I would do it, and if I could save it by freeing all the slaves I would do it; and if I could save it by freeing some and leaving others alone I would also do that.";
		System.out.println(new SmallestSubArrayUnOrdered().solve(string, "save Union"));
	}
	public Pair solve(String s1, String s2) {
		String[] array1 = s1.split(" ");
		String[] array2 = s2.split(" ");
		Set<String> set2 = new HashSet<String>();
		for (int i = 0; i < array2.length; i++) {
			set2.add(array2[i]);
		}
		Map<String, Integer> map1 = new HashMap<String, Integer>();
		Set<String> set1 = new HashSet<String>();
		PriorityQueue<Integer> minQ = new PriorityQueue<Integer>();
		int minDistance = Integer.MAX_VALUE;
		int minIndex = -1;
		for (int i = 0; i < array1.length; i++) {
			if (set2.contains(array1[i])) {
				if (set1.add(array1[i])) {
					minQ.add(i);
				} else {
					if (map1.get(array1[i]) == minQ.peek()) { //we're replacing the minimum element
						minQ.remove();
					}
					minQ.add(i);
				}
				if (set1.size() == set2.size()) {
					int distance = i - minQ.peek();
					if (distance < minDistance) {
						minDistance = distance;
						minIndex = minQ.peek();
					}
				}
			}
			map1.put(array1[i], i);
		}
		if (minDistance == -1) {
			return new Pair(-1,-1);
		}
		return new Pair(minIndex, minIndex + minDistance);
	}
	public static class Pair {
		int left;
		int right;
		public Pair(int left, int right) {this.left=left;this.right=right;}
		@Override
		public String toString() {
			return left + "," + right;
		}
	}
}
