/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaHeurestique.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
            this.genes = generSolutionAleo (size);
            // fitnness c'est nombre du reines en securite
            this.fitness = this.evaluate();
        }
    }
    
    private int[] generSolutionAleo(int n ){        
        List<Integer> liste = new ArrayList<Integer>();
        // remplir la liste avec des entiers de 0 à n-1
        for (int i = 0; i < n; i++) {
            liste.add(i);
        }        
        // mélanger aléatoirement les éléments de la liste
        Collections.shuffle(liste);
        // convertir la liste en un tableau d'entiers
       return liste.stream().mapToInt(Integer::intValue).toArray();   
    }
    
    public static List<Individual> crossover (Individual parent1 , Individual parent2 , double crossoverRate){  
        
        List<Individual> newGeneration = new ArrayList<Individual>();
        
        if ( crossoverRate < Math.random() ) {
            newGeneration.add(parent1);
            newGeneration.add(parent2);
            return newGeneration;
        }      
        
        int size , crossoverPoint;
        Individual child1 , child2;    
                
        size = parent1.getGenes().length;
        child1 = new Individual ( size , true );
        child2 = new Individual ( size , true );
        
    //    crossoverPoint = (int) (Math.random() * ( ( size - 2) + 1 )  );
        crossoverPoint = 2;
        System.arraycopy(parent1.genes, 0, child1.genes, 0, crossoverPoint);
        System.arraycopy(parent2.genes, crossoverPoint, child1.genes, crossoverPoint, size - crossoverPoint);

        System.arraycopy(parent2.genes, 0, child2.genes, 0, crossoverPoint);
        System.arraycopy(parent1.genes, crossoverPoint, child2.genes, crossoverPoint, size - crossoverPoint);
        
        
    //    child1.reparerIndividuGene();
    //    child2.reparerIndividuGene();
        child1.setFitness(child1.evaluate());
        child2.setFitness(child2.evaluate()); 
        newGeneration.add(child1);
        newGeneration.add(child2);
        return newGeneration;
    }
    
    public void reparerIndividuGene(){
        int col , n , i;
        boolean colonnesOccupees[];
        List<Integer> colonnesEnConflit  = new ArrayList<Integer>(); 
        n = this.genes.length;
        colonnesOccupees = new boolean[n];
        Arrays.fill(colonnesOccupees, false);
        for ( i = 0; i < n; i++){
            col = this.genes[i];
            if (! colonnesOccupees[col]) colonnesOccupees[col] = true;
            else colonnesEnConflit.add(col);            
        }
        for ( i = 0; ! colonnesEnConflit.isEmpty() && i < n; i++){
            if ( !colonnesOccupees[i] ){
                col = colonnesEnConflit.remove(0);
                this.genes[col] = i;
            }
        }
        
    }
    
    
    public void mutate(double mutationRate ){
         
        if ( mutationRate < Math.random() ) return;
        int  index1 , index2 , m ; 
        
        index1 = (int) (Math.random() * this.getGenes().length);
        do 
            index2 = (int) (Math.random() * this.getGenes().length);
        while( index1 == index2 ) ;  
      
        m = this.genes[index1];
        this.genes[index1] = this.genes[index2];
        this.genes[index2] = m;
          
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
        int enSafe = 0 ;        
        for ( int i = 0 ; i < this.genes.length; i++)
            if (  this.ifSafe(i, this.genes[i]) ) enSafe++;
        return enSafe;        
    }
    
    private boolean ifSafe (int row , int col ){        
        int diagonal ;
        for ( int i = 0; i < row ; i++ ){          
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
