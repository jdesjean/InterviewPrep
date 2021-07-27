package org.ip.array;

import java.util.Collections;
import java.util.PriorityQueue;

import org.ip.array.Utils;

/**
 * <a href="https://leetcode.com/discuss/interview-question/553399/">OA 2019</a>
 */
public class MinimumAmplitude {
	public static void main(String[] s) {
		System.out.println(new MinimumAmplitude().solve(new int[] {-1, 3, -1, 8, 5, 4}, 3));
		System.out.println(new MinimumAmplitude().solve(new int[] {10, 10, 3, 4, 10}, 3));
	}
	public int solve(int[] a, int k) {
		PriorityQueue<Integer> min = new PriorityQueue<>(Collections.reverseOrder());
		PriorityQueue<Integer> max = new PriorityQueue<>();
		for (int i = 0; i < a.length; i++) {
			min.add(a[i]);
			if (min.size() > k + 1) {
				max.add(min.remove());
				if (max.size() > k + 1) {
					max.remove();
				}
			}
		}
		int[][] res = new int[2][k + 1];
		int temp = !min.isEmpty() ? min.peek() : 0;
		for (int i = Math.min(k, min.size() - 1); i >= 1; i--) {
			if (min.isEmpty()) break;
			int val = min.remove();
			int diff = val - min.peek();
			res[0][i] = Math.abs(diff);
		}
		for (int i = 1; i <= k; i++) {
			res[0][i] = res[0][i] + res[0][i - 1];
		}
		if (max.size() > 0 && max.size() <= k) {
			res[1][Math.min(k, max.size())] = max.peek() - temp;
		}
		for (int i = Math.min(k, max.size() - 1); i >= 1; i--) {
			if (max.isEmpty()) break;
			int val = max.remove();
			int diff = max.peek() - val;
			res[1][i] = diff;
		}
		for (int i = 1; i <= k; i++) {
			res[1][i] = res[1][i] + res[1][i - 1];
		}
		int m = 0;
		for (int i = 0; i <= k; i++) {
			m = Math.max(res[0][i] + res[1][k - i], m);
		}
		return amplitude(a) - m;
	}
	int amplitude(int[] a) {
		return Utils.max(a) - Utils.min(a);
	}
}
