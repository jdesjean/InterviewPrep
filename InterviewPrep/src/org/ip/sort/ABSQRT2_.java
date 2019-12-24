
package org.ip.sort;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Consumer;

import org.ip.array.Utils;

// EPI 2018: 14.7
public class ABSQRT2_ {
	public static void main(String[] s) {
		Utils.println(new ABSQRT2_().solve(10));
	}
	public Pair[] solve(int k) {
		Pair[] pairs = new Pair[k];
		pairs[0] = new Pair(0,0);
		for (int n = 1, i = 0, j = 0; n < k; n++) {
			Pair a = new Pair(pairs[i].a + 1, pairs[i].b);
			Pair b = new Pair(pairs[j].a, pairs[j].b + 1);
			if (a.compareTo(b) == 0) {
				i++;
				j++;
				pairs[n] = a;
			} else if (a.compareTo(b) < 0) {
				i++;
				pairs[n] = a;
			} else {
				j++;
				pairs[n] = b;
			}
		}
		return pairs;
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
		@Override
		public Pair clone() {
			return new Pair(a, b);
		}
		
	}
}
