package org.ip.string;

// EPI: 7.1
public class Convert {
	public static void main(String[] s) {
		System.out.println((new Convert("123")).toInteger());
		System.out.println((new Convert(123)).toString());
	}
	private Object data;
	public Convert(Object data) {
		this.data = data;
	}
	public String toString() {
		int d = (int)data;
		StringBuilder sb = new StringBuilder();
		while (d > 0) {
			sb.append(d % 10);
			d /= 10;
		}
		return sb.reverse().toString();
	}
	public int toInteger() {
		String d = (String)data;
		int result = 0;
		for (int i = 0; i < d.length(); i++) {
			result *= 10;
			result += Character.digit(d.charAt(i), 10);
		}
		return result;
	}
}
