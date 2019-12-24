package org.ip.recursion;

import java.util.function.BiConsumer;

import org.ip.array.Utils;

// EPI 2018: 15.4
public class PowerSet2 {
	public static void main(String[] s) {
		int[] array = new int[] {1,2,3};
		new PowerSet2().solve((x, l) -> Utils.println(x, l), array);
	}
	// 2^k
	public void solve(BiConsumer<int[], Integer> consumer, int[] array) {
		int n = (int)Math.pow(2, array.length);
		int[] buffer = new int[array.length];
		for (int i = 1; i < n; i++) {
			int k = 0;
			for (int j = 0, bits = i; j < array.length; j++, bits >>= 1) {
				if ((bits & 1) != 1) continue;
				buffer[k++] = array[j]; 
			}
			consumer.accept(buffer, k);
		}
	}
}
