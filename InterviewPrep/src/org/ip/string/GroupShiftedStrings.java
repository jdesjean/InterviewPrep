package org.ip.string;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <a href="https://leetcode.com/problems/group-shifted-strings/">LC: 249</a>
 */
public class GroupShiftedStrings {
	public static void main(String[] s) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object[] tc1 = new Object[] {Arrays.asList(
				Arrays.asList("abc","bcd","xyz"),
				Arrays.asList("az","ba"),
				Arrays.asList("acef"),
				Arrays.asList("a","z")), new String[] {"abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"}};
		Object[] tc2 = new Object[] {Arrays.asList(), new String[] {}}; 
		List<Object[]> tcs = Arrays.asList(tc1, tc2);
		Class<? extends Problem>[] solvers = new Class[] {Solver.class};
		for (Object[] tc : tcs) {
			System.out.print(tc[0]);
			System.out.println();
			for (Class<? extends Problem> solver : solvers) {
				Problem function = solver.getConstructor().newInstance();
				System.out.print(function.apply((String[]) tc[1]));
				System.out.println();
			}
			System.out.println();
		}
	}
	public static class Solver implements Problem {

		@Override
		public List<List<String>> apply(String[] t) {
			Map<List<Integer>, List<String>> map = new HashMap<>();
			for (String s : t) {
				List<Integer> key = new ArrayList<>();
				for (int i = 1; i < s.length(); i++) {
					int n1 = s.charAt(i) - 'a';
					int n2 = s.charAt(i - 1) - 'a';
					int diff = n2 - n1;
					if (diff < 0) {
						diff += 26;
					}
					key.add(diff);
				}
				List<String> values = map.computeIfAbsent(key, k -> new ArrayList<>());
				values.add(s);
			}
			return map.values().stream().collect(Collectors.toList());
		}
		
	}
	public interface Problem extends Function<String[], List<List<String>>> {
		
	}
}
