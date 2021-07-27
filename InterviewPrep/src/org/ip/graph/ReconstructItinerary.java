package org.ip.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/reconstruct-itinerary/">LC: 332</a>
 */
public class ReconstructItinerary {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new String[] {"JFK","MUC","LHR","SFO","SJC"}, Arrays.asList(
				Arrays.asList(new String[] {"MUC","LHR"}),
				Arrays.asList(new String[] {"JFK","MUC"}),
				Arrays.asList(new String[] {"SFO","SJC"}),
				Arrays.asList(new String[] {"LHR","SFO"}))};
		Object[] tc2 = new Object[] {new String[] {"JFK","ATL","JFK","SFO","ATL","SFO"}, Arrays.asList(
				Arrays.asList(new String[] {"JFK","SFO"}),
				Arrays.asList(new String[] {"JFK","ATL"}),
				Arrays.asList(new String[] {"SFO","ATL"}),
				Arrays.asList(new String[] {"ATL","JFK"}),
				Arrays.asList(new String[] {"ATL","SFO"}))};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2() };
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public List<String> apply(List<List<String>> t) {
			Map<String, PriorityQueue<String>> graph = graph(t);
			List<String> res = new LinkedList<>();
			dfs(graph, "JFK", res);
			return res;
		}
		void dfs(Map<String, PriorityQueue<String>> graph, String v, List<String> res) {
			PriorityQueue<String> pq = graph.get(v);
			while (pq != null && !pq.isEmpty()) {
				dfs(graph, pq.remove(), res);
			}
			res.add(0, v);
		}
		Map<String, PriorityQueue<String>> graph(List<List<String>> t) {
			Map<String, PriorityQueue<String>> graph = new HashMap<>();
			for (List<String> l : t) {
				PriorityQueue<String> pq = graph.computeIfAbsent(l.get(0), (k) -> new PriorityQueue<>());
				pq.add(l.get(1));
			}
			return graph;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public List<String> apply(List<List<String>> t) {
			if (t == null) return null;
			Map<String, Map<String, AtomicInteger>> graph = graph(t);
			String[] buffer = new String[t.size() + 1];
			buffer[0] = "JFK";
			return dfs(graph, buffer, 0);
		}
		List<String> dfs(Map<String, Map<String, AtomicInteger>> graph, String[] buffer, int index) {
			if (buffer.length - 1 == index) {
				return Arrays.asList(buffer);
			}
			String v = buffer[index];
			Map<String, AtomicInteger> edges = graph.get(v);
			if (edges == null) return null;
			for (Map.Entry<String, AtomicInteger> entry : edges.entrySet()) {
				if (entry.getValue().get() == 0) continue;
				buffer[index + 1] = entry.getKey();
				entry.getValue().decrementAndGet();
				List<String> res = dfs(graph, buffer, index + 1);
				if (res != null) return res;
				entry.getValue().incrementAndGet();
			}
			return null;
		}
		Map<String, Map<String, AtomicInteger>> graph(List<List<String>> t) {
			Map<String, Map<String, AtomicInteger>> edges = new HashMap<>();
			for (List<String> list : t) {
				Map<String, AtomicInteger> map = edges.computeIfAbsent(list.get(0), (k) -> new TreeMap<>());
				AtomicInteger count = map.computeIfAbsent(list.get(1), (k) -> new AtomicInteger());
				count.incrementAndGet();
			}
			return edges;
		}
	}
	interface Problem extends Function<List<List<String>>, List<String>> {
		
	}
}
