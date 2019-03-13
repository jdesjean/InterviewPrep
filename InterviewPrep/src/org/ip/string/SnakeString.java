package org.ip.string;

import org.ip.string.Visitors.CharArrayVisitor;

// EPI: 7.11
public class SnakeString {
	public static void main(String[] s) {
		solve("hello world", Visitors.PRINT_CONSOLE_CHAR_ARRAY);
	}
	public static void solve(String string, CharArrayVisitor visitor) {
		char[] buffer = new char[string.length()];
		for (int i = 1; i < string.length(); i+=4) {
			buffer[i] = string.charAt(i);
		}
		visitor.visit(buffer, buffer.length);
		for (int i = 0; i < string.length(); i++) {
			buffer[i] = i % 2 == 0 ? string.charAt(i) : ' ';
		}
		visitor.visit(buffer, buffer.length);
		for (int i = 0; i < string.length(); i++) {
			buffer[i] = (i - 3) % 4 == 0 ? string.charAt(i) : ' ';
		}
		visitor.visit(buffer, buffer.length);
	}
}