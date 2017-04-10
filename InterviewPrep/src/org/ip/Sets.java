package org.ip;

import org.ip.primitives.ArrayUtils;

public class Sets {
	public static void main(String[] s) {
		subsets(new SubsetVisitor(){
			@Override
			public void visit(int[] subset, int length) {
				ArrayUtils.println(subset,length);
			}
		},new int[]{1,2,3});
	}
	public static void subsets(SubsetVisitor visitor, int[] set) {
		subsets(visitor,set,0,new int[set.length], 0);
	}
	private interface SubsetVisitor {
		public void visit(int[] subset, int length);
	}
	private static void subsets(SubsetVisitor visitor, int[] set, int read, int[] subset, int write) {
		if (read == set.length) {
			visitor.visit(subset, write);
			return;
		}
		subset[write] = set[read];
		subsets(visitor,set,read+1,subset,write+1);
		subsets(visitor,set,read+1,subset,write);
	}
}
