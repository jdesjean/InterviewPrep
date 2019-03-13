package org.ip.array;

import java.util.Random;

// EPI: 6.11, 6.13, 6.14
public class RandomPartitionUniformOffline {
	public static void main(String[] s) {
		int k = 2;
		int[] array = new int[] {0,1,2,3,4};
		RandomPartitionUniformOffline partition = new RandomPartitionUniformOffline(new Random(0));
		partition.partition(array, k);
		Utils.println(array, k);
	}
	private Random random;
	public RandomPartitionUniformOffline(Random random) {
		this.random=random;
	}
	public void partition(int[] array, int k) {
		for (int i = 0; i < k; i++) {
			int j = random.nextInt(array.length - i);
			Utils.swap(array, i, i + j);
		}
	}
}
