package org.ip.string;

public class Trie {
	public static class Node {
		public Node[] childs = new Node[26];
		public String end;
	}
	private static final Node ROOT = new Node();
	public Trie(String[] s) {
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
		current.end = s;
	}
	public Node getNode(char c) {
		return getNode(c,ROOT);
	}
	public Node getNode(char c, Node current) {
		int index = c - 'a';
		return current.childs[index]; 
	}
	public Node getNode(String s) {
		return getNode(s,ROOT);
	}
	public Node getNode(String s, Node current) {
		for (int i = 0; i < s.length(); i++) {
			int index = s.charAt(i) - 'a';
			if (current.childs[index] == null) return null;
			current = current.childs[index];
		}
		return current;
	}
	public boolean hasWord(String s) {
		Node node = getNode(s);
		return node != null ? node.end != null : false; 
	}
}
