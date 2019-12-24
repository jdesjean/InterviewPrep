package org.ip.leetcode;

import java.util.HashMap;
import java.util.Map;

// leetcode 1
public class TwoSum {
	public static void main(String[] s) {
		TwoSum TwoSum = new TwoSum();
		int[] result = TwoSum.twoSum(new int[] {2,7,11,15}, 9);
		System.out.println(result[0] + "," + result[1]);
	}
	public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();
        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            Integer index = map.get(nums[i]);
            if (index != null) {
                return new int[]{index, i};
            }
            map.put(diff, i);
        }
        return new int[0];
    }
}
