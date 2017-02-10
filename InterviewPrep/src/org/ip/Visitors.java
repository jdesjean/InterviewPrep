package org.ip;

public class Visitors {
	public interface StringVisitor {
		public void visit(String string);
	}
	public interface IntArrayVisitor {
		public void visit(int[] array, int length);
	}
}
