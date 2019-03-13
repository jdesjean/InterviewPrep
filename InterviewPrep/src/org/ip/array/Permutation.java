package org.ip.array;

// EPI: 6.9
public class Permutation {
	public static void main(String[] s) {
		Permutation permutation = new Permutation(new int[] {2,0,1,3});
		int[] array = new int[] {1,2,3,4};
		permutation.apply(array);
		Utils.println(array, array.length);
	}
	int[] permutation;
	public Permutation(int[] permutation) {
		this.permutation = permutation;
	}
	public void apply(int[] array) {
		for (int i = 0; i < array.length;) {
			if (permutation[i] == i) {
				i++;
			} else {
				Utils.swap(array, permutation[i], i);
				Utils.swap(permutation, permutation[i], i);
			}
		}
	}
}
