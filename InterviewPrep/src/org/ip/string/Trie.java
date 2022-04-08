package org.ip.string;

import java.util.List;

public class Trie {
	public static class Node {
		public Node[] childs = new Node[26]; //lower case letters + digits
		public String end;
	}
	private Node root = new Node();
	public Trie(String[] s) {
		for (int i = 0; i < s.length; i++) {
			add(s[i],i);
		}
	}
	public Trie(List<String> s) {
		for (int i = 0; i < s.size(); i++) {
			add(s.get(i),i);
		}
	}
	private void add(String s, int position) {
		if (s == null || s.length() == 0) return;
		Node current = root;
		for (int i = 0; i < s.length(); i++) {
			int index = index(s.charAt(i)); 
			if (current.childs[index] == null) {
				current.childs[index] = new Node();
			}
			current = current.childs[index]; 
		}
		current.end = s;
	}
	int index(char c) {
		return c - 'a';
	}
	public Node getNode(char c) {
		return getNode(c,root);
	}
	public Node getNode(char c, Node current) {
		int index = index(c);
		return current.childs[index]; 
	}
	public Node getNode(String s) {
		return getNode(s,root);
	}
	public Node getNode(String s, Node current) {
		for (int i = 0; i < s.length(); i++) {
			int index = index(s.charAt(i));
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
