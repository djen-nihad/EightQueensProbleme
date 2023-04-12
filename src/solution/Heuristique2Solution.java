/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solution;

import java.util.LinkedList;
import utile.NouedH;

/**
 *
 * @author client
 */
public class Heuristique2Solution extends Solution {

    public Heuristique2Solution(int size) {
        super(size);
    }

       
 public boolean solveN_Queens(boolean[][] board){
        
       int col;   
       NouedH nouedCurrent;
       NouedH tempNoued;
       LinkedList<NouedH> listOvert = new LinkedList<NouedH>();       
    
       nouedCurrent = new NouedH(this.getSize()); 
       
       for ( col = 0; col < this.getSize(); col++ ){
           tempNoued = new NouedH( nouedCurrent.etat , 0  , col , this.getSize() );
           tempNoued.addNouedSortH(listOvert);                
           this.nbrNodeGener++;
       }  
       
       while ( ! listOvert.isEmpty() ) {
           nouedCurrent = listOvert.removeFirst();
           this.nbrNodeDevelop++;
           if  ( nouedCurrent.row + 1 == this.getSize()  ) {
               Solution.copyMatrix(nouedCurrent.etat, board);
               return true;
            }           
           for ( col = 0; col < this.getSize() ; col++ ){
                if (this.ifSafe(nouedCurrent.etat, nouedCurrent.row + 1  , col)) {
                    tempNoued = new NouedH( nouedCurrent.etat , nouedCurrent.row + 1  , col , this.getSize() );
                    tempNoued.addNouedSortH(listOvert);
                    this.nbrNodeGener++;                       
                }             
            }        
        }
        return false;
    }    
}
