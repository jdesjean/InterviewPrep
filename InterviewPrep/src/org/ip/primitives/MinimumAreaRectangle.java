package org.ip.primitives;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MinimumAreaRectangle {
	public static void main(String[] s) {
		int[][] a = new int[][] {{1,1},{1,3},{3,1},{3,3},{2,2}};
		System.out.println(new MinimumAreaRectangle().solve(a)); //4;
		a = new int[][] {{1,1},{1,3},{3,1},{3,3},{4,1},{4,3}};
		System.out.println(new MinimumAreaRectangle().solve(a)); //2;
	}
	public int solve(int[][] a) {
		Map<Integer,Set<Integer>> y = new HashMap<Integer,Set<Integer>>();
		for (int i = 0; i < a.length; i++) {
			y.computeIfAbsent(a[i][0], (v) -> new HashSet<Integer>()).add(a[i][1]);
		}
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < a.length; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (a[i][0] == a[j][0] || a[i][1] == a[j][1]) continue; //we looking for opposite corners
				if (!y.get(a[i][0]).contains(a[j][1]) || !y.get(a[j][0]).contains(a[i][1])) continue;
				int dx = Math.abs(a[i][0] - a[j][0]);
				int dy = Math.abs(a[i][1] - a[j][1]);
				min = Math.min(min,  dx * dy);
			}
		}
		return min == Integer.MAX_VALUE ? 0 : min; 
	}
}
