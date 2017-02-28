package org.ip.graph;

import java.util.Deque;
import java.util.LinkedList;

public class Knight {
	public static final Point EMPTY = new Point(0,0);
	public static class Point {
		public int x;
		public int y;
		public Point(int x, int y){this.x=x;this.y=y;}
		@Override
		public boolean equals(Object object) {
			if (!(object instanceof Point)) return false;
			Point point = (Point)object;
			return point.x == x && point.y == y;
		}
	}
	public static final int[][] MOVES = new int[][]{{-2,1},{-2,-1},{-1,2},{-1,-2},{1,2},{1,-2},{2,1},{2,-1}};
	public static void main(String[] s) {
		System.out.println(min(new Point(0,0), new Point(0,7)));
		System.out.println(min(new Point(0,0), new Point(7,7)));
		System.out.println(min(new Point(3,3), new Point(4,5)));
	}
	public static int index(int x, int y) {
		return x*8+y;
	}
	public static boolean isWithinBounds(int x, int y) {
		return x >=0 && x <= 7 && y >= 0 && y <= 7;
	}
	public static int min(Point start, Point end) {
		if (start.equals(end)) return 0;
		Deque<Point> queue = new LinkedList<Point>();
		boolean[] visited = new boolean[64];
		queue.addLast(start);
		queue.addLast(EMPTY);
		visited[index(start.x,start.y)] = true;
		int depth = 0;
		
		while (!queue.isEmpty()) {
			Point current = queue.removeFirst();
			if (current.equals(end)) return depth;
			if (current == EMPTY && !queue.isEmpty()) {
				depth++;
				queue.addLast(EMPTY);
			}
			for (int i = 0; i < MOVES.length; i++) {
				int x = current.x+MOVES[i][0];
				int y = current.y+MOVES[i][1];
				int index = index(x,y);
				if (!isWithinBounds(x,y) || visited[index]) continue;
				visited[index] = true;
				queue.addLast(new Point(x,y));
			}
		}
		return depth;
	}
}
