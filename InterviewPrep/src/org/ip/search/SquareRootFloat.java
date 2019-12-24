package org.ip.search;

// EPI 2018: 11.5
public class SquareRootFloat {
	public static void main(String[] s) {
		System.out.println(new SquareRootFloat().find(300));
	}
	SquareRootInt sqi = new SquareRootInt();
	double EPS = 0.00001;
	public double find(double value) {
		double left = 0;
		double right = 0;
		if (value >= 1) {
			left = 1;
			right = value / 2;
		} else {
			left = value;
			right = 1;
		}
		double result = -1;
		for (; right > left && right - left > EPS; ) {
			double mid = left + (right - left) / 2;
			double square = mid * mid;
			if (Math.abs(square - value) < EPS) {
				return square;
			} else if (square > value) {
				right = mid;
			} else {
				left = mid;
				result = mid;
			}
		}
		
		return result;
	}
}
