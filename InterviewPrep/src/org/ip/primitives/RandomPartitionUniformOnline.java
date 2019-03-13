package org.ip.primitives;

import java.util.Random;

// EPI 6.12
public class RandomPartitionUniformOnline {
	public static void main(String[] s) {
		RandomPartitionUniformOnline partition = new RandomPartitionUniformOnline(new Random(0), 2);
		int[] array = new int[] {1,2,3,4};
		for (int i = 0; i < array.length; i++) {
			int[] part = partition.accept(array[i]);
			org.ip.array.Utils.println(part, Math.min(i + 1, part.length));
		}
	}
	private int[] array;
	private int n;
	private Random random;
	private int k;
	public RandomPartitionUniformOnline(Random random, int k) {
		this.array = new int[k];
		this.n = 0;
		this.k = k;
		this.random = random;
	}
	public int[] accept(int value) {
		if (this.n < k) {
			this.array[this.n++] = value;
		} else {
			int index = this.random.nextInt(++this.n); 
			if (index < k) {
				this.array[index] = value;
			}
		}
		return this.array;
	}
}
