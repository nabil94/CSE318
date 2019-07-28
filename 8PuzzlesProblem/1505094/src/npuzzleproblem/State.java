/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npuzzleproblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author User
 */
public class State {

    int[][] board;
    int boardSize;
    State ParentState;
    int pathCost;
    int totCostHamming;
    int totCostManhattan;
    int totCostLinear;

    State(int[][] board) {
        this.boardSize = board.length;
        this.board = new int[boardSize][];
        for (int i = 0; i < board.length; i++) {
            this.board[i] = board[i].clone();
        }
        ParentState = null;
        pathCost = 0;
        totCostHamming = hammingDist(board);
        totCostManhattan = manhattan(board);
        totCostLinear = calculateLinearConf(board);
    }

    State(int[][] board, State ParentState) {
        this.boardSize = board.length;
        this.board = new int[boardSize][];
        for (int i = 0; i < board.length; i++) {
            this.board[i] = board[i].clone();
        }
        this.ParentState = ParentState;
        this.pathCost = ParentState.pathCost + 1;
        this.totCostHamming = pathCost + hammingDist(board);
        this.totCostManhattan = pathCost + manhattan(board);
        this.totCostLinear = pathCost + calculateLinearConf(board);
    }
   /* 
    public int Inversion(Integer[] array) {
        int count = 0;
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (array[i] > array[j]) {
                    count++;
                }
            }
        }
        return count;
    }
    
    public int invert(int[][] matrix){
         int n = matrix.length;
        List<Integer> arr = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] != 0) {
                    arr.add(matrix[i][j]);
                }
            }
        }
        Integer[] arr2 = new Integer[arr.size()];
        arr.toArray(arr2);
        int k = Inversion(arr2);
        return k;
    }*/
    
    public boolean IsGoalNode() {
        int n = board.length;
        if (board[n - 1][n - 1] != 0) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((board[i][j] != 0) && (board[i][j] != (i * n + j + 1))) {
                    // break;
                    return false;
                    //break;

                }

            }
        }
        return true;

    }

    public int hammingDist(int[][] board) {
        int c = 0;
        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0 && board[i][j] != (i * n + j + 1)) {
                    // break;
                    c++;

                }
            }
        }

        return c;
    }
    
     public int manhattan(int[][] mat){
        int man = 0;
        int n = mat.length;
        int row = 0;
        int col = 0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(mat[i][j]!= 0 && mat[i][j] != (i*n + j + 1)){
                   row = (mat[i][j] - 1) / n;
                   col = (mat[i][j] - 1) % n;
                   man = man + Math.abs(row - i) + Math.abs(col - j);
                  
                }
            }
        }
        
        return man;
    }

     public int RowlinearConflict(int[][] mat){
        int lc = 0;
        int n = mat.length;
        for(int i = 0; i < n; i++){
            int max = -1;
            for(int j = 0; j < n; j++){
                if(mat[i][j]!= 0 && (mat[i][j] - 1)/n == i){
                   if(mat[i][j] > max){
                       max = mat[i][j];
                   }
                   else{
                       lc++;
                   }
                  
                  
                }
            }
        }
        
        return lc;
    }
    
    public int ColumnlinearConflict(int[][] mat){
        int lc = 0;
        int n = mat.length;
        for(int i = 0; i < n; i++){
            int max = -1;
            for(int j = 0; j < n; j++){
                if(mat[i][j]!= 0 && (mat[i][j] - 1) % n == j){
                   if(mat[i][j] > max){
                       max = mat[i][j];
                   }
                   else{
                       lc++;
                   }
                  
                  
                }
            }
        }
        
        return lc;
    }
    
    public int calculateLinearConf(int[][] mat){
        int cost = manhattan(mat) + 2*((RowlinearConflict(mat)) + (ColumnlinearConflict(mat)));
        return cost;
    }
     
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof State)) {
            return false;
        }
        State s = (State) obj;
        if (this.board[boardSize - 1][boardSize - 1] != s.board[boardSize - 1][boardSize - 1]) {
            return false;
        }
        if (s.boardSize != this.boardSize) {
            return false;
        }
        return Arrays.deepEquals(this.board, s.board);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(4 * boardSize * boardSize);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public void swap(int a, int b) {
        int temp = a;
        a = b;
        b = temp;
    }
    
    public boolean IsSafe(int x,int y){
        return (x < 0 && y < 0 && x >= boardSize && y >= boardSize);
    }

    public Vector getChildNodes() {
        Vector<State> v = new Vector<>();
        int zRow = 0;
        int zCol = 0;

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == 0) {
                    zRow = i;
                    zCol = j;
                    break;
                }
            }
        }

        if (zRow - 1 >= 0) {
            int[][] copy = new int[boardSize][boardSize];
            //ArrayCopy(copy);
            // = Arrays.copyOf(board, board.length);
            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++){
                    copy[i][j] = board[i][j];
                }
            }
            int temp = copy[zRow][zCol];
            copy[zRow][zCol] = copy[zRow - 1][zCol];
            copy[zRow - 1][zCol] = temp;
            v.addElement(new State(copy));
        }
        if (zRow + 1 < boardSize) {
            //swap(s.board[zRow][zCol],s.board[zRow+1][zCol]);
            int[][] mat = new int[boardSize][boardSize];
            // = Arrays.copyOf(board, board.length);
            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++){
                    mat[i][j] = board[i][j];
                }
            }
            int temp = mat[zRow][zCol];
            mat[zRow][zCol] = mat[zRow + 1][zCol];
            mat[zRow + 1][zCol] = temp;
            v.addElement(new State(mat));
        }

        if(zCol - 1 >= 0){
            int[][] mat = new int[boardSize][boardSize];
            // = Arrays.copyOf(board, board.length);
            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++){
                    mat[i][j] = board[i][j];
                }
            }
            int temp = mat[zRow][zCol];
            mat[zRow][zCol] = mat[zRow][zCol-1];
            mat[zRow][zCol - 1] = temp;
            v.addElement(new State(mat));
        }
        if(zCol + 1 < boardSize){
            int[][] mat = new int[boardSize][boardSize];
            // = Arrays.copyOf(board, board.length);
            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++){
                    mat[i][j] = board[i][j];
                }
            }
            int temp = mat[zRow][zCol];
            mat[zRow][zCol] = mat[zRow][zCol + 1];
            mat[zRow][zCol + 1] = temp;
            v.addElement(new State(mat));
        }
        return v;
    }
    
    
    public void printPath() {
         if (ParentState != null)
            ParentState.printPath();
         System.out.println("Step " + pathCost +  " :\n" + toString());
          System.out.println("Total Hamming Cost  " + totCostHamming +  "\nTotal ManHattan Cost: " + totCostManhattan);
          System.out.println("Total  Linear Cost  " + totCostLinear);
      }

}
