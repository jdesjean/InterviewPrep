package org.ip;

import org.ip.Visitors.CharArrayVisitor;
import org.ip.primitives.ArrayUtils;

public class Brackets {
	public static void main(String[] s) {
		/*
		 * ()
		 * (()) ()()
		 * ((())) (()()) (())() ()(()) ()()()
		 */
		solve(new CharArrayVisitor(){
			boolean started = false;
			@Override
			public void visit(char[] array, int length) {
				if (started) System.out.print(",");
				ArrayUtils.print(array, length);
				started = true;
			}}, 3);
	}
	public static void solve(CharArrayVisitor visitor, int size) {
		solve(visitor,size,size,0,new char[size*2]);
	}
	public static void solve(CharArrayVisitor visitor, int open, int close, int write, char[] buffer) {
		if (open == 0 && close == 0) {
			visitor.visit(buffer, write);
			return;
		}
		if (open > 0) {
			buffer[write] = '(';
			solve(visitor,open-1,close,write+1,buffer);
		}
		if (close > open) {
			buffer[write] = ')';
			solve(visitor,open,close-1,write+1,buffer);
		}
	}
}
