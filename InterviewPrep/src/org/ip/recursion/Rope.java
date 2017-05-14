package org.ip.recursion;

public class Rope {
	public static void main(String[] s) {
		Cutter[] cutters = new Cutter[]{new RecursiveCutter(), new DPCutter()};
		for (Cutter cutter : cutters) {
			System.out.println(cutter.cut(4));
			System.out.println(cutter.cut(10));
		}
		
	}
	public interface Cutter {
		public int cut(int n);
	}
	public static class RecursiveCutter implements Cutter {

		@Override
		public int cut(int n) {
			int max = n;
			for (int i = 1; i <= n/2; i++) {
				max = Math.max(max, i*cut(n-i));
			}
			return max;
		}
		
	}
	public static class DPCutter implements Cutter {

		@Override
		public int cut(int n) {
			int[] cache = new int[n+1];
			for (int i = 1; i <= n; i++) {
				cache[i] = i;
				for (int j = 1; j <= i; j++) {
					cache[i] = Math.max(cache[i], j*cache[i-j]);
				}
			}
			return cache[n];
		}
		
	}
}
