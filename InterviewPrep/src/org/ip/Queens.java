package org.ip;

import org.ip.ArrayUtils.Filter;
import org.ip.ArrayUtils.PermutationVisitor;
import org.ip.ArrayUtils.PermutatorIterative;
import org.ip.ArrayUtils.PermutatorRecursive;

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
		solveRecursive(new PermutationVisitor(){

			@Override
			public void visit(int[] array) {
				printChess(array);
				System.out.println("");
			}
			
		},4);
		System.out.println("**");
		solveIterative(new PermutationVisitor(){

			@Override
			public void visit(int[] array) {
				if (isDiagonal(array)) return;
				printChess(array);
				System.out.println("");
			}
			
		},4);
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
	public static void solveRecursive(PermutationVisitor visitor, int size) {
		int[] columns = new int[size];
		for (int i = 0; i < size; i++) {
			columns[i] = i;
		}
		new PermutatorRecursive(new Filter(){

			@Override
			public boolean test(int[] array, int i) {
				return !isDiagonal(columns,i);
			}
			
		}).permute(visitor, columns);
	}
	public static void solveIterative(PermutationVisitor visitor, int size) {
		int[] columns = new int[size];
		for (int i = 0; i < size; i++) {
			columns[i] = i;
		}
		new PermutatorIterative().permute(visitor, columns);
	}
}
