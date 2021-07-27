package org.ip.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/discuss/interview-question/584289/">OA 2020</a>
 * <a href="https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/">LC 1530</a>
 */
public class LeafNodeDistance {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				LeafNodeDistance::tc1);
		Solver[] solvers = new Solver[] {new Recursive2(), new Recursive()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		int result = solver.solve(
				new Node(2, 
					new Node(3, 
							new Node(5, 
									new Node(9), 
									null), 
							new Node(6, 
									new Node(10), 
									new Node(11))), 
					new Node(4, 
							new Node(7), 
							new Node(8, 
									null, 
									new Node(12)))), 4);
		System.out.println(result);
	}
	public static class Recursive2 implements Solver {

		@Override
		public int solve(Node root, int k) {
			return _solve(root, k).count;
		}
		
		public Holder2 _solve(Node root, int k) {
			Holder2 result = new Holder2();
			if (root == null) {
				return result;
			} else if (root.left == null && root.right == null) {
				result.nodeHeight.add(1);
				return result;
			}
			Holder2 left = _solve(root.left, k);
			Holder2 right = _solve(root.right, k);
			
			result.count = left.count + right.count;
			for (Integer rh : right.nodeHeight) {
				for (Integer lh : left.nodeHeight) {
					int distance = lh + rh;
					if (distance <= k) {
						result.count++;
					}
				}
			}
			result.nodeHeight.addAll(left.nodeHeight);
			result.nodeHeight.addAll(right.nodeHeight);
			for (int i = 0; i < result.nodeHeight.size(); i++) {
				result.nodeHeight.set(i, result.nodeHeight.get(i) + 1);
			}
			return result;
		}
	}
	public static class Recursive implements Solver {

		@Override
		public int solve(Node root, int k) {
			return solve(root, 0, k).count;
		}
		
		public Holder solve(Node root, int height, int k) {
			Holder result = new Holder();
			if (root == null) {
				return result;
			} else if (root.left == null && root.right == null) {
				result.nodeHeight.put(height, 1);
				return result;
			}
			Holder left = solve(root.left, height + 1, k);
			Holder right = solve(root.right, height + 1, k);
			
			result.count = left.count + right.count;
			for (Integer rh : right.nodeHeight.keySet()) {
				for (Integer lh : left.nodeHeight.keySet()) {
					int distance = (lh - height) + (rh - height);
					if (distance <= k) {
						result.count += (left.nodeHeight.get(lh) * right.nodeHeight.get(rh));
					}
				}
			}
			left.nodeHeight.forEach((key, value) -> {
				result.nodeHeight.merge(key, value, (v1, v2) -> v1 + v2);
			});
			right.nodeHeight.forEach((key, value) -> {
				result.nodeHeight.merge(key, value, (v1, v2) -> v1 + v2);
			});
			return result;
		}
		
	}
	public interface Solver {
		public int solve(Node root, int k);
	}
	public static class Holder {
		Map<Integer, Integer> nodeHeight = new HashMap<>();
		public int count = 0;
	}
	public static class Holder2 {
		List<Integer> nodeHeight = new ArrayList<>();
		public int count = 0;
	}
}
