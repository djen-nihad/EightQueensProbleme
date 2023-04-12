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
    
    private  int maxIter;
    private final int sizePopulation;
    private final int sizeProblem;
    private final int mutationRate;
    private final int crossoverRate;
    private final int nbrParents;
        

    public Genetic(int maxIter, int sizePopulation, int sizeProblem, int mutationRate, int crossoverRate, int nbrParents) {
        this.maxIter = maxIter;
        this.sizePopulation = sizePopulation;
        this.sizeProblem = sizeProblem;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.nbrParents = nbrParents;
    }
    
    public Individual genticAlgorithm(){
        
        int nbrItr = 0 ;
        List<Individual> population , children , twoChildren;
        NavigableSet<Integer> parents;
        Individual parent1 , parent2 , child1 , child2 , bestSolution , bestSolutionNewGeneration;
        
        population = Genetic.generatePopulation(this.sizePopulation, this.sizeProblem);      
        bestSolution = Genetic.GetBestSolution(population);
        
        while ( nbrItr < maxIter  && bestSolution.getFitness() !=  sizeProblem ) {
            parents = selectParentsUniforme(population, nbrParents);
            children = new ArrayList<Individual>();
            while ( ! parents.isEmpty() ){
                parent1 = population.get(parents.pollFirst());
                parent2 = population.get(parents.pollFirst());
                twoChildren = Genetic.crossover(parent1, parent2, this.crossoverRate);
                child1 = twoChildren.get(0);
                child2 = twoChildren.get(1);
                Genetic.mutate(child1, mutationRate);
                Genetic.mutate(child2, mutationRate);
                children.add(child1);
                children.add(child2);
            }            
            bestSolutionNewGeneration = Genetic.GetBestSolution(children);         
            if ( bestSolutionNewGeneration.getFitness()  > bestSolution.getFitness() )
                bestSolution = bestSolutionNewGeneration;
            population = replace(population, children);  
            nbrItr++;
        } 
        return bestSolution; 
    }
    
    public static List<Individual> generatePopulation( int sizePopulation , int sizeProblem ){
        
        List<Individual> population = new ArrayList<Individual>();
        
        for ( int i = 0; i < sizePopulation; i++ ) 
             population.add(new Individual(sizeProblem , false));
        
        return population;
    }    
    
    public static Individual GetBestSolution ( List<Individual> population ){
        
        if ( population.isEmpty() ) return null;
        int max = 0;
        for ( int i = 1; i < population.size(); i++)
            if (population.get(i).getFitness() > population.get(max).getFitness())
                max = i;
        
        return population.get(max);
        
    }
    
    public static List<Individual> crossover (Individual parent1 , Individual parent2 , int crossoverRate){        
        int size;
        boolean genesFromParent1;
        Individual child1 , child2;
        List<Individual> children;
        NavigableSet<Integer> crossoverPoints = new TreeSet<>();
        int crossoverPoint;
        
        size = parent1.getGenes().length;
        child1 = new Individual ( size , true );
        child2 = new Individual ( size , true );
        children = new ArrayList<Individual>();
        
        // Générer des points de croisement uniques
        while ( crossoverPoints.size() < crossoverRate  )
            crossoverPoints.add((int) (Math.random() * ( ( size - 1) - 1 )  )  );
        
        genesFromParent1 = true;
        crossoverPoint = crossoverPoints.pollFirst();
       
        for ( int i = 0; i < size; i++ ){
           if ( i == crossoverPoint && ! crossoverPoints.isEmpty() ) {
               crossoverPoint = crossoverPoints.pollFirst();
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
        children.add(child1);
        children.add(child2);
        return children;
    }
    
    public static void mutate(Individual individual, int mutationRate){
        
        int col ;
        NavigableSet<Integer> rowsToMutate = new TreeSet<>();
        
        while ( rowsToMutate.size() < mutationRate )
            rowsToMutate.add((int) (Math.random() * individual.getGenes().length));
 
        for (Integer row : rowsToMutate){           
            row = rowsToMutate.pollFirst();
            do
                col = (int) ( Math.random() * individual.getGenes().length ); 
            while ( individual.getGenes()[row] == col ); 
            individual.getGenes()[row] = col;            
        }           
    }
    
    public static NavigableSet<Integer> selectParentsUniforme(List<Individual> population , int nbrParents){
        
        NavigableSet<Integer> indexParents = new TreeSet<>(); 
        
        while ( indexParents.size() < nbrParents)
            indexParents.add((int) (Math.random() * population.size() ) );
        
        return indexParents;
    }
     
    public static List<Individual> replace(List<Individual> population , List<Individual> children){
        
        int size = population.size();        
        population.addAll(children);
        
        // trie la liste
        Collections.sort(population, new Comparator<Individual>(){
            @Override
            public int compare(Individual o1, Individual o2) {
                 return o2.getFitness() - o1.getFitness();              
            }                 
        });
        for ( int i = size; i < population.size(); i++)
            population.remove(i);
        
        return population;       
    }
    
    public void printSolution( int[] tab){
        for ( int i=0 ; i < tab.length; i++)
            System.out.print(" || " + tab[i]);
    }
    
    
    
}
