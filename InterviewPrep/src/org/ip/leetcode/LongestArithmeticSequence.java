package org.ip.leetcode;

import java.util.HashMap;
import java.util.Map;

//leetcode 1027
public class LongestArithmeticSequence {
	public static void main(String[] s) {
		System.out.println(new LongestArithmeticSequence().longestArithSeqLength(new int[] {3,6,9,12}));
		System.out.println(new LongestArithmeticSequence().longestArithSeqLength(new int[] {9,4,7,2,10}));
	}
	public int longestArithSeqLength(int[] A) {
        Map<Integer,Integer>[] cache = new HashMap[A.length];
        int max = 0;
        for (int i = 0; i < A.length; i++) {
            cache[i] = new HashMap<Integer,Integer>();
            cache[i].put(A[i], 1);
            for (int j = 0; j < i; j++) {
                int diff = A[i] - A[j];
                int prev = cache[j].getOrDefault(diff, 1) + 1;
                int current = cache[i].getOrDefault(diff, 0);
                int cmax = Math.max(prev, current);
                cache[i].put(diff, cmax);
                max = Math.max(max, cmax);
            }
        }
        return max;
    }
}
