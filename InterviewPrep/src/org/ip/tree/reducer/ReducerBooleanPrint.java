package org.ip.tree.reducer;

import org.ip.tree.Node;
import org.ip.tree.Tree;
import org.ip.tree.Tree.ArrayVisitor;
import java.lang.reflect.Array;

public class ReducerBooleanPrint<T extends Comparable<T>> implements Boolean<T> {
	private ArrayVisitor<T> visitor;
	private T[] path;

	public ReducerBooleanPrint(Tree<T> tree, ArrayVisitor<T> visitor) {
		this.visitor = visitor;
		int height = tree.height();
		this.path = (T[]) Array.newInstance(tree.root().value.getClass(), height);
	}

	@Override
	public boolean visit(Node<T> node, int depth) {
		path[depth] = node.value;
		if (node.isLeaf())
			visitor.visit(path, depth + 1);
		return true;
	}
}