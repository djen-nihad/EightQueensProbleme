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
public abstract class Solution extends NQueensProblem  {    
    
    public long nbrNodeGener;
    public long nbrNodeDevelop; 
    
    public Solution( int size ){
        super(size);      
        this.nbrNodeGener = 0;
        this.nbrNodeDevelop = 0;
    } 
    
    abstract public boolean solveN_Queens(boolean[][] board);

    public boolean ifSafe( boolean[][] board , int row , int col ){
         
        int diagonal ;
        for ( int i = 0; i < row ; i++ ){            
            
            // verifier la colone 
            if ( board[i][col] == true ) return false;          
                        
            // verifier diagonal superieur a droit 
            diagonal = i - row + col ;               
            if (diagonal  >= 0 && diagonal < this.getSize() &&  board[i][ diagonal ] == true) return false;
                        
            // verifier diagonal superieur a gauche          
            diagonal = row + col - i;          
                       
            if (diagonal  >= 0 && diagonal < this.getSize() && board[i][ diagonal ] == true) return false;  
        }
         return true;
     }    
    
    public void printBorad(boolean[][] borad){
        
        for ( int row = 0; row < borad.length; row++){
            for ( int col = 0; col < borad.length; col++){
                if (borad[row][col] == true) 
                      System.out.print(" Q ");
                else  System.out.print(" X ");            
            }
            System.out.print(" \n"); 
        }          
    } 
    
    public void printTable(boolean[][] board){
        
        for ( int row = 0; row < board.length; row++)
            for ( int col = 0; col < board.length; col++)
                if (board[row][col])   System.out.print(" " + col + " | ");
        System.out.println();
    }
    
       
    public int evaluate ( boolean[][] board){        
        int attack = 0 ;        
        for ( int i = 0 ; i < board.length; i++)
            for ( int j = 0; j < board.length ; j++)
                if ( board[i][j] && this.enAttcak(board, i, j) ) attack++;
        return attack;        
    }
    
    private boolean enAttcak (boolean[][] board , int row , int col ){        
        int diagonal ;
        for ( int i = 0; i < board.length ; i++ ){          
            // verifier la colone 
            if ( i != row && board[i][col] == true ) return true;                            
            // verifier diagonal superieur a droit 
            diagonal = i - row + col ;               
            if (diagonal  >= 0 && diagonal < board.length && i != row && diagonal != col &&  board[i][ diagonal ] == true) return true;           
            // verifier diagonal superieur a gauche          
            diagonal = row + col - i;                  
            if (diagonal  >= 0 && diagonal < board.length &&  i != row && diagonal != col && board[i][ diagonal ] == true) return true;  
        }
        return false;
    } 
    
    
    public static void copyMatrix(boolean[][] source, boolean[][] destination) {
    for (int i = 0; i < destination.length; i++) {
        for (int j = 0; j < destination[i].length; j++) {
            destination[i][j] = source[i][j];
        }
    }
}

    
}
