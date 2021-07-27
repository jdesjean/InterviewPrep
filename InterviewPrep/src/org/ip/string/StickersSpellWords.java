package org.ip.string;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.ToIntBiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/stickers-to-spell-word/">LC: 691</a>
 */
public class StickersSpellWords {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { 3, new String[] {"with", "example", "science"}, "thehat"};
		Object[] tc2 = new Object[] { -1, new String[] {"notice", "possible"}, "basicbasic"};
		Object[] tc3 = new Object[] { 3, new String[] {"control","heart","interest","stream","sentence","soil","wonder","them","month","slip","table","miss","boat","speak","figure","no","perhaps","twenty","throw","rich","capital","save","method","store","meant","life","oil","string","song","food","am","who","fat","if","put","path","come","grow","box","great","word","object","stead","common","fresh","the","operate","where","road","mean"}, "stoodcrease"};
		
		Object[][] tcs = new Object[][] { tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new BottomUpSolver(), new TopDownSolver() };
		Test.apply(solvers, tcs);
	}
	public static class TopDownSolver implements Problem {

		@Override
		public int applyAsInt(String[] t, String u) {
			if (find(t, u) != -1) return 1;
			int[] root = freq(u);
			int[][] freqs = freqs(root, t);
			Map<String, Integer> cache = new HashMap<>();
			cache.put("", 0);
			return apply(cache, freqs, stringnify(root));
		}
		int apply(Map<String, Integer> cache, int[][] freqs, String sCurrent) {
			Integer iCache = cache.get(sCurrent);
			if (iCache != null) {
				return iCache;
			}
			int min = -1;
			for (int i = 0; i < freqs.length; i++) {
				int count = 0;
				StringBuilder sb = new StringBuilder();
				int[] freq = new int[26];
				for (int j = 0; j < sCurrent.length(); j++) {
					int index = sCurrent.charAt(j) - 'a';
					if (++freq[index] <= freqs[i][index]) {
						count++;
					} else {
						sb.append(sCurrent.charAt(j));
					}
				}
				if (count != 0) {
					int apply = apply(cache, freqs, sb.toString());
					if (apply != -1) {
						if (min == -1) {
							min = apply + 1;
						} else {
							min = Math.min(min, apply + 1);
						}
					}
				}
			}
			cache.put(sCurrent, min);
			return min;
		}
		String stringnify(int[] freq) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < freq.length; i++) {
				if (freq[i] <= 0) continue;
				char c = (char)('a' + i);
				for (int j = 0; j < freq[i]; j++) {
					sb.append(c);
				}
			}
			return sb.toString();
		}
		int[][] freqs(int[] root, String[] t) {
			int[][] freqs = new int[t.length][];
			for (int i = 0; i < freqs.length; i++) {
				freqs[i] = freq(t[i]);
			}
			return freqs;
		}
		int[] freq(String t) {
			int[] freq = new int[26];
			for (int i = 0; i < t.length(); i++) {
				freq[t.charAt(i) - 'a']++;
			}
			return freq;
		}
		int find(String[] t, String u) {
			for (int i = 0; i < t.length; i++) {
				if (u.compareTo(t[i]) == 0) return i;
			}
			return -1;
		}
	}
	public static class BottomUpSolver implements Problem {

		@Override
		public int applyAsInt(String[] t, String u) {
			if (find(t, u) != -1) return 1;
			RootFreq rootFreq = new RootFreq(u);
			Freq[] freqs = freqs(rootFreq, t);
			int[] cache = new int[(1 << u.length())];
			Arrays.fill(cache, Integer.MAX_VALUE);
			cache[0] = 0;
			int[] current = new int[rootFreq.freqs.length];
			int[] prev = new int[rootFreq.freqs.length];
			for (int i = 0; i < cache.length; i++) {
				if (cache[i] == Integer.MAX_VALUE) continue;
				Freq.fromInt(prev, rootFreq, i);
				for (int j = 0; j < freqs.length; j++) {
					Freq.add(current, prev, freqs[j]);
					int k = Freq.hashCode(rootFreq, current);
					//int k = add(i, freqs[j].hashCode(), rootFreq);
					cache[k] = Math.min(cache[i] + 1, cache[k]);
				}
			}
			return cache[cache.length - 1] == Integer.MAX_VALUE ? -1 : cache[cache.length - 1];
		}
		// Takes twice as much time as Freq.add + Freq.hashCode. 
		int add(int a, int b, RootFreq freq) {
			int res = 0;
			int shift = 0;
			for (int i = freq.freqs.length - 1; i >= 0; i--) {
				int mask = ((1 << freq.freqs[i]) - 1) << shift;
				int ma = countOne(a & mask);
				int mb = countOne(b & mask);
				int min = Math.min(ma + mb, freq.freqs[i]); 
				res |= ((1 << min) - 1) << shift;
				shift += freq.freqs[i];
			}
			return res;
		}
		Freq[] freqs(RootFreq rootFreq, String[] t) {
			Set<Freq> freqs = new HashSet<>();
			for (int i = 0; i < t.length; i++) {
				Freq freq = new Freq(rootFreq, t[i]);
				if (freq.count() == 0) continue;
				freqs.add(freq);
			}
			Freq[] array = new Freq[freqs.size()];
			freqs.toArray(array);
			return array;
		}
		int find(String[] t, String u) {
			for (int i = 0; i < t.length; i++) {
				if (u.compareTo(t[i]) == 0) return i;
			}
			return -1;
		}
		static class RootFreq {
			int[] freqs;
			Map<Integer, Integer> characters = new HashMap<>();
			public RootFreq(String s) {
				int[] freqs = new int[26];
				int count = 0;
				for (int i = 0; i < s.length(); i++) {
					int index = s.charAt(i) - 'a';
					if (freqs[index]++ == 0) {
						count++;
					}
				}
				this.freqs = new int[count];
				for (int i = 0, j = 0; i < 26; i++) {
					if (freqs[i] == 0) continue;
					characters.put(i, j);
					this.freqs[j++] = freqs[i];
				}
			}
		}
		private static int countOne(int i) {
			int count = 0;
			while (i != 0) {
	            i &= (i - 1);
	            count++;
	        }
			return count;
		}
		static class Freq {
			RootFreq parent;
			int[] freqs;
			/**
			 * We can use int as target has length in the range [1, 15]
			 */
			private int hash = -1;
			private int count = -1;
			public Freq(RootFreq rootFreq, int hash) {
				parent = rootFreq;
				freqs = new int[rootFreq.freqs.length];
				this.hash = hash;
				for (int i = rootFreq.freqs.length - 1; i >= 0; i--) {
					int max = rootFreq.freqs[i];
					int count = countOne(hash & (1 << max) - 1);
					int min = Math.min(max, count);
					hash >>= max;
					freqs[i] = min;
				}
			}
			public Freq(RootFreq rootFreq, String s) {
				parent = rootFreq;
				freqs = new int[rootFreq.characters.size()];
				for (int i = 0; i < s.length(); i++) {
					int index = s.charAt(i) - 'a';
					Integer idx = rootFreq.characters.get(index);
					if (idx == null) continue;
					freqs[idx]++;
				}
			}
			public Freq(RootFreq rootFreq, int[] freqs) {
				parent = rootFreq;
				this.freqs = freqs;
			}
			/**
			 * We can use int as target has length in the range [1, 15]
			 */
			@Override
			public int hashCode() {
				if (hash != -1) return hash;
				hash = hashCode(parent, freqs);
				return hash;
			}
			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				Freq other = (Freq) obj;
				return Integer.compare(hashCode(), other.hashCode()) == 0;
			}
			
			public int count() {
				if (count != -1) return count;
				count = countOne(hashCode());
				return count;
			}
			public static int hashCode(RootFreq parent, int[] freqs) {
				int hash = 0;
				for (int i = 0; i < parent.freqs.length; i++) {
					int max = parent.freqs[i];
					int count = Math.min(max, freqs[i]);
					hash <<= max;
					hash |= (1 << count) - 1;
				}
				return hash;
			}
			
			public static void add(int[] freqs, int[] a, Freq b) {
				for (int i = 0; i < a.length; i++) {
					freqs[i] = a[i] + b.freqs[i];
				}
			}
			public static void fromInt(int[] freqs, RootFreq parent, int hash) {
				for (int i = parent.freqs.length - 1; i >= 0; i--) {
					int max = parent.freqs[i];
					int count = countOne(hash & (1 << max) - 1);
					int min = Math.min(max, count);
					hash >>= max;
					freqs[i] = min;
				}
			}
		}
	}
	public interface Problem extends ToIntBiFunction<String[], String>{
		
	}
}
