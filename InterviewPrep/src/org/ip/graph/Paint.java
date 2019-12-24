package org.ip.graph;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.ip.graph.Maze.Vertex;

//EPI: 19.2
public class Paint {
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
		solve(new Graph(map), 5,4);
		println(map);
	}
	public static void solve(Graph g, int x, int y) {
		bfs(g, x, y);
	}
	public static void bfs(Graph g, int x, int y) {
		Deque<Vertex> queue = new LinkedList<Vertex>();
		boolean color = g.map[x][y];
		boolean target = !g.map[x][y];
		queue.push(new Vertex(x,y));
		while (!queue.isEmpty()) {
			Vertex current = queue.removeFirst();
			List<Vertex> edges = g.getEdges(current, color);
			g.map[current.x][current.y] = target; 
			for (Vertex edge : edges) {
				queue.addLast(edge);
			}
		}
	}
	public static void println(boolean[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				System.out.print("," + (map[i][j] ? "1" : "0"));
			}
			System.out.println();
		}
	}
	public static class Graph {
		private boolean[][] map;
		public Graph(boolean[][] map) {
			this.map=map;
		}
		public List<Vertex> getEdges(Vertex v, boolean color) {
			List<Vertex> list = new LinkedList<Vertex>();
			int x = v.x;
			int y = v.y;
			if (x > 0 && map[x - 1][y] == color) {
				list.add(new Vertex(x - 1, y));
			}
			if (x < map.length - 1 && map[x + 1][y] == color) {
				list.add(new Vertex(x + 1, y));
			}
			if (y > 0 && map[x][y - 1] == color) {
				list.add(new Vertex(x, y - 1));
			}
			if (y < map[0].length - 1 && map[x][y + 1] == color) {
				list.add(new Vertex(x, y + 1));
			}
			return list;
		}
	}
}
