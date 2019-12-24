package org.ip.recursion;

import java.util.Deque;
import java.util.LinkedList;

// EPI: 16.1
public class Hanoi {
	public static void main(String[] s) {
		Deque<Integer>[] pegs = new LinkedList[3];
		for (int i = 0; i < pegs.length; i++) {
			pegs[i] = new LinkedList<Integer>();
		}
		for (int i = 4; i >= 1; i--) {
			pegs[0].push(i);
		}
		solve(pegs[0], pegs[1], pegs[2], 4);
		System.out.println(pegs[2]);
	}
	public static void solve(Deque<Integer> source, Deque<Integer> buffer, Deque<Integer> dest, int size) {
		if (size == 0) return;
		solve(source, dest, buffer, size - 1);
		dest.push(source.pop());
		solve(buffer, source, dest, size -1);
	}
}
