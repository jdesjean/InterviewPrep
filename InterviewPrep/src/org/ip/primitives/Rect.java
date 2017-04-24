package org.ip.primitives;

public class Rect {
	public static void main(String[] s) {
		Rect rect = new Rect (0,0,3,3);
		Rect[] rects = new Rect[]{new Rect (-3,0,0,3), new Rect (0,-3,3,0), new Rect (3,0,6,3), new Rect (3,3,6,6), new Rect (1,1,2,2), new Rect (-1,-1,2,2)};
		for (int i = 0; i < rects.length; i++) {
			System.out.println(rect.intersect(rects[i]).isEmpty());
		}
	}
	public static final Rect EMPTY = new Rect(0,0,0,0);
	public final int x1;
	public final int y1;
	public final int x2;
	public final int y2;
	public Rect(int x1, int y1, int x2, int y2) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	public boolean isEmpty() {
		return width() == 0 || height() == 0;
	}
	public int width() {
		return x2-x1;
	}
	public int height() {
		return y2-y1;
	}
	public Rect intersect(Rect rect) {
		if (x2 <= rect.x1 || rect.x2 <= x1 || y2 <= rect.y1 || rect.y2 <= y1) return EMPTY;
		else {
			int xx1 = Math.max(x1, rect.x1);
			int yy1 = Math.max(y1, rect.y1);
			int xx2 = Math.min(x2, rect.x2);
			int yy2 = Math.min(y2, rect.y2);
			return new Rect(xx1,yy1,xx2,yy2);
		}
	}
}
