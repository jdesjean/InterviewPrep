package org.ip.leetcode;

import java.util.ArrayList;
import java.util.List;

// leetcode 229
public class MajorityII {
	public static void main(String[] s) {
		MajorityII majorityII = new MajorityII();
		System.out.println(majorityII.majorityElement(new int[] {3,2,3}));
		System.out.println(majorityII.majorityElement(new int[] {1,1,1,3,3,2,2}));
		System.out.println(majorityII.majorityElement(new int[] {1,3,2,1,3,1,2}));
		System.out.println(majorityII.majorityElement(new int[] {1,1,1,3,3,2,2,2}));
	}
	public List<Integer> majorityElement(int[] nums) {
		List<Integer> results = new ArrayList<Integer>(2);
		if (nums.length == 0) return results;
        Candidate c1 = new Candidate(nums[0],1);
        Candidate c2 = new Candidate(nums[0],0);
        for (int i = 1; i < nums.length; i++) {
        	if (nums[i] == c1.value) {
        		c1.count++;
        	} else if (nums[i] == c2.value) {
        		c2.count++;
        	} else if (c1.count == 0) {
        		c1.count = 1;
        		c1.value = nums[i];
        	} else if (c2.count == 0){
        		c2.count = 1;
        		c2.value = nums[i];
        	} else {
        		c1.count--;
        		c2.count--;
        	}
        }
        c1.count = 0;
        c2.count = 0;
        for (int i = 0; i < nums.length; i++) {
        	if (nums[i] == c1.value) {
        		c1.count++;
        	} else if (nums[i] == c2.value) {
        		c2.count++;
        	}
        }
        int min = nums.length / 3;
        if (c1.count > min) {
        	results.add(c1.value);
        }
        if (c2.count > min) {
        	results.add(c2.value);
        }
        return results;
    }
	public static class Candidate {
		int value;
		int count;
		public Candidate(int value, int count) {
			super();
			this.value = value;
			this.count = count;
		}
	}
}
