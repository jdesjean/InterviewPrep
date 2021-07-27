package org.ip.interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/exclusive-time-of-functions/">LC: 636</a>
 */
public class ExclusiveTimeOfFunctions {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[] {3,4}, 2, Arrays.asList(new String[] {
			"0:start:0",
			"1:start:2",
			"1:end:5",
			"0:end:6"
		})};
		Object[] tc2 = new Object[] {new int[] {8}, 1, Arrays.asList(new String[] {
			"0:start:0","0:start:2","0:end:5","0:start:6","0:end:6","0:end:7"
		})};
		Object[] tc3 = new Object[] {new int[] {7,1}, 2, Arrays.asList(new String[] {
			"0:start:0","0:start:2","0:end:5","1:start:6","1:end:6","0:end:7"
		})};
		Object[] tc4 = new Object[] {new int[] {8,1}, 2, Arrays.asList(new String[] {
			"0:start:0","0:start:2","0:end:5","1:start:7","1:end:7","0:end:8"
		})};
		Object[] tc5 = new Object[] {new int[] {1}, 1, Arrays.asList(new String[] {
			"0:start:0","0:end:0"
		})};
		Object[] tc6 = new Object[] {new int[] {6}, 1, Arrays.asList(new String[] {
			"0:start:0","0:start:1","0:start:2","0:end:3","0:end:4","0:end:5"
		})};
		Object[] tc7 = new Object[] {new int[] {1,1,2}, 3, Arrays.asList(new String[] {
			"0:start:0","0:end:0","1:start:1","1:end:1","2:start:2","2:end:2","2:start:3","2:end:3"
		})};
		Object[][] tcs = new Object[][] {tc2, tc1, tc2, tc3, tc4, tc5, tc6, tc7};
		Problem[] solvers = new Problem[] { new Iterative(), new Recursive(), new Iterative2(), new Iterative3() };
		Test.apply(solvers, tcs);
	}
	public static class Iterative3 implements Problem {

		@Override
		public int[] apply(Integer t, List<String> u) {
			Deque<Integer> stack = new LinkedList<>();
			int[] res = new int[t];
			int prev = 0;
			for (int i = 0; i < u.size(); i++) {
				String[] split = u.get(i).split(":");
				int proc = Integer.parseInt(split[0]);
				boolean isStart = split[1].equals("start");
				int time = Integer.parseInt(split[2]);
				if (isStart) {
					if (!stack.isEmpty()) {
						res[stack.peek()] += time - prev;
					}
					prev = time;
					stack.push(proc);
				} else {
					int v = time - prev + 1;
					res[proc] += v;
					prev = time + 1;
				}
			}
			return res;
		}
		
	}
	public static class Iterative2 implements Problem {

		@Override
		public int[] apply(Integer t, List<String> u) {
			int[] res = new int[t];
			Deque<int[]> stack = new LinkedList<>();
			for (int i = 0; i < u.size(); i++) {
				String[] split = u.get(i).split(":");
				int proc = Integer.parseInt(split[0]);
				boolean isStart = split[1].equals("start");
				int time = Integer.parseInt(split[2]);
				if (isStart) {
					stack.push(new int[] {proc, time});
				} else {
					int diff = time - stack.pop()[1] + 1;
					res[proc] += diff;
					if (!stack.isEmpty()) {
						res[stack.peek()[0]] -= diff;
					}
				}
			}
			return res;
		}
		
	}
	public static class Iterative implements Problem {

		@Override
		public int[] apply(Integer t, List<String> u) {
			if (u == null) return null;
			if (u.size() == 0) return new int[0];
			List<Token> tokens = parse(u);
			int[] res = new int[t];
			Deque<Integer> stack = new LinkedList<>();
			stack.push(tokens.get(0).proc);
			for (int i = 1; i < tokens.size(); i++) {
				Token prev = tokens.get(i - 1);
				Token current = tokens.get(i);
				int proc = -1;
				if (current.event == Event.START) {
					if (prev.event == Event.END) { // parent
						if (!stack.isEmpty()) {
							proc = stack.peek();
						} else {
							stack.push(current.proc);
							continue; // nothing to add
						}
					} else {
						proc = prev.proc;
					}
					stack.push(current.proc);
				} else {
					stack.pop();
					proc = current.proc;
				}
				int end = current.event == Event.END ? current.ts + 1 : current.ts;
				int start = prev.event == Event.END ? prev.ts + 1 : prev.ts;
				res[proc] += end - start;
			}
			return res;
		}
		
	}
	public static class Recursive implements Problem {

		@Override
		public int[] apply(Integer t, List<String> u) {
			int[] res = new int[t];
			AtomicInteger index = new AtomicInteger(0);
			List<Token> tokens = parse(u);
			while (index.get() < u.size()) {
				_solve(res, tokens, index);
			}
			return res;
		}
		
		int _solve(int[] res, List<Token> u, AtomicInteger index) {
			if (index.get() == u.size()) {
				return 0;
			}
			Token token = u.get(index.getAndIncrement());
			int start = token.ts;
			int proc = token.proc;
			int parent = 0;
			for (Token next = u.get(index.get());
					next.event == Event.START; 
					next = u.get(index.get())) {
				parent += _solve(res, u, index);
			}
			token = u.get(index.getAndIncrement());
			int end = token.ts;
			int val = end - start + 1 - parent;
			res[proc] += val;
			return end - start + 1;
		}
		
	}
	static List<Token> parse(List<String> u) {
		List<Token> tokens = new ArrayList<>(u.size());
		for (String string : u) {
			String[] splits = string.split(":");
			int ts = Integer.parseInt(splits[2]);
			int proc = Integer.parseInt(splits[0]);
			tokens.add(new Token(proc, Event.valueOf(splits[1].toUpperCase()), ts));
		}
		return tokens;
	}
	public static class Token {
		final int proc;
		final Event event;
		final int ts;
		public Token(int proc, Event event, int ts) {
			this.proc = proc; this.event = event; this.ts = ts;
		}
	}
	enum Event {
		START, END
	}
	interface Problem extends BiFunction<Integer, List<String>, int[]> {
		
	}
}
