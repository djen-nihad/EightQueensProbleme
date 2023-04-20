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
    
    public static List<Individual> crossover (Individual parent1 , Individual parent2 , int crossoverType , double crossoverRate){  
        
        List<Individual> newGeneration = new ArrayList<Individual>();
        if ( crossoverRate < Math.random() ) {
            newGeneration.add(parent1);
            newGeneration.add(parent2);
            return newGeneration;
        }        
        int size , crossoverPoint;
        boolean genesFromParent1;
        Individual child1 , child2;
        NavigableSet<Integer> crossoverPoints = new TreeSet<>();
        
        
        size = parent1.getGenes().length;
        child1 = new Individual ( size , true );
        child2 = new Individual ( size , true );
        
        // Générer des points de croisement uniques
        while ( crossoverPoints.size() < crossoverRate && crossoverPoints.size() < size  )
            crossoverPoints.add((int) (Math.random() * ( ( size - 1) - 1 )  )  );
        
        genesFromParent1 = true;
        crossoverPoint = crossoverPoints.pollFirst();
       
        for ( int i = 0; i < size; i++ ){
           if ( i == crossoverPoint ) {
               if ( ! crossoverPoints.isEmpty() ) crossoverPoint = crossoverPoints.pollFirst();
               genesFromParent1 = ! genesFromParent1;                
           } 
           if (genesFromParent1) {
               child1.getGenes()[i] = parent1.getGenes()[i];
               child2.getGenes()[i] = parent2.getGenes()[i];
           }
           else {
               child1.getGenes()[i] = parent2.getGenes()[i]; 
               child2.getGenes()[i] = parent1.getGenes()[i]; 
           }            
        }
        child1.setFitness(child1.evaluate());
        child2.setFitness(child2.evaluate()); 
        newGeneration.add(child1);
        newGeneration.add(child2);
        return newGeneration;
    }
    
    
     public void mutate(double mutationRate , int pos){
         
        if ( mutationRate < Math.random() ) return;
        int row , col; 
        do
                col = (int) ( Math.random() * this.getGenes().length ); 
        while ( this.getGenes()[pos] == col );
        this.getGenes()[pos] = col; 
        
 /**       NavigableSet<Integer> rowsToMutate = new TreeSet<>();        
        while ( rowsToMutate.size() < mutationRate )
            rowsToMutate.add((int) (Math.random() * this.getGenes().length));
 
        while ( ! rowsToMutate.isEmpty()){           
            row = rowsToMutate.pollFirst();
            do
                col = (int) ( Math.random() * this.getGenes().length ); 
            while ( this.getGenes()[row] == col ); 
            this.getGenes()[row] = col;            
        }  
        **/
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
