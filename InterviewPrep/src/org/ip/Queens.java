package org.ip;

import org.ip.Permutator.PermutatorFactoradicIterative;
import org.ip.Permutator.PermutatorIterative;
import org.ip.Permutator.PermutatorRecursive;
import org.ip.primitives.ArrayUtils.Filter;
import org.ip.primitives.ArrayUtils.PermutationVisitor;

public class Queens {
	public static void main(String[] s) {
		/*
		 * +-+-+-+-+

			| | |*| |
			
			+-+-+-+-+
			
			|*| | | |
			
			+-+-+-+-+
			
			| | | |*|
			
			+-+-+-+-+
			
			| |*| | |
			
			+-+-+-+-+
			
			
			+-+-+-+-+
			
			| |*| | |
			
			+-+-+-+-+
			
			| | | |*|
			
			+-+-+-+-+
			
			|*| | | |
			
			+-+-+-+-+
			
			| | |*| |
			
			+-+-+-+-+
					 */
		PermutationVisitor visitor = new PermutationVisitor(){

			@Override
			public void visit(int[] array) {
				printChess(array);
				System.out.println("");
			}
			
		};
		Filter filter = new Filter(){

			@Override
			public boolean test(int[] array, int i) {
				return !isDiagonal(array,i);
			}
			
		};
		Filter filter2 = new Filter(){

			@Override
			public boolean test(int[] array, int i) {
				return !isDiagonal(array);
			}
			
		};
		
		
		Permutator[] permutators = new Permutator[]{new PermutatorRecursive(filter),new PermutatorIterative(filter2), new PermutatorFactoradicIterative(filter)};
		long t1, t2;
		for (int i = 0; i < permutators.length; i++) {
			t1 = System.currentTimeMillis();
			solve(permutators[i],visitor,4);
			t2 = System.currentTimeMillis();
			System.out.println("**" + (t2-t1));
		}
	}
	private static boolean isDiagonal(int[] columns, int column) {
		for (int i = 0; i < column; i++) {
			if (Math.abs(columns[i] - columns[column]) == column - i) return true;
		}
		return false;
	}
	private static boolean isDiagonal(int[] columns) {
		for (int i = 0; i < columns.length; i++) {
			for (int j = 0; j < columns.length; j++) {
				if (i == j) continue;
				if (Math.abs(columns[i] - columns[j]) == Math.abs(j - i)) return true;
			}
		}
		return false;
	}
	private static void printChessLine(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print("+");
			System.out.print("-");
		}
		System.out.println("+");
	}
	private static void printChess(int[] array) {
		printChessLine(array);
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				System.out.print("|");
				if (array[i] == j) System.out.print("*");
				else System.out.print(" ");
			}
			System.out.println("|");
			if (i < array.length - 1) printChessLine(array);
		}
		printChessLine(array);
	}
	public static void solve(Permutator permutator, PermutationVisitor visitor, int size) {
		int[] columns = new int[size];
		for (int i = 0; i < size; i++) {
			columns[i] = i;
		}
		permutator.permute(visitor, columns);
	}
	public static void solveRecursive(PermutationVisitor visitor, int size) {
		int[] columns = new int[size];
		for (int i = 0; i < size; i++) {
			columns[i] = i;
		}
		new PermutatorRecursive(new Filter(){

			@Override
			public boolean test(int[] array, int i) {
				return !isDiagonal(array,i);
			}
			
		}).permute(visitor, columns);
	}
	public static void solveIterative(PermutationVisitor visitor, int size) {
		int[] columns = new int[size];
		for (int i = 0; i < size; i++) {
			columns[i] = i;
		}
		new PermutatorIterative(new Filter(){

			@Override
			public boolean test(int[] array, int i) {
				return !isDiagonal(array,i);
			}
			
		}).permute(visitor, columns);
	}
	public static void solveIterative2(PermutationVisitor visitor, int size) {
		int[] columns = new int[size];
		for (int i = 0; i < size; i++) {
			columns[i] = i;
		}
		new PermutatorFactoradicIterative(new Filter(){

			@Override
			public boolean test(int[] array, int i) {
				return !isDiagonal(array,i);
			}
			
		}).permute(visitor, columns);
	}
	
}
