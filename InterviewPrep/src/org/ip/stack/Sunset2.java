package org.ip.stack;

import java.util.Deque;
import java.util.LinkedList;

//EPI 2018: 8.5
public class Sunset2 {
	public static void main(String[] s) {
		Building[] buildings = new Building[]{building(0,6),building(1,4),building(2,1),building(3,5)};
		Deque<Building> stack = sunset(buildings);
		System.out.println(stack);
		org.ip.array.Utils.reverse(buildings, 0, buildings.length - 1);
		stack = sunset(buildings);
		System.out.println(stack);
	}
	public static Deque<Building> sunset(Building[] buildings) {
		Deque<Building> stack = new LinkedList<Building>();
		for (int i = buildings.length - 1; i >= 0; i--) {
			while (!stack.isEmpty() && buildings[i].height > stack.peek().height) {
				stack.pop();
			}
			stack.push(buildings[i]);
		}
		return stack;
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
}
