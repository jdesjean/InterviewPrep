package org.ip.leetcode;

// leetcode 980
public class UniquePathsIII {
	public static void main(String[] s) {
		int[][] grid = new int[][] {
			{1,0,0,0},
			{0,0,0,0},
			{0,0,2,-1}
			};
		UniquePathsIII paths = new UniquePathsIII();
		System.out.println(paths.uniquePathsIII(grid));
	}
	public int uniquePathsIII(int[][] grid) {
        if(grid == null || grid.length == 0) return 0;
        Result r = new Result(0);
        int zeros = 0;
        for(int i=0;i<grid.length;i++) {
            for(int j=0;j<grid[0].length;j++) {
                if(grid[i][j] == 0) zeros++;
            }
        }
       
        for(int i=0;i<grid.length;i++) {
            for(int j=0;j<grid[0].length;j++) {
                if(grid[i][j] == 1) {
                    helper(grid, i, j, r, zeros);
                }
            }
        }
        return r.count;
    }
    
    private void helper(int[][] grid, int i, int j, Result r, int zeros) {
        if(grid[i][j] == 2) {
            if(zeros == -1) {
                r.count++;
            }
            return;
        }
        grid[i][j] = -1;
        if (i > 0 && grid[i - 1][j] != -1) {
            helper(grid, i-1, j, r, zeros - 1);
        }
        if (i + 1 < grid.length && grid[i + 1][j] != -1) {
            helper(grid, i+1, j, r, zeros - 1);
        }
        if (j > 0 && grid[i][j - 1] != -1) {
            helper(grid, i, j-1, r, zeros - 1);
        }
        if (j + 1 < grid[i].length && grid[i][j + 1] != -1) {
            helper(grid, i, j+1, r, zeros - 1);
        }
        grid[i][j] = 0;
    }
    public static class Result {
        int count;
        public Result(int count) {
            this.count = count;
        }
    }
}

