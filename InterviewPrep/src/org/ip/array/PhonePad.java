package org.ip.array;

import org.ip.Visitors;
import org.ip.Visitors.CharArrayVisitor;

public class PhonePad {
	public static final String[] KEY_PAD = new String[]{"","","ABC","DEF","GHI","JKL","MNO","PQRS","TUV","WXYZ"};
	public static void main(String[] s) {
		mnemonics("036",Visitors.PRINT_CONSOLE_CHAR_ARRAY);
	}
	public static void mnemonics(String number, CharArrayVisitor visitor) {
		char[] buffer = new char[number.length()];
		mnemonics(number,visitor,buffer,0,0);
	}
	public static void mnemonics(String number, CharArrayVisitor visitor, char[] buffer, int read, int write) {
		if (read == number.length()) {
			visitor.visit(buffer,write);
			return;
		}
		String string = KEY_PAD[Character.digit(number.charAt(read), 10)];
		for (int i = 0; i < string.length(); i++) {
			buffer[write] = string.charAt(i); 
			mnemonics(number,visitor,buffer,read+1,write+1);
		}
		if (string.length() == 0) {
			mnemonics(number,visitor,buffer,read+1,write);
		}
	}
}
