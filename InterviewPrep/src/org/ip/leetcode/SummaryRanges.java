package org.ip.leetcode;

import java.util.ArrayList;
import java.util.List;

// leetcode 228
public class SummaryRanges {
	public static void main(String[] s) {
		SummaryRanges summaryRanges = new SummaryRanges();
		System.out.println(summaryRanges.summaryRanges(new int[] {0,2,3,4,6,8,9}) + "=" + "[0,2->4,6,8->9]");
		System.out.println(summaryRanges.summaryRanges(new int[] {0,1,2,4,5,7}) + "=" + "[0->2,4->5,7]");
	}
	public List<String> summaryRanges(int[] nums) {
		List<String> list = new ArrayList<String>();
		if (nums.length == 0) return list;
		if (nums.length == 1) {
			list.add(nums[0] + "");
		}
        for (int i = 1, prev = 0; i < nums.length; i++) {
        	if (nums[i] - nums[i - 1] != 1) {
        		if (prev != i - 1) {
        			list.add(nums[prev] + "->" + nums[i-1]);
        		} else {
        			list.add(nums[i-1] + "");
        		}
        		if (i == nums.length - 1) {
        			list.add(nums[i] + "");
        		}
        		prev = i;
        	} else if (i == nums.length - 1) {
        		list.add(nums[prev] + "->" + nums[i]);
        	}
        }
        return list;
    }
}
