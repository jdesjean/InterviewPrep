package org.ip.primitives;

public class Utils {
	public static void main(String[] s) {
		testSqrtD();
	}
	public static void testGcd() {
		System.out.println(gcd(12,3));
		System.out.println(gcd(12,2));
		System.out.println(gcd(12,4));
		System.out.println(gcd(12,6));
		System.out.println(gcd(12,5));
	}
	public static void testSqrt() {
		for (int i = 0; i <= 25; i++) {
			System.out.println(i + ":"+ sqrt(i));
		}
	}
	public static void testSqrtD() {
		for (double i = 24; i <= 25; i++) {
			System.out.println(i + ":"+ sqrt(i));
		}
	}
	public static int gcd(int u, int v) {
	    // simple cases (termination)
	    if (u == v || u == 0 || v == 0) return Math.max(u, v);

	    int uEven = (~u & 1);
	    int vEven = (~v & 1);
	    if (uEven == 1 || vEven == 1) {
	    	int uvEven = uEven & vEven;
	    	return gcd(u >> uEven, v >> vEven) << uvEven;
	    }

	    // reduce larger argument
	    //2 odd numbers gives an even 
	    int max = Math.max(u, v);
	    int min = Math.min(u, v);
	    return gcd((max - min) >> 1, min);
	}
	//EPI: 12.4
	//Time: O(log(n)), Space: O(1)
	public static int sqrt(int v) {
		int left = 0;
		int right = v;
		for (;left <= right;) {
			int mid = (right - left)/2 + left;
			int pow = mid * mid;
			if (pow == v) return mid;
			else if (pow > v) {
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		return left - 1;
	}
	
	//EPI: 12.5
	//Time: O(log(x/s)), Space: O(1)
	public static double sqrt(double v) {
		double left;
		double right;
		if (v < 0) {
			left = v;
			right = 1;
		} else {
			int sqrt = sqrt((int)v);
			if (sqrt*sqrt == (int)v) return sqrt;
			left = sqrt;
			right = sqrt+1;
		}
		
		for (;compareEps(left,right) <= 0;) {
			double mid = (right - left) / 2 + left;
			double pow = mid*mid;
			if (compareEps(pow,v) == 0) return mid;
			else if (compareEps(pow,v) > 0) {
				right = mid;
			} else {
				left = mid;
			}
		}
		return left - 1;
	}
	public static double EPS = 0.0001;
	public static double compareEps(double d1, double d2) {
		double diff = (d1 - d2)/d2;
		return Math.abs(diff) < EPS ? 0 : diff;
	}
	
}
