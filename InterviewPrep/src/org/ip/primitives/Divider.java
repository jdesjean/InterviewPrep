package org.ip.primitives;

//EPI 2018: 4.6
public class Divider {
	public static void main(String[] s) {
		Divider d = new Divider();
		System.out.println(d.divide(12,4));
		System.out.println(d.divide(12,5));
		System.out.println(d.divide(15,3));
	}
	Substracter substracter = new Substracter();
	Adder adder = new Adder();
	public int divide(int a, int b) {
		int power = 1 << 30;
		long bpower = (long)b << 30;
		int result = 0;
		while (a >= b) {
			while (bpower > a) {
				bpower >>= 1;
				power >>= 1;
			}
			result = adder.add(result, power);
			a = substracter.substract(a, (int)bpower);
		}
		return result;
	}
}
