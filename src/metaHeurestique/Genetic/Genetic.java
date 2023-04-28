/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaHeurestique.Genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author client
 */
public class Genetic {
    private final int maxIter;
    private final int sizePopulation;
    private final double mutationRate;
    private final double crossoverRate;

        

    public Genetic(int maxIter, int sizePopulation, double mutationRate, double crossoverRate) {
        this.maxIter = maxIter;
        this.sizePopulation = sizePopulation;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
    }
    
    public Chromosome geneticAlgorithm(int sizeProblem){
        
        int nbrItr = 0 ;
        List<Integer> parents;
        List<Chromosome> population , children , newGeneration;
        Chromosome parent1 , parent2 ,bestSolution ;
        population = Genetic.generatePopulation(this.sizePopulation, sizeProblem);      
        bestSolution = population.get(0);
        
        while ( bestSolution.fitness !=  0 &&  nbrItr < maxIter  ) {
            newGeneration = new ArrayList<Chromosome>(); 
            parents = selectParents(this.sizePopulation, 20);            
            while ( ! parents.isEmpty() ){                
                parent1 = population.get(parents.remove(0));
                parent2 = population.get(parents.remove(0));
                children = Chromosome.crossover(parent1, parent2, crossoverRate);
                newGeneration.addAll(children);
                newGeneration.add(Chromosome.mutate(children.get(0), mutationRate));
                newGeneration.add(Chromosome.mutate(children.get(1), mutationRate));              
            }
            
            // diminuer la taille de population en utilisant des techenique du remplacement
            population = replace(population , newGeneration);
            bestSolution = population.get(0);
            nbrItr++;
        } 
        return bestSolution; 
    }
    
    public static List<Chromosome> generatePopulation( int sizePopulation , int sizeProblem ){
        
        List<Chromosome> population = new ArrayList<Chromosome>();
        
        for ( int i = 0; i < sizePopulation; i++ ) 
            population.add(new Chromosome(sizeProblem , false));
        
        // Trie la population de maniere croissante
        Collections.sort(population, new Comparator<Chromosome>(){
            @Override
            public int compare(Chromosome o1, Chromosome o2) {
                 return o1.fitness - o2.fitness;              
            }                 
        });
        
        return population;
    }     
    
    public static List<Integer> selectParents(int sizePopulation , int nbrParents){
        
        List<Integer> indexParents = new ArrayList<>();

        for ( int i = 0; i < nbrParents; i++){
            indexParents.add(i);
            indexParents.add(i + 1);
            indexParents.add(i);
            indexParents.add(sizePopulation - i - 1 );
            
        }         
        return indexParents;
    } 
    
    public static List<Chromosome> replace(List<Chromosome> population , List<Chromosome> children){
        
        int size = population.size();  
        
        population.addAll(children);
        
        Collections.sort(population, new Comparator<Chromosome>(){
            @Override
            public int compare(Chromosome o1, Chromosome o2) {
                 return o1.fitness - o2.fitness;              
            }                 
        });
        // Garder les  meilleurs individus et enlever les autres
        population.subList(size, population.size()).clear();   
        
        return population;       
    }
    
    public void printSolution( int[] tab){
        for ( int i=0 ; i < tab.length; i++)
            System.out.print(" || " + tab[i]);
    }
    
    public void printBorad(int[] borad){
        
        for ( int row = 0; row < borad.length; row++){
            for ( int col = 0; col < borad.length; col++){
                if (borad[row] == col) 
                      System.out.print(" Q ");
                else  System.out.print(" X ");            
            }
            System.out.print(" \n"); 
        }          
    }
    
    
    
}
