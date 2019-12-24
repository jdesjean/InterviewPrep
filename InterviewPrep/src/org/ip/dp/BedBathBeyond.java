package org.ip.dp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

// EPI 2018: 16.7
public class BedBathBeyond {
	public static void main(String[] s) {
		Set<String> dict = new HashSet<String>();
		dict.add("bed");
		dict.add("bath");
		dict.add("beyond");
		dict.add("hand");
		dict.add("bat");
		new BedBathBeyond().solve((x) -> System.out.println(x), "bedbathbeyond", dict);
	}
	public void solve(Consumer<List<String>> consumer, String s, Set<String> dict) {
		int[] cache = new int[s.length() + 1];
		cache[0] = 1;
		for (int i = 1; i <= s.length(); i++) {
			for (int j = 0; j < i; j++) {
				if (cache[0] == 0) continue;
				String tmp = s.substring(j, i);
				if (dict.contains(tmp)) {
					cache[i] = tmp.length();
				}
			}
		}
		if (cache[s.length()] == 0) return;
		List<String> result = new ArrayList<String>();
		for (int i = s.length(); i > 0; i -= cache[i]) {
			result.add(s.substring(i - cache[i], i));
		}
		Collections.reverse(result);
		consumer.accept(result);
	}
}
