package org.ip.array;

import java.util.Deque;
import java.util.LinkedList;

public class Histogram {
	public static void testMaxArea() {
		System.out.println(maxArea(new int[] { 6, 2, 5, 4, 5, 1, 6 }));
	}
	public static int maxArea(int[] a) {
		Deque<Integer> stack = new LinkedList<Integer>();
		int max = 0;
		for (int i = 0; i < a.length;) {
			if (stack.isEmpty() || a[i] > a[stack.peek()]) {
				stack.push(i++);
				continue;
			}

			int j = stack.pop();
			int size = (stack.isEmpty() ? i : i - stack.peek() - 1);
			max = Math.max(max, a[j] * size);
		}

		while (!stack.isEmpty()) {
			int j = stack.pop();
			int size = (stack.isEmpty() ? a.length - 1 : a.length - stack.peek() - 1);
			max = Math.max(max, a[j] * size);
		}

		return max;
	}
}
