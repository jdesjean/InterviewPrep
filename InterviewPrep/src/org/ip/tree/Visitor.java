
package org.ip.tree;
public interface Visitor<T extends Comparable<T>> {
	public void visit(Node<T> node);
}
