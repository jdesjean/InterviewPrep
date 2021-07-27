package org.ip.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <a href="https://leetcode.com/problems/dot-product-of-two-sparse-vectors/">LC: 1570</a>
 */
public class SparseVectorSkip {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new SparseVectorSkip(new int[] {1,0,0,2,3}), new SparseVectorSkip(new int[] {0,3,0,4,0}), 8};
		Object[] tc2 = new Object[] {new SparseVectorSkip(new int[] {0,1,0,0,0}), new SparseVectorSkip(new int[] {0,0,0,0,2}), 0};
		Object[] tc3 = new Object[] {new SparseVectorSkip(new int[] {0,1,0,0,2,0,0}), new SparseVectorSkip(new int[] {1,0,0,0,3,0,4}), 6};
		List<Object[]> tcs = Arrays.asList(tc1, tc2, tc3);
		for (Object[] tc : tcs) {
			System.out.print(((SparseVectorSkip) tc[0]).dotProduct((SparseVectorSkip) tc[1]) + "," + String.valueOf(tc[2]));
			System.out.println();
		}
	}
	List<Integer> values = new ArrayList<>();
	private int[] nums;
	SparseVectorSkip(int[] nums) {
		this.nums = nums;
        for (int i = 0; i < nums.length; i++) {
        	if (nums[i] == 0) continue;
        	values.add(i);
        }
    }
    
	// Return the dotProduct of two sparse vectors
    public int dotProduct(SparseVectorSkip vec) {
    	SparseVectorSkip v1 = values.size() <= vec.values.size() ? this : vec;
    	SparseVectorSkip v2 = values.size() <= vec.values.size() ? vec : this;
    	int product = 0;
    	for (Integer index : v1.values) {
    		product += v1.nums[index] * v2.nums[index];
    	}
    	return product;
    }
}
