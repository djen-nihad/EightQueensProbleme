/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solution;

import problem.NQueensProblem;
import java.awt.List;
import java.util.LinkedList;
import utile.Noued;

/**
 *
 * @author client
 */
public class BFSsolution extends Solution {
    
    public BFSsolution(int size) {
        super(size);
    }   
    
    @Override
    public boolean solveN_Queens(boolean[][] board){        
        
       int  col ;
       Noued nouedCurrent;
       Noued tempNoued;
       LinkedList<Noued> listOvert = new LinkedList<Noued>(); 
       // noued contient l'echequier vide
       nouedCurrent = new Noued(this.getSize());
       
       for ( col = 0; col < this.getSize(); col++ ){
           tempNoued = new Noued(nouedCurrent.etat , 0  , col , this.getSize() );
           listOvert.add(tempNoued); 
           this.nbrNodeGener++;
       } 
              
       while ( ! listOvert.isEmpty() ) {           
           nouedCurrent = listOvert.removeFirst();
           this.nbrNodeDevelop++;
           if ( nouedCurrent.row + 1 == this.getSize() ){
               Solution.copyMatrix(nouedCurrent.etat, board);
               return true;
           }             
           for ( col = 0; col < this.getSize() ; col++ ){
               if (this.ifSafe(nouedCurrent.etat, nouedCurrent.row + 1  , col)){
                   tempNoued = new Noued(nouedCurrent.etat , nouedCurrent.row + 1  , col , this.getSize() );
                   listOvert.add(tempNoued);
                   this.nbrNodeGener++;
               }               
           }
       }
       return false;
    }   
}



    


