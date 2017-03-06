package org.ip.graph;

import java.util.Deque;

public class AlphabetOrder {
	public static void main(String[] s) {
		System.out.println(solve(new String[]{"baa", "abcd", "abca", "cab", "cad"}));
		System.out.println(solve(new String[]{"abc","v","dlk"}));
		System.out.println(solve(new int[][]{{5,0},{5,2},{4,0},{4,1},{2,3},{3,1}},6));
		System.out.println(solve(new int[][]{{5,1},{2,1},{4,2},{3,2},{6,3},{9,3},{7,2}},10));
	}
	public static String solve(int[][] g, int max) {
		Graph graph = new Graph();
		Vertex[] vertex = new Vertex[max];
		for (int i = 0; i < max; i++) {
			vertex[i] = new Vertex(i);
		}
		for (int i = 0; i < g.length; i++) {
			graph.addEdge(vertex[g[i][0]], vertex[g[i][1]]);
		}
		Deque<Vertex> stack = graph.topoSort();
		StringBuffer sb = new StringBuffer();
		while (!stack.isEmpty()) {
			sb.append(String.valueOf(stack.pop().val));
		}
		return sb.toString();
	}
	public static String solve(String[] s) {
		Graph graph = new Graph();
		Vertex[] vertex = new Vertex[26];
		for (int i = 0; i < vertex.length; i++) {
			vertex[i] = new Vertex('a' + i);
		}
		for (int j = 1; j < s.length; j++) {
			int min = Math.min(s[j].length(), s[j-1].length()); 
			for (int i = 0; i < min; i++) {
				if (s[j].charAt(i) == s[j-1].charAt(i)) continue;
				Vertex v1 = vertex[s[j-1].charAt(i) - 'a'];
				Vertex v2 = vertex[s[j].charAt(i) - 'a'];
				graph.addEdge(v1, v2);
				break;
			}
		}
		Deque<Vertex> stack = graph.topoSort();
		StringBuffer sb = new StringBuffer();

		while (!stack.isEmpty()) {
			int val = stack.pop().val;
			sb.append((char)(val));
		}
		return sb.toString();
	}
	public static boolean equal(String s1, String s2, int i) {
		for (int j = 0; j <= i; j++) {
			if (s1.charAt(j) != s2.charAt(j)) return false;
		}
		return true;
	}
	public static int max(String[] s) {
		int max = 0;
		for (int i = 0; i < s.length; i++) {
			max = Math.max(s[i].length(), max);
		}
		return max;
	}
}
