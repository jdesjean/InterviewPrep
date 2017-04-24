package org.ip.string;

import org.ip.Visitors;
import org.ip.Visitors.CharArrayVisitor;

public class IPAddress {
	public static void main(String[] s) {
		decompose("19216811",Visitors.PRINT_CONSOLE_CHAR_ARRAY2);
	}
	public static void decompose(String s, CharArrayVisitor visitor) {
		char[] buffer = new char[s.length()+3];
		for (int i = 1; i <= 3; i++) {
			buffer[i-1] = s.charAt(i-1);
			buffer[i] = '.';
			if (!isValid(buffer,0,i-1)) continue;
			int left1 = i+1;
			for (int j = 1; j <= 3; j++) {
				if (i+j-1 > s.length()-1) break;
				buffer[i+j] = s.charAt(i+j-1);
				buffer[i+j+1] = '.';
				if (!isValid(buffer,left1,i+j)) continue;
				int left2 = i+j+2;
				for (int k = 1; k <= 3; k++) {
					if (i+j+k-1 > s.length()-1) break;
					buffer[i+j+k+1] = s.charAt(i+j+k-1);
					buffer[i+j+k+2] = '.';
					if (!isValid(buffer,left2,i+j+k+1)) continue;
					
					int left3 = i+j+k+3;
					for (int l = left3; l < s.length()+3; l++) {
						buffer[l] = s.charAt(l-3);
					}
					if (!isValid(buffer,left3,s.length()+2)) continue;
					visitor.visit(buffer, buffer.length);
				}
			}
		}
	}
	private static boolean isValid(char[] buffer, int left, int right) {
		int length = right - left + 1;
		if (length > 3 || length == 0) return false;
		if (length > 1 && buffer[left] == '0') return false;
		int value = org.ip.primitives.Number.fromCharacter(buffer,left,right);
		return value >= 0 && value <= 255;
	}
	/*public static boolean decompose(String s, CharArrayVisitor visitor, char[] buffer, int read, int write, int period) {
		
	}*/
}
