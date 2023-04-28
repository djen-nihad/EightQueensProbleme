/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaHeurestique.Genetic;

import java.util.ArrayList;
import java.util.List;
import metaHeurestique.OptimizationProblem;

/**
 *
 * @author client
 */
public class Chromosome extends OptimizationProblem {
    
    public int[] genes;
    public int fitness;
    
    public Chromosome(int size, boolean empty){
        this.genes = new int[size];
        if ( !empty ){
            this.genes = this.generSolutionAleo(size);
            // fitnness c'est nombre du reines en securite
            this.fitness = this.fitness(genes);
        }
    }    
    public Chromosome(Chromosome chromosome){
        this.genes = new int[chromosome.genes.length];
        System.arraycopy(chromosome.genes, 0, this.genes, 0, chromosome.genes.length);
        this.fitness = chromosome.fitness;
    }
    
    public static List<Chromosome> crossover (Chromosome parent1 , Chromosome parent2 , double crossoverRate){  
        
        List<Chromosome> newGeneration = new ArrayList<Chromosome>();        
        
        if ( crossoverRate <= Math.random() ) {
            newGeneration.add(Chromosome.mutate(parent1, 1));
            newGeneration.add(Chromosome.mutate(parent2, 1));
            return newGeneration;
        }      
        
        int size , crossoverPoint;
        Chromosome child1 , child2;    
                
        size = parent1.genes.length;
        child1 = new Chromosome ( size , true );
        child2 = new Chromosome ( size , true );
        
        crossoverPoint = (int) (Math.random() * ( size  - 1 )  );
        System.arraycopy(parent1.genes, 0, child1.genes, 0, crossoverPoint);
        System.arraycopy(parent2.genes, crossoverPoint, child1.genes, crossoverPoint, size - crossoverPoint);

        System.arraycopy(parent2.genes, 0, child2.genes, 0, crossoverPoint);
        System.arraycopy(parent1.genes, crossoverPoint, child2.genes, crossoverPoint, size - crossoverPoint);
        
        
        child1.fitness = child1.fitness(child1.genes);
        child2.fitness = child2.fitness(child2.genes); 
        newGeneration.add(child1);
        newGeneration.add(child2);
        return newGeneration;
    }
    
    public static Chromosome mutate(Chromosome chromosome, double mutationRate ){
         
        if ( mutationRate <= Math.random() ) return chromosome;
        
        int  index1 , index2 , m , size ;
        size = chromosome.genes.length;
        
        index1 = (int) ( Math.random() * size );
        do 
            index2 = (int) (Math.random() *size);
        while( index1 == index2 );
        
               
        m = chromosome.genes[index1];
        chromosome.genes[index1] = chromosome.genes[index2];
        chromosome.genes[index2] = m;
        
        chromosome.fitness = chromosome.fitness(chromosome.genes);
 
        return new Chromosome(chromosome);
    }

    
    
}
