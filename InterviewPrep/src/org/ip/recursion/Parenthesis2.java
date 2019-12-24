package org.ip.recursion;

import java.util.function.Consumer;

import org.ip.array.Utils;

// EPI 2018: 15.6
public class Parenthesis2 {
	public static void main(String[] s) {
		new Parenthesis2().solve((x) -> Utils.println(x, x.length, false), 3);
	}
	public void solve(Consumer<char[]> consumer, int k) {
		solve(consumer, k, new char[k*2], 0, 0);
	}
	public void solve(Consumer<char[]> consumer, int k, char[] buffer , int open, int closed) {
		if (closed == k) {
			consumer.accept(buffer);
		}
		if (open < k) {
			buffer[closed + open] = '(';
			solve(consumer, k, buffer, open + 1, closed);
		}
		if (open > closed) {
			buffer[closed + open] = ')';
			solve(consumer, k, buffer, open, closed + 1);
		}
	}
}
