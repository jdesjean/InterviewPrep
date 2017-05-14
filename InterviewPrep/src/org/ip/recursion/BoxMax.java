package org.ip.recursion;

import java.util.Arrays;
import java.util.Comparator;

import org.ip.array.Subsequence;
import org.ip.array.Subsequence.Sizer;

//http://www.geeksforgeeks.org/dynamic-programming-set-21-box-stacking-problem/
public class BoxMax {
	public static void main(String[] s) {
		Box[] boxes = new Box[]{box(4,6,7),box(1,2,3),box(4,5,6),box(10,12,32)};
		System.out.println(max(boxes));
	}
	public interface Box {
		public int h();
		public int w();
		public int d();
	}
	public static class BoxUnrotated implements Box{
		public int h;
		public int w;
		public int d;
		public BoxUnrotated(int h, int w, int d) {
			super();
			this.h = h;
			this.w = w;
			this.d = d;
		}
		@Override
		public int h() {
			return h;
		}
		@Override
		public int w() {
			return w;
		}
		@Override
		public int d() {
			return d;
		}
		@Override
		public String toString() {
			return "h:"+h()+",w:"+w()+",d:"+d();
		}
	}
	public static class BoxRotatedX90 implements Box{
		private Box box;
		public BoxRotatedX90(Box box){
			this.box=box;
		}

		@Override
		public int h() {
			return box.w();
		}
		@Override
		public int w() {
			return Math.min(box.d(), box.h());
		}
		@Override
		public int d() {
			return Math.max(box.d(), box.h());
		}
		@Override
		public String toString() {
			return "h:"+h()+",w:"+w()+",d:"+d();
		}
	}
	public static class BoxRotatedY90 implements Box{
		private Box box;
		public BoxRotatedY90(Box box){
			this.box=box;
		}

		@Override
		public int h() {
			return box.d();
		}
		@Override
		public int w() {
			return Math.min(box.w(), box.h());
		}
		@Override
		public int d() {
			return Math.max(box.w(), box.h());
		}
		@Override
		public String toString() {
			return "h:"+h()+",w:"+w()+",d:"+d();
		}
	}
	public static Box box(int h, int w, int d) { return new BoxUnrotated(h,w,d); }
	public static int area(Box box){return box.w()*box.d();}
	public static class ComparatorBoxArea implements Comparator<Box> {
		@Override
		public int compare(Box o1, Box o2) {
			int area1 = area(o1);
			int area2 = area(o2);
			return area1 - area2;
		}
	}
	public static class ComparatorBoxStrict implements Comparator<Box> {

		@Override
		public int compare(Box o1, Box o2) {
			if (o1.w() > o2.w() && o1.d() > o2.d()) return 1;
			else if (o1.w() < o2.w() && o1.d() < o2.d()) return -1;
			return 0;
		}
		
	}
	public static class SizerBoxHeight implements Sizer<Box> {

		@Override
		public int size(Box t) {
			return t.h();
		}
		
	}
	public static Box[] getRotated(Box[] boxes) {
		Box[] boxesRotated = new Box[boxes.length*3];
		for (int i = 0; i < boxes.length; i++) {
			boxesRotated[i*3] = boxes[i];
			boxesRotated[i*3+1] = new BoxRotatedX90(boxes[i]);
			boxesRotated[i*3+2] = new BoxRotatedY90(boxes[i]);
		}
		return boxesRotated;
	}
	public static int max(Box[] boxes) {
		boxes = getRotated(boxes);
		Arrays.sort(boxes,new ComparatorBoxArea());
		return Subsequence.longestIncreasing(boxes, new ComparatorBoxStrict(), new SizerBoxHeight());
	}
}
