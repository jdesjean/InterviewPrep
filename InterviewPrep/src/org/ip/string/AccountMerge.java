package org.ip.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/accounts-merge/">LC: 721</a>
 */
public class AccountMerge {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {Arrays.asList(
				Arrays.asList(new String[] {"John", "john00@mail.com", "john_newyork@mail.com", "johnsmith@mail.com"}),
				Arrays.asList(new String[] {"John", "johnnybravo@mail.com"}),
				Arrays.asList(new String[] {"Mary", "mary@mail.com"})), Arrays.asList(
				Arrays.asList(new String[] {"John", "johnsmith@mail.com", "john00@mail.com"}), 
				Arrays.asList(new String[] {"John", "johnnybravo@mail.com"}), 
				Arrays.asList(new String[] {"John", "johnsmith@mail.com", "john_newyork@mail.com"}),
				Arrays.asList(new String[] {"Mary", "mary@mail.com"}))};
		Object[][] tcs = new Object[][] {tc1};
		Problem[] solvers = new Problem[] {new Solver(), new UnionFind(), new Solver2(), new UnionFind2()};
		Test.apply(solvers, tcs);
	}
	static class UnionFind2 implements Problem {

		@Override
		public List<List<String>> apply(List<List<String>> t) {
			UnionFind unionFind = new UnionFind(t);
			Map<String, String> names = new HashMap<>();
			for (int i = 0; i < t.size(); i++) {
				String name = t.get(i).get(0);
				String email1 = t.get(i).get(1);
				names.put(email1, name);
				for (int j = 2; j < t.get(i).size(); j++) {
					String email2 = t.get(i).get(j);
					unionFind.union(email1, email2);
					names.put(email2, name);
				}
			}
			
			Map<String, List<String>> parentToEmails = new HashMap<>();
			for (String email : unionFind.parents.keySet()) {
				String pa = unionFind.find(email);
				List<String> emails = parentToEmails.computeIfAbsent(pa, (k) -> {
					List<String> list = new ArrayList<>();
					list.add(names.get(email)); // add name
					return list;
				});
				emails.add(email);
			}
			List<List<String>> res = new ArrayList<>(parentToEmails.size());
			for (List<String> emails : parentToEmails.values()) {
				Collections.sort(emails.subList(1, emails.size())); // first element is name
				res.add(emails);
			}
			return res;
		}
		static class UnionFind {
			Map<String, String> parents = new HashMap<>();
			public UnionFind(List<List<String>> t) {
				for (int i = 0; i < t.size(); i++) {
					for (int j = 1; j < t.get(i).size(); j++) {
						String email2 = t.get(i).get(j);
						parents.put(email2, email2);
					}
				}
			}
			public String find(String a) {
				if (parents.get(a) != a) {
					return parents.compute(a, (k, v) -> find(parents.get(a)));
				}
				return parents.get(a);
			}
			public void union(String a, String b) {
				String pa = find(a);
				String pb = find(b);
				parents.put(pa, pb);
			}
		}
	}
	
	public static class UnionFind implements Problem {

		@Override
		public List<List<String>> apply(List<List<String>> t) {
			Map<String, String> names = new HashMap<>();
			Map<String, Integer> emails = new HashMap<>();
			AtomicInteger ids = new AtomicInteger();
			DSU dsu = new DSU(10001);
			for (List<String> list : t) {
				int j = 0;
				String name = list.get(0);
				String email1 = list.get(1);
				for (String email : list) {
					if (j++ == 0) continue;
					names.put(email, name);
					Integer id = emails.computeIfAbsent(email, k -> ids.getAndIncrement());
					dsu.union(emails.get(email1), id);
				}
			}
			Map<Integer, Set<String>> map = new HashMap<>();
			for (List<String> list : t) {
				int j = 0;
				for (String email : list) {
					if (j++ == 0) continue;
					int id = emails.get(email);
					int parent = dsu.find(id);
					Set<String> set = map.computeIfAbsent(parent, k -> new TreeSet<>());
					set.add(email);
				}
			}
			List<List<String>> res = new ArrayList<>();
			for (Map.Entry<Integer, Set<String>> entry : map.entrySet()) {
				List<String> list = new ArrayList<>();
				list.add(names.get(entry.getValue().iterator().next()));
				list.addAll(entry.getValue());
				res.add(list);
			}
			return res;
		}
		public static class DSU {
			private int[] parent;
			public DSU(int size) {
				parent = new int[size];
				for (int i = 0; i <= 10000; i++) {
					parent[i] = i;
				}
			}
			int find(int x) {
				if (parent[x] != x) parent[x] = find(parent[x]);
				return parent[x];
			}
			void union(int x, int y) {
				parent[find(x)] = find(y);
			}
		}
	}
	public static class Solver2 implements Problem {

		@Override
		public List<List<String>> apply(List<List<String>> t) {
			Map<String, Set<String>> map = build(t);
			Map<String, String> names = names(t);
			List<List<String>> list = new ArrayList<>();
			dfs(map, names, list);
			return list;
		}
		
		void dfs(Map<String, Set<String>> map, Map<String, String> names, List<List<String>> res) {
			Set<String> visited = new HashSet<>();
			for (String email : map.keySet()) {
				Set<String> set = new TreeSet<String>();
				if (dfs(map, visited, email, set)) {
					List<String> list = new ArrayList<>();
					String name = names.get(email);
					list.add(name);
					list.addAll(set);
					res.add(list);
				}
			}
		}
		
		boolean dfs(Map<String, Set<String>> map, Set<String> visited, String email, Set<String> res) {
			if (!visited.add(email)) {
				return false;
			}
			res.add(email);
			Set<String> edges = map.get(email);
			if (edges == null) return true;
			for (String email2 : edges) {
				dfs(map, visited, email2, res);
			}
			return true;
		}
		
		Map<String, String> names(List<List<String>> t) {
			Map<String, String> names = new HashMap<>();
			for (List<String> list : t) {
				int j = 0;
				String name = list.get(0);
				for (String string : list) {
					if (j++ == 0) continue;
					names.put(string, name);
				}
			}
			return names;
		}
		
		Map<String, Set<String>> build(List<List<String>> t) {
			Map<String, Set<String>> map = new HashMap<>();
			for (List<String> list : t) {
				int j = 0;
				String first = list.get(1);
				for (String email : list) {
					if (j++ == 0) continue;
					Set<String> set = map.computeIfAbsent(first, k -> new HashSet<>());
					set.add(email);
					set = map.computeIfAbsent(email, k -> new HashSet<>());
					set.add(first);
				}
			}
			return map;
		}
		
	}
	public static class Solver implements Problem {

		@Override
		public List<List<String>> apply(List<List<String>> t) {
			Graph g = build(t);
			List<List<String>> list = new ArrayList<>();
			dfs(g, t, list);
			return list;
		}
		void dfs(Graph g, List<List<String>> t, List<List<String>> res) {
			Set<String> visited = new HashSet<>();
			for (String keys : g.map.keySet()) {
				Set<String> set = new TreeSet<String>();
				if (dfs(visited, g, keys, t, set)) {
					List<String> list = new ArrayList<>();
					String name = t.get(g.map.get(keys).get(0)).get(0);
					list.add(name);
					list.addAll(set);
					res.add(list);
				}
			}
		}
		
		boolean dfs(Set<String> visited, Graph g, String email, List<List<String>> t, Set<String> res) {
			if (!visited.add(email)) {
				return false;
			}
			List<Integer> index = g.map.get(email);
			for (Integer integer : index) {
				List<String> list = t.get(integer);
				int j = 0;
				for (String string : list) {
					if (j++ == 0) continue;
					res.add(string);
					dfs(visited, g, string, t, res);
				}
			}
			return true;
		}
		
		Graph build(List<List<String>> t) {
			Graph g = new Graph();
			int i = 0;
			for (List<String> list : t) {
				int j = 0;
				for (String string : list) {
					if (j++ == 0) continue;
					g.put(string, i);
				}
				i++;
			}
			return g;
		}
		public static class Graph {
			Map<String, List<Integer>> map = new HashMap<>();
			void put(String key, Integer value) {
				List<Integer> list = map.computeIfAbsent(key, k -> new ArrayList<>());
				list.add(value);
			}
		}
	}
	interface Problem extends Function<List<List<String>>, List<List<String>>> {
		
	}
}
