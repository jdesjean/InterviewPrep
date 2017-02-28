package org.ip.graph;

import java.util.Deque;
import java.util.LinkedList;

public class SnakeAndLadder {
	public static final int[] BOARD = new int[]{1,15,3,4,7,6,7,8,27,10,11,12,13,14,15,16,17,29,19,20,21,22,23,16,35,26,27,28,29,30,31,30,33,12,35,36};
	public static final Integer EMPTY = 0;
	public static void main(String[] s) {
		System.out.println(new DFSMinimizer().min(BOARD, 36));
	}
	public interface Minimizer {
		public int min(int[] board, int target);
	}
	public static class DFSMinimizer implements Minimizer {

		@Override
		public int min(int[] board, int target) {
			Deque<Integer> queue = new LinkedList<Integer>();
			boolean[] visited = new boolean[board.length];
			queue.add(board[0]);
			queue.add(EMPTY);
			visited[0] = true;
			int depth = 0;
			while (!queue.isEmpty()) {
				Integer current = queue.removeFirst();
				if (current == target) return depth;
				if (current == EMPTY && !queue.isEmpty()) {
					depth++;
					queue.addLast(EMPTY);
				}
				for (int i = current; i < Math.min(current+6, target); i++) {
					if (visited[board[i]-1]) continue;
					visited[board[i]-1] = true;
					queue.addLast(board[i]);
				}
			}
			return depth;
		}
		
	}
}
