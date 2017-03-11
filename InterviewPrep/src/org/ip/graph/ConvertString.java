package org.ip.graph;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class ConvertString {
	public static void main(String[] s) {
		Set<String> dictionary = new HashSet<String>();
		dictionary.add("cat");
		dictionary.add("bat");
		dictionary.add("hat");
		dictionary.add("bad");
		dictionary.add("had");
		Map<String,Vertex<String>> map = new HashMap<String,Vertex<String>>();
		for (String word : dictionary) {
			map.put(word, new Vertex<String>(word));
		}
		Graph<String> graph = convert("bat","had",dictionary, map);
		String current = "had";
		while (current != null){
			System.out.println(current);
			Set<Vertex<String>> set = graph.map.get(map.get(current));
			if (set == null || set.isEmpty()) break;
			for (Iterator<Vertex<String>> iterator = set.iterator(); iterator.hasNext();) {
				current = ((Vertex<String>)iterator.next()).val;
				break;
			}
		}
	}
	public static final String EMPTY = "";
	public static Graph<String> convert(String s1, String s2, Set<String> dictionary, Map<String,Vertex<String>> map) {
		Graph<String> graph = new Graph<String>();
		Deque<String> queue = new LinkedList<String>();
		Set<String> visited = new HashSet<String>();
		queue.add(s1);
		visited.add(s1);
		while (!queue.isEmpty()) {
			String current = queue.removeFirst();
			//neighbors
			char[] buffer = current.toCharArray();
			Vertex<String> currentVertex = map.get(current);
			for (int i = 0; i < current.length(); i++) {
				for (int j = 0; j < 26; j++) {
					buffer[i] = (char)(j + 'a');
					String neighbor = String.copyValueOf(buffer);
					if (visited.contains(neighbor) || !dictionary.contains(neighbor)) continue;
					queue.addLast(neighbor);
					visited.add(neighbor);
					graph.addEdge(map.get(neighbor), currentVertex);
					if (neighbor.compareTo(s2) == 0) return graph;
				}
				buffer[i] = current.charAt(i);
			}
		}
		return graph;
	}
}
