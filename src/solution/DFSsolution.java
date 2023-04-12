/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solution;

import problem.NQueensProblem;

/**
 *
 * @author client
 */
public class DFSsolution extends Solution {

    private boolean[][] temp;
    private boolean solutionFounded;

    public DFSsolution(int size) {        
        super(size);
        solutionFounded = false;
        temp = new boolean[size][size];
        for(int i = 0; i< size; i++)
            for (int j = 0; j < size; j++)
                temp[i][j]= false; 
        
        
    }
    
    public boolean solveN_Queens(boolean[][] borad, int row) {
        
        if ( this.solutionFounded ) {
            return true;
        }
        this.nbrNodeDevelop++;
        if ( row == board.length) {
            this.solutionFounded = true;
            Solution.copyMatrix( temp, borad);
            return true;
        }
        else {      
            for ( int col = 0; col < temp.length; col++){
                if ( this.ifSafe(temp, row, col) ){
                   this.nbrNodeGener++;
                   temp[row][col] = true;
                   solveN_Queens(board , row + 1);   
                   temp[row][col] = false;                
                }            
            } 
        }         
        return solutionFounded;            
    }
    @Override
    public boolean solveN_Queens(boolean[][] board) {           
         return this.solveN_Queens( board, 0);
    }
    
  
}
