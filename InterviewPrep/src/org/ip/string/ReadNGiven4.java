package org.ip.string;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import org.ip.array.Utils;

/**
 * <a href="https://leetcode.com/problems/read-n-characters-given-read4-ii-call-multiple-times/">LC: 158</a>
 * <a href="https://leetcode.com/problems/read-n-characters-given-read4/">LC: 157</a>
 */
public class ReadNGiven4 {
	public static void main(String[] s) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object[] tc1 = new Object[] {new int[] {1, 2, 0}, "abc", new int[] {1,2,1}};
		Object[] tc2 = new Object[] {new int[] {3,0}, "abc", new int[] {4,1}};
		Object[] tc3 = new Object[] {new int[] {1,4}, "abcde", new int[] {1,4}};
		List<Object[]> tcs = Arrays.asList(tc3, tc1, tc2);
		char[] buffer = new char[4];
		for (Object[] tc : tcs) {
			Class<? extends BiFunction<char[], Integer, Integer>>[] solvers = new Class[] {Solver.class};
			Utils.print((int[]) tc[0]);
			for (Class<? extends BiFunction<char[], Integer, Integer>> solver : solvers) {
				BiFunction<char[], Integer, Integer> instance = solver.getConstructor(Reader4.class).newInstance(new Reader4(((String) tc[1]).toCharArray()));
				System.out.print(" : ");
				int[] words = (int[]) tc[2];
				for (int i = 0; i < words.length; i++) {
					int n = instance.apply(buffer, words[i]);
					System.out.print(", " + n + ":");
					Utils.print(buffer, n);
				}
			}
			System.out.println();
		}
	}
	public static class Solver implements BiFunction<char[], Integer, Integer> {
		private Buffer buffer = new Buffer(4);
		private Reader4 reader;
		public Solver(Reader4 reader) {
			this.reader = reader;
		}
		
	    /**
	     * @param buf Destination buffer
	     * @param n   Number of characters to read
	     * @return    The number of actual characters read
	     */
		@Override
	    public Integer apply(char[] buf, Integer n) {
	    	int i = 0;
	    	for (; i < n && (!buffer.isEmpty() || buffer.copyFrom(reader) != 0); i += buffer.copyInto(buf, i, n - i)) {
	    		// empty
	        }
	        return i;
	    }
	}
	public static class Buffer {
		int index = 0;
		int count = 0;
		char[] buf;
		public Buffer(int size) {
			buf = new char[size];
		}
		public Buffer(char[] buf) {
			this.buf = buf;
			count = buf.length;
		}
		public boolean isEmpty() {
			return index == count;
		}
		public char next() {
			return buf[index++];
		}
		public int copyInto(char[] buf, int i, int n) {
			int length = Math.min(n, count - index);
			System.arraycopy(this.buf, index, buf, i, length);
			index += length;
			return length;
		}
		public int copyFrom(Reader4 reader) {
			index = 0;
			count = reader.read4(buf);
			return count;
		}
	}
	public static class Reader4 extends Buffer {
		public Reader4(char[] buf) {
			super(buf);
		}
		int read4(char[] buf4) {
			return copyInto(buf4, 0, 4);
		}
	}
}
