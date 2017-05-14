
package org.ip.tree.iterator;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.ip.tree.Node;

public class InputStreamNode implements Iterator<Node<Integer>>{
	private DataInputStream os;

	public InputStreamNode(InputStream os) {
		this.os=new DataInputStream(os);
	}

	@Override
	public boolean hasNext() {
		try {
			return this.os.available() > 0;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Node<Integer> next() {
		int value;
		try {
			value = this.os.readInt();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return value > Integer.MIN_VALUE ? new Node<Integer>(value) : null;  
	}
	
}
