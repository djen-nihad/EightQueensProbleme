/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaHeurestique.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

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
    
    public Individual genticAlgorithm(int sizeProblem){
        
        int nbrItr = 0 ;
        List<Individual> population , children , newGeneration;
        Individual parent1 , parent2 ,bestSolution ;
        List<Integer> parents;
        
        population = Genetic.generatePopulation(this.sizePopulation, sizeProblem);      
        bestSolution = population.get(0);
        while ( bestSolution.getFitness() !=  sizeProblem &&  nbrItr < maxIter  ) {
            parents = selectParentsUniforme(this.sizePopulation, 1);
            newGeneration = new ArrayList<Individual>();
            while ( ! parents.isEmpty() ){
                parent1 = population.get(parents.remove(0));
                parent2 = population.get(parents.remove(0));
                parent1 = population.get(0);
                parent2 = population.get(1);
                // Effectue le croisement et la mutation des deux parents sélectionnés pour créer des enfants
                children = Individual.crossover(parent1, parent2, 1 , crossoverRate);
                children.get(0).mutate(mutationRate , 1);
                children.get(1).mutate(mutationRate , 1);  
                newGeneration.addAll(children);
            }            
            // diminuer la taille de population en utilisant des techenique du remplacement
            population = replace(population , newGeneration);
            bestSolution = population.get(0);
            nbrItr++;
        } 
        return bestSolution; 
    }
    
    public static List<Individual> generatePopulation( int sizePopulation , int sizeProblem ){
        
        List<Individual> population = new ArrayList<Individual>();
        
        for ( int i = 0; i < sizePopulation; i++ ) 
            population.add(new Individual(sizeProblem , false));
        
        // Trie la population de maniere decroissante
        Collections.sort(population, new Comparator<Individual>(){
            @Override
            public int compare(Individual o1, Individual o2) {
                 return o2.getFitness() - o1.getFitness();              
            }                 
        });
        
        return population;
    }     
    
    public static List<Integer> selectParentsUniforme(int sizePopulation , int nbrParents){
        
        List<Integer> indexParents = new ArrayList<>();
        NavigableSet<Integer> parents = new TreeSet<>(); 
        for ( int i = 0; i < nbrParents; i++){
            
            // selectionne deux parents differents 
            while ( parents.size() < 2  )
                parents.add((int) (Math.random() * sizePopulation ) );
            
            // ajouter a la list des parents
            indexParents.addAll(parents);            
        }         
        return indexParents;
    }
     
    public static List<Individual> replace(List<Individual> population , List<Individual> children){
        
        int size = population.size();  
        
        population.addAll(children);
        
        Collections.sort(population, new Comparator<Individual>(){
            @Override
            public int compare(Individual o1, Individual o2) {
                 return o2.getFitness() - o1.getFitness();              
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
    
    
    
}
