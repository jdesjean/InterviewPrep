package org.ip.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <a href="https://leetcode.com/problems/dot-product-of-two-sparse-vectors/">LC: 1570</a>
 */
public class SparseVectorHashMap {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new SparseVectorHashMap(new int[] {1,0,0,2,3}), new SparseVectorHashMap(new int[] {0,3,0,4,0}), 8};
		Object[] tc2 = new Object[] {new SparseVectorHashMap(new int[] {0,1,0,0,0}), new SparseVectorHashMap(new int[] {0,0,0,0,2}), 0};
		Object[] tc3 = new Object[] {new SparseVectorHashMap(new int[] {0,1,0,0,2,0,0}), new SparseVectorHashMap(new int[] {1,0,0,0,3,0,4}), 6};
		List<Object[]> tcs = Arrays.asList(tc1, tc2, tc3);
		for (Object[] tc : tcs) {
			System.out.print(((SparseVectorHashMap) tc[0]).dotProduct((SparseVectorHashMap) tc[1]) + "," + String.valueOf(tc[2]));
			System.out.println();
		}
	}
	Map<Integer, Integer> values = new HashMap<>();
	SparseVectorHashMap(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
        	if (nums[i] == 0) continue;
        	values.put(i, nums[i]);
        }
    }
    
	// Return the dotProduct of two sparse vectors
    public int dotProduct(SparseVectorHashMap vec) {
    	Map<Integer, Integer> v1 = values.size() <= vec.values.size() ? values : vec.values;
    	Map<Integer, Integer> v2 = values.size() <= vec.values.size() ? vec.values : values;
    	int product = 0;
    	for (Entry<Integer,Integer> index : v1.entrySet()) {
    		Integer n2 = v2.get(index.getKey());
    		if (n2 == null) continue;
    		
    		Integer n1 = index.getValue();
    		product += n1 * n2;
    	}
    	return product;
    }
}
