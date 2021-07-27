package org.ip.string;

import java.util.HashMap;
import java.util.Map;

// EPI 2018: 12.10
public class StringDecomposition {
	public static void main(String[] s) {
		String sentence = "amanaplanacanal";
		String[] words = new String[] {"can", "apl", "ana"};
		System.out.println(new StringDecomposition().solve(sentence, words));
		sentence = "aplcancan";
		words = new String[] {"can", "apl", "can"};
		System.out.println(new StringDecomposition().solve(sentence, words));
		sentence = "aplcancan";
		words = new String[] {"can", "apl"};
		System.out.println(new StringDecomposition().solve(sentence, words));
	}
	public int solve(String sentence, String[] words) {
		int wlength = words[0].length();
		Map<String,Integer> map = new HashMap<String,Integer>();
		for (int i = 0; i < words.length; i++) {
			int count = map.getOrDefault(words[i], 0);
			map.put(words[i], ++count);
		}
		String[] cache = new String[sentence.length()];
		for (int i = 0; i < sentence.length() - wlength + 1; i++) {
			String word = sentence.substring(i, i + wlength);
			Integer pos = map.get(word);
			if (pos != null) {
				cache[i] = word;
			}
		}
		int index = -1;
		for (int i = sentence.length() - wlength; i >= 0; i--) {
			int length = 0;
			Map<String,Integer> freq = new HashMap<String,Integer>();
			for (int j = i; j >= 0 && cache[j] != null; j -= wlength) {
				length++;
				int count = freq.getOrDefault(cache[j], 0);
				if (++count > map.get(cache[j])) {
					break;
				}
				freq.put(cache[j], count);
				if (length == words.length) {
					index = i - words.length * wlength + wlength;
					break;
				}
			}
		}
		return index;
	}
}
