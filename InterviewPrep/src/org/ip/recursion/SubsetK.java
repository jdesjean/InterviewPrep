package org.ip.recursion;

import java.util.function.Consumer;

import org.ip.array.Utils;

// EPI 2018: 15.5
public class SubsetK {
	public static void main(String[] s) {
		int[] a = new int[] {1,2,3,4};
		new SubsetK().solve((x) -> Utils.println(x), a, new int[2]);
	}
	//binomial coefficient of n over k n!/(n-k)!k!. 4 over 2: 6
	public void solve(Consumer<int[]> consumer, int[] array, int[] buffer) {
		solve(consumer, array, buffer, 0, 0);
	}
	public void solve(Consumer<int[]> consumer, int[] array, int[] buffer, int ri, int wi) {
		if (wi == buffer.length) {
			consumer.accept(buffer);
			return;
		} else if (ri == array.length) {
			return;
		}
		solve(consumer, array, buffer, ri + 1, wi);
		buffer[wi] = array[ri];
		solve(consumer, array, buffer, ri + 1, wi + 1);
	}
	public void _solve(Consumer<int[]> consumer, int[] array, int[] buffer) {
		int n = (int)Math.pow(2, array.length);
		for (int i = 1; i < n; i++) {
			int k = 0;
			if (numBits(i) != buffer.length) continue;
			for (int j = 0, bits = i; j < array.length; j++, bits >>= 1) {
				if ((bits & 1) != 1) continue;
				buffer[k++] = array[j];
			}
			consumer.accept(buffer);
		}
		//_solve(consumer, array, buffer, 0);
	}
	private int numBits(int i) {
		int count = 0;
		while (i > 0) {
			i = (i - 1) & i;
			count++;
		}
		return count;
	}
}
