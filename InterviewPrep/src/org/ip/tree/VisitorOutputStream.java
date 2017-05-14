
package org.ip.tree;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class VisitorOutputStream implements Visitor<Integer>{
	private DataOutputStream os;

	public VisitorOutputStream(OutputStream os) {
		this.os=new DataOutputStream(os);
	}

	@Override
	public void visit(Node<Integer> node) {
		try {
			os.writeInt(node != null ? node.value : Integer.MIN_VALUE);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
