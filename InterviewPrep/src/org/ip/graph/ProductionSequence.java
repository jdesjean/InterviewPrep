package org.ip.graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// EPI: 19.7
public class ProductionSequence {
	public static void main(String[] s) {
		Set<String> set = new HashSet<String>();
		set.add("bat");
		set.add("cot");
		set.add("dog");
		set.add("dag");
		set.add("dot");
		set.add("cat");
		System.out.println(solve(new Graph(set), "cat", "dog"));
	}
	public static List<String> solve(Graph g, String w1, String w2) {
		String[] buffer = new String[g.set.size()];
		buffer[0] = w1;
		return solve(g, null, w1, w2, buffer, 1);
	}
	public static List<String> solve(Graph g, String parent, String w1, String w2, String[] buffer, int i) {
		if (w2.equals(w1)) {
			if (i != buffer.length) {
				return Arrays.asList(Arrays.copyOfRange(buffer, 0, i));
			} else {
				return Arrays.asList(buffer);
			}
		}
		for (String e: g.getEdges(w1)) {
			if (parent != null && parent.equals(e)) continue;
			buffer[i] = e;
			List<String> result = solve(g, w1, e, w2, buffer, i + 1);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	public static class Graph {
		public Set<String> set;
		public Graph(Set<String> set) {
			this.set=set;
		}
		public Set<String> getEdges(String node) {
			char[] buffer = node.toCharArray();
			Set<String> results = new HashSet<String>();
			for (int i = 0; i < node.length(); i++) {
				for (int j = 0; j < 26; j++) {
					buffer[i] = (char)('a' + j);
					String tmp = new String(buffer);
					if (!tmp.equals(node) && set.contains(tmp)) {
						results.add(tmp);
					}
				}
				buffer[i] = node.charAt(i);
			}
			return results;
		}
	}
}
