//package test;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//
//import metaHeurestique.Genetic.Chromosome;
//import metaHeurestique.Genetic.Genetic;
//import metaHeurestique.PSO.PSO;
//
///**
// *
// * @author client
// */
//public class main_1 {
//    
//    
//    
//    static final int  MAX_ITER = 1000;
//    static int SIZE_POP = 50 ;
//        
//    static final double MUTATION_RATE  = 0.4 ;
//    static final double CROSSOVER_RATE = 1;    
//
//    static final double ALPHA = 0.9 ;
//    static final double C1 = 0.2 ;
//    static final double C2 = 0.9;
//    
//    static long startTime , endTime , tempsExecution;
//    
//    
//    
//    
//    
//    public static void main(String[] args){
//        
//        double fitnessMoy = 0 , tpMoy = 0;
//        
//       PSO algorithmePSO = new PSO(MAX_ITER,SIZE_POP, ALPHA, C1, C2);
//       
//       for ( int i =0; i < 5; i++){
//         startTime = System.currentTimeMillis();
//         algorithmePSO.PSOAlgorithme(10);  
//         endTime = System.currentTimeMillis();  
//         fitnessMoy = fitnessMoy + algorithmePSO.GBestFitness;
//         tpMoy = tpMoy + (endTime - startTime);
//       }
//       
//       System.out.println( 5 + " .... " + " fitness :  " + fitnessMoy / 5 );
//       System.out.println( " Temps d'execution : " + tpMoy / 5 + " ms" );
//       System.out.println();
////       
//       
//       
//      
//       for ( int i = 10; i <= 100; i = i + 5) {
//            algorithmePSO.PSOAlgorithme(i);  
//            System.out.println( i + " .... " + " fitness :  " + algorithmePSO.GBestFitness );  
//         //   if ( i == 10 ) algorithme.printBorad(algorithmePSO.GBest);
//        } 
//       
//       SIZE_POP = 100;
//
//       Genetic algorithmeGA = new Genetic(MAX_ITER, SIZE_POP , MUTATION_RATE, CROSSOVER_RATE);
//       Chromosome chromosome;
//      for ( int i =0 ; i < 5; i++){
//          fitnessMoy =0;
//          tpMoy = 0;
//        startTime = System.currentTimeMillis();
//       chromosome = algorithmeGA.geneticAlgorithm((100));
//       endTime = System.currentTimeMillis();
//       tpMoy = tpMoy + (endTime - startTime);
//       fitnessMoy = fitnessMoy + chromosome.fitness;
//    
//      }
//         System.out.println( 50 + " .... " + " fitness :  " + fitnessMoy / 5 );
//       System.out.println( " Temps d'execution : " + tpMoy / 5 + " ms" );
//       
//       
//       
//       
//        for ( int  i = 5; i <= 100; i = i + 5 ){
//            chromosome = algorithmeGA.geneticAlgorithm(i);
//            System.out.println( i + " .... " + " fitness :  " + chromosome.fitness );
//        }
//        
//        
//
//  
//     }
//}
