package org.ip.string;

//EPI 2018: 6.5
public class Palindrome2 {
	public static void main(String[] s) {
		String s1 = "A man, a plan, a canal, Panama";
		String s2 = "Ray a Ray";
		String s3 = "Able was I, ere I saw Elba!";
		System.out.println(new Palindrome2().isPalindrome(s1));
		System.out.println(new Palindrome2().isPalindrome(s2));
		System.out.println(new Palindrome2().isPalindrome(s3));
	}
	public boolean isPalindrome(String s) {
		for (int l = 0, r = s.length() -1; l < r;) {
			if (!Character.isLetterOrDigit(s.charAt(l))) {
				l++;
			} else if (!Character.isLetterOrDigit(s.charAt(r))) { 
				r--;
			} else if (Character.toLowerCase(s.charAt(l)) == Character.toLowerCase(s.charAt(r))) {
				l++;
				r--;
			} else {
				return false;
			}
		}
		return true;
	}
}
