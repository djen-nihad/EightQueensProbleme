/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaHeurestique.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 *
 * @author client
 */
public class Individual {
    
    private int[] genes;
    private int fitness;
    
    public Individual(int size , boolean empty){
        this.genes = new int [size];
        if ( !empty ){
            for ( int i = 0 ; i < size; i++)
               this.genes[i] = (int) ( Math.random() * size );
            this.fitness = this.evaluate();
        }
    }

    public int[] getGenes() {
        return genes;
    }
    
    
    public int getFitness() {
        return fitness;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
    
    
    public int evaluate (){        
        int safe = 0 ;        
        for ( int i = 0 ; i < this.genes.length; i++)
            if ( this.ifSafe(i, this.genes[i]) ) safe++;
        return safe;        
    }
    
    private boolean ifSafe (int row , int col ){        
        int diagonal ;
        for ( int i = 0; i < this.genes.length ; i++ ){          
            // verifier la colone 
            if ( i != row && this.genes[i] == col ) return false;                         
            // verifier diagonal superieur a droit 
            diagonal = i - row + col ;               
            if (diagonal  >= 0 && diagonal < this.genes.length && i != row && diagonal != col &&  this.genes[i]== diagonal ) return false;           
            // verifier diagonal superieur a gauche          
            diagonal = row + col - i;                  
            if (diagonal  >= 0 && diagonal < this.genes.length &&  i != row && diagonal != col && this.genes[i]== diagonal) return false;  
        }
        return true;
    } 
  
    
  
}
