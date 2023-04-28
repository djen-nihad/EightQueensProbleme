/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import metaHeurestique.Genetic.Chromosome;
import metaHeurestique.Genetic.Genetic;
import metaHeurestique.PSO.PSO;

/**
 *
 * @author client
 */
public class main {
    
    static final int  MAX_ITER = 1000;
    static final int SIZE_POP = 50 ;
        
    static final double MUTATION_RATE  = 1;
    static final double CROSSOVER_RATE = 0;    

    static final double ALPHA = 0.5 ;
    static final double C1 = 0.9 ;
    static final double C2 = 0.9;
    
    
    
    public static void main(String[] args){
        
//       PSO algorithme = new PSO(MAX_ITER,SIZE_POP, ALPHA, C1, C2);
//   
//        
//       for ( int i = 5; i <= 100; i = i + 5) {
//            algorithme.PSOAlgorithme(100);  
//            System.out.println( 100 + " .... " + " fitness :  " + algorithme.GBestFitness );
//            if ( i == 20 ) algorithme.printBorad(algorithme.GBest);
//
//        } 

        Genetic algorithme = new Genetic(MAX_ITER, SIZE_POP , MUTATION_RATE, CROSSOVER_RATE);
        Chromosome chromosome;
        chromosome = algorithme.geneticAlgorithm(100);
        System.out.println( 100 + " .... " + " fitness :  " + chromosome.fitness );
        

  
     }
}
