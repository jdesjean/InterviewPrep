package org.ip.string;

import org.ip.string.Visitors.CharArrayVisitor;

// EPI: 7.10
public class IPV4 {
	public static void main(String[] s) {
		IPV4 ipv4 = new IPV4();
		ipv4.generate("19216811", (char[] text, int len) -> {org.ip.array.Utils.println(text, len);});
	}
	public void generate(String origin, CharArrayVisitor visitor) {
		char[] buffer = new char[15];
		for (int i = 0; i < 3; i++) {
			buffer[i] = origin.charAt(i);
			if (!isWithinBounds(buffer, 0, i)) break;
			buffer[i + 1] = '.';
			for (int j = 0; j < 3; j++) {
				int jj = j+i+2;
				int jjj = j+i+1;
				buffer[jj] = origin.charAt(jjj);
				if (!isWithinBounds(buffer, i+2, jj)) break;
				buffer[jj + 1] = '.';
				for (int k = 0; k < 3; k++) {
					int kkk = k+jjj+1;
					if (kkk >= origin.length() - 1) break;
					int kk = k+jj+2;
					buffer[kk] = origin.charAt(kkk);
					if (!isWithinBounds(buffer, jj+2, kk)) break;
					buffer[kk + 1] = '.';
					for (int l = 0; l < 3; l++) {
						int lll = kkk+l+1;
						if (lll >= origin.length()) break;
						int ll =  l+kk+2;
						buffer[ll] = origin.charAt(lll);
						if (!isWithinBounds(buffer, kk+2, ll)) break;
						if (lll != origin.length() - 1) continue;
						visitor.visit(buffer, ll+1);
					}
				}
			}
		}
	}
	private boolean isWithinBounds(char[] buffer, int l, int r) {
		int len = r - l + 1;
		if (len <= 2) return true;
		if (buffer[l] > '2' || (buffer[l] == '2' && buffer[l + 1] > '5') || (buffer[l] == '2' && buffer[l + 1] == '5' && buffer[l + 2] > '5')) return false;
		return true;
	}
}
