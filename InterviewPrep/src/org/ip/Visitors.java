package org.ip;

import org.ip.array.ArrayUtils;

public class Visitors {
	public interface StringVisitor {
		public void visit(String string);
	}
	public interface IntArrayVisitor {
		public void visit(int[] array, int length);
	}
	public interface CharArrayVisitor {
		public void visit(char[] array, int length);
	}
	public static final CharArrayVisitor PRINT_CONSOLE_CHAR_ARRAY2 = new CharArrayVisitor(){
		@Override
		public void visit(char[] string, int length) {
			ArrayUtils.println2(string,length);
		}
	};
	public static final CharArrayVisitor PRINT_CONSOLE_CHAR_ARRAY = new CharArrayVisitor(){
		@Override
		public void visit(char[] string, int length) {
			ArrayUtils.println(string,length);
		}
	};
	public static final StringVisitor PRINT_CONSOLE = new StringVisitor(){
		@Override
		public void visit(String string) {
			System.out.println(string);
		}
	};
}
