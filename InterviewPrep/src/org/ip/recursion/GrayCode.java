package org.ip.recursion;

import java.util.BitSet;

import org.ip.array.Utils;

public class GrayCode {
	public static void main(String[] s) {
		new RecursiveSolver().solve(new PrintVisitor(), 3);
		new IterativeSolver().solve(new PrintVisitor(), 3);
	}
	public interface Solver {
		public void solve(Visitor visitor, int n);
	}
	public static class IterativeSolver implements Solver {

		@Override
		public void solve(Visitor visitor, int n) {
			if (n == 0) {
				visitor.visit(new int[] {0});
			} else if (n == 1) {
				visitor.visit(new int[] {0, 1});
			}
			int length = (int)Math.pow(2,n);
			int[] results = new int[length];
			results[0] = 0;
			results[1] = 1;
			int p = 1 << 1;
			for (int i = 2, j = 1; i < length; i++) {
				results[i] = p | results[j--];
				if (j < 0) {
 					p <<= 1;
					j = i;
				}
			}
		}
		
	}
	public static class RecursiveSolver implements Solver {
		@Override
		public void solve(Visitor visitor, int n) {
			int[] numbers = new int[(int)Math.pow(2, n)];
			BitSet set = new BitSet();
			numbers[0] = 0;
			set.set(0);
			solve(visitor, numbers, set, n, 1);
		}
		public boolean solve(Visitor visitor, int[] numbers, BitSet set, int n, int i) {
			if (i == numbers.length) {
				if (differOneBit(numbers[0], numbers[numbers.length-1])) {
					visitor.visit(numbers);
					return true;
				}
			}
			for (int j = 0; j <= n; j++) {
				numbers[i] = flip(numbers[i - 1], j);
				if (!set.get(numbers[i])) {
					set.set(numbers[i]);
					if (solve(visitor, numbers, set, n, i + 1)) {
						return true;
					}
					set.clear(numbers[i]);
				}
			}
			return false;
		}
		public int flip(int i, int j) {
			return i ^ (1 << j);
		}
		public boolean differOneBit(int x, int y) {
			int xor = x ^ y;
			return xor != 0 && (xor & (xor - 1) ) == 0;
		}
	}
	
	public static class PrintVisitor implements Visitor{
		@Override
		public void visit(int[] code) {
			Utils.println(code, code.length);
		}
	}
	public interface Visitor {
		public void visit(int[] code);
	}
}
