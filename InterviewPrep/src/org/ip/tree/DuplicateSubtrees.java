package org.ip.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/find-duplicate-subtrees/">LC: 652</a>
 */
public class DuplicateSubtrees {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[][] {{2,4},{4}}, new Integer[] {1,2,3,4,null,2,4,null,null,4}};
		Object[][] tcs = new Object[][] {tc1};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] { new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public List<TreeNode> apply(TreeNode t) {
			List<TreeNode> res = new ArrayList<>();
			postOrder(t, new HashMap<>(), res);
			return res;
		}
		String postOrder(TreeNode t, Map<String, Integer> map, List<TreeNode> list) {
			if (t == null) {
				return "N";
			}
			String left = postOrder(t.left, map, list);
			String right = postOrder(t.right, map, list);
			String current = left + "," + right + "," + t.val;
			if (map.compute(current, (k, v) -> v == null ? 1 : v + 1) == 2) {
				list.add(t);
			}
			return current;
		}
	}
	interface Problem extends Function<TreeNode, List<TreeNode>> {
		
	}
}
