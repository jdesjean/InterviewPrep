package org.ip;

import java.util.HashSet;
import java.util.Set;

// Dropbox
public class SubsequenceZero {
	public static void main(String[] s) {
		System.out.println(new SubsequenceZero().solve(new int[] {1,2,-3,1}));
		System.out.println(new SubsequenceZero().solve(new int[] {1,2,-2,1}));
	}
	public boolean solve(int[] a) {
		if (a.length == 0) return false;
	    Set<Integer> partialSums = new HashSet<Integer>();
	    int partSum = 0;
	    for (int i = 0; i < a.length; i++) {
	        partSum += a[i];
	        if (partialSums.contains(partSum)) {
	        	return true;
	        }
	        if (partSum == 0) {
	        	return true;
	        }
	        partialSums.add(partSum);
	    }
	    return false;
	}
}
