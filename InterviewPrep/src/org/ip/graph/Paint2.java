package org.ip.graph;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.ip.array.Utils;
import org.ip.graph.Maze2.Position;

// EPI 2018: 18.2
public class Paint2 {
	public static void main(String[] s) {
		boolean t = true;
		boolean f = false;
		boolean[][] map = new boolean[][] {
			{t,f,t,f,f,f,t,t,t,t},
			{f,f,t,f,f,t,f,f,t,t},
			{t,t,t,f,f,t,t,f,t,t},
			{f,t,f,t,t,t,t,f,t,f},
			{t,f,t,f,f,f,f,t,f,f},
			{t,f,t,f,f,t,f,t,t,t},
			{f,f,f,f,t,f,t,f,f,t},
			{t,f,t,f,t,f,t,f,f,f},
			{t,f,t,t,f,f,f,t,t,t},
			{f,f,f,f,f,f,f,t,t,f}
		};
		new Paint2().bfs(new Graph(map), new Position(5, 4));
		new Paint2().bfs(new Graph(map), new Position(6, 3));
		Utils.println(map);
	}
	public void bfs(Graph g, Position start) {
		Deque<Position> queue = new LinkedList<Position>();
		queue.add(start);
		boolean color = g.getColor(start);
		while (!queue.isEmpty()) {
			Position p = queue.remove();
			if (g.getColor(p) != color) continue;
			g.color(p, !color);
			for (Position edge : g.edges(p, color)) {
				queue.add(edge);
			}
		}
	}
	public static class Graph {
		public boolean[][] map;
		public Graph(boolean[][] map) {
			this.map = map;
		}
		public List<Position> exterior(boolean color) {
			List<Position> list = new ArrayList<Position>();
			for (int x = 0; x < map[0].length; x++) {
				if (map[0][x] == color) {
					list.add(new Position(x, 0));
				}
				int end = map.length - 1;
				if (map[end][x] == color) {
					list.add(new Position(x, 0));
				}
			}
			for (int y = 1; y < map.length - 1; y++) {
				if (map[y][0] == color) {
					list.add(new Position(0, y));
				}
				int end = map[0].length - 1;
				if (map[y][end] == color) {
					list.add(new Position(end, y));
				}
			}
			return list;
		}
		public List<Position> edges(Position p, boolean color) {
			int y = p.y;
			int x = p.x;
			List<Position> list = new ArrayList<Position>(4);
			if (x + 1 < map[0].length && map[y][x+1] == color) {
				list.add(new Position(x + 1, y));
			}
			if (x - 1 >= 0 && map[y][x - 1] == color) {
				list.add(new Position(x - 1, y));
			}
			if (y + 1 < map.length && map[y + 1][x] == color) {
				list.add(new Position(x, y + 1));
			}
			if (y - 1 >= 0 && map[y - 1][x] == color) {
				list.add(new Position(x, y - 1));
			}
			return list;
		}
		public boolean getColor(Position p) {
			return map[p.y][p.x];
		}
		public void color(Position p, boolean color) {
			map[p.y][p.x] = color;
		}
	}
}
