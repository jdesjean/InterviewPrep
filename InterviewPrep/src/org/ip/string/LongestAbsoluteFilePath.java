package org.ip.string;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToIntFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/longest-absolute-file-path/">LC: 388</a>
 */
public class LongestAbsoluteFilePath {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {20, "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext"};
		Object[] tc2 = new Object[] {32, "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext"};
		Object[] tc3 = new Object[] {0, "a"};
		Object[] tc4 = new Object[] {12, "file1.txt\nfile2.txt\nlongfile.txt"};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	static class Solver2 implements Problem {

		@Override
		public int applyAsInt(String value) {
			int max = 0;
			Deque<Integer> stack = new LinkedList<>();
			for (String string : value.split("\n")) {
				int tab = dirIndex(string);
				int length = string.length() - tab + (tab > 0 ? 1 : 0);
				while (tab < stack.size() && !stack.isEmpty()) {
					stack.pop();
				}
				stack.push(stack.isEmpty() ? length : stack.peek() + length);
				boolean isFile = string.indexOf('.') > 0;
				if (isFile) {
					max = Math.max(stack.peek(), max);
				}
			}
			return max;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public int applyAsInt(String value) {
			AtomicInteger max = new AtomicInteger();
			_apply(value.split("\n"), 0, 0, max, new AtomicInteger());
			return max.get();
		}
		void _apply(String[] splits, int level, int count, AtomicInteger max, AtomicInteger idx) {
			if (idx.get() == splits.length) {
				return;
			}
			if (count > max.get()) {
				max.set(count);
			}
			int nextCount = count;
			while (idx.get() < splits.length) {
				int tabs = dirIndex(splits[idx.get()]);
				if (tabs == level) {
					boolean isFile = splits[idx.get()].indexOf('.') > 0;
					int length = splits[idx.getAndIncrement()].length() - tabs;
					nextCount = count + length;
					if (isFile && nextCount > max.get()) {
						max.set(nextCount);
					}
				} else if (tabs > level) {
					_apply(splits, level + 1, nextCount + 1, max, idx);
				} else if (tabs < level) {
					break;
				}
			}
		}
		
	}
	static int dirIndex(String s) {
		int i = 0;
		for (; i < s.length(); i++) {
			if (s.charAt(i) != '\t') break;
		}
		return i;
	}
	interface Problem extends ToIntFunction<String> {
		
	}
}
