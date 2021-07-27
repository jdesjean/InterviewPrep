package org.ip.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import org.ip.Test;
import org.ip.Test.QuadFunction;

/**
 * <a href="https://leetcode.com/problems/the-most-similar-path-in-a-graph/">LC: 1548</a>
 */
public class MostSimilarPath {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { Arrays.asList(Arrays.asList(0,2,4,2), Arrays.asList(0,3,0,2), Arrays.asList(0,3,1,2)), 5, new int[][] {{0,2},{0,3},{1,2},{1,3},{1,4},{2,4}}, new String[] {"ATL","PEK","LAX","DXB","HND"}, new String[] {"ATL","DXB","HND","LAX"}};
		Object[] tc2 = new Object[] { Arrays.asList(Arrays.asList(0,1,0,1,0,1,0,1)), 4, new int[][] {{1,0},{2,0},{3,0},{2,1},{3,1},{3,2}}, new String[] {"ATL","PEK","LAX","DXB"}, new String[] {"ABC","DEF","GHI","JKL","MNO","PQR","STU","VWX"}};
		/**
		 * ATL <-> PEK <-> LAX <-> ATL <-> DBX <-> HND
		 * ALT, PEK, LAX, ATL, LAX, PEK
		 */
		Object[] tc3 = new Object[] { 
				Arrays.asList(Arrays.asList(3,4,5,4,3,2,1)), 
				6, 
				new int[][] {{0,1},{1,2},{2,3},{3,4},{4,5}}, 
				new String[] {"ATL","PEK","LAX","ATL","DXB","HND"}, 
				new String[] {"ATL","DXB","HND","DXB","ATL","LAX","PEK"}};
		
		Object[][] tcs = new Object[][] { tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new DijkstraSolver(), new DPSolver() };
		Test.apply(solvers, tcs);
	}
	static class DPSolver implements Problem {

		@Override
		public List<Integer> apply(Integer a, int[][] b, String[] c, String[] p) {
			int n = a;
			int m = p.length;
			boolean[][] g = new boolean[n][n];
			for (int i = 0; i < b.length; i++) {
				g[b[i][0]][b[i][1]] = true;
				g[b[i][1]][b[i][0]] = true;
			}
			
			int[][] cache = new int[m][n];
			int[][] prev = new int[m][n];
			for(int i = 0; i < n; i++){
	            if(p[0].equals(c[i])){
	                cache[0][i] = 1;
	            }
	        }
			for (int im = 1; im < m; im++) {
				for (int in = 0; in < n; in++) {
					int maxScore = -1;
	                int maxPrevNode = -1;
	                int score = p[im].equals(c[in]) ? 1 : 0;
	                for(int jn = 0; jn < n; jn++) {
	                	if(!g[in][jn]) continue;
	                    if(score + cache[im - 1][jn] > maxScore){
	                        maxScore = score + cache[im - 1][jn];
	                        maxPrevNode = jn;
	                    }
	                }
	                cache[im][in] = maxScore;
	                prev[im][in] = maxPrevNode;
				}
			}
			return toList(cache, prev);
		}
		List<Integer> toList(int[][] cache, int[][] prev) {
			int m = cache.length;
			int n = cache[0].length;
			int maxScore = -1;
	        int maxFinalNode = -1;
	        for(int in = 0; in < n; in++){
	            if(cache[m - 1][in] > maxScore){
	                maxScore = cache[m - 1][in];
	                maxFinalNode = in;
	            }
	        }
	        List<Integer> res = new ArrayList<>();
	        res.add(maxFinalNode);
	        int curNode = maxFinalNode;
	        for(int im = m - 1; im > 0; im--){
	            res.add(prev[im][curNode]);
	            curNode = prev[im][curNode];
	        }
	        Collections.reverse(res);
	        return res;
		}
		
	}
	/**
	 * O(NMlog(EM)), N cities, M targets, E edges
	 */
	static class DijkstraSolver implements Problem {
		
		@Override
		public List<Integer> apply(Integer a, int[][] b, String[] c, String[] d) {
			int n = a;
			Graph<Integer> g = new Graph<>();
			for (int i = 0; i < b.length; i++) {
				g.addEdge(b[i][0], b[i][1]);
			}
			PriorityQueue<Pair> pq = new PriorityQueue<>(new PairComparator());
			for (int i = 0; i < n; i++) {
				pq.add(new Pair(i, 0, d[0].equals(c[i]) ? 0 : 1, null));
			}
			while (!pq.isEmpty()) {
				Pair pair = pq.remove();
				if (pair.pathIndex == d.length - 1) {
					return toList(pair);
				}
				Set<Integer> edges = g.map.get(pair.node);
				if (edges == null) continue;
				for (Integer edge : edges) {
					int index = pair.pathIndex + 1;
					int cost = c[edge].equals(d[index]) ? pair.cost : pair.cost + 1;
					pq.add(new Pair(edge, index, cost, pair));
				}
			}
			return null;
		}
		List<Integer> toList(Pair pair) {
			Pair parent = pair;
			List<Integer> res = new ArrayList<>();
			while (parent != null) {
				res.add(parent.node);
				parent = parent.parent;
			}
			Collections.reverse(res);
			return res;
		}
	}
	
	static class PairComparator implements Comparator<Pair> {

		@Override
		public int compare(Pair o1, Pair o2) {
			return Integer.compare(o1.cost, o2.cost);
		}
		
	}
	static class Pair {
		int node;
		int pathIndex;
		int cost;
		Pair parent;
		public Pair(int node, int pathIndex, int cost, Pair parent) {
			this.node = node;
			this.pathIndex = pathIndex;
			this.cost = cost;
			this.parent = parent;
		}
	}
	interface Problem extends QuadFunction<Integer, int[][], String[], String[], List<Integer>> {
		
	}
}
