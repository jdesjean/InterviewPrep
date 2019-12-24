package org.ip.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// EPI 2018: 18.7
public class ProductionSequence2 {
	public static void main(String[] s) {
		Set<String> dict = new HashSet<String>();
		String[] strings = new String[] {"bat", "cot", "dog", "dag", "dot", "cat"};
		for (String w : strings) {
			dict.add(w);
		}
		System.out.println(new ProductionSequence2().solve(dict, "cat", "dog"));
	}
	public Graph<String> buildGraph(Set<String> dict) {
		Graph<String> g = new Graph<String>();
		Set<String> visited =  new  HashSet<String>();
		for (String s : dict) {
			if (visited.contains(s)) continue;
			visited.add(s);
			char[] c = s.toCharArray();
			for (int i = 0; i < c.length; i++) {
				for (int j = 0; j < 26; j++) {
					c[i] = (char)('a' + j);
					String tmp = new String(c);
					if (dict.contains(tmp)) {
						g.addEdge(s, tmp);
					}
				}
				c[i] = s.charAt(i);
			}
		}
		return g;
	}
	public List<String> solve(Set<String> dict, String a, String b) {
		Graph<String> g = buildGraph(dict);
		List<String> sequence = new ArrayList<String>();
		Set<String> visited = new HashSet<String>();
		_solve(g, visited, sequence, a, b, 0);
		return sequence;
	}
	private boolean _solve(Graph<String> g, Set<String> visited, List<String> sequence, String a, String b, int index) {
		if (visited.contains(a)) return false;
		visited.add(a);
		if (index >= sequence.size()) {
			sequence.add(a);
		} else {
			sequence.set(index, a);
		}
		if (a.compareTo(b) == 0) {
			return true;
		}
		int next = index + 1;
		for (String e : g.getEdges(a)) {
			boolean f = _solve(g, visited, sequence, e, b, next);
			if (f) return f;
		}
		return false;
	}
}
