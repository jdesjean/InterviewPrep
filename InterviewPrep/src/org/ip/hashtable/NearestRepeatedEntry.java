package org.ip.hashtable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// EPI 2018: 12.5
public class NearestRepeatedEntry {
	public static void main(String[] s) {
		String string = "All work and no play makes for no work no fun no play";
		System.out.println(new NearestRepeatedEntry().solve(string));
	}
	public int solve(String s) {
		String[] array = s.split(" ");
		Map<String,Integer> map = new HashMap<String,Integer>();
		int candidateIndex = -1;
		int candidateDistance = Integer.MAX_VALUE;
		for (int i = 0; i < array.length; i++) {
			Integer prev = map.get(array[i]);
			if (prev != null) {
				int distance = i - prev;
				if (distance < candidateDistance){
					candidateDistance = distance;
					candidateIndex = i;
				}
			}
			map.put(array[i], i);
		}
		return candidateIndex;
	}
}
