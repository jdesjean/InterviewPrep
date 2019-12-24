package org.ip.recursion;

import java.util.function.Consumer;

// EPI 2018: 15.2
public class Queens3 {
	public static void main(String[] s) {
		int[] a = new int[8];
		final IntegerWrapper wrapper = new IntegerWrapper();
		new Queens3().solve((x) -> {
			System.out.println(wrapper.value++);
			Queens2.printChess(x);
		} , a);
	}
	public void solve(Consumer<int[]> consumer, int[] result) {
		_solve(consumer, result, 0);
	}
	public void _solve(Consumer<int[]> consumer, int[] result, int index) {
		if (index == result.length) {
			consumer.accept(result);
			return;
		}
		for (int i = 0; i < result.length; i++) {
			if (!canPlace(result, index, i)) continue;
			result[index] = i;
			_solve(consumer, result, index + 1);
		}
	}
	public boolean canPlace(int[] result, int index, int pos) {
		//rows
		for (int i = 0; i < index; i++) {
			if (result[i] == pos) return false;
		}
		//diags
		for (int i = 0; i < index; i++) {
			int x = index - i;
			int y = Math.abs(pos - result[i]);
			if (x == y) return false;
		}
		return true;
	}
	private static class IntegerWrapper {
		int value;
	}
}
