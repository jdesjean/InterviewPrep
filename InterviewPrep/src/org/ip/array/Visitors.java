package org.ip.array;

public class Visitors {
	public interface IntArrayVisitor {
		public void visit(int[] array, int length);
	}
}
