package org.ip.string;

import org.ip.array.Utils;

public class Visitors {
	public interface StringVisitor {
		public void visit(String string);
	}
	public interface CharArrayVisitor {
		public void visit(char[] array, int length);
	}
	public interface CharSequenceVisitor {
		public void visit(CharSequence array);
	}
	public static final CharArrayVisitor PRINT_CONSOLE_CHAR_ARRAY2 = new CharArrayVisitor(){
		@Override
		public void visit(char[] string, int length) {
			Utils.println2(string,length);
		}
	};
	public static final CharArrayVisitor PRINT_CONSOLE_CHAR_ARRAY = new CharArrayVisitor(){
		@Override
		public void visit(char[] string, int length) {
			Utils.println(string,length);
		}
	};
	public static final CharSequenceVisitor PRINT_CONSOLE_CHAR_SEQUENCE = new CharSequenceVisitor(){
		@Override
		public void visit(CharSequence string) {
			Utils.println(string);
		}
	};
	public static final StringVisitor PRINT_CONSOLE = new StringVisitor(){
		@Override
		public void visit(String string) {
			System.out.println(string);
		}
	};
}
