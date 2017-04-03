package org.ip;

import org.ip.Visitors.IntArrayVisitor;

public class PascalTriangle {
	public static void main(String[] s) {
		solve(6,new IntArrayVisitor(){

			@Override
			public void visit(int[] array, int length) {
				for (int i = 0; i < Math.min(array.length,length); i++) {
					if (i > 0) System.out.print(" ");
					System.out.print(array[i]);
				}
				System.out.println("");
			}
			
		});
	}
	public static void solve(int n, IntArrayVisitor visitor) {
		int[] buffer = new int[n];
		buffer[0] = 1;
		for (int i = 0; i < n; i++) {
			int prev = 0;
			for (int j = 0; j <= i; j++) {
				int current = buffer[j];
				buffer[j] = prev + current;
				prev = current;
			}
			visitor.visit(buffer, i+1);
		}
	}
}
