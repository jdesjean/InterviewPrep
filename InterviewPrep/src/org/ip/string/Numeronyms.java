package org.ip.string;

import java.util.Arrays;

import org.ip.string.Visitors.StringVisitor;

public class Numeronyms {
	public static void main(String[] s) {
		solve("nailed",Visitors.PRINT_CONSOLE);
		solve("batch",Visitors.PRINT_CONSOLE);
	}
	public static void solve(String string, StringVisitor visitor) {
		char[] buffer = new char[string.length()];
		for (int i = string.length()-2; i > 1; i--) {
			Arrays.fill(buffer, ' ');
			String l = String.valueOf(i);
			for (int j = 1; j + i < string.length(); j++) {
				for (int k = 0; k < j; k++) {
					buffer[k] = string.charAt(k);
				}
				for (int k = 0; k < l.length(); k++) {
					buffer[j+k] = l.charAt(k);
				}
				for (int k = j+i, m = 0; k < string.length(); k++,m++) {
					buffer[j+l.length()+m] = string.charAt(k);
				}
				int length = 2 + string.length()-2-i+l.length();
				visitor.visit(String.copyValueOf(buffer, 0, length));
			}
		}
	}
}
