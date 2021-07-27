package org.ip.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * <a href="https://leetcode.com/problems/group-anagrams/">LC: 49</a>
 */
public class GroupAnagram {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				GroupAnagram::tc1,
				GroupAnagram::tc2,
				GroupAnagram::tc3);
		Solver[] solvers = new Solver[] {new TrieSolver(), new HashSolver()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		//Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
		System.out.println(solver.solve(new String[] {"eat","tea","tan","ate","nat","bat"}));
	}
	public static void tc2(Solver solver) {
		System.out.println(solver.solve(new String[] {""}));
	}
	public static void tc3(Solver solver) {
		System.out.println(solver.solve(new String[] {"a"}));
	}
	/*
	 * Solutions
	 * 1) Sort letters within a word nklog(k) + create hash O(n).
	 * 2) Sort letters within a word nklog(k) + create a trie O(kn).
	 * 3) Frequency of letters O(n). Compare frequencies O(n^2) 
	 * 
	 */
	public static class HashSolver implements Solver {

		@Override
		public List<List<String>> solve(String[] strs) {
			Map<String, List<String>> map = new HashMap<>();
			for (String str : strs) {
				char[] copy = str.toCharArray();
				Arrays.sort(copy);
				List<String> list = map.computeIfAbsent(new String(copy), k -> new ArrayList<>());
				list.add(str);
			}
			return map.values().stream().collect(Collectors.toList());
		}
		
	}
	public static class TrieSolver implements Solver {

		@Override
		public List<List<String>> solve(String[] strs) {
			Trie trie = new Trie();
			for (String str : strs) {
				char[] copy = str.toCharArray();
				Arrays.sort(copy);
				trie.add(new String(copy), str);
			}
			List<List<String>> res = new ArrayList<>();
			trie.forEachLeaf(node -> {
				res.add(node.values);
			});
			return res;
		}
	}
	public static class Trie {
		Node root = new Node();
		public void add(String str, String orig) {
			if (str == null) return;
			Node parent = root;
			for (int i = 0; i < str.length(); i++) {
				parent = parent.child.computeIfAbsent(Character.valueOf(str.charAt(i)), k -> new Node());
			}
			parent.values.add(orig);
		}
		public void forEachLeaf(Consumer<Node> f) {
			forEachLeaf(root, f);
		}
		void forEachLeaf(Node node, Consumer<Node> f) {
			if (node == null) return;
			if (!node.values.isEmpty()) {
				f.accept(node);
			}
			for (Node child : node.child.values()) {
				forEachLeaf(child, f);
			}
		}
	}
	public static class Node {
		Map<Character, Node> child = new HashMap<>();
		List<String> values = new ArrayList<>();
	}
	public interface Solver {
		public List<List<String>> solve(String[] strs);
	}
}
