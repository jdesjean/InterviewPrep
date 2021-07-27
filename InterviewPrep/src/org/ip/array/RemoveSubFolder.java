package org.ip.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/remove-sub-folders-from-the-filesystem/">LC: 1233</a>
 */
public class RemoveSubFolder {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { Arrays.asList("/a","/c/d","/c/f"), new String[] { "/a","/a/b","/c/d","/c/d/e","/c/f" }};
		Object[] tc2 = new Object[] { Arrays.asList("/a"), new String[] { "/a","/a/b/c","/a/b/d" }};
		Object[] tc3 = new Object[] { Arrays.asList("/a/b/c","/a/b/ca","/a/b/d"), new String[] { "/a/b/c","/a/b/ca","/a/b/d" }};
		Object[] tc4 = new Object[] { Arrays.asList("/ah/al"), new String[] { "/ah/al/am","/ah/al" }};
		Object[][] tcs = new Object[][] { tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] { new Solver(), new SortSolver() };
		Test.apply(solvers, tcs);
	}
	static class SortSolver implements Problem {

		@Override
		public List<String> apply(String[] t) {
			if (t == null || t.length == 0) return new ArrayList<>();
			Arrays.sort(t);
			List<String> res = new ArrayList<>();
			res.add(t[0]);
			for (int i = 1; i < t.length; i++) {
				if (t[i].startsWith(res.get(res.size() - 1) + "/")) continue;
				res.add(t[i]);
			}
			return res;
		}
		
	}
	static class Solver implements Problem {

		@Override
		public List<String> apply(String[] t) {
			FileSystem fs = new FileSystem();
			for (int i = 0; i < t.length; i++) {
				fs.add(t[i]);
			}
			List<String> res = new ArrayList<>();
			fs.forEach(path -> {
				res.add(path);
			});
			return res;
		}
		
	}
	static class FileSystem {
		Directory root = new Directory("root");
		public void add(String string) {
			if (string == null || string.length() == 0) return;
			String[] folders = string.split("/");
			if (folders.length <= 1) return;
			Directory parent = root;
			for (int i = 1; i < folders.length; i++) {
				if (parent.isEnd) return;
				Directory current = parent.childs.computeIfAbsent(folders[i], (k) -> new Directory(k));
				parent = current;
			}
			parent.isEnd = true;
		}
		public void forEach(Consumer<String> consumer) {
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, Directory> child : root.childs.entrySet()) {
				sb.setLength(0);
				_forEach(consumer, sb, child.getKey(), child.getValue());
			}
		}
		void _forEach(Consumer<String> consumer, StringBuilder sb, String name, Directory directory) {
			if (directory == null) {
				return;
			}
			sb.append("/");
			sb.append(name);
			if (directory.isEnd) {
				consumer.accept(sb.toString());
				return;
			}
			int length = sb.length();
			for (Map.Entry<String, Directory> child : directory.childs.entrySet()) {
				sb.setLength(length);
				_forEach(consumer, sb, child.getKey(), child.getValue());
			}
		}
	}
	static class Directory {
		final String value;
		Map<String, Directory> childs = new TreeMap<>();
		boolean isEnd = false;
		public Directory(String value) {
			this.value = value;
		}
	}
	interface Problem extends Function<String[], List<String>> {
		
	}
}
