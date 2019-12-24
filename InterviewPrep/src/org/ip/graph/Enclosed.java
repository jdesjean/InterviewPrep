package org.ip.graph;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.ip.graph.Maze.Vertex;

//EPI: 19.3
public class Enclosed {
	public static void main(String[] s) {
		byte[][] map = new byte[][] {
			{0,0,0,0},
			{1,0,1,0},
			{0,1,1,0},
			{0,0,0,0}
		};
		solve(new Graph(map));
		println(map);
	}
	public static void solve(Graph g) {
		int cc = g.map[0].length - 1;
		int ll = g.map.length-1;
		for (int c = 0; c <= cc; c++) {
			if (g.map[0][c] == 1) {
				bfs(g, 0, c, (byte)2);
			}
			if (g.map[ll][c] == 1) {
				bfs(g, ll, c, (byte)2);
			}
		}
		for (int l = 0; l < g.map.length; l++) {
			if (g.map[l][0] == 1) {
				bfs(g, l, 0, (byte)2);
			}
			if (g.map[l][cc] == 1) {
				bfs(g, l, cc, (byte)2);
			}
		}
		for (int l = 0; l < g.map.length; l++) {
			for (int c = 0; c < g.map[0].length; c++) {
				if (g.map[l][c] == 2) {
					g.map[l][c] = 1;
				} else if (g.map[l][c] == 1) {
					g.map[l][c] = 0;
				}
			}
		}
	}
	
	public static void bfs(Graph g, int x, int y, byte target) {
		Deque<Vertex> queue = new LinkedList<Vertex>();
		byte color = g.map[x][y];
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
	
	public static class Graph {
		private byte[][] map;
		public Graph(byte[][] map) {
			this.map=map;
		}
		public List<Vertex> getEdges(Vertex v, byte color) {
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
	public static void println(byte[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				System.out.print("," + (map[i][j] == 1 ? "1" : "0"));
			}
			System.out.println();
		}
	}
}
