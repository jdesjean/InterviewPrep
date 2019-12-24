package org.ip.recursion;

//EPI: 17.10
public class Stairs {
	public static void main(String[] s) {
		int[][] tests = new int[3][2];
		tests[0] = new int[]{1,1};
		tests[1] = new int[]{2,2};
		tests[2] = new int[]{4,5};
		Counter[] counters = new Counter[]{new RecursiveCounter(), new DPCounter()};
		for (Counter counter : counters) {
			for (int i = 0; i < tests.length; i++) {
				System.out.println(counter.count(tests[i][0]) + "=="+tests[i][1]);
			}
		}
	}
	public interface Counter {
		public int count(int stairs);
	}
	public static class RecursiveCounter implements Counter {

		@Override
		public int count(int stairs) {
			if (stairs == 1 || stairs == 2) return stairs;
			return count(stairs-1) + count(stairs-2);
		}
		
	}
	public static class DPCounter implements Counter {

		@Override
		public int count(int stairs) {
			if (stairs == 1 || stairs == 2) return stairs;
			int prev2 = 1;
			int prev1 = 2;
			for (int i = 3; i < stairs; i++) {
				int value = prev1 + prev2;
				prev2 = prev1;
				prev1 = value;
			}
			return prev2 + prev1;
		}
		
	}
}
