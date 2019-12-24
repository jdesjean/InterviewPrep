package org.ip.primitives;

//EPI 2018: 4.11
public class Rectangle {
	public static void main(String[] s) {
		Rectangle rect = new Rectangle (0,0,3,3);
		Rectangle[] rects = new Rectangle[]{new Rectangle (-3,0,0,3), new Rectangle (0,-3,3,0), new Rectangle (3,0,6,3), new Rectangle (3,3,6,6), new Rectangle (1,1,2,2), new Rectangle (-1,-1,2,2)};
		for (int i = 0; i < rects.length; i++) {
			System.out.println(rect.intersect(rects[i]).isEmpty());
		}
	}
	public final int x1;
	public final int y1;
	public final int x2;
	public final int y2;
	public Rectangle(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	public static final Rectangle EMPTY = new Rectangle(0,0,0,0);
	public Rectangle intersect(Rectangle rect) {
		if (rect.x2 < x1 || rect.x1 > x2 || rect.y2 < y1 || rect.y1 > y2) return EMPTY;
		int xx1 = Math.max(x1, rect.x1);
		int yy1 = Math.max(y1, rect.y1);
		int xx2 = Math.min(x2, rect.x2);
		int yy2 = Math.min(y2, rect.y2);
		return new Rectangle(xx1,yy1,xx2,yy2);
	}
	public int width() {
		return x2 - x1;
	}
	public int height() {
		return y2 - y1;
	}
	public boolean isEmpty() {
		return width() == 0 || height() == 0;
	}
}
