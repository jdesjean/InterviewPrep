package org.ip.recursion;

import java.util.BitSet;
import java.util.function.Consumer;

import org.ip.array.Utils;
import org.ip.primitives.Bit;

// EPI 2018: 5.10
public class GrayCode2 {
	public static void main(String[] s) {
		new GrayCode2().solve((x) -> {
			for (int i = 0; i < x.length; i++) {
				System.out.println(x[i] + ":" + Bit.toBinary(x[i]));
			}
			System.out.println("**");
		}, 3);
	}
	public void solve(Consumer<int[]> consumer, int n) {
		int length = (int)Math.pow(2, n);
		int[] set = new int[length];
		for (int i = 0; i < set.length; i++) {
			set[i] = i;
		}
		int[] buffer = new int[length];
		_solve(consumer, buffer, set, 0);
	}
	public void _solve(Consumer<int[]> consumer, int[] buffer, int[] set, int i) {
		if (i == set.length) {
			if (differByOne(buffer[i - 1], buffer[0])) consumer.accept(buffer);
			return;
		}
		int prev = i > 0 ? set[i - 1] : 0;
		for (int j = i; j < set.length; j++) {
			if (!differByOne(prev, set[j])) continue;
			Utils.swap(set, i, j);
			buffer[i] = set[i];
			_solve(consumer, buffer, set, i + 1);
			Utils.swap(set, i, j);
		}
	}
	public static int value(BitSet set) {
		int value=0;
		for (int i = set.nextSetBit(0); i >= 0; i = set.nextSetBit(i+1)) {
			value += (1 << i);
		}
		return value;
	}
	private static boolean differByOne(int i, int j) {
		int xor = i ^ j;
		return (xor & (xor - 1)) == 0;
	}
}
