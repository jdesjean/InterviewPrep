package org.ip.array;

import java.util.BitSet;

/**
 * OA 2019
 * <a href="https://leetcode.com/discuss/interview-question/341295/Google-or-Online-Assessment-2019-or-Fill-Matrix">OA 2019</a>
 * <br> Related: <a href="https://en.wikipedia.org/wiki/Magic_square">Wiki</a>
 */
/*
 * There are three functions:
fillMatrixOdd for n being odd
fillMatrixDoublyEven for n being doubly even; multiples of 4
fillMatrixSinglyEven for n being singly even; 4*k + 2 where an integer k >= 0
For singly even case, I found this link(https://en.wikipedia.org/wiki/Strachey_method_for_magic_squares) very helpful.
 */
public class MagicSquare2 {
	public static void main(String[] s) {
		MagicSquare2 square = new MagicSquare2();
		Utils.println(square.solve(3));
	}
	public int[][] solve(int n) {
        if(n == 0 || n == 2) return null;
        if(n % 2 == 1)
            return fillMatrixOdd(n);
        else if(n % 4 == 0)
            return fillMatrixDoublyEven(n);
        else 
            return fillMatrixSinglyEven(n);
    }
    
    public int[][] fillMatrixOdd(int n){
        int[][] arr = new int[n][n];
        
        //starting position for 1
        int i = n/2;
        int j = n-1;
        
        //put all values in magic square
        for(int num = 1; num <= n*n; ){
            if(i == -1 && j == n){
                i = 0;
                j = n-2;
            }
            else{
                if(j == n)
                    j = 0;
                if(i == -1)
                    i = n-1;
            }
            
            if(arr[i][j] != 0){
                i++;
                j -= 2;
                continue;
            }
            else{
                arr[i][j] = num++;
            }
            
            i--;
            j++;
        }
        
        return arr;
        
    }
    
    public int[][] fillMatrixDoublyEven(int n){
        int[][] arr = new int[n][n];
        int i, j;
        //fill matrix from 1 to n^2
        for(i = 0; i < n; i++){
            for(j = 0; j < n; j++){
                arr[i][j] = n*i+j+1;
            }
        }
        
        for(i = 0; i < n; i++){
            for(j = 0; j < n; j++){
                if(((i % 4 == 0 || i % 4 == 3) && (j % 4 == 1 || j % 4 == 2)) ||
                   ((i % 4 == 1 || i % 4 == 2) && (j % 4 == 0 || j % 4 == 3))){
                    arr[i][j] = n*n + 1 - arr[i][j];
                }
            }
        }
        
        return arr;
    }
    
    //hardest part
    public int[][] fillMatrixSinglyEven(int n){
        //n = 4*k+2
        int k = (n-2)/4;
        
        int[][] arr = new int[n][n];
        //create a quarter grid
        int[][] quarter = fillMatrixOdd(n/2);
        int add = n*n/4;
        
        //Using fillMatrixOdd function to complete the individual magic squares of odd order 2k + 1 in subsquares A, B, C, D, 
        //first filling up the sub-square A with the numbers 1 to n2/4, then the sub-square B with the numbers n2/4 + 1 to 2n2/4,
        //then the sub-square C with the numbers 2n2/4 + 1 to 3n2/4, //then the sub-square D with the numbers 3n2/4 + 1 to n2.
        //A
        for(int i = 0; i < n/2; i++){
            for(int j = 0; j < n/2; j++){
                arr[i][j] = quarter[i][j];
            }
        }
        
        //B
        for(int i = n/2; i < n; i++){
            for(int j = n/2; j < n; j++){
                arr[i][j] = quarter[i-n/2][j-n/2] + add;
            }
        }
        
        //C
        for(int i = 0; i < n/2; i++){
            for(int j = n/2; j < n; j++){
                arr[i][j] = quarter[i][j-n/2] + 2*add;
            }
        }
        
        //D
        for(int i = n/2; i < n; i++){
            for(int j = 0; j < n/2; j++){
                arr[i][j] = quarter[i-n/2][j] + 3*add;
            }
        }
        
        //Exchange the leftmost k columns in sub-square A with the corresponding columns of sub-square D
        for(int i = 0; i < n/2; i++){
            for(int j = 0; j < k; j++){
                //swap
                int temp = arr[i][j];
                arr[i][j] = arr[i+n/2][j];
                arr[i+n/2][j] = temp;
            }
        }
        
        //Exchange the rightmost k - 1 columns in sub-square C with the corresponding columns of sub-square B
        for(int i = 0; i < n/2; i++){
            for(int j = n-1; j > n-k ; j--){
                //swap
                int temp = arr[i][j];
                arr[i][j] = arr[i+n/2][j];
                arr[i+n/2][j] = temp;
            }
        }
        
        //Exchange the middle cell of the leftmost column of sub-square A with the corresponding cell of sub-square D. 
        //Exchange the central cell in sub-square A with the corresponding cell of sub-square D
        int temp = arr[n/4][0];
        arr[n/4][0] = arr[n-1 - n/4][0];
        arr[n-1 - n/4][0] = temp;
        
        temp = arr[n/4][n/4];
        arr[n/4][n/4] = arr[n-1 - n/4][n/4];
        arr[n-1 - n/4][n/4] = temp;
        
        
        return arr;
    }
}
