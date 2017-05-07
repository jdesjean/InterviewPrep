package org.ip;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.ip.array.Utils;

public class Change {
	public static void main(String[] s) {
		ChangeMinimizer[] minimizers = new ChangeMinimizer[] { new RecursiveChangeMinimizer(),
				new DPChangeMinimizer() };
		for (ChangeMinimizer minimizer : minimizers) {
			minimizer.min(new IntVisitor() {

				@Override
				public void visit(int[] value, int l) {
					Utils.println(value, l);
				}
			}, 4, new int[] { 1, 2, 3 });
			System.out.println("**");
		}
	}

	public interface IntVisitor {
		public void visit(int[] value, int l);
	}

	public interface ChangeMinimizer {
		public int min(IntVisitor visitor, int total, int[] denominations);
	}

	public static class RecursiveChangeMinimizer implements ChangeMinimizer {

		@Override
		public int min(IntVisitor visitor, int total, int[] denominations) {
			int min = min(total, denominations, 0);
			min(visitor, total, denominations, min, new int[min], 0);
			return min;
		}

		public int min(int total, int[] denominations, int depth) {
			if (total == 0) {
				return depth;
			}
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < denominations.length; i++) {
				int value = total - denominations[i];
				if (value < 0)
					continue;
				min = Math.min(min, min(value, denominations, depth + 1));
			}
			return min;
		}

		public int min(IntVisitor visitor, int total, int[] denominations, int min, int[] buffer, int depth) {
			if (total == 0) {
				visitor.visit(buffer, buffer.length);
				return depth;
			}
			for (int i = 0; i < denominations.length && depth < min; i++) {
				int value = total - denominations[i];
				if (value < 0)
					continue;
				buffer[depth] = denominations[i];
				min(visitor, value, denominations, min, buffer, depth + 1);
			}
			return min;
		}
	}

	private static class ChangePair {
		int coin;
		List<Integer> denomination = new LinkedList<Integer>();
		public ChangePair(int coin, int denomination)
		{
			this.coin = coin;
			this.denomination.add(denomination);
		}
		@Override
		public String toString() {
			return denomination.toString();
		}
	}

	public static class DPChangeMinimizer implements ChangeMinimizer {

		public int min0(IntVisitor visitor, int total, int[] denominations) {
			LinkedList<Integer>[][] cache = new LinkedList[total][total + 1];
			int l;
			for (l = 1; l < total; l++) {
				for (int i = 0; i < denominations.length; i++) {
					for (int j = total; j >= denominations[i]; j--) {
						int k = j - denominations[i];
						if (cache[l - 1][k] == null && k != 0)
							continue;
						if (cache[l][j] == null)
							cache[l][j] = new LinkedList<Integer>();
						cache[l][j].add(denominations[i]);
					}
				}
				if (cache[l][total] != null)
					break;
			}
			int[] buffer = new int[l];
			while (cache[l][total].size() > 0) {
				for (int i = l - 1, j = total; i >= 0; i--) {
					buffer[i] = cache[i + 1][j].remove(0);
					j -= buffer[i];
				}
				visitor.visit(buffer, l);
			}
			return l;
		}
		@Override
		public int min(IntVisitor visitor, int total, int[] denominations) {
			ChangePair[] cache = new ChangePair[total + 1];
			cache[0] = new ChangePair(0,0);
			for (int j = 1; j <= total; j++) {
				for (int i = 0; i < denominations.length; i++) {
					int k = j - denominations[i];
					if (k < 0 || cache[k] == null) continue;
					if (cache[j] == null) cache[j] = new ChangePair(cache[k].coin+1,denominations[i]);
					else if (cache[j].coin > cache[k].coin+1) {
						cache[j].coin = cache[k].coin + 1;
						cache[j].denomination.clear();
						cache[j].denomination.add(denominations[i]);
					} else if (cache[j].coin == cache[k].coin+1) {
						cache[j].denomination.add(denominations[i]);
					}
				}	
			}
			if (cache[total] == null) return 0;
			int[] buffer = new int[cache[total].coin];
			while (!cache[total].denomination.isEmpty()) {
				for (int i = cache[total].coin - 1, j = total; i >= 0; i--) {
					buffer[i] = cache[j].denomination.remove(0);
					j -= buffer[i];
				}
				visitor.visit(buffer, cache[total].coin);
			}
			return cache[total].coin;
		}
	}
}
