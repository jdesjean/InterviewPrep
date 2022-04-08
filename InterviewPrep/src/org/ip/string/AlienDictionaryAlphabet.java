package org.ip.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/alien-dictionary/">LC: 269</a>
 */
public class AlienDictionaryAlphabet {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {"wertf", new String[] {"wrt","wrf","er","ett","rftt"}};
		Object[] tc2 = new Object[] {"zx", new String[] {"z","x"}};
		Object[] tc3 = new Object[] {"", new String[] {"z","x","z"}};
		Object[] tc4 = new Object[] {"z", new String[] {"z", "z"}};
		Object[] tc5 = new Object[] {"z", new String[] {"z"}};
		Object[] tc6 = new Object[] {"", new String[] {"abc", "ab"}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5, tc6};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	public static class Solver2 implements Problem {

		@Override
		public String apply(String[] t) {
			if (t.length == 0) return "";
			Map<Character, Set<Character>> g = new HashMap<>();
			for (int i = 0; i < t.length; i++) {
				for (int j = 0; j < t[i].length(); j++) {
					g.computeIfAbsent(t[i].charAt(j), (k) -> new HashSet<>());
				}
			}
			for (int i = 1; i < t.length; i++) {
				int l1 = t[i - 1].length();
				int l2 = t[i].length();
				int j = 0;
				for (; j < Math.min(l1, l2); j++) {
					if (t[i - 1].charAt(j) == t[i].charAt(j)) continue;
					g.get(t[i].charAt(j)).add(t[i - 1].charAt(j));
					break;
				}
				if (l1 > l2 && j == l2) { // [aa, a] is invalid. Add cycle to make invalid during topoSort
					for (j = 0; j < l1; j++) {
						g.get(t[i - 1].charAt(j)).add(t[i - 1].charAt(j));
					}
				}
			}
			StringBuilder sb = new StringBuilder();
			Map<Character, State> visited = new HashMap<>();
			for (Character key : g.keySet()) {
				topoSort(sb, g, visited, key);
			}
			return sb.toString();
		}
		boolean topoSort(StringBuilder sb, Map<Character, Set<Character>> g, Map<Character, State> visited, Character current) {
			State state = visited.get(current);
			if (state != null) {
				return state == State.VISITING;
			}
			visited.put(current, State.VISITING);
			Set<Character> edges = g.get(current);
			if (edges != null) {
				for (Character edge : edges) {
					if (topoSort(sb, g, visited, edge)) return true;
				}
			}
			visited.put(current, State.VISITED);
			sb.append(current);
			return false;
		}
		enum State {
			VISITING, VISITED;
		}
	}
	public static class Solver implements Problem {

		@Override
		public String apply(String[] t) {
			Graph g = new Graph();
			for (int i = 0; i < t.length; i++) {
				for (int j = 0; j < t[i].length(); j++) {
					g.put(t[i].charAt(j), null);
				}
				if (i == 0) continue;
				for (int j = 0; j < t[i].length() && j < t[i - 1].length(); j++) {
					if (t[i].charAt(j) == t[i - 1].charAt(j)) {
						if (j == t[i].length() - 1 && t[i].length() < t[i - 1].length()) { // [aa, a] Invalid ordering not caught via cycle
							return "";
						}
						continue;
					}
					g.put(t[i - 1].charAt(j), t[i].charAt(j));
					break;
				}
			}
			StringBuilder sb = new StringBuilder();
			if (g.postOrder(x -> sb.append(x))) return "";
			return sb.reverse().toString();
		}
	}
	public static class Graph {
		Map<Character, Set<Character>> map = new HashMap<>();
		public void put(Character v1, Character v2) {
			Set<Character> set = map.computeIfAbsent(v1, (k) -> new HashSet<>());
			if (v2 != null) {
				set.add(v2);
			}
		}
		/**
		 * // Post order i.e. topoSort
		 * @param consumer
		 * @return boolean true if has cycle
		 */
		public boolean postOrder(Consumer<Character> consumer) {
			Map<Character, Status> status = new HashMap<>();
			for (Character v : map.keySet()) {
				if (_postOrder(consumer, v, status)) {
					return true;
				}
			}
			return false;
		}
		boolean _postOrder(Consumer<Character> consumer, Character v1, Map<Character, Status> status) {
			Status current = status.get(v1);
			if (current == Status.VISITED) {
				return false;
			} else if (current == Status.VISITING)  {
				return true;
			}
			status.put(v1, Status.VISITING);
			Set<Character> set = map.get(v1);
			if (set != null) {
				for (Character v2 : set) {
					if (_postOrder(consumer, v2, status)) {
						return true;
					}
				}
			}
			status.put(v1, Status.VISITED);
			consumer.accept(v1);
			return false;
		}
		private enum Status {
			NONE, VISITING, VISITED
		}
	}
	interface Problem extends Function<String[], String> {
		
	}
}
