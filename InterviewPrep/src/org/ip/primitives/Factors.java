package org.ip.primitives;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.ip.array.Utils;

// Google: 10/03/19
// LC: 254
public class Factors {
	public static void main(String[] s) {
		new Factors().solve(16);
		System.out.println();
		new Factors().solve(12);
	}
	public void solve(int number) {
		// Max size is log2, because worse case is all 2s as factors and the max number of 2s is parity of the number  
		int[] buffer = new int[org.ip.primitives.Number.log2(number)];
		System.out.println(number);
		solve((x) -> System.out.println(Arrays.stream(x).boxed().collect(Collectors.toList())), buffer, number, 2, 0);
	}
	public void solve(Consumer<int[]> consumer, int[] buffer, int number, int factor, int bufferIndex) {
		for (int i = factor; i <= Math.sqrt(number); i++) {
			if (number % i != 0) continue;
			buffer[bufferIndex] = i;
			buffer[bufferIndex + 1] = number / i;
			consumer.accept(Arrays.copyOfRange(buffer, 0, bufferIndex + 2));
			solve(consumer, buffer, number / i, i, bufferIndex + 1);
		}
	}
}
