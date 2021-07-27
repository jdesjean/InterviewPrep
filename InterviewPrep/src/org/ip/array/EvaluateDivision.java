package org.ip.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/evaluate-division/">LC: 399</a>
 */
public class EvaluateDivision {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new double[] {6.00000,0.50000,-1.00000,1.00000,-1.00000}, Arrays.asList(
				Arrays.asList(new String[] {"a","b"}), 
				Arrays.asList(new String[] {"b","c"})),
				new double[] {2.0,3.0},
				Arrays.asList(
						Arrays.asList(new String[]{"a","c"}),
						Arrays.asList(new String[] {"b","a"}),
						Arrays.asList(new String[] {"a","e"}),
						Arrays.asList(new String[] {"a","a"}),
						Arrays.asList(new String[] {"x","x"}))};
		Object[][] tcs = new Object[][] {tc1};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2() };
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem  {

		@Override
		public double[] apply(List<List<String>> a, double[] b, List<List<String>> c) {
			DSU dsu = new DSU();
			for (int i = 0; i < a.size(); i++) {
				List<String> list = a.get(i);
				dsu.union(list.get(0), list.get(1), b[i]);
			}
			double[] res = new double[c.size()];
			for (int i = 0; i < c.size(); i++) {
				List<String> list = c.get(i);
				if (!dsu.map.containsKey(list.get(0))
						|| !dsu.map.containsKey(list.get(1))) {
					res[i] = -1;
				} else {
					Pair<String, Double> px = dsu.find(list.get(0));
					Pair<String, Double> py = dsu.find(list.get(1));
					if (!px.t.equals(py.t)) {
						res[i] = -1;
					} else {
						res[i] = px.u / py.u;
					}
				}
			}
			return res;
		}
		static class DSU {
			Map<String, Pair<String, Double>> map = new HashMap<>();
			Pair<String, Double> find(String v) {
				Pair<String, Double> pair = map.get(v);
				if (pair == null) {
					pair = new Pair<>(v, 1d);
					map.put(v, pair);
				} else {
					if (!pair.t.equals(v)) {
						Pair<String, Double> f = find(pair.t);
						pair = new Pair<>(f.t, f.u * pair.u);
						map.put(v, pair);
					}
				}
				return pair;
			}
			void union(String x, String y, double v) {
				Pair<String, Double> pDividend = find(x);
				Pair<String, Double> pDivisor = find(y);
				if (!pDividend.t.equals(pDivisor.t)) {
					map.put(pDividend.t, new Pair<>(pDivisor.t, pDivisor.u * v / pDividend.u));
				}
			}
		}
		static class Pair<T, U> {
			public final T t;
			public final U u;
			public Pair(T t, U u) {
				this.t = t;
				this.u = u;
			}
			@Override
			public String toString() {
				return "Pair [t=" + t + ", u=" + u + "]";
			}
			
		}
	}
	static class Solver implements Problem {

		@Override
		public double[] apply(List<List<String>> a, double[] b, List<List<String>> c) {
			Map<String, Map<String, Double>> g = new HashMap<>();
			for (int i = 0; i < a.size(); i++) {
				List<String> lists = a.get(i);
				Map<String, Double> v = g.computeIfAbsent(lists.get(0), (k) -> new HashMap<>());
				v.put(lists.get(1), b[i]);
				
				v = g.computeIfAbsent(lists.get(1), (k) -> new HashMap<>());
				v.put(lists.get(0), 1 / b[i]);
			}
			double[] res = new double[c.size()];
			for (int i = 0; i < c.size(); i++) {
				Double value = eval(g, c.get(i).get(0), c.get(i).get(1), new HashSet<>(), 1);
				res[i] = value != null ? value : -1;
			}
			return res;
		}
		Double eval(Map<String, Map<String, Double>> g, String start, String end, Set<String> visited, double v) {
			if (!visited.add(start)) {
				return null;
			}
			Map<String, Double> map = g.get(start);
			if (map == null) {
				return null;
			}
			if (start.equals(end)) {
				return v;
			}
			for (Map.Entry<String, Double> entry : map.entrySet()) {
				Double eval = eval(g, entry.getKey(), end, visited, v * entry.getValue());
				if (eval != null) return eval;
			}
			return null;
		}
		
	}
	interface Problem extends TriFunction<List<List<String>>, double[], List<List<String>>, double[]> {
		
	}
}
