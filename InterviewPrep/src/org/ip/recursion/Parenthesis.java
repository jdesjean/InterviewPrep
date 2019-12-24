package org.ip.recursion;

import org.ip.array.Utils;

// EPI: 16.6
public class Parenthesis {
	public static void main(String[] s) {
		PrintVisitor visitor = new PrintVisitor();
		System.out.println("2");
		solve(visitor, 2);
		System.out.println("3");
		solve(visitor, 3);
	}
	public static void solve(Visitor visitor, int size) {
		char[] buffer = new char[size * 2];
		solve(visitor, buffer, 0, 0, size);
	}
	public static void solve(Visitor visitor, char[] buffer, int open, int closed, int size) {
		if (closed == size) {
			visitor.visit(buffer);
			return;
		}
		if (open < size) {
			buffer[open + closed] = '(';
			solve(visitor, buffer, open + 1, closed, size);
		}
		if (open > closed) {
			buffer[open + closed] = ')';
			solve(visitor, buffer, open, closed + 1, size);
		}
	}
	public static class PrintVisitor implements Visitor {
		@Override
		public void visit(char[] parens) {
			Utils.println(parens, parens.length);
		}
	}
	public interface Visitor {
		public void visit(char[] parens);
	}
}
