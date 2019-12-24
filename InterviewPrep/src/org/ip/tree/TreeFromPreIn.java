package org.ip.tree;

// EPI 2018: 9.12
public class TreeFromPreIn {
	public static void main(String[] s) {
		//012345678
		//ABCDEFGHI
		String in = "FBAEHCDIG";
		String pre = "HBFEACDGI";
		BasicNode node = new TreeFromPreIn().solve(in, pre);
		for (IteratorOrderIn it = new IteratorOrderIn(node); it.hasNext();) {
			System.out.println((char)(it.next().value + 'A'));
		}
		System.out.println("**");
		for (IteratorOrderPre it = new IteratorOrderPre(node); it.hasNext();) {
			System.out.println((char)(it.next().value + 'A'));
		}
	}
	public BasicNode solve(String in, String pre) {
		return _solve(in, pre, 0, in.length() - 1, 0);
	}
	public BasicNode _solve(String in, String pre, int left, int right, int p) {
		if (left > right || p >= pre.length()) return null;
		BasicNode n = new BasicNode(pre.charAt(p) - 'A');
		int i = in.indexOf(pre.charAt(p));
		n.left = _solve(in, pre, left, i - 1, p + 1);
		int leftChild = left <= i - 1 ? i - left : 0;
		n.right = _solve(in, pre, i + 1, right, p + leftChild + 1);
		return n;
	}
}
