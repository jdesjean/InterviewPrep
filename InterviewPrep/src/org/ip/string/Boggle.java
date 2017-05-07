package org.ip.string;

import java.util.ArrayList;
import java.util.List;

import org.ip.string.Trie.Node;
import org.ip.string.Visitors.StringVisitor;

public class Boggle {
	public static void main(String[] s) {
		new PreOrder().
		solve(new String[]{"geeks", "for", "quiz", "go"}, new char[][]{
			{'g','i','z'},
			{'u','e','k'},
			{'q','s','e'}
		}, new StringVisitor(){
			@Override
			public void visit(String string) {
				System.out.println(string);
			}
		});
		new DP().
		solve(new String[]{"geeks", "for", "quiz", "go"}, new char[][]{
			{'g','i','z'},
			{'u','e','k'},
			{'q','s','e'}
		}, new StringVisitor(){
			@Override
			public void visit(String string) {
				System.out.println(string);
			}
		});
	}
	public static String[] filter(String[] s, char[][] board) {
		List<String> list = new ArrayList<String>();
		int[] bucket = new int[26];
		int max = board.length*board[0].length;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				bucket[board[i][j] - 'a']++;
			}
		}
		for (int i = 0; i < s.length; i++) {
			boolean add = true;
			if (s[i].length() > max) continue;
			for (int j = 0; j < s[i].length(); j++) {
				if (bucket[s[i].charAt(j) - 'a'] == 0) {
					add = false;
					break;
				}
			}
			if (add) list.add(s[i]);
		}
		return list.toArray(new String[list.size()]);
	}
	public static class PreOrder {
		public void solve(String[] s, char[][] board, StringVisitor visitor) {
			Trie trie = new Trie(filter(s,board));
			int size = board.length*board[0].length;
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					Node node = trie.getNode(board[i][j]);
					if (node == null) continue;
					boolean[][] visited = new boolean[board.length][board[0].length];
					visited[i][j] = true;
					solve(trie,node,board,visited,visitor,i,j,size-1);
				}
			}
		}
		public void solve(Trie trie, Node node, char[][] board, boolean[][] visited, StringVisitor visitor, int x, int y, int size) {
			if (size <= 0) return;
			if (node.end != null) {
				visitor.visit(node.end);
			}
			for (int i = Math.max(x-1, 0); i <= Math.min(x+1, board.length-1); i++) {
				for (int j = Math.max(y-1, 0); j <= Math.min(y+1, board[0].length-1); j++) {
					Node current;
					if (visited[i][j] || (current = trie.getNode(board[i][j], node)) == null) continue;
					visited[i][j] = true;
					solve(trie,current,board,visited,visitor,i,j,size-1);
				}
			}
		}
	}
	public static class DP {
		public static int max(String[] s) {
			int max = 0;
			for (int i = 0; i < s.length; i++) {
				Math.max(max, s[i].length());
			}
			return max;
		}
		public void solve(String[] s, char[][] board, StringVisitor visitor) {
			s = filter(s,board);
			for (int i = 0 ; i < s.length; i++) {
				if (found(s[i],board)) visitor.visit(s[i]);
			}
		}
		public boolean found(String s, char[][] board) {
			boolean[][][] cache = new boolean[s.length()][board.length][board[0].length];
			for (int k = 0; k < s.length(); k++) {
				boolean found = false;
				for (int i = 0; i < board.length; i++) {
					for (int j = 0; j < board[0].length; j++) {
						if (s.charAt(k) != board[i][j]) continue;
						if (k == 0) {
							found = true;
						} else {
							for (int x = Math.max(i-1, 0); x <= Math.min(i+1, board.length-1); x++) {
								for (int y = Math.max(j-1, 0); y <= Math.min(j+1, board[0].length-1); y++) {
									if (cache[k-1][x][y]) {
										found = true;
										break;
									}
								}
							}
						}
						if (found == true) cache[k][i][j] = true;
					}
				}
				if (found == false) return found;
			}
			return true;
		}
	}
}
