package org.ip;

import java.util.Iterator;
import java.util.Set;

// Google 10/03/19
// Find usernames in Iterator<String> use trie
public class Find {
	public static void main(String[] s) {
		
	}
	public void find(Visitor visitor, Trie trie, Iterator<String> it) {
		while (it.hasNext()) {
			String string = it.next();
			for (int i = 0; i < string.length(); i++) {
				Node current = trie.root;
				for (int j = i; j < string.length(); j++) {
					Node next = trie.find(current, string.charAt(j));
					if (next == null) break;
					if (!next.isEnd()) continue;
					visitor.visit(string, i, j);
				}
			}
		}
	}
	public static class Trie {
		Node root;
		public Node find(Node h, char c) {
			return null;
		}
	}
	public static class Node {
		public boolean isEnd() {
			return true;
		}
		public Set<Node> children;
	}
	public static class Visitor {
		public void visit(String string, int i, int j) {
			System.out.println(string.substring(i, j));
		}
	}
}
