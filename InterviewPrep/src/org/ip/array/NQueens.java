package org.ip.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.function.Consumer;

import org.ip.tree.BinaryTreePath;
import org.ip.tree.BinaryTreePath.Recursive;
import org.ip.tree.BinaryTreePath.Solver;

/**
 * <a href="https://leetcode.com/problems/n-queens/">LC: 51</a>
 */
public class NQueens {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				NQueens::tc1);
		Solver[] solvers = new Solver[] {new Recursive()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		System.out.println(solver.solve(4));
	}
	/**
	 * n^n
	 */
	public static class Recursive implements Solver {
		@Override
		public List<List<String>> solve(int n) {
			int[] current = new int[n];
			List<List<String>> res = new ArrayList<>();
			BitSet lines = new BitSet();
			solve(res, current, lines, 0);
			return res;
		}
		void solve(List<List<String>> res, int[] current, BitSet lines, int c) {
			if (c == current.length) {
				res.add(toList(current));
				return;
			}
			for (int l = 0; l < current.length; l++) {
				if (lines.get(l)) continue;
				current[c] = l;
				if (hasDiagonal(current, c)) {
					lines.set(l);
					solve(res, current, lines, c + 1);
					lines.clear(l);
				}
			}
		}
		boolean hasDiagonal(int[] current, int c) {
			for (int i = 0; i < c; i++) {
				int lDiff = Math.abs(current[c] - current[i]);
				int cDiff = c - i;
				if (lDiff == cDiff) return false;
			}
			return true;
		}
		List<String> toList(int[] buffer) {
			List<String> res = new ArrayList<>(4);
			char[] current = new char[buffer.length];
			for (int l = 0; l < buffer.length; l++) {
				for (int c = 0; c < buffer.length; c++) {
					if (buffer[c] != l) {
						current[c] = '.';
					} else {
						current[c] = 'Q';
					}
				}
				res.add(new String(current));
			}
			return res;
		}
	}
	public interface Solver {
		public List<List<String>> solve(int n);
	}
}
