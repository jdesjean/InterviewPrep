
package org.ip.tree;
public interface Visitor<T> {
	public void visit(Node<T> node);
}
