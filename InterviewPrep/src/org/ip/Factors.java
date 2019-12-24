package org.ip;

import org.ip.array.Utils;

// Google 10/03/19
public class Factors {
	public static void main(String[] s) {
		new Factors().solve(new Visitor(), 16);
		System.out.println();
		new Factors().solve(new Visitor(), 12);
	}
	public void solve(Visitor visitor, int f) {
		int[] buffer = new int[f];
		buffer[0] = 1;
		buffer[1] = f;
		solve(visitor, f, buffer, 1, 2);
	}
	public void solve(Visitor visitor, int f, int[] buffer, int index, int start) {
		visitor.visit(buffer, index + 1);
		for (int i = start; i <= Math.sqrt(f); i++) {
			if (f % i != 0) continue;
			buffer[index] = i;
			buffer[index + 1] = f / i;
			solve(visitor, f / i, buffer, index + 1, i);
		}
	}
	public static class Visitor {
		public void visit(int[] buffer, int length) {
			Utils.println(buffer, length);
		}
		
	}
}
