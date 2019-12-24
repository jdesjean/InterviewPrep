package org.ip.tree;

// EPI 2018: 9.3
public class LCAParentless2 {
	public static void main(String[] s) {
		BasicNode root = new BasicNode(45,
				new BasicNode(25, new BasicNode(15, new BasicNode(10), new BasicNode(20)),
						new BasicNode(30)),
				new BasicNode(65, new BasicNode(55, new BasicNode(50), new BasicNode(60)),
						new BasicNode(75, null, new BasicNode(80))));
		LCAParentless2 lca = new LCAParentless2();
		System.out.println(lca.solve(root, 10, 20));
		System.out.println(lca.solve(root, 50, 80));
		System.out.println(lca.solve(root, 20, 60));
		System.out.println(lca.solve(root, 20, 90));
	}
	public BasicNode solve(BasicNode root, int v1, int v2) {
		Result result = _solve(root, v1, v2);
		return result != null ? result.lca : null;
	}
	private Result _solve(BasicNode root, int v1, int v2) {
		if (root == null) return null;
		Result result = new Result();
		if (root.value == v1 || root.value == v2) {
			result.count+=1;
			result.lca = root;
		}
		for (BasicNode n : new BasicNode[] {root.left, root.right}) {
			Result tmp = _solve(n, v1, v2);
			if (tmp == null) continue;
			result.count += tmp.count;
			if (tmp.count == 2) {
				return tmp;
			} else if (tmp.count == 1 && result.count == 2) {
				result.lca = root;
				return result;
			}
		}
		return result;
	}
	public static class Result {
		int count = 0;
		BasicNode lca;
	}
}
