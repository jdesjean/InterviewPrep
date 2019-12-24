package org.ip.honor;

// Google 09/04/19
public class LongestLine {
	public static void main(String[] s) {
		Color[][] m = new Color[][]{
			{Color.BLACK, Color.BLACK, Color.WHITE, Color.BLACK},
			{Color.WHITE, Color.BLACK, Color.BLACK, Color.BLACK},
			{Color.WHITE, Color.BLACK, Color.BLACK, Color.WHITE},
			{Color.BLACK, Color.WHITE, Color.WHITE, Color.WHITE},
		};
		
		System.out.println(new LongestLine().solve(m));
	}
	public int solve(Color[][] m) {
		int max = 0;
		for (int y = 0; y < m.length; y++) {
			for (int x = 0; x < m[y].length; x++) {
				if (m[y][x] == Color.BLACK) {
					if (y == 0 || m[y - 1][x] == Color.WHITE) { // Top most Vertical
						int count = 0;
						for (int yy = y; yy < m.length; yy++) {
							if (m[yy][x] == Color.BLACK) count++;
							else break;
						}
						max = Math.max(max, count);
					}
					if (x == 0 || m[y][x - 1] == Color.WHITE) { // Left most Horizontal
						int count = 0;
						for (int xx = x; xx < m[y].length; xx++) {
							if (m[y][xx] == Color.BLACK) count++;
							else break;
						}
						max = Math.max(max, count);
					}
					if (y == 0 || x == 0 || m[y - 1][x - 1] == Color.WHITE) { // Top most Diagonal right
						int count = 0;
						for (int yy = y, xx = x; yy < m.length && xx < m[yy].length; yy++, xx++) { 
							if (m[yy][xx] == Color.BLACK) count++;
							else break;
						}
						max = Math.max(max, count);
					}
					if (y == 0 || x == m[y].length - 1 || m[y - 1][x + 1] == Color.WHITE) { // Top most diagonal left
						int count = 0;
						for (int yy = y, xx = x; yy < m.length && xx >= 0; yy++, xx--) {
							if (m[yy][xx] == Color.BLACK) count++;
							else break;
						}
						max = Math.max(max, count);
					}
				}
			}
		}
		return max;
	}
	public enum Color {
		WHITE, BLACK
	}
}
