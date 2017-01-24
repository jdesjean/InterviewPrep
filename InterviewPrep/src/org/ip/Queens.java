package org.ip;

import java.util.List;

import org.ip.ArrayUtils.Filter;
import org.ip.ArrayUtils.PermutationVisitor;

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
		solve(new PermutationVisitor(){

			@Override
			public void visit(int[] array) {
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
	private static void printChess(int[] array) {
		System.out.println("+-+-+-+-+");
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				System.out.print("|");
				if (array[i] == j) System.out.print("*");
				else System.out.print(" ");
			}
			System.out.println("|");
			if (i < array.length - 1) System.out.println("+-+-+-+-+");
		}
		System.out.println("+-+-+-+-+");
	}
	public static void solve(PermutationVisitor visitor, int size) {
		int[] columns = new int[size];
		for (int i = 0; i < size; i++) {
			columns[i] = i;
		}
		ArrayUtils.permutations(visitor, new Filter(){

			@Override
			public boolean test(int[] array, int i) {
				return !isDiagonal(columns,i);
			}
			
		}, columns, 0);
	}
}
