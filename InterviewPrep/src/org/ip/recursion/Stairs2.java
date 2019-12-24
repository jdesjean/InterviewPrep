package org.ip.recursion;

//EPI: 17.10
public class Stairs2 {
	public static void main(String[] s) {
		Counter[] counter = new Counter[] {new RecursiveCounter(), new DPCounter()};
		for (int j = 0; j < counter.length; j++) {
			System.out.println("");
			for (int i = 1; i <= 4; i++) {
				System.out.println(counter[j].count(i));
			}
		}
	}
	public interface Counter {
		public int count(int n);
	}
	public static class RecursiveCounter implements Counter {
		@Override
		public int count(int n) {
			if (n < 0) return 0;
			if (n == 0) return 1;
			return count(n - 1) + count(n - 2);
		}
	}
	public static class DPCounter implements Counter {
		@Override
		public int count(int n) {
			if (n <= 0) return 0;
			if (n == 1) return 1;
			int prev = 1;
			int current = 1;
			for (int i = 2; i <= n; i++) {
				int tmp = prev + current;
				prev = current;
				current = tmp;
			}
			return current;
		}
	}
}
