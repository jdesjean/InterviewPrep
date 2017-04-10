package org.ip;

import org.ip.primitives.ArrayUtils;

public class Buffer {
	public static void main(String[] s) {
		Buffer buffer = new Buffer(new File(30));
		int n = 5;
		char[] output = new char[n];
		int size = 0;
		do {
			size = buffer.read(output,n);
			if (size == 0) break;
			ArrayUtils.print(output, size);
			System.out.println("");
		} while (size == n);
	}
	private File file;
	public Buffer(File file) {
		this.file=file;
	}
	int remaining = 0;
	int remainingIndex = 0;
	char[] buffer = new char[File.k];
	public int read(char[] output, int n) {
		int h = 0;
		for (int i = 0, size = 0; h < n; i++) {
			if (remaining > 0) {
				for (int j = remainingIndex; remaining > 0 && h < n; j++,h++,remaining--,remainingIndex++) {
					output[h] = buffer[j];
				}
			} else {
				size = file.readK(buffer);
				int j;
				for (j = 0; j < size && h < n; j++,h++) {
					output[h] = buffer[j];
				}
				remaining = size-j;
				remainingIndex = j;
				if (size != file.k) break;
			}
		}
		return h;
	}
	public static class File {
		int size;
		int pos = 0;
		public static final int k = 10;
		public File(int size) {
			this.size=size;
		}
		public int readK(char[] output) {
			int i = 0;
			for (; i < k && pos < size; pos++, i++) {
				output[i] = String.valueOf((i % 10)).charAt(0);
			}
			return i; 
		}
	}
	
}
