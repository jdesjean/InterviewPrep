package org.ip;

import org.ip.array.Point2D;

// Google 10/03/19
// leetcode 74
public class SortedMatrix {
	public static void main(String[] s) {
		int[][] matrix = new int[][]{
			{-1,2,4,4 ,6},
			{1 ,5,5,9 ,21},
			{3 ,6,6,9 ,22},
			{3 ,6,8,10,24},
			{6 ,8,9,12,25}
		};
		Point2D point = new Point2D();
		SortedMatrix searcher = new SortedMatrix();
		for (int y = 0; y < matrix.length; y++) {
			for (int x = 0; x < matrix[y].length; x++) {
				if (searcher.search(matrix, matrix[y][x], point)) {
					System.out.println(point.toString());
				} else {
					System.out.println("not found");
				}
			}
		}
		//int[] array = new int[]{12,6,5,25,0};
		/*int[] array = new int[]{2};
		for (int i= 0; i < array.length; i++) {
			if (searcher.search(matrix, array[i], point)) {
				System.out.println(point.toString());
			} else {
				System.out.println("not found");
			}
		}*/
	}
	public boolean search(int[][] m, int value, Point2D p) {
		return search(m, value, p, new Point2D(0,0), new Point2D(m[0].length-1,m.length-1));
	}
	private boolean search(int[][] m, int value, Point2D p, Point2D c1, Point2D c2) {
		if (!inbounds(m, c1) || !inbounds(m,c2) || !before(c1,c2)) return false;
		if (m[c1.y][c1.x] == value) {
			p.x = c1.x;
			p.y = c1.y;
			return true;
		}
		Point2D mid = binarySearch(m, c1, c2, value);
		if (inbounds(m, mid) &&  m[mid.y][mid.x] == value) {
			p.x = mid.x;
			p.y = mid.y;
			return true;
		}
		return search(m, value, p, new Point2D(c1.x,mid.y), new Point2D(mid.x - 1, c2.y)) ||
				search(m, value, p, new Point2D(mid.x, c1.y), new Point2D(c2.x, mid.y - 1));
	}
	private Point2D binarySearch(int[][] m, Point2D c1, Point2D c2, int value) {
		int diagDist = Math.min(c2.x - c1.x, c2.y-c1.y);
		c2 = new Point2D(c1.x + diagDist, c1.y + diagDist);
		for (;before(c1,c2);) {
			int my = c1.y + (c2.y - c1.y) / 2;
 			int mx = c1.x + (c2.x - c1.x) / 2;
			if (m[my][mx] == value) return new Point2D(mx,my);
			else if (m[my][mx] > value) c2 = new Point2D(mx - 1, my - 1);
			else c1 = new Point2D(mx + 1, my + 1);
		}
 		return c1;
	}
	private int area(Point2D c1, Point2D c2) {
		return (c2.x-c1.x)*(c2.y-c1.y);
	}
	private boolean before(Point2D c1, Point2D c2) {
		return c1.x <= c2.x && c1.y <= c2.y;
	}
	private boolean inbounds(int[][] m, Point2D p) {
		return p.y >= 0 && p.x >= 0 && p.y < m.length && p.x < m[p.y].length;
	}
}
