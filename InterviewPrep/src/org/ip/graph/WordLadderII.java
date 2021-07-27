package org.ip.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/word-ladder/">LC: 127</a>
 */
public class WordLadderII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { Arrays.asList(Arrays.asList("hit","hot","dot","dog","cog"), Arrays.asList("hit","hot","lot","log","cog")), "hit", "cog", Arrays.asList("hot","dot","dog","lot","log","cog")};
		Object[] tc2 = new Object[] { Arrays.asList(""), "hit", "cog", Arrays.asList("hot","dot","dog","lot","log")};
		Object[][] tcs = new Object[][] { tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		private final static String SENTINEL = "";
		@Override
		public List<List<String>> apply(String s1, String s2, List<String> words) {
			Set<String> dictionary = new HashSet<>();
			dictionary.addAll(words);
			AdjacencyList adjacency = convert(s1, s2, dictionary);
			List<List<String>> res = new ArrayList<>();
			Deque<String> buffer = new LinkedList<>();
			buffer.add(s2);
			visit(buf -> res.add(new ArrayList<>(buffer)), adjacency, s2, buffer, s1);
			return res;
		}
		void visit(Consumer<Deque<String>> consumer, AdjacencyList adjacency, String current, Deque<String> buffer, String s1) {
			if (current.equals(s1)) {
				consumer.accept(buffer);
				return;
			}
			if (!adjacency.map.containsKey(current)) return;
			for (String neighbor : adjacency.map.get(current)) {
				buffer.addFirst(neighbor);
				visit(consumer, adjacency, neighbor, buffer, s1);
				buffer.removeFirst();
			}
		}
		
		public static AdjacencyList convert(String s1, String s2, Set<String> dictionary) {
			AdjacencyList graph = new AdjacencyList();
			Deque<String> queue = new LinkedList<String>();
			Set<String> visited = new HashSet<String>();
			queue.add(s1);
			queue.add(SENTINEL);
			Set<String> level = new HashSet<>();
			while (!queue.isEmpty()) {
				String current = queue.removeFirst();
				if (current == SENTINEL) {
					if (!queue.isEmpty()) {
						queue.add(SENTINEL);
					}
					level.clear();
					continue;
				}
				if (visited.contains(current)) continue;
				visited.add(current);
				if (current.compareTo(s2) == 0) return graph;
				//neighbors
				char[] buffer = current.toCharArray();
				for (int i = 0; i < current.length(); i++) {
					for (int j = 0; j < 26; j++) {
						char c = (char)(j + 'a');
						if (c == current.charAt(i)) continue;
						buffer[i] = c;
						String neighbor = String.copyValueOf(buffer);
						if (visited.contains(neighbor) || !dictionary.contains(neighbor)) continue;
						queue.addLast(neighbor);
						// 
						/* Multiple edges can only be added within the same level to be minimal
						 * i.e. If there exists a relationship between 1,3 & 1,2
						 * 3,2 should not be added
						 * 1
						 * | \
						 * 2  |  
						 *  \ |
						 *   3
						 * If there exists a relationship between 1,2
						 * 1,3 can be added
						 * 1
						 * | \
						 * 2  3  
						 *   
						 */
						if (!graph.map.containsKey(neighbor) || level.contains(neighbor)) {
							graph.addEdge(neighbor, current);
							level.add(neighbor);
						}
					}
					buffer[i] = current.charAt(i);
				}
			}
			return graph;
		}
		
	}
	static class AdjacencyList {
		Map<String, Set<String>> map = new HashMap<>();
		public void addEdge(String node1, String node2) {
			Set<String> set = map.computeIfAbsent(node1, k -> new HashSet<>());
			set.add(node2);
		}
	}
	interface Problem extends TriFunction<String, String, List<String>, List<List<String>>> {
		
	}
}
