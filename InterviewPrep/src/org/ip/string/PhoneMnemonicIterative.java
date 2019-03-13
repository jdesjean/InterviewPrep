package org.ip.string;

import org.ip.string.Visitors.CharArrayVisitor;

//EPI: 7.7
public class PhoneMnemonicIterative {
	char[][] PHONE_MAP = new char[][] {{' '}, {' '}, {'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'}, {'j', 'k', 'l'}, {'m','n','o'}, {'p', 'q', 'r', 's'}, {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}}; 
	public static void main(String[] s) {
		PhoneMnemonicIterative mnemonic = new PhoneMnemonicIterative();
		mnemonic.generate(new int[] {2,2,7,6,6,9,6}, (char[] array, int length) -> {org.ip.array.Utils.println(array, length);} );
	}
	public void generate(int[] number, CharArrayVisitor visitor) {
		int[] write = new int[number.length];
		char[] mnemonic = new char[number.length];
		for (int depth = 0; depth >= 0;) {
			if (depth >= number.length) {
				visitor.visit(mnemonic, number.length);
				depth--;
				// like adding 1 to a number expressed as an array;
				while (depth >= 0) {
					write[depth]++;
					if (write[depth] >= PHONE_MAP[number[write[depth]]].length) {
						write[depth--] = 0;
					} else {
						break;
					}
				}
			} else {
				mnemonic[depth] = PHONE_MAP[number[depth]][write[depth]];
				depth++;
			}
		}
	}
	public void generate2(int[] number, CharArrayVisitor visitor) {
		int[] write = new int[number.length];
		char[] mnemonic = new char[number.length];
		for (int depth = 0; depth >= 0;) {
			if (write[depth] < PHONE_MAP[number[depth]].length) {
				mnemonic[depth] = PHONE_MAP[number[depth]][write[depth]];
				if (depth < number.length - 1) {
					depth++;
				} else {
					visitor.visit(mnemonic, number.length);
					write[depth]++;
				}
			} else {
				while (depth >= 0 && write[depth] >= PHONE_MAP[number[write[depth]]].length) {
					write[depth--] = 0;
				}
				if (depth >= 0) {
					write[depth]++;
				}
			}
		}
	}
}
