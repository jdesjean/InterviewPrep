package org.ip.array;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// EPI 2018: 5.15
public class Generator {
	public static void main(String[] s) {
		int k = 2;
		int n = 4;
		int[] array = new int[k];
		new GeneratorParity(new Random(0)).generate(array, n);
		Utils.println(array);
		new GeneratorMap(new Random(0)).generate(array, n);
		Utils.println(array);
	}
	public static class GeneratorMap {
		private Random random;

		public GeneratorMap(Random random) {
			this.random=random;
		}
		public void generate(int[] array, int n) {
			Map<Integer,Integer> map = new HashMap<Integer,Integer>();
			for (int i = 0; i < array.length; i++) {
				int r = i + random.nextInt(n - i);
				Integer m = map.get(r);
				Integer j = map.get(i);
				if (m == null && j == null) {
					map.put(i, r);
					map.put(r, i);
					array[i] = r;
				} else if (m != null && j == null) {
					array[i] = m;
					map.put(i, m);
					map.put(r, i);
				} else if (m == null && j != null) {
					array[i] = r;
					map.put(i, r);
					map.put(r, j);
				} else {
					array[i] = m;
					map.put(i, m);
					map.put(r, j);
				}
			}
		}
	}
	public static class GeneratorParity {
		private Random random;
		public GeneratorParity(Random random) {
			this.random = random;
		}
		public void generate(int[] array, int n) {
			int pow = (int)Math.pow(2, n + 1); // - 1, but random is exclusive
			int r;
			do {
				r = random.nextInt(pow);
			} while (count(r) != array.length);
			int j = 0;
			int i = 0;
			while (r > 0) {
				if ((r & 1) == 1) {
					array[i++] = j;
				}
				r>>=1;
				j++;
			}
		}
		public int count(int value) {
			int count = 0;
			while (value > 0) {
				value = value & (value - 1);
				count++;
			}
			return count;
		}
	}
}
