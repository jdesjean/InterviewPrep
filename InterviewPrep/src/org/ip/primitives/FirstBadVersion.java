package org.ip.primitives;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * <a href="https://leetcode.com/problems/first-bad-version/">LC: 278</a>
 */
public class FirstBadVersion {
	public static void main(String[] s) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object[] tc1 = new Object[] {4, (VersionControl)((v) -> v >= 4), 5};
		Object[] tc2 = new Object[] {1, (VersionControl)((v) -> v >= 1), 1};
		List<Object[]> tcs = Arrays.asList(tc1, tc2);
		Class<? extends Function<Integer, Integer>>[] solvers = new Class[] {Solver.class};
		for (Object[] tc : tcs) {
			System.out.print(tc[0]);
			for (Class<? extends Function<Integer, Integer>> solver : solvers) {
				Function<Integer, Integer> function = solver.getConstructor(VersionControl.class).newInstance(tc[1]);
				System.out.print(":" + function.apply((Integer) tc[2]));
			}
			System.out.println();
		}
	}
	static class Solver implements Function<Integer, Integer> {
		private VersionControl versionControl;

		public Solver(VersionControl versionControl) {
			this.versionControl = versionControl;
		}

		@Override
		public Integer apply(Integer t) {
			int l = 1;
			for (int h = t; l < h; ) {
				int m = l + (h - l) / 2;
				if (versionControl.isBadVersion(m)) {
					h = m;
				} else {
					l = m + 1;
				}
			}
			return l;
		}
	}
	public interface VersionControl {
		boolean isBadVersion(int version);
	}
}
