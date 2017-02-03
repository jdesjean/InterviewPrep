package org.ip;

public class MathUtils {
	public static void main(String[] s) {
		for (int i = -5; i < 5; i++) {
			System.out.println(pow(2,i) + "==" + Math.pow(2,i));
		}
	}
	public static double pow(double d, int p) {
		if (p < 0) return 1/pow(d, -p);
		else if (p == 0) return 1;
		
		if ((p & 1) == 1) {
			return d*pow (d, p-1);
		} else {
			double value = pow(d,p>>1);
			return value*value;
		}
	}
}
