package org.ip.tree;

// EPI 2018: 9.13
public class TreeFromPre {
	public static void main(String[] s) {
		Character[] pre = new Character[] {'H', 'B', 'F', null, null, 'E', 'A', null, null, null, 'C', null, 'D', null, 'G', 'I', null, null, null};
		BasicNode node = new TreeFromPre().solve(pre);
		for (IteratorOrderPre it = new IteratorOrderPre(node); it.hasNext();) {
			System.out.println((char)(it.next().value + 'A'));
		}
	}
	public BasicNode solve(Character[] pre) {
		return _solve(pre, new IntegerHolder());
	}
	public BasicNode _solve(Character[] pre, IntegerHolder i) {
		if (pre[++i.value] == null) return null;
		BasicNode node = new BasicNode(pre[i.value] - 'A');
		node.left = _solve(pre, i);
		node.right = _solve(pre, i);
		return node;
	}
	public static class IntegerHolder {
		int value = -1;
	}
}
