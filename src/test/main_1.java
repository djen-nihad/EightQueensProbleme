package test;

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
public class main_1 {
    
    static final int  MAX_ITER = 1000;
    static int SIZE_POP = 50 ;
        
    static final double MUTATION_RATE  = 1;
    static final double CROSSOVER_RATE = 1;    

    static final double ALPHA = 0.5 ;
    static final double C1 = 0.9 ;
    static final double C2 = 0.9;
    
    
    
    public static void main(String[] args){
        
       PSO algorithmePSO = new PSO(MAX_ITER,SIZE_POP, ALPHA, C1, C2);
//   
//       algorithmePSO.PSOAlgorithme(5);  
//       System.out.println( 5 + " .... " + " fitness :  " + algorithmePSO.GBestFitness );
//       for ( int i = 10; i <= 100; i = i + 5) {
//            algorithmePSO.PSOAlgorithme(i);  
//            System.out.println( i + " .... " + " fitness :  " + algorithmePSO.GBestFitness );  
//         //   if ( i == 10 ) algorithme.printBorad(algorithmePSO.GBest);
//        } 
       
       SIZE_POP = 100;

        Genetic algorithmeGA = new Genetic(MAX_ITER, SIZE_POP , MUTATION_RATE, CROSSOVER_RATE);
        Chromosome chromosome;
      chromosome = algorithmeGA.geneticAlgorithm(100);
      System.out.println( 100 + " .... " + " fitness :  " + chromosome.fitness );
        for ( int  i = 5; i <= 100; i = i + 5 ){
            chromosome = algorithmeGA.geneticAlgorithm(i);
            System.out.println( i + " .... " + " fitness :  " + chromosome.fitness );
        }
        
        

  
     }
}
