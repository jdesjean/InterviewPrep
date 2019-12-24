package org.ip.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.ip.graph.Deadlock.Graph;

// EPI 2018: 18.5
public class Clone2 {
	public static void main(String[] s) {
		Graph<Integer> g = Deadlock.cycle();
		System.out.println(g.toString());
		Integer test = 1;
		System.out.println(new Clone2().clone(g).toString());
	}
	public Graph<Integer> clone(Graph<Integer> g) {
		Graph<Integer> clone = new Graph<Integer>();
		Map<Integer,Integer> originalToClone = new HashMap<Integer,Integer>();
		Map<Integer, State> states = new HashMap<Integer, State>();
		for (Integer v : g.map.keySet()) {
			dfs(g, clone, originalToClone, states, v);
		}
		return clone;
	}
	public void dfs(Graph<Integer> g, Graph<Integer> clone, Map<Integer,Integer> originalToClone, Map<Integer, State> states, Integer v) {
		State state = states.get(v);
		if (state == State.VISITING || state == State.VISITED) {
			return;
		}
		states.put(v, State.VISITING);
		Integer cv = originalToClone.computeIfAbsent(v, vv -> new Integer(vv));
		for (Integer e : g.getEdges(v)) {
			Integer ce = originalToClone.computeIfAbsent(e, ee -> new Integer(ee) );
			clone.addEdge(cv, ce);
			dfs(g, clone, originalToClone, states, e);
		}
		states.put(v, State.VISITED);
	}
	public enum State {
		NONE, VISITED, VISITING
	}
}
