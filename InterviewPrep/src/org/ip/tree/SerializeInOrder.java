package org.ip.tree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.ip.Test;

public class SerializeInOrder {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new Integer[] {2,1,3}, new Integer[] {2,1,3}};
		Object[] tc2 = new Object[] {new Integer[] {null}, new Integer[] {null}};
		Object[] tc3 = new Object[] {new Integer[] {3,1,null,null,2}, new Integer[] {3,1,null,null,2}};
		Object[] tc4 = new Object[] {new Integer[] {4,-7,-3,null,null,-9,-3,9,-7,-4,null,6,null,-6,-6,null,null,0,6,5,null,9,null,null,-1,-4,null,null,null,-2}, new Integer[] {4,-7,-3,null,null,-9,-3,9,-7,-4,null,6,null,-6,-6,null,null,0,6,5,null,9,null,null,-1,-4,null,null,null,-2}};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		@Override
		public Integer[] apply(TreeNode t) {
			TreeNode copy = deserialize(serialize(t));
			Integer[] values = new Integer[TreeNode.len(copy)]; 
			TreeNode.toBfs(copy, values);
			return values;
		}
		List<Integer> serialize(TreeNode t) {
			List<Integer> list = new ArrayList<>();
			_serialize(list, t);
			return list;
		}
		void _serialize(List<Integer> list, TreeNode t) {
			if (t == null) {
				list.add(null);
				return;
			}
			_serialize(list, t.left);
			list.add(t.val);
			_serialize(list, t.right);
		}
		TreeNode deserialize(List<Integer> list) {
			AtomicInteger idx = new AtomicInteger();
			Deque<TreeNode> stack = new LinkedList<>();
			TreeNode root = null;
			while (idx.get() < list.size()) {
				root = _deserialize(list, idx, stack, true);
				stack.push(root);
			}
			return root;
		}
		//Both give same inorder output: 2,1,3 + 3,2,N,1  -> N,1,N,2,N,3,N
		TreeNode _deserialize(List<Integer> list, AtomicInteger idx, Deque<TreeNode> stack, boolean isLeft) {
			if (stack.isEmpty() && idx.get() >= list.size()) return null;
			if (stack.isEmpty()) {
				Integer value = list.get(idx.getAndIncrement());
				if (!isLeft && value == null) return null; // Doesn't work. Can't determine size of right subtree
				stack.push(value != null ? new TreeNode(value) : null);
				while (value != null) {
					value = list.get(idx.getAndIncrement());
					stack.push(value != null ? new TreeNode(value) : null);
				}
			}
			TreeNode left = stack.pop();
			if (idx.get() >= list.size()) {
				return null;
			}
			TreeNode current = stack.isEmpty() ? new TreeNode(list.get(idx.getAndIncrement())) : stack.pop();
			TreeNode right = _deserialize(list, idx, stack, false);
			current.left = left;
			current.right = right;
			return current;
		}
	}
	public interface Problem extends Function<TreeNode, Integer[]> {
		
	}
}
