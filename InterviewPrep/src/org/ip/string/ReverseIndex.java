package org.ip.string;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ReverseIndex {
	public static class ReverseIndexMap {
		private final Map<String, List<Integer>> map = new HashMap<String,List<Integer>>();
		public ReverseIndexMap(String[] words) {
			for (int i = 0; i < words.length; i++) {
				List<Integer> list = map.get(words[i]);
				if (list == null) {
					list = new LinkedList<Integer>();
					map.put(words[i], list);
				}
				list.add(i);
			}
		}
		public List<Integer> get(String s) {
			return map.get(s);
		}
	}
	public static class ReverseIndexTrie {
		private static class Node {
			public Node[] childs = new Node[26];
			public List<Integer> ends = new LinkedList<Integer>();
		}
		private static final Node ROOT = new Node();
		public ReverseIndexTrie(String[] s) {
			for (int i = 0; i < s.length; i++) {
				add(s[i],i);
			}
		}
		private void add(String s, int position) {
			if (s == null || s.length() == 0) return;
			Node current = ROOT;
			for (int i = 0; i < s.length(); i++) {
				int index = s.charAt(i) - 'a';
				if (current.childs[index] == null) {
					current.childs[index] = new Node();
				}
				current = current.childs[index]; 
			}
			current.ends.add(position);
		}
		private Node getNode(String s) {
			Node current = ROOT;
			for (int i = 0; i < s.length(); i++) {
				int index = s.charAt(i) - 'a';
				if (current.childs[index] == null) return null;
				current = current.childs[index];
			}
			return current;
		}
		public List<Integer> get(String s) {
			Node node = getNode(s);
			return node != null ? node.ends : null;
		}
	}
	public static void main(String args[]) throws Exception {
		ReverseIndexMap indexMap = new ReverseIndexMap(new String[] { "baa", "abcd", "abca", "cab", "cad", "cab" });
		ReverseIndexTrie indexTrie = new ReverseIndexTrie(new String[] { "baa", "abcd", "abca", "cab", "cad", "cab"});
		System.out.println(indexMap.get("cab"));
		System.out.println(indexTrie.get("cab"));

	}
}
