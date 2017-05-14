package org.ip.tree.reducer;

public interface Object<S, T> {
	public S visit(T current, S init);
}