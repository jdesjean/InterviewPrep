package org.ip.sort;

import java.util.Comparator;
import java.util.Iterator;

//EPI: 11.5
//Time: O(nlog(k)), Space: O(k)
public class Neighbor {
	public static void main(String args[]) {
		Point[] points = new Point[] { point(1, 1), point(2, 2), point(4, 1), point(-1, -1), point(3, 3), point(-1, 2) };
		int k = 2;
		for (Iterator<Point> iterator = new IteratorArrayTopKPartitionMin<Point>(points,k,new PointPairComparator(point(0,0))); iterator.hasNext();) {
			System.out.print(iterator.next());
			if (iterator.hasNext()) System.out.print(",");
		}
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
}
