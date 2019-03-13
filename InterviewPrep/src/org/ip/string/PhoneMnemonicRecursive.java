package org.ip.string;

import org.ip.string.Visitors.CharArrayVisitor;

// EPI: 7.7
public class PhoneMnemonicRecursive {
	char[][] PHONE_MAP = new char[][] {{' '}, {' '}, {'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'}, {'j', 'k', 'l'}, {'m','n','o'}, {'p', 'q', 'r', 's'}, {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}}; 
	public static void main(String[] s) {
		PhoneMnemonicRecursive mnemonic = new PhoneMnemonicRecursive();
		mnemonic.generate(new int[] {2,2,7,6,6,9,6}, (char[] array, int length) -> {org.ip.array.Utils.println(array, length);} );
	}
	public void generate(int[] number, CharArrayVisitor visitor) {
		generate(number, new char[number.length], 0, visitor);
	}
	private void generate(int[] number, char[] mnemonic, int write, CharArrayVisitor visitor) {
		if (write >= number.length) {
			visitor.visit(mnemonic, number.length);
			return;
		}
		for (int i = 0; i < PHONE_MAP[number[write]].length; i++) {
			mnemonic[write] = PHONE_MAP[number[write]][i];
			generate(number, mnemonic, write + 1, visitor);
		}
	}
}
