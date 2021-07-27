package org.ip.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

/**
 * <a href=
 * "https://leetcode.com/problems/binary-tree-vertical-order-traversal/">LC:
 * 314</a>
 * <a href="https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/">LC: 987</a>
 */
public class VerticalOrder {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(VerticalOrder::tc4);
		Solver[] solvers = new Solver[] { new DFSTreeMap(), new DFS() };
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}

	public static void tc1(Solver solver) {
		List<List<Integer>> res = solver.solve(
				new TreeNode(3,
						new TreeNode(9),
						new TreeNode(20,
								new TreeNode(15),
								new TreeNode(7))));
		System.out.println(res);
	}
	
	public static void tc2(Solver solver) {
		List<List<Integer>> res = solver.solve(
				new TreeNode(3,
						new TreeNode(9,
								new TreeNode(4),
								new TreeNode(0)),
						new TreeNode(8,
								new TreeNode(1),
								new TreeNode(7))));
		System.out.println(res);
	}
	
	public static void tc3(Solver solver) {
		List<List<Integer>> res = solver.solve(
				new TreeNode(3,
						new TreeNode(9,
								new TreeNode(4),
								new TreeNode(0,
										null,
										new TreeNode(2))),
						new TreeNode(8,
								new TreeNode(1,
										new TreeNode(5),
										null),
								new TreeNode(7))));
		System.out.println(res);
	}
	
	public static void tc4(Solver solver) {
		//[0,2,1,3,null,null,null,4,5,null,7,6,null,10,8,11,9]
		List<List<Integer>> res = solver.solve(
				new TreeNode(0,
						new TreeNode(2,
								new TreeNode(3,
										new TreeNode(4,
												null,
												new TreeNode(7,
														new TreeNode(10),
														new TreeNode(8))),
										new TreeNode(5,
												new TreeNode(6,
														new TreeNode(11),
														new TreeNode(9)),
												null)),
								null),
						new TreeNode(1,
								null,
								null)));
		System.out.println(res);
	}
	
	private static class DFS implements Solver {

		@Override
		public List<List<Integer>> solve(TreeNode node) {
			int min = minX(node, 0);
			int max = maxX(node, 0);
			int diameter = node != null ? max - min + 1 : 0;
			List<List<TreeNodePair>> list = new ArrayList<>(diameter);
			for (int i = 0; i < diameter; i++) {
				list.add(new ArrayList<>());
			}
			dfs(node, list, -min);
			List<List<Integer>> res = new ArrayList<>(diameter);
			for (int i = 0; i < diameter; i++) {
				Collections.sort(list.get(i), TreeNodePairComparator.COMPARATOR);
				List<Integer> lres = new ArrayList<>(list.get(i).size());
				res.add(lres);
				for (TreeNodePair pair : list.get(i)) {
					lres.add(pair.node.val);
				}
			}
			return res;
		}
		private int minX(TreeNode node, int index) {
			if (node == null) return 0;
			return Math.min(index, Math.min(minX(node.left, index - 1), minX(node.right, index + 1)));
		}
		
		private int maxX(TreeNode node, int index) {
			if (node == null) return 0;
			return Math.max(index, Math.max(maxX(node.left, index - 1), maxX(node.right, index + 1)));
		}
		
		private void dfs(TreeNode root, List<List<TreeNodePair>> res, int min) {
			if (root == null) return;
			Deque<TreeNodePair> queue = new LinkedList<>();
			int y = 0;
			queue.add(new TreeNodePair(root, min, y));
			queue.add(TreeNodePair.SENTINEL);
			while (!queue.isEmpty()) {
				TreeNodePair current = queue.removeFirst();
				if (current == TreeNodePair.SENTINEL) {
					y++;
					if (!queue.isEmpty()) {
						queue.addLast(TreeNodePair.SENTINEL);
					}
					continue;
				}
				res.get(current.x).add(current);
				if (current.node.left != null) {
					queue.addLast(new TreeNodePair(current.node.left, current.x - 1, y));
				}
				if (current.node.right != null) {
					queue.addLast(new TreeNodePair(current.node.right, current.x + 1, y));
				}
			}
		}
		
	}
	private static class TreeNodePairComparator implements Comparator<TreeNodePair> {
		public final static TreeNodePairComparator COMPARATOR = new TreeNodePairComparator();

		@Override
		public int compare(TreeNodePair o1, TreeNodePair o2) {
			int c = Integer.compare(o1.y, o2.y);
			if (c != 0) {
				return c;
			}
			return Integer.compare(o1.node.val, o2.node.val);
		}
		
	}
	private static class DFSTreeMap implements Solver {

		@Override
		public List<List<Integer>> solve(TreeNode node) {
			Map<Integer, List<TreeNodePair>> map = new TreeMap<>();
			dfs(node, map);
			List<List<Integer>> res = new ArrayList<>();
			for (List<TreeNodePair> list : map.values()) {
				Collections.sort(list, TreeNodePairComparator.COMPARATOR);
				List<Integer> lres = new ArrayList<>(list.size());
				res.add(lres);
				for (TreeNodePair pair : list) {
					lres.add(pair.node.val);
				}
			}
			return res;
		}
		
		private void dfs(TreeNode root, Map<Integer, List<TreeNodePair>> map) {
			if (root == null) return;
			Deque<TreeNodePair> queue = new LinkedList<>();
			int y = 0;
			queue.add(new TreeNodePair(root, 0, y));
			queue.add(TreeNodePair.SENTINEL);
			while (!queue.isEmpty()) {
				TreeNodePair current = queue.removeFirst();
				if (current == TreeNodePair.SENTINEL) {
					y++;
					if (!queue.isEmpty()) {
						queue.addLast(TreeNodePair.SENTINEL);
					}
					continue;
				}
				List<TreeNodePair> list = map.computeIfAbsent(current.x, (k) -> new ArrayList<>());
				list.add(current);
				if (current.node.left != null) {
					queue.addLast(new TreeNodePair(current.node.left, current.x - 1, y));
				}
				if (current.node.right != null) {
					queue.addLast(new TreeNodePair(current.node.right, current.x + 1, y));
				}
			}
		}

	}
	
	private static class TreeNodePair {
		private final static TreeNodePair SENTINEL = new TreeNodePair(null, 0, 0);
		TreeNode node;
		int x;
		int y;
		public TreeNodePair(TreeNode node, int x, int y) {
			this.node=node;this.x=x;this.y=y;
		}
	}

	private interface Solver {
		public List<List<Integer>> solve(TreeNode node);
	}

	private static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode() {
		}

		TreeNode(int val) {
			this.val = val;
		}

		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}
}
