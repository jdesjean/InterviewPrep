package org.ip.recursion;

//EPI: 17.12
public class Sequence {
	public static void main(String[] s) {
		int[] sequence = new int[]{0,8,4,12,2,10,6,14,1,9};
		System.out.println(new RecursiveSolve().solve(sequence));
		System.out.println(new DPSolve().solve(sequence));
	}
	public static class RecursiveSolve implements Solve {
		@Override
		public int solve(int[] sequence) {
			return solve(sequence, -1, 0, 0);
		}
		public int solve(int[] sequence, int last, int length, int i) {
			if (i == sequence.length) {
				return length;
			}
			int max = solve(sequence, last, length, i + 1);
			if (sequence[i] >= last) {
				max = Math.max(max, solve(sequence, sequence[i], length + 1, i + 1));
			}
			return max;
		}
	}
	public static class DPSolve implements Solve {

		@Override
		public int solve(int[] sequence) {
			int[] cache = new int[sequence.length];
			cache[0] = 1;
			int max = 1;
			for (int i = 1; i < sequence.length; i++) {
				for (int j = 0; j < i; j++) {
					if (sequence[i] >= sequence[j]) {
						cache[i] = Math.max(cache[i], cache[j] + 1);
					}
				}
				max = Math.max(max, cache[i]);
			}
			return max;
		}
		
	}
	public interface Solve {
		public int solve(int[] sequence);
	}
}
