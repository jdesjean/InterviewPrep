package org.ip.graph;

import java.util.HashSet;
import java.util.Set;

import org.ip.array.Utils;
import org.ip.graph.Maze2.Position;
import org.ip.graph.Paint2.Graph;

// EPI 2018: 18.3
public class Enclosed2 {
	public static void main(String[] s) {
		boolean t = true;
		boolean f = false;
		boolean[][] map = new boolean[][] {
			{f,f,f,f},
			{t,f,t,f},
			{f,t,t,f},
			{f,f,f,f}
		};
		new Enclosed2().solve(new Graph(map));
		Utils.println(map);
	}
	public void solve(Graph g) {
		Set<Position> visited = new HashSet<Position>();
		for (Position ext : g.exterior(true)) {
			dfs(g, ext, visited);
		}
		for (int y = 0; y < g.map.length; y++) {
			for (int x = 0; x < g.map[y].length; x++) {
				if (g.map[y][x] == true && !visited.contains(new Position(x, y))) {
					g.map[y][x] = false;
				}
			}
		}
	}
	private void dfs(Graph g, Position p, Set<Position> visited) {
		if (visited.contains(p)) return;
		visited.add(p);
		for (Position edge: g.edges(p, g.getColor(p))) {
			dfs(g, edge, visited);
		}
	}
}
