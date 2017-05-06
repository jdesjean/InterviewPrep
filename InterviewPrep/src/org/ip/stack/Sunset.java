package org.ip.stack;

import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class Sunset {
	public static void main(String[] s) {
		Building[] buildings = new Building[]{building(0,6),building(1,4),building(2,1),building(3,5)};
		Deque<Building> stack = sunset(Arrays.asList(buildings).iterator());
		System.out.print(stack);
	}
	public static Building building(int pos, int height) {
		return new Building(pos,height);
	}
	public static class Building {
		public int pos;
		public int height;
		public Building(int pos, int height) {
			super();
			this.pos = pos;
			this.height = height;
		}
		@Override
		public String toString() {
			return "Building [pos=" + pos + ", height=" + height + "]";
		}
		
	}
	//EPI 9.6
	public static Deque<Building> sunset(Iterator<Building> buildings) {
		Deque<Building> stack = new LinkedList<Building>();
		for (;buildings.hasNext();) {
			Building building = buildings.next();
			while (!stack.isEmpty() && building.height >= stack.peek().height) {
				stack.pop();
			}
			stack.push(building);
		}
		return stack;
	}
}
