package org.ip.matrix;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/the-skyline-problem/">LC: 218</a>
 */
public class Skyline {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[][] {{1, 11}, {3, 13}, {9, 0}, {12, 7}, {16, 3}, {19,18}, {22, 3}, {23, 13}, {29, 0}}, new int[][] {{1,5,11},{2,7,6},{3,9,13},{12,16,7},{14,25,3},{19,22,18},{23,29,13},{24,28,4}}};
		Object[] tc2 = new Object[] {new int[][] {{2,10},{3,15},{7,12},{12,0},{15,10},{20,8},{24,0}}, new int[][] {{2,9,10},{3,7,15},{5,12,12},{15,20,10},{19,24,8}}};
		Object[] tc3 = new Object[] {new int[][] {{0,3},{5,0}}, new int[][] {{0,2,3},{2,5,3}}};
		Object[] tc4 = new Object[] {new int[][] {{1,3},{2,0}}, new int[][] {{1,2,1},{1,2,2},{1,2,3}}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2(), new Solver3(), new Solver4()};
		Test.apply(solvers, tcs);
	}
	
	static class Solver4 implements Problem {

		@Override
		public List<List<Integer>> apply(int[][] t) {
			if (t == null || t.length == 0) return null;
			int[][] tt = new int[t.length * 2][2];
			for (int i = 0; i < t.length; i++) {
				tt[i * 2] = new int[] {0, t[i][0], t[i][2]};
				tt[i * 2 + 1] = new int[] {1, t[i][1], t[i][2]};
			}
			Arrays.sort(tt, new StartComparator());
			Deque<int[]> deque = new ArrayDeque<>(tt.length);
			TreeMap<Integer, Integer> map = new TreeMap<>();
			for (int i = 0; i < tt.length; i++) {
				boolean isStart = tt[i][0] == 0;
				int x = tt[i][1];
				int h = tt[i][2];
				if (isStart) {
					if (map.isEmpty() || map.lastKey() < h) {
						deque.add(new int[] {x, h});
					}
					map.compute(h, (k, v) -> v != null ? v + 1 : 1);
				} else {
					Integer cnt = map.compute(h, (k, v) -> v != null ? v - 1 : 0);
					if (cnt == 0) {
						map.remove(h);
					}
					int lastHeight = map.isEmpty() ? 0 : map.lastKey();
					if (map.isEmpty() || lastHeight < h) {
						deque.add(new int[] {x, lastHeight});
					}
				}
			}
			return deque
					.stream()
					.map(x -> Arrays.asList(new Integer[] {x[0],x[1]}))
					.collect(Collectors.toList());
		}
		static class StartComparator implements Comparator<int[]> {
			@Override
			public int compare(int[] o1, int[] o2) {
				int c = Integer.compare(o1[1], o2[1]);
				if (c != 0) return c;
				c = Integer.compare(o1[0], o2[0]);
				if (c != 0) return c;
				return (o1[0] == 0 ? -1 : 1) * Integer.compare(o1[2], o2[2]);
			}
			
		}
	}
	
	static class Solver3 implements Problem {

		@Override
		public List<List<Integer>> apply(int[][] t) {
			if (t == null || t.length == 0) return null;
			int[][] tt = new int[t.length * 2][2];
			for (int i = 0; i < t.length; i++) {
				tt[i * 2] = new int[] {0, t[i][0], t[i][2]};
				tt[i * 2 + 1] = new int[] {1, t[i][1], t[i][2]};
			}
			Arrays.sort(tt, new StartComparator());
			Deque<int[]> deque = new ArrayDeque<>(tt.length);
			PriorityQueue<Integer> pqHeight = new PriorityQueue<Integer>(Comparator.reverseOrder());
			for (int i = 0; i < tt.length; i++) {
				boolean isStart = tt[i][0] == 0;
				int x = tt[i][1];
				int h = tt[i][2];
				if (isStart) {
					if (pqHeight.isEmpty() || pqHeight.peek() < h) {
						deque.add(new int[] {x, h});
					}
					pqHeight.add(h);
				} else {
					pqHeight.remove(h); // Can we improve this?
					int peek = pqHeight.isEmpty() ? 0 : pqHeight.peek();
					if (pqHeight.isEmpty() || peek < h) {
						deque.add(new int[] {x, peek});
					}
				}
			}
			return deque
					.stream()
					.map(x -> Arrays.asList(new Integer[] {x[0],x[1]}))
					.collect(Collectors.toList());
		}
		static class StartComparator implements Comparator<int[]> {
			@Override
			public int compare(int[] o1, int[] o2) {
				int c = Integer.compare(o1[1], o2[1]);
				if (c != 0) return c;
				c = Integer.compare(o1[0], o2[0]);
				if (c != 0) return c;
				return (o1[0] == 0 ? -1 : 1) * Integer.compare(o1[2], o2[2]);
			}
			
		}
	}
	
	static class Solver2 implements Problem {

		@Override
		public List<List<Integer>> apply(int[][] t) {
			if (t == null || t.length == 0) return null;
			Arrays.sort(t, new StartHeightComparator());
			Deque<int[]> deque = new ArrayDeque<>();
			PriorityQueue<int[]> pqEnd = new PriorityQueue<int[]>(new EndComparator());
			PriorityQueue<int[]> pqHeight = new PriorityQueue<int[]>(new HeightComparator().reversed());
			for (int i = 0; i < t.length; ) {
				// end of a previous building before current building start
				if (pqEnd.isEmpty() || pqEnd.peek()[1] >= t[i][0]) {
					if (pqHeight.isEmpty() || pqHeight.peek()[2] < t[i][2]) {
						deque.add(new int[] {t[i][0], t[i][2]});
					}
					pqHeight.add(t[i]);
					pqEnd.add(t[i]);
					i++;
				} else {
					int[] x = pqEnd.remove();
					pqHeight.remove(x); // Can we improve this?
					if (pqHeight.isEmpty() || pqHeight.peek()[2] < x[2]) {
						deque.add(new int[] {x[1], pqHeight.isEmpty() ? 0 : pqHeight.peek()[2]});
					}
				}
			}
			while (!pqEnd.isEmpty()) {
				int[] x = pqEnd.remove();
				pqHeight.remove(x); // Can we improve this?
				if (pqHeight.isEmpty() || pqHeight.peek()[2] < x[2]) {
					deque.add(new int[] {x[1], pqHeight.isEmpty() ? 0 : pqHeight.peek()[2]});
				}
			}
			return deque
					.stream()
					.map(x -> Arrays.asList(new Integer[] {x[0], x[1]}))
					.collect(Collectors.toList());
		}
		static class HeightComparator implements Comparator<int[]> {

			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(o1[2], o2[2]);
			}
			
		}
		static class EndComparator implements Comparator<int[]> {

			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(o1[1], o2[1]);
			}
		}
		static class StartHeightComparator implements Comparator<int[]> {

			@Override
			public int compare(int[] o1, int[] o2) {
				int c = Integer.compare(o1[0], o2[0]);
				if (c != 0) return c;
				c = Integer.compare(o2[2], o1[2]); //tallest first
				if (c != 0) return c;
				return Integer.compare(o1[1], o2[1]);
			}
			
		}
	}

	static class Solver implements Problem {

		@Override
		public List<List<Integer>> apply(int[][] t) {
			Building[] buildings = new Building[t.length];
			for (int i = 0; i < t.length; i++) {
				buildings[i] = b(t[i][0], t[i][1], t[i][2]);
			}
			return solve(buildings)
			.stream()
			.map(pair -> Arrays.asList(new Integer[] {pair.left, pair.height}))
			.collect(Collectors.toList());
		}
		
		public static Pair p(int left, int height) {
			return new Pair(left,height);
		}
		public static Building b(int left, int right, int height) {
			return new Building(left,height,right);
		}
		public static class Pair {
			public final int left;
			public final int height;
			public Pair(int left, int height) {
				super();
				this.left = left;
				this.height = height;
			}
			@Override
			public String toString() {
				return "Pair [left=" + left + ", height=" + height + "]";
			}
		}
		public static class Building implements Comparable<Building>{
			public final int left;
			public final int right;
			public final int height;
			public Building(int left, int height, int right) {
				super();
				this.left = left;
				this.right = right;
				this.height = height;
			}
			@Override
			public int compareTo(Building o) {
				if (o == null) return -1;
				else return height-o.height;
			}
		}
		public static class BuildingSide implements Comparable<BuildingSide>{
			public final Building building;
			public final boolean start;
			public BuildingSide(Building building, boolean start) {
				super();
				this.building = building;
				this.start = start;
			}
			public int side() {
				return start ? building.left : building.right;
			}
			public int height() {
				return building.height;
			}
			@Override
			public int compareTo(BuildingSide o) {
				if (o == null) return -1;
				int c = Integer.compare(side(), o.side());
				if (c != 0) return c;
				return Boolean.compare(start, o.start) * -1;
			}
		}
		public static List<Pair> solve(Building[] buildings) {
			BuildingSide[] sides = new BuildingSide[buildings.length*2];
			for (int i = 0; i < buildings.length; i++) {
				int j = i*2;
				sides[j] = new BuildingSide(buildings[i],true);
				sides[j+1] = new BuildingSide(buildings[i],false);
			}
			Arrays.sort(sides);
			PriorityQueue<Building> queue = new PriorityQueue<Building>(Collections.reverseOrder());
			List<Pair> pairs = new ArrayList<Pair>();
			for (int i = 0; i < sides.length; i++) {
				BuildingSide side = sides[i];
				if (side.start) {
					if (queue.isEmpty() || queue.peek().height < side.building.height) {
						pairs.add(new Pair(side.side(),side.building.height));
					}
					queue.add(side.building);
				}
				else {
					queue.remove(side.building);
					int height = queue.isEmpty() ? 0 : queue.peek().height; 
					if (height < side.building.height) {
						pairs.add(new Pair(side.side(),height));
					}
				}
			}
			return pairs;
		}
		
	}
	interface Problem extends Function<int[][], List<List<Integer>>> {
		
	}
	
}
