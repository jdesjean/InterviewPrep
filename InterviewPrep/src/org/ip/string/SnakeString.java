package org.ip.string;

import java.util.Arrays;

import org.ip.Visitors;
import org.ip.Visitors.StringVisitor;

public class SnakeString {
	public static void main(String[] s) {
		solve("hello world", Visitors.PRINT_CONSOLE);
	}
	public static void solve(String string, StringVisitor visitor) {
		char[] buffer = new char[string.length()];
		for (int i = 2; i < string.length(); i+=4) {
			buffer[i] = string.charAt(i);
		}
		visitor.visit(String.copyValueOf(buffer));
		Arrays.fill(buffer, ' ');
		for (int i = 1; i < string.length(); i+=2) {
			buffer[i] = string.charAt(i);
		}
		visitor.visit(String.copyValueOf(buffer));
		Arrays.fill(buffer, ' ');
		for (int i = 0; i < string.length(); i+=4) {
			buffer[i] = string.charAt(i);
		}
		visitor.visit(String.copyValueOf(buffer));
	}
}
