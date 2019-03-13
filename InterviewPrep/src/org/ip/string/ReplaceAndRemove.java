package org.ip.string;

import org.ip.array.Utils;

//EPI: 7.4
public class ReplaceAndRemove {
	public static void main(String[] s) {
		ReplaceAndRemove rr = new ReplaceAndRemove();
		char[] array = new char[] {'a','c','d','b','b','c','a'};
		rr.execute(array, array.length);
		Utils.print(array, array.length);
	}
	public void execute(char[] s, int length) {
		int i = 0, j = 0;
		for (; i < length; i++) {
			if (s[i] != 'b') {
				s[j++] = s[i];
			}
		}
		length = length - (i - j);
		for (i = length - 1, j = s.length - 1; i >= 0; i--) {
			if (s[i] == 'a') {
				s[j--] = 'd';
				s[j--] = 'd';
			} else {
				s[j--] = s[i];
			}
		}
	}
}
