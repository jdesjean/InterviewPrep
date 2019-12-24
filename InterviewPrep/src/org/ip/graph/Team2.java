package org.ip.graph;

import java.util.HashMap;
import java.util.Map;

import org.ip.graph.Team.Graph;

// EPI 2018: 18.8
public class Team2 {
	public static void main(String[] s) {
		Graph<Integer> g = new Graph<Integer>();
		g.addEdge(0, 1);
		g.addEdge(0, 2);
		g.addEdge(1, 2);
		g.addEdge(3, 0);
		System.out.println(new Team2().solve(g));
	}
	public int solve(Graph<Integer> g) {
		Map<Integer, Integer> distances = new HashMap<Integer,Integer>();
		int max = 0;
		for (Integer v : g.map.keySet()) {
			if (distances.getOrDefault(v, 0) == 0) {
				max = Math.max(max, dfs(g, distances, v));
			}
		}
		return max;
	}
	public int dfs(Graph<Integer> g, Map<Integer, Integer> map, Integer v) {
		map.put(v, 1);
		for (Integer e : g.getEdges(v)) {
			int d = map.getOrDefault(e, 0);
			map.put(v, Math.max(map.get(v), d != 0 ? d : dfs(g, map, e) + 1));
		}
		return map.get(v);
	}
}
