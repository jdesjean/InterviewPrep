package org.ip;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * <a href="https://leetcode.com/discuss/interview-question/397524/">OA 2020</a>
 */
public class DelayedProjects {
	public static void main(String[] s) {
		tc1(new DFS());
		tc2(new DFS());
		tc3(new DFS());
	}
	public static void tc1(Solver solver) {
		Problem p = parse("2 1\r\n" + 
				"		B A\r\n" + 
				"		C B\r\n" + 
				"		B");
		System.out.println(solver.solve(p));
		
	}
	public static void tc2(Solver solver) {
		Problem p = parse("5 2\r\n" + 
				"P Q\r\n" + 
				"P S\r\n" + 
				"Q R\r\n" + 
				"R T\r\n" + 
				"S T\r\n" + 
				"Q S");
		System.out.println(solver.solve(p));
	}
	public static void tc3(Solver solver) {
		Problem p = parse("8 2\r\n" + 
				"B A\r\n" + 
				"C B\r\n" + 
				"C E\r\n" + 
				"D C\r\n" + 
				"D F\r\n" + 
				"E A\r\n" + 
				"F E\r\n" + 
				"G F\r\n" + 
				"B F");
		System.out.println(solver.solve(p));
	}
	public static class DFS implements Solver {
		private Set<Character> EMPTY = new HashSet<Character>();
		
		@Override
		public String solve(Problem p) {
			Set<Character> delayed = new TreeSet<Character>();
			Deque<Character> queue = new LinkedList<Character>();
			queue.addAll(p.delayed);
			while (!queue.isEmpty()) {
				Character project = queue.remove();
				if (!delayed.add(project)) {
					continue;
				}
				for (Character dependency : p.g.map.getOrDefault(project, EMPTY)) {
					queue.add(dependency);
				}
			}
			
			return delayed.toString();
		}

	}
	public static Problem parse(String s) {
		Scanner scanner = new Scanner(s);
		int k = scanner.nextInt();
		int j = scanner.nextInt();
		Graph g = new Graph();
		scanner.nextLine();
		for (int i = 0; i < k; i++) {			
			String line = scanner.nextLine();
			String[] chars = line.trim().split(" ");
			g.add(chars[1].charAt(0), chars[0].charAt(0));
		}
		String line = scanner.nextLine();
		String[] lineSplit = line.trim().split(" ");
		List<Character> delayed = new ArrayList<Character>(j);
		for (int i = 0; i < j; i++) {
			delayed.add(lineSplit[i].charAt(0));
		}
		return new Problem(g, delayed);
	}
	public interface Solver {
		public String solve(Problem p);
	}
	private static class Problem {
		final Graph g;
		final List<Character> delayed;
		public Problem(Graph g, List<Character> delayed) {
			this.g = g;
			this.delayed = delayed;
		}
	}
	private static class Graph {
		Map<Character, Set<Character>> map = new HashMap<>();
		public void add(Character c1, Character c2) {
			Set<Character> set = map.computeIfAbsent(c1, ignored -> new HashSet<>());
			set.add(c2);
		}
	}
}
