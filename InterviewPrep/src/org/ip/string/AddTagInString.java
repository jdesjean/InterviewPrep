package org.ip.string;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/add-bold-tag-in-string/">LC: 616</a>
 */
public class AddTagInString {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { "<b>abc</b>xyz<b>123</b>", "abcxyz123", new String[] {"abc","123"}};
		Object[] tc2 = new Object[] { "<b>aaabbc</b>c", "aaabbcc", new String[] {"aaa","aab","bc"}};
		Object[] tc3 = new Object[] { "<b>a</b>b<b>c</b>d<b>e</b>f", "abcdef", new String[] {"a","c","e","g"}};
		Object[] tc4 = new Object[] { "<b>xhhjzbkvpmasiypsqqjobufcqmlhdjffrdohsxgksftaekzhwzydhbfdiylihnvjlvpoptnqigszckimljbepgisnmyszfsxkx</b>y<b>fd</b>fqngytfuihe<b>poh</b>apv<b>hb</b>yhq<b>yd</b>vroflfnsyjmygtykdejfudrhxxawcewg<b>ig</b>uiwsvqrgbxrbdnrvguzjftqcsjb<b>vj</b>lbxfsvzpd<b>pm</b>tlzobwnxrtg<b>is</b>bc<b>qm</b>hugncjwgat<b>fc</b>t<b>yd</b>rya<b>kv</b>b<b>nm</b>lbiftndfefylsmlebzdumefuflwhtwijtrhhhmknklalgqjaoicmnywtvzld<b>be</b>ftk<b>ydj</b>sdkkonayhd<b>xh</b>rjazosqloilagc<b>wz</b>eezavnsqelxqhtlzymedxmkrov<b>xh</b>krgfenyhxgdroeejedbwpnkqbqknalwgxoxweyxngorvrpnf<b>kv</b>agdqkbtuayaihyhwcsdtjzzvxfavrhzgf", "xhhjzbkvpmasiypsqqjobufcqmlhdjffrdohsxgksftaekzhwzydhbfdiylihnvjlvpoptnqigszckimljbepgisnmyszfsxkxyfdfqngytfuihepohapvhbyhqydvroflfnsyjmygtykdejfudrhxxawcewgiguiwsvqrgbxrbdnrvguzjftqcsjbvjlbxfsvzpdpmtlzobwnxrtgisbcqmhugncjwgatfctydryakvbnmlbiftndfefylsmlebzdumefuflwhtwijtrhhhmknklalgqjaoicmnywtvzldbeftkydjsdkkonayhdxhrjazosqloilagcwzeezavnsqelxqhtlzymedxmkrovxhkrgfenyhxgdroeejedbwpnkqbqknalwgxoxweyxngorvrpnfkvagdqkbtuayaihyhwcsdtjzzvxfavrhzgf", new String[] {"xh","hj","zb","kv","pm","as","iy","ps","qq","jo","bu","fc","qm","lh","dj","ff","rd","oh","sx","gk","sf","ta","ek","zh","wz","yd","hb","fd","li","hn","vj","lv","po","pt","nq","ig","sz","ck","im","lj","be","pg","is","nm","ys","zf","kx"}};
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver2(), new Solver(), new Solver3(), new Solver4() };
		Test.apply(solvers, tcs);
	}
	static class Solver4 implements Problem {

		@Override
		public String apply(String t, String[] u) {
			StringBuilder sb = new StringBuilder();
			int end = -1;
			for (int i = 0, start = -1; i < t.length(); i++) {
				for (String word : u) {
					if (t.startsWith(word, i)) {
						if (end < i) start = i;
						end = Math.max(end, i + word.length());
					}
				}
				if (end == i) {
					sb.append("</b>");
				}
				if (i == start) {
					sb.append("<b>");
				}
				sb.append(t.charAt(i));
			}
			if (end == t.length()) {
				sb.append("</b>");
			}
			// need to close
			return sb.toString();
		}
		
		
	}
	static class Solver3 implements Problem {

		@Override
		public String apply(String t, String[] u) {
			Trie trie = new Trie(u);
			StringBuilder sb = new StringBuilder();
			int end = -1;
			for (int i = 0, start = -1; i < t.length(); i++) {
				Node prev = null;
				for (int j = i; j < t.length(); j++) {
					Node node = j == i ? trie.getNode(t.charAt(j)) : trie.getNode(t.charAt(j), prev);
					prev = node;
					if (node == null) break;
					if (node.end == null) continue;
					if (end < i) start = i;
					end = Math.max(end, j + 1);
				}
				if (end == i) {
					sb.append("</b>");
				}
				if (i == start) {
					sb.append("<b>");
				}
				sb.append(t.charAt(i));
			}
			if (end == t.length()) {
				sb.append("</b>");
			}
			// need to close
			return sb.toString();
		}
		
		
	}
	static class Node {
		public Map<Character, Node> childs = new HashMap<>(); //lower case letters + digits
		public String end;
	}
	static class Trie {
		private Node root = new Node();
		public Trie(String[] s) {
			for (int i = 0; i < s.length; i++) {
				add(s[i],i);
			}
		}
		public Trie(List<String> s) {
			for (int i = 0; i < s.size(); i++) {
				add(s.get(i),i);
			}
		}
		private void add(String s, int position) {
			if (s == null || s.length() == 0) return;
			Node current = root;
			for (int i = 0; i < s.length(); i++) {
				current = current.childs.computeIfAbsent(s.charAt(i), (k) -> new Node());
			}
			current.end = s;
		}
		public Node getNode(char c) {
			return getNode(c,root);
		}
		public Node getNode(char c, Node current) {
			return current.childs.get(c); 
		}
	}
	static class Solver2 implements Problem {

		@Override
		public String apply(String t, String[] u) {
			Set<String> set = new HashSet<>();
			int max = 0;
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < u.length; i++) {
				set.add(u[i]);
				max = Math.max(max, u[i].length());
				min = Math.min(min, u[i].length());
			}
			StringBuilder res = new StringBuilder();
			StringBuilder buffer = new StringBuilder();
			boolean[] mask = new boolean[t.length()];
			for (int i = 0; i < t.length(); i++) {
				buffer.setLength(0);
				int found = 0;
				for (int j = 1; j <= max && i + j - 1 < t.length(); j++) {
					buffer.append(t.charAt(i + j - 1));
					if (j < min || !set.contains(buffer.toString())) continue;
					found = Math.max(found, j);
				}
				for (int j = 0; j < found; j++) {
					mask[i + j] = true;
				}
			}
			for (int i = 0; i < t.length(); i++) {
				if ((i == 0 || !mask[i - 1]) && mask[i]) {
					res.append("<b>");
				}
				res.append(t.charAt(i));
				if (mask[i] && (i == t.length() - 1 || !mask[i + 1])) {
					res.append("</b>");
				}
			}
			return res.toString();
		}
		
	}
	static class Solver implements Problem {

		@Override
		public String apply(String t, String[] u) {
			List<int[]> intervals = new ArrayList<>();
			for (int i = 0; i < u.length; i++) {
				int prev = -1;
				while (true) {
					int next = t.indexOf(u[i], prev);
					if (next == -1) break;
					prev = next + 1;
					int[] interval = new int[] {next, next + u[i].length() - 1};
					intervals.add(interval);
				}
			}
			if (intervals.size() == 0) return t;
			intervals = merge(intervals);
			StringBuilder sb = new StringBuilder();
			for (int i = 0, j = 0; i < t.length(); i++) {
				if (j < intervals.size()) {
					if (i == intervals.get(j)[0] && i == intervals.get(j)[1]) {
						sb.append("<b>");
						sb.append(t.charAt(i));
						sb.append("</b>");
						j++;
						continue;
					} else if (i == intervals.get(j)[0]) {
						sb.append("<b>");
						sb.append(t.charAt(i));
						continue;
					} else if (i == intervals.get(j)[1]) {
						sb.append(t.charAt(i));
						sb.append("</b>");
						j++;
						continue;
					}
				}
				sb.append(t.charAt(i));
			}
			return sb.toString();
		}
		List<int[]> merge(List<int[]> intervals) {
			Collections.sort(intervals, new IntervalComparator());
			List<int[]> res = new ArrayList<int[]>();
			int[] prev = intervals.get(0);
			res.add(prev);
			for (int i = 1; i < intervals.size(); i++) {
				int[] a = intervals.get(i);
				if (overlap(prev, a)) {
					prev[0] = Math.min(a[0], prev[0]);
					prev[1] = Math.max(a[1], prev[1]);
				} else {
					res.add(a);
					prev = a;
				}
			}
			return res;
		}
		boolean overlap(int[] a, int[] b) {
			return !(b[0] > a[1] + 1 || b[1] < a[0] - 1);
		}
	}
	public static class IntervalComparator implements Comparator<int[]> {

		@Override
		public int compare(int[] o1, int[] o2) {
			return Integer.compare(o1[0], o2[0]);
		}
		
	}
	interface Problem extends BiFunction<String, String[], String> {
		
	}
}
