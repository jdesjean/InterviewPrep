package org.ip.tree;

// EPI 2018: 9.15
public class Exterior {
	public static void main(String[] s) {
		BasicNode root = new BasicNode(314,new BasicNode(6,new BasicNode(271,new BasicNode(28),new BasicNode(0)),new BasicNode(561,null,new BasicNode(3,new BasicNode(17)))),new BasicNode(6,new BasicNode(2,null,new BasicNode(1,new BasicNode(401,new BasicNode(641)),new BasicNode(257))),new BasicNode(271,null,new BasicNode(28))));
		new Exterior().solve(new PrintVisitor(), root);
	}
	public void solve(PrintVisitor visitor, BasicNode node) {
		_solve(visitor,node, true, true);
	}
	public void _solve(PrintVisitor visitor, BasicNode node, boolean isLeft, boolean isRight) {
		if (node == null) return;
		boolean pre = isLeft || node.isLeaf();
		boolean post = !pre && isRight;
		if (pre) {
			visitor.visit(node);
		}
		_solve(visitor, node.left, isLeft, false);
		_solve(visitor, node.right, false, isRight);
		if (post) {
			visitor.visit(node);
		}
	}
	public static class PrintVisitor {
		public void visit(BasicNode node) {
			System.out.println(node);
		}
	}
}
