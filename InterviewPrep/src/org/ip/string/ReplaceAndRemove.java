package org.ip.string;

import org.ip.array.ArrayUtils;

public class ReplaceAndRemove {
	public static void main(String[] s) {
		char[] array = new char[]{'a','c','d','b','b','c','a'};
		solve(array);
		ArrayUtils.println(array,array.length);
	}
	public static void solve(char[] c) {
		int lengthB = 0;
		int lengthA = 0;
		//remove b
		for (int i = 0; i < c.length; i++) {
			c[lengthB] = c[i];
			if (c[i] == 'a') {
				lengthA++;
			}
			if (c[i] != 'b') {
				lengthA++;
				lengthB++;
			}
		}
		
		if (lengthA == lengthB) return;
		
		for (int i = lengthA-1, j = lengthB-1; j >= 0; j--,i--) {
			if (c[j] != 'a') {
				c[i] = c[j];
			} else {
				c[i--] = 'd';
				c[i] = 'd';
			}
		}
	}
}
