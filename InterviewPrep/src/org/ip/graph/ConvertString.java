package org.ip.graph;

import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class ConvertString {
	public static void main(String[] s) {
		Set<String> dictionary = new HashSet<String>();
		dictionary.add("cat");
		dictionary.add("bat");
		dictionary.add("hat");
		dictionary.add("bad");
		dictionary.add("had");
		AdjacencyList<String> graph = convert("bat","had",dictionary);
		String current = "had";
		while (current != null){
			System.out.println(current);
			Set<String> set = graph.map.get(current);
			if (set == null || set.isEmpty()) break;
			for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
				current = iterator.next();
				break;
			}
		}
	}
	public static final String EMPTY = "";
	public static AdjacencyList<String> convert(String s1, String s2, Set<String> dictionary) {
		AdjacencyList<String> graph = new AdjacencyList<String>();
		Deque<String> queue = new LinkedList<String>();
		Set<String> visited = new HashSet<String>();
		queue.add(s1);
		visited.add(s1);
		while (!queue.isEmpty()) {
			String current = queue.removeFirst();
			//neighbors
			char[] buffer = current.toCharArray();
			for (int i = 0; i < current.length(); i++) {
				for (int j = 0; j < 26; j++) {
					buffer[i] = (char)(j + 'a');
					String neighbor = String.copyValueOf(buffer);
					if (visited.contains(neighbor) || !dictionary.contains(neighbor)) continue;
					queue.addLast(neighbor);
					visited.add(neighbor);
					graph.addEdge(neighbor, current);
					if (neighbor.compareTo(s2) == 0) return graph;
				}
				buffer[i] = current.charAt(i);
			}
		}
		return graph;
	}
}
