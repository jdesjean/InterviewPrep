package org.ip.string;

import org.ip.string.Visitors.CharArrayVisitor;

//EPI 2018: 6.11
public class SnakeString2 {
	public static void main(String[] s) {
		new SnakeString2().solve("hello world", Visitors.PRINT_CONSOLE_CHAR_ARRAY);
	}
	public void solve(String s, CharArrayVisitor visitor) {
		char[] buffer = new char[s.length()];
		for (int i = 1; i < s.length(); i+=4) {
			buffer[i] = s.charAt(i);
		}
		visitor.visit(buffer, s.length());
		buffer = new char[s.length()];
		for (int i = 0; i < s.length(); i+=2) {
			buffer[i] = s.charAt(i);
		}
		visitor.visit(buffer, s.length());
		buffer = new char[s.length()];
		for (int i = 3; i < s.length(); i+=4) {
			buffer[i] = s.charAt(i);
		}
		visitor.visit(buffer, s.length());
		
	}
}
