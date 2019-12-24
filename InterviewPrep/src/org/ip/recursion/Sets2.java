package org.ip.recursion;

import org.ip.array.Utils;

// EPI: 16.5
public class Sets2 {
	public static void main(String[] s) {
		sets(new PrintVisitor(), new int[] {1,2,3,4,5}, 2);
	}
	public static class PrintVisitor implements Visitor {
		@Override
		public void visit(int[] permutation, int l) {
			Utils.println(permutation, l);
		}
	}
	public interface Visitor {
		public void visit(int[] set, int l);
	}
	public static void sets(Visitor visitor, int[] array, int size) {
		int[] buffer = new int[array.length];
		for (int i = 1; i < Math.pow(2, array.length); i++) {
			int numOnes = numOnes(i);
			if (numOnes != size) continue;
			int k = 0;
			for (int j = 0, value = i; j < array.length; j++, value >>>= 1) {
				if ((value & 1) == 1) {
					buffer[k++] = array[j];
				}
			}
			visitor.visit(buffer, k);
		}
	}
	public static int numOnes(int value) {
		int count = 0;
		while (value != 0) {
			value = value & (value - 1);
			count++;
		}
		return count;
	}
}
