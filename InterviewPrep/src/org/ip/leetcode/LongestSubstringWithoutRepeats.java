package org.ip.leetcode;

import java.util.HashMap;
import java.util.Map;

// leetcode 3
public class LongestSubstringWithoutRepeats {
	public static void main(String[] s) {
		LongestSubstringWithoutRepeats LongestSubstringWithoutRepeats = new LongestSubstringWithoutRepeats();
		System.out.println(LongestSubstringWithoutRepeats.lengthOfLongestSubstring("aab"));
		System.out.println(LongestSubstringWithoutRepeats.lengthOfLongestSubstring("abba"));
		System.out.println(LongestSubstringWithoutRepeats.lengthOfLongestSubstring("abab"));
		System.out.println(LongestSubstringWithoutRepeats.lengthOfLongestSubstring("baa"));
	}
	public int lengthOfLongestSubstring(String s) {
        Map<Character,Integer> map = new HashMap<Character,Integer>();
        int max = 0;
        int prev = -1;
        for (int i = 0; i < s.length(); i++) {
            prev = Math.max(map.getOrDefault(s.charAt(i), -1), prev);
            max = Math.max(max, i - prev);
            map.put(s.charAt(i), i);
        }
        return max;
    }
}
