package org.ip.recursion;

import org.ip.array.Utils;

// EPI: 16.3
public class Permutator2 {
	public static void main(String[] s) {
		permute(new PrintVisitor(), new int[] {2,3,5,7});
	}
	public static class PrintVisitor implements Visitor {
		@Override
		public void visit(int[] permutation) {
			Utils.println(permutation, permutation.length);
		}
	}
	public interface Visitor {
		public void visit(int[] permutation);
	}
	public static void permute(Visitor visitor, int[] array) {
		permute(visitor, array, 0);
	}
	public static void permute(Visitor visitor, int[] array, int i) {
		if (i == array.length) {
			visitor.visit(array);
			return;
		}
		for (int j = i; j < array.length; j++) {
			Utils.swap(array, i, j);
			permute(visitor, array, i + 1);
			Utils.swap(array, j, i);
		}
	}
}
