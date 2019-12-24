package org.ip.recursion;

import java.util.BitSet;

import org.ip.array.Utils;

// EPI: 16.4
public class PowerSet {
	public static void main(String[] s) {
		sets(new PrintVisitor(), new int[] {1,2,3});
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
	public static void sets(Visitor visitor, int[] array) {
		int[] buffer = new int[array.length];
		for (int i = 1; i < Math.pow(2, array.length); i++) {
			int k = 0;
			for (int j = 0, value = i; j < array.length; j++, value >>>= 1) {
				if ((value & 1) == 1) {
					buffer[k++] = array[j];
				}
			}
			visitor.visit(buffer, k);
		}
	}
}
