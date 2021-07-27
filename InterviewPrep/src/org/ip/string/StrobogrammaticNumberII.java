package org.ip.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/strobogrammatic-number-ii/">LC: 247</a>
 */
public class StrobogrammaticNumberII {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {Arrays.asList("0","1","8"), 1};
		Object[] tc2 = new Object[] {Arrays.asList("11","69","88","96"), 2};
		Object[] tc3 = new Object[] {Arrays.asList("101", "111", "181", "609", "619", "689", "808", "818", "888", "906", "916", "986"), 3};
		Object[] tc4 = new Object[] {Arrays.asList("1001","1111","1691","1881","1961","6009","6119","6699","6889","6969","8008","8118","8698","8888","8968","9006","9116","9696","9886","9966"), 4};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {
		private final static char[][] EVEN = new char[][] {{'0','0'}, {'1', '1'}, {'6', '9'}, {'8', '8'}, {'9', '6'}};
		private final static char[] ODD = new char[] {'0', '1', '8'};
		
		@Override
		public List<String> apply(Integer t) {
			List<String> res = new ArrayList<>();
			_apply((x) -> res.add(x), new char[t], 0, 0);
			return res;
		}
		void _apply(Consumer<String> consumer, char[] buffer, int index, int length) {
			if (length >= buffer.length) {
				consumer.accept(new String(buffer));
				return;
			}
			if (buffer.length % 2 == 1 && index == buffer.length / 2) {
				for (int i = 0; i < ODD.length; i++) {
					buffer[index] = ODD[i];
					_apply(consumer, buffer, index + 1, length + 1);
				}
			} else {
				for (int i = index == 0 ? 1 : 0; i < EVEN.length; i++) {
					buffer[index] = EVEN[i][0];
					buffer[buffer.length - index - 1] = EVEN[i][1];
					_apply(consumer, buffer, index + 1, length + 2);
				}
			}
		}
	}
	public interface Problem extends Function<Integer, List<String>> {
		
	}
}
