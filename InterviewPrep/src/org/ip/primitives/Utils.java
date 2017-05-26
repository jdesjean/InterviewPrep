package org.ip.primitives;

public class Utils {
	public static void main(String[] s) {
		testSqrt();
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
	public static int sqrt(int u) {
		int left = 0;
		int right = u;
		for (;left <= right;) {
			int mid = (right - left)/2 + left;
			int pow = mid * mid;
			if (pow == u) return mid;
			else if (pow > u) {
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		return left - 1;
	}
}
