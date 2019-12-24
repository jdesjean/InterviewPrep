package org.ip.dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.ip.array.Utils;

// EPI 2018: 16.5
public class Sequence2D {
	public static void main(String[] s) {
		System.out.println(new Sequence2D().solve(new int[][] {
				{1,2,3},
				{3,4,5},
				{5,6,7}
				},
				new int[] {1,3,4,6}));
	}
	public boolean solve(int[][] grid, int[] sequence) {
		int[][] cache = new int[grid.length][grid[0].length];
		Map<Integer,List<Pair>> map = getMap(grid);
		for (int i = 0; i < sequence.length; i++) {
			for (Pair pair : map.get(sequence[i])) {
				//1,0
				//-1,0
				//0,1
				//0,-1
				if (pair.l < grid.length - 1 && cache[pair.l + 1][pair.c] == i) {
					cache[pair.l][pair.c] = i + 1;
				} else if (pair.l > 0 && cache[pair.l - 1][pair.c] == i) {
					cache[pair.l][pair.c] = i + 1;
				} else if (pair.c < grid[pair.l].length - 1 && cache[pair.l][pair.c + 1] == i) {
					cache[pair.l][pair.c] = i + 1;
				} else if (pair.c > 0 && cache[pair.l][pair.c - 1] == i) {
					cache[pair.l][pair.c] = i + 1;
				}
			}
		}
		for (Pair pair : map.get(sequence[sequence.length - 1])) {
			if (cache[pair.l][pair.c] != sequence.length) continue;
			return true;
		}
		return false;
	}
	public Map<Integer, List<Pair>> getMap(int[][] grid) {
		Map<Integer, List<Pair>> map = new HashMap<Integer, List<Pair>>();
		for (int l = 0; l < grid.length; l++) {
			for (int c = 0; c < grid[l].length; c++) {
				List<Pair> list = map.get(grid[l][c]);
				if (list == null) {
					list = new ArrayList<Pair>();
					map.put(grid[l][c], list);
				}
				list.add(new Pair(l,c));
			}
		}
		return map;
	}
	public static class Pair {
		int l;
		int c;
		public Pair(int x, int y) {
			super();
			this.l = x;
			this.c = y;
		}
		
	}
}
