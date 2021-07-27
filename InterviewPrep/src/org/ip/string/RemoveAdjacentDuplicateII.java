package org.ip.string;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/">LC: 1209</a>
 */
public class RemoveAdjacentDuplicateII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { "abcd", "abcd", 2};
		Object[] tc2 = new Object[] { "aa", "deeedbbcccbdaa", 3};
		Object[] tc3 = new Object[] { "ps", "pbbcggttciiippooaais", 2};
		Object[][] tcs = new Object[][] { tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new SolverRecursive(), new SolverLinear() };
		Test.apply(solvers, tcs);
	}
	static class SolverLinear implements Problem {

		@Override
		public String apply(String t, Integer u) {
			int k = u;
			if (t == null || t.length() == 0 || k <= 1) return "";
			Deque<AtomicInteger> deque = new LinkedList<>();
			Deque<Character> dequeCharacter = new LinkedList<>();
			for (int i = 0; i < t.length(); i++) {
				if (!deque.isEmpty() && t.charAt(i) == dequeCharacter.peekLast()) {
					AtomicInteger prev = deque.peekLast();
					int next = prev.incrementAndGet();
					if (next % k == 0) {
						dequeCharacter.removeLast();
						deque.removeLast();
					}
				} else {
					dequeCharacter.addLast(t.charAt(i));
					deque.addLast(new AtomicInteger(1));
				}
			}
			StringBuilder res = new StringBuilder();
			while (!deque.isEmpty()) {
				AtomicInteger size = deque.removeFirst();
				char c = dequeCharacter.removeFirst();
				for (int j = 0; j < size.get(); j++) {
					res.append(c);
				}
			}
			return res.toString();
		}
	}
	static class SolverRecursive implements Problem {

		@Override
		public String apply(String t, Integer u) {
			int k = u;
			if (t == null || t.length() == 0 || k <= 1) return "";
			char[] buffer = t.toCharArray();
			int j = buffer.length;
			while (true) {
				int next = solve(buffer, k, j);
				if (j == next) break;
				j = next;
			}
			return new String(buffer, 0, j);
		}
		int solve(char[] buffer, int k, int bufferLength) {
			int l = 0;
			int r = 1;
			int j = 0;
			for (; r < bufferLength; r++) {
				if (buffer[r] == buffer[l]) continue;
				int length = (r - l) % k;
				for (int i = 0; i < length; i++) {
					buffer[j++] = buffer[l++]; 
				}
				l = r;
			}
			int length = (r - l) % k;
			for (int i = 0; i < length; i++) {
				buffer[j++] = buffer[l++]; 
			}
			return j;
		}
	}
	interface Problem extends BiFunction<String, Integer, String> {
		
	}
}
