package org.ip.sort;

import java.util.Comparator;

import org.ip.ArrayUtils;

public class Neighbor {
	public static void main(String args[]) {
		Point[] points = new Point[] { point(1, 1), point(2, 2), point(4, 1), point(-1, -1), point(3, 3), point(-1, 2) };
		int k = 2;
		neareast(points, point(0, 0), k);
		ArrayUtils.println(points,0,k-1);
	}
	
	public static Point point(int x, int y) {
		return new Point(x,y);
	}

	public static class Point {
		int x;
		int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public double distanceTo(Point p) {
			int dx = x - p.x;
			int dy = y - p.y;
			return Math.sqrt(dx * dx + dy * dy);
		}

		@Override
		public String toString() {
			return "Point [x=" + x + ", y=" + y + "]";
		}
		
	}
	public static class PointPairComparator implements Comparator<Point> {
		private final Point point;
		public PointPairComparator(Point point) {
			if (point == null) throw new IllegalArgumentException();
			this.point=point;
		}
		@Override
		public int compare(Point o1, Point o2) {
			return (int)(point.distanceTo(o1) - point.distanceTo(o2));
		}
	}

	public static void neareast(Point[] points, Point point, int k) {
		PointPairComparator comparator = new PointPairComparator(point);
		TopK<Point> topk = new TopKPartitionMin<Point>();
		topk.solve(points, k, comparator);
	}
}
