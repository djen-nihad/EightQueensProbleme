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
    static final int  maxIter = 50000;
    static final int sizePopulation = 400 ;
    static final double mutationRate  = 1;
    static final double crossoverRate = 1;
    
    
    public static void main(String[] args){
        Genetic test = new Genetic( maxIter , sizePopulation  , mutationRate , crossoverRate );
        Individual solution = test.genticAlgorithm(100);

     //   test.printBorad(solution.getGenes());
        System.out.println( "\n  fitness :  " + solution.getFitness());
  
     }
}
