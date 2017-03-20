package org.ip;

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
	public static final StringVisitor PRINT_CONSOLE = new StringVisitor(){
		@Override
		public void visit(String string) {
			System.out.println(string);
		}
	};
}
