package org.ip.array;

// EPI 2018: 5.10
public class Permutator {
	public static void main(String[] s) {
		int[] perm = new int[] {2,0,1,3};
		int[] a = new int[] {0,1,2,3}; //1, 2, 0, 3
		Permutator p = new Permutator(perm);
		p.apply(a);
		Utils.println(a, a.length);
	}
	private int[] perm;
	public Permutator(int[] perm) {
		this.perm=perm;
	}
	public void apply(int[] array) {
		for (int i = 0; i < array.length; i++) {
			while (perm[i] != i) {
				Utils.swap(array, i, perm[i]);
				Utils.swap(perm, i, perm[i]);
			}
		}
	}
}
