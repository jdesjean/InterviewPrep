package org.ip.tree;

import java.io.OutputStream;
import java.io.InputStream;

public interface Serializer {
	public void serialize(Tree<Integer> tree, OutputStream os);
	public Tree<Integer> deserialize(InputStream is);
}
