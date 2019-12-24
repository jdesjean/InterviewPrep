
package org.ip.sort;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Consumer;

// EPI 2018: 14.7
public class ABSQRT2 {
	public static void main(String[] s) {
		new ABSQRT2().solve((i) -> System.out.print(i + ","), 10);
	}
	public void solve(Consumer<Pair> visitor, int k) {
		SortedSet<Pair> heap = new TreeSet<Pair>();
		heap.add(new Pair(0,0));
		for (int i = 0; i < k; i++) {
			Pair pair = heap.first();
			visitor.accept(pair);
			heap.add(new Pair(pair.a + 1, pair.b));
			heap.add(new Pair(pair.a, pair.b + 1));
			heap.remove(pair);
		}
	}
	public static class Pair implements Comparable<Pair>{
		int a;
		int b;
		private final double SQRT = Math.sqrt(2);
		private double val;
		public Pair(int a, int b) {
			this.a=a;
			this.b=b;
			this.val=a+b*SQRT;
		}
		@Override
		public int compareTo(Pair o) {
			return Double.compare(val, o.val);
		}
		@Override
		public String toString() {
			return "Pair [a=" + a + ", b=" + b + ", val=" + val + "]";
		}
		
	}
}
