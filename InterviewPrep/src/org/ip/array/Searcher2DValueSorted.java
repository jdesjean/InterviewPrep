package org.ip.array;

//EPI: 12.6
//Time: O(n+m), Space: O(1)
public class Searcher2DValueSorted implements Searcher2DValue{
	public static void main(String[] s) {
		int[][] matrix = new int[][]{
			{-1,2,4,4,6},
			{1,5,5,9,21},
			{3,6,6,9,22},
			{3,6,8,10,24},
			{6,8,9,12,25},
			{8,10,12,13,40}
		};
		Point2D point = new Point2D();
		Searcher2DValue searcher = new Searcher2DValueSorted();
		int[] array = new int[]{6,5,40,0};
		for (int i= 0; i < array.length; i++) {
			if (searcher.search(matrix, array[i], point)) {
				System.out.println(point.toString());
			} else {
				System.out.println("not found");
			}
		}
		
	}
	@Override
	public boolean search(int[][] array, int value, Point2D point) {
		int col = array[0].length - 1;
		int row = 0;
		for (;col >= 0 && row < array.length; ) {
			int current = array[row][col];
			if (current == value) {
				point.x = row;
				point.y = col;
				return true;
			} else if (current > value) {
				col--;
			} else {
				row++;
			}
		}
		return false;
	}

}
