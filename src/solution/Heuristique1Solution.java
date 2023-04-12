/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solution;

import java.util.LinkedList;
import problem.NQueensProblem;
import utile.NouedH;


/**
 *
 * @author client
 */
public class Heuristique1Solution extends Solution {
    
    int[][] heurstique;

    public Heuristique1Solution(int size) {
        super(size);
        this.heurstique = new int[size][size];
        for ( int i = 0; i < size; i++)
            for ( int j = 0; j < size; j++)
                this.heurstique[i][j] = Math.min((size - ( i + 1 )), ( size - ( j + 1 ))) + Math.min( ( size - ( i + 1 )), j);       
    }
    
       
    public boolean solveN_Queens(boolean[][] board){
        
       int col;        
       NouedH nouedCurrent;
       NouedH tempNoued;
       LinkedList<NouedH> listOvert = new LinkedList<NouedH>();       
       
    
       nouedCurrent = new NouedH(this.getSize());              
              
       for ( col = 0; col < this.getSize(); col++ ){
           tempNoued = new NouedH(nouedCurrent.etat , 0  , col , this.getSize() , this.heurstique[0][col] );
           tempNoued.addNouedSortH(listOvert);   
           this.nbrNodeGener++;
       }  
              
       while ( ! listOvert.isEmpty() ) {           
           
           nouedCurrent = listOvert.removeFirst();
           this.nbrNodeDevelop++;
           
           if ( nouedCurrent.row + 1 == this.getSize() ){
               Solution.copyMatrix(nouedCurrent.etat, board);
               return true;
           }           
           for ( col = 0; col < this.getSize() ; col++ )
               if ( this.ifSafe(nouedCurrent.etat, nouedCurrent.row + 1  , col)){                   
                   tempNoued = new NouedH(nouedCurrent.etat , nouedCurrent.row + 1  , col ,this.getSize() , this.heurstique[nouedCurrent.row + 1][col] );
                   this.nbrNodeGener++;
                   tempNoued.addNouedSortH(listOvert); 
               }
       }           
       return false;
    }
}
