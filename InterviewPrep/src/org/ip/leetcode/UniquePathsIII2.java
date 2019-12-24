package org.ip.leetcode;

// leetcode 980
public class UniquePathsIII2 {
	public static void main(String[] s) {
		int[][] grid = new int[][] {
			{1,0,0,0},
			{0,0,0,0},
			{0,0,2,-1}
			};
		UniquePathsIII2 paths = new UniquePathsIII2();
		System.out.println(paths.uniquePathsIII(grid));
	}
	public int uniquePathsIII(int[][] grid) {
        if(grid == null || grid.length == 0) return 0;
        int zeros = 0;
        for(int i=0;i<grid.length;i++) {
            for(int j=0;j<grid[0].length;j++) {
                if(grid[i][j] == 0) {
                	zeros |= code(i, j, grid[0].length);
                }
            }
        }
        Integer[][][] memo = new Integer[grid.length][grid[0].length][1 << (grid.length * grid[0].length)]; // 2^LC
        for(int i=0;i<grid.length;i++) {
            for(int j=0;j<grid[0].length;j++) {
                if(grid[i][j] == 1) {
                    return helper(grid, i, j, zeros, memo);
                }
            }
        }
        return 0;
    }
	public int code(int r, int c, int C) {
        return 1 << (r * C + c);
    }
    
    private int helper(int[][] grid, int i, int j, int zeros, Integer[][][] memo) {
    	if(grid[i][j] == 2) {
            if(zeros == 0) {
                return 1;
            }
            return 0;
        }
    	if (memo[i][j][zeros] != null) {
    		return memo[i][j][zeros];
    	}
        grid[i][j] = -1;
        int count = 0;
        if (i > 0 && grid[i - 1][j] != -1) {
            count += helper(grid, i-1, j, zeros ^ code(i - 1, j, grid[0].length), memo);
        }
        if (i + 1 < grid.length && grid[i + 1][j] != -1) {
            count += helper(grid, i+1, j, zeros ^ code(i + 1, j, grid[0].length), memo);
        }
        if (j > 0 && grid[i][j - 1] != -1) {
            count += helper(grid, i, j-1, zeros ^ code(i, j - 1, grid[0].length), memo);
        }
        if (j + 1 < grid[i].length && grid[i][j + 1] != -1) {
            count += helper(grid, i, j+1, zeros ^ code(i, j + 1, grid[0].length), memo);
        }
        grid[i][j] = 0;
        memo[i][j][zeros] = count;
        return count;
    }
    public static class Result {
        int count;
        public Result(int count) {
            this.count = count;
        }
    }
}

