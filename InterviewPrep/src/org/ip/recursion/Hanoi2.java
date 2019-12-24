package org.ip.recursion;

import java.util.Deque;
import java.util.LinkedList;

// EPI 2018: 15.1
public class Hanoi2 {
	public static void main(String[] s) {
		new Hanoi2().solve();
	}
	public void solve() {
		Deque<Integer>[] towers = new Deque[3];
		int count = 3;
		for (int i = 0; i < count; i++) {
			towers[i] = new LinkedList<Integer>();
			towers[0].push(count - i);
		}
		_solve(towers[0], towers[1], towers[2], count);
		while (!towers[2].isEmpty()) {
			System.out.println(towers[2].pop());
		}
	}
	public void _solve(Deque<Integer> source, Deque<Integer> buffer, Deque<Integer> dest, int size) {
		if (size <= 0) return;
		_solve(source, dest, buffer, size - 1);
		dest.push(source.pop());
		_solve(buffer, source, dest, size - 1);
	}
}
