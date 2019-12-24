package org.ip.recursion;

import java.util.ArrayList;
import java.util.List;

import org.ip.array.Utils;

public class Change2 {
	public static void main(String[] s) {
		ChangeMinimizer[] minimizers = new ChangeMinimizer[] { new RecursiveChangeMinimizer(),
				new DPChangeMinimizer() };
		for (ChangeMinimizer minimizer : minimizers) {
			minimizer.min(new IntVisitor() {

				@Override
				public void visit(int[] value, int l) {
					Utils.println(value, l);
				}
			}, 4, new int[] { 3, 2, 1 });
			System.out.println("**");
		}
	}

	public interface IntVisitor {
		public void visit(int[] value, int l);
	}

	public interface ChangeMinimizer {
		public void min(IntVisitor visitor, int total, int[] denominations);
	}

	public static class RecursiveChangeMinimizer implements ChangeMinimizer {

		@Override
		public void min(IntVisitor visitor, int total, int[] denominations) {
			int min = min(total, denominations, 0);
			int[] buffer = new int[min];
			min(visitor, total, denominations, buffer, 0);
		}
		public int min(int total, int[] denominations, int count) {
			if (total < 0) {
				return Integer.MAX_VALUE;
			} else if (total == 0) {
				return count;
			}
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < denominations.length; i++) {
				min = Math.min(min, min(total - denominations[i], denominations, count + 1));
			}
			return min;
		}
		public void min(IntVisitor visitor, int total, int[] denominations, int[] buffer, int w) {
			if (total == 0) {
				visitor.visit(buffer, buffer.length);
				return;
			} else if (total < 0 || w >= buffer.length) {
				return;
			}
			for (int i = 0; i < denominations.length; i++) {
				buffer[w] = denominations[i];
				min(visitor, total - denominations[i], denominations, buffer, w + 1);
			}
		}
		
	}
	public static class CachePair {
		int depth;
		List<Integer> denominations;
		public CachePair(int depth) {this.depth = depth;this.denominations = new ArrayList<Integer>();}
	}
	public static class DPChangeMinimizer implements ChangeMinimizer {
		@Override
		public void min(IntVisitor visitor, int total, int[] denominations) {
			CachePair[] cache = new CachePair[total + 1];
			cache[0] = new CachePair(0);
			for (int j = 0; j < denominations.length; j++) {
				for (int i = denominations[j]; i < cache.length; i++) {
					if (cache[i - denominations[j]] != null) {
						if (cache[i] == null || cache[i - denominations[j]].depth + 1 < cache[i].depth) {
							cache[i] = new CachePair(cache[i - denominations[j]].depth + 1);
							cache[i].denominations.add(denominations[j]);
						} else if (cache[i].depth == cache[i - denominations[j]].depth + 1) {
							cache[i].denominations.add(denominations[j]);
						}
					}
				}
			}
			if (cache[total] != null) {
				int length = cache[total].depth;
				int[] buffer = new int[length];
				while (!cache[total].denominations.isEmpty()) {
					for (int i = length - 1, j = total; i >= 0; i--) {
						buffer[i] = cache[j].denominations.remove(0);
						j -= buffer[i];
					}
					visitor.visit(buffer, length);
				}
			}
		}
	}
}
