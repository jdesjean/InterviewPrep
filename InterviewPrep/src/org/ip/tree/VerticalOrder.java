package org.ip.tree;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/binary-tree-vertical-order-traversal/">LC: 314</a>
 * <a href="https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/">LC: 987</a>
 */
public class VerticalOrder {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[][] {{9},{3,15},{20},{7}}, new Integer[] {3,9,20,null,null,15,7}};
		Object[] tc2 = new Object[] {new int[][] {{4},{9},{3,0,1},{8},{7}}, new Integer[] {3,9,8,4,0,1,7}};
		Object[] tc3 = new Object[] {new int[][] {{4},{9,5},{3,0,1},{8,2},{7}}, new Integer[] {3,9,8,4,0,1,7,null,null,null,2,5}};
		Object[] tc4 = new Object[] {new int[][] {}, new Integer[] {}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] {new DFSTreeMap(), new DFS(), new Solver()};
		Test.apply(solvers, tcs);
	}
	
	private static class Solver implements Problem {

		@Override
		public List<List<Integer>> apply(TreeNode t) {
			List<List<Integer>> positive = new ArrayList<>();
			List<List<Integer>> negative = new ArrayList<>();
			bfs((node) -> {
				List<List<Integer>> list = positive;
				int x = node.x;
				if (x < 0) {
					x = Math.abs(x) - 1;
					list = negative;
				}
				if (list.size() <= x) {
					list.add(new ArrayList<>());
				}
				list.get(x).add(node.node.val);
			}, t);
			Collections.reverse(negative);
			return new CompositeUnmodifiableList<>(negative, positive);
		}
		void bfs(Consumer<TreeNodePair> consumer, TreeNode t) {
			if (t == null) return;
			Deque<TreeNodePair> deque = new LinkedList<>();
			deque.add(new TreeNodePair(t, 0));
			while(!deque.isEmpty()) {
				TreeNodePair current = deque.remove();
				consumer.accept(current);
				if (current.node.left != null) {
					deque.add(new TreeNodePair(current.node.left, current.x - 1));
				}
				if (current.node.right != null) {
					deque.add(new TreeNodePair(current.node.right, current.x + 1));
				}
			}
		}
		static class CompositeUnmodifiableList<E> extends AbstractList<E> {

		    private final List<? extends E> list1;
		    private final List<? extends E> list2;

		    public CompositeUnmodifiableList(List<? extends E> list1, List<? extends E> list2) {
		        this.list1 = list1;
		        this.list2 = list2;
		    }
		    
		    @Override
		    public E get(int index) {
		        if (index < list1.size()) {
		            return list1.get(index);
		        }
		        return list2.get(index-list1.size());
		    }

		    @Override
		    public int size() {
		        return list1.size() + list2.size();
		    }
		}
		private static class TreeNodePair {
			TreeNode node;
			int x;
			public TreeNodePair(TreeNode node, int x) {
				this.node=node;this.x=x;
			}
		}
	}
	
	private static class DFS implements Problem {

		@Override
		public List<List<Integer>> apply(TreeNode node) {
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
	private static class DFSTreeMap implements Problem {

		@Override
		public List<List<Integer>> apply(TreeNode node) {
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

	interface Problem extends Function<TreeNode, List<List<Integer>>> {
	}
}
