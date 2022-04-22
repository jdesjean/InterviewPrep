package org.ip.tree;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.ip.Test;

public class DFSConstantSpace {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {3, new Integer[] {1,2,3,4,5}};
		Object[] tc2 = new Object[] {6, new Integer[] {1,2,3,4,5,null,null,6,null,7,null,8,null,9,null}};
		Object[][] tcs = new Object[][] {tc1, tc2};
		for (Object[] tc : tcs) {
			tc[1] = TreeNode.fromBfs((Integer[]) tc[1]);
		}
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		TreeNode prev;
		
		@Override
		public void accept(TreeNode t) {
			if (t == null) return;
			TreeNode current = t;
			while (current != null) {
				current = current.left;
				if (current.left == null) {
					
				}
				prev = t;
			}
		}
		
	}
	interface Problem extends Consumer<TreeNode> {
		
	}
}
