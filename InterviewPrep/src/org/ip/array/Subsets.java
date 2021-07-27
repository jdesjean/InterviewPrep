package org.ip.array;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.ip.Test;

public class Subsets {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new int[][] {{},{1},{2},{1,2},{3},{1,3},{2,3},{1,2,3}}, new int[] {1, 2, 3}};
		Object[][] tcs = new Object[][] {tc1};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public List<List<Integer>> apply(int[] set) {
			List<List<Integer>> res = new ArrayList<>();
			int[] buffer = new int[set.length];
			subset((buf, idx) -> {
				List<Integer> list = new ArrayList<>(idx);
				res.add(list);
				for (int i = 0; i < idx; i++) {
					list.add(buf[i]);
				}
			},set,0,buffer,0);
			return res;
		}
		public static void subset(BiConsumer<int[], Integer> consumer, int[] input, int i, int[] output, int j) {
			if (i == input.length) {
				consumer.accept(output, j);
				return;
			}
			subset(consumer,input,i+1,output,j);
			output[j] = input[i];
			subset(consumer,input,i+1,output,j+1);
		}
		
	}
	interface Problem extends Function<int[], List<List<Integer>>> {
		
	}
}
