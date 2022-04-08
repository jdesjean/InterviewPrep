package org.ip.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/synonymous-sentences/">LC: 1258</a>
 */
public class SynonymousSentences {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {Arrays.asList(new String[] {"I am cheerful today but was sad yesterday","I am cheerful today but was sorrow yesterday","I am happy today but was sad yesterday","I am happy today but was sorrow yesterday","I am joy today but was sad yesterday","I am joy today but was sorrow yesterday"}), Arrays.asList(Arrays.asList(new String[]{"happy","joy"}),Arrays.asList(new String[] {"sad","sorrow"}),Arrays.asList(new String[] {"joy","cheerful"})), "I am happy today but was sad yesterday"};
		Object[][] tcs = new Object[][] {tc1};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public List<String> apply(List<List<String>> t, String u) {
			UnionFind dsu = new UnionFind();
			for (List<String> synonyms : t) {
				dsu.setParent(synonyms.get(0));
				for (int i = 1; i < synonyms.size(); i++) {
					dsu.setParent(synonyms.get(i));
					dsu.union(synonyms.get(0), synonyms.get(i));
				}
			}
			Map<String, List<String>> sorted = new HashMap<>();
			for (String word : dsu.map.keySet()) {
				String parent = dsu.find(word);
				sorted.computeIfAbsent(parent, (k) -> new ArrayList<>()).add(word);
			}
			for (Map.Entry<String, List<String>> toSort : sorted.entrySet()) {
				Collections.sort(toSort.getValue());
			}
			List<String> res = new ArrayList<>();
			String[] buffer = u.split(" ");
			perms(res, dsu, sorted, buffer, 0);
			return res;
		}
		void perms(List<String> res, UnionFind dsu, Map<String, List<String>> sorted, String[] u, int i) {
			if (i == u.length) {
				res.add(String.join(" ", u));
				return;
			}
			String parent = dsu.find(u[i]);
			if (parent == null) {
				perms(res, dsu, sorted, u, i + 1);
				return;
			}
			for (String synonyms : sorted.get(parent)) {
				u[i] = synonyms;
				perms(res, dsu, sorted, u, i + 1);
			}
		}
		static class UnionFind {
			Map<String, String> map = new HashMap<>();
			private int count;
			int setParent(String x) {
				if (map.containsKey(x)) return count;
				map.put(x, x);
				return ++count;
			}
			String find(String x) {
				String v = map.get(x);
				if (v != x) {
					v = find(v);
					map.put(x, v);
					return v;
				} else {
					return v; 
				}
			}
			int union(String x, String y) {
				String fx = find(x);
				String fy = find(y);
				if (fx != fy) {
					map.put(fx, fy);
					return --count;
				} else {
					return count;
				}
			}
		}
	}
	interface Problem extends BiFunction<List<List<String>>, String, List<String>> {
		
	}
}
