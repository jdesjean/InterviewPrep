package org.ip.string;

// EPI 2018: 6.1
public class Convert2 {
	public static void main(String[] s) {
		System.out.println(new Convert2().fromString("-314"));
		System.out.println(new Convert2().fromInt(-314));
	}
	public int fromString(String s) {
		int value = 0;
		int sign = s.charAt(0) == '-' ? -1 : 1;
		for (int i = sign == 1 ? 0 : 1; i < s.length(); i++) {
			value *= 10;
			value += (s.charAt(i) - '0');
		}
		return sign * value;
	}
	public String fromInt(int n) {
		boolean sign = n >= 0;
		n = Math.abs(n);
		StringBuilder sb = new StringBuilder();
		while (n > 0) {
			sb.append(n % 10);
			n /= 10;
		}
		if (!sign) {
			sb.append('-');
		}
		return sb.reverse().toString();
	}
}
