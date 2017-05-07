package org.ip.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Skyline {
	public static void main(String[] s) {
		/*
		 * Input: Array of buildings
			{ (1,11,5), (2,6,7), (3,13,9), (12,7,16), (14,3,25), (19,18,22), (23,13,29), (24,4,28) }
			
			Output: Skyline (an array of rectangular strips)
			
			A strip has x coordinate of left side and height
			
			(1, 11), (3, 13), (9, 0), (12, 7), (16, 3), (19,18), (22, 3), (23, 13), (29, 0)
		 */
		System.out.println(solve(new Building[]{b(1,11,5),b(2,6,7),b(3,13,9),b(12,7,16),b(14,3,25),b(19,18,22),b(23,13,29),b(24,4,28)}));
	}
	public static Pair p(int left, int height) {
		return new Pair(left,height);
	}
	public static Building b(int left, int height, int right) {
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
			else return side() - o.side();
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
