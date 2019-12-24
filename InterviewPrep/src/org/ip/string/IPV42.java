package org.ip.string;

import org.ip.array.Utils;

//EPI 2018: 6.10
public class IPV42 {
	public static void main(String[] s) {
		new IPV42().generate(new PrintVisitor(), "19216811");
	}
	public void generate(PrintVisitor visitor, String s) {
		char[] buffer = new char[s.length() + 3];
		for (int i = 1; i <= 3; i++) {
			if (!isValid(s,0,i-1)) {
				continue;
			}
			copy(s, buffer, 0, i - 1, 0);
			for (int j = 1; j <= 3; j++) {
				int jj = i+j;
				if (!isValid(s,i,jj-1)) {
					continue;
				}
				copy(s, buffer, i, jj - 1, i);
				for (int k = 1; k <= 3; k++) {
					int kk = i+j+k;
					if (!isValid(s,jj,kk-1) || !isValid(s,kk,s.length()-1)) {
						continue;
					}
					copy(s, buffer, jj, kk - 1, jj + 1);
					copy(s, buffer, kk, s.length() - 1, kk + 2);
					visitor.visit(buffer);
				}
			}
		}
	}
	public void copy(String s, char[] buffer, int i, int j, int k) {
		if (k > 0) {
			buffer[k++] = '.';
		}
		for (; i <= j; i++, k++) {
			buffer[k] = s.charAt(i);
		}
	}
	public boolean isValid(String s, int i, int j) {
		if (j >= s.length()) {
			return false;
		}
		if (i < j && s.charAt(i) == '0') {
			return false;
		}
		return value(s, i, j) <= 255;
	}
	public int value(String s, int i, int j) {
		int value = 0;
		for (; i <= j; i++) {
			value *= 10;
			value += s.charAt(i) - '0';
		}
		return value;
	}
	public static class PrintVisitor {
		public void visit(char[] s) {
			Utils.println(s, s.length, false);
		}
	}
}
