package org.ip.recursion;

// EPI: 17.3
public class Paths2 {
	public static void main(String[] s) {
		Counter[] counters = new Counter[] {new RecursiveCounter(), new DPCounter()};
		for (Counter counter : counters) {
			System.out.println(counter.count(new int[][]{{1,1},{0,1}}));
			System.out.println(counter.count(new int[][]{{1,1,1,1},{1,1,1,1},{1,1,1,1}}));
			
		}
	}
	public interface Counter {
		public int count(int[][] m);
	}
	public static class RecursiveCounter implements Counter {

		@Override
		public int count(int[][] m) {
			return count(m, 0, 0);
		}
		public int count(int[][] m, int l, int c) {
			if (l == m.length - 1 && c == m[l].length - 1) return 1;
			int count = 0;
			if (l + 1 < m.length && m[l + 1][c] == 1) {
				count += count(m, l + 1, c);
			}
			if (l < m.length && c + 1 < m[l].length && m[l][c + 1] == 1) {
				count += count(m, l, c + 1);
			}
			return count;
		}
		
	}
	public static class DPCounter implements Counter {

		@Override
		public int count(int[][] m) {
			int[] cache = new int[m[0].length];
			cache[0] = 1;
			for (int l = 0; l < m.length; l++) {
				for (int c = 0; c < m[l].length; c++) {
					if (m[l][c] == 0) {
						cache[c] = 0;
					} else if (c > 0){
						cache[c] += cache[c - 1];
					}
				}
			}
			return cache[m[0].length - 1];
		}
		
	}
}
