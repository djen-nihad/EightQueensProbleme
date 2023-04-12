/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaHeurestique.GeneticAlgorithm;

/**
 *
 * @author client
 */
public class main {
    static final int  maxIter = 1000;
    static final int sizePopulation = 120 ;
    static final int sizeProblem = 40;
    static final int mutationRate  = 1;
    static final int crossoverRate = 3 ;
    static final int nbrParents = 10 ;
    
    public static void main(String[] args){
        
        Genetic test = new Genetic( maxIter , sizePopulation , sizeProblem , mutationRate , crossoverRate , nbrParents);
        Individual solution = test.genticAlgorithm();
        test.printSolution(solution.getGenes());
        System.out.println( "\n  fitness :  " + solution.getFitness());
  
     }
}
