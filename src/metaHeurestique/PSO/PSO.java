/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaHeurestique.PSO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author client
 */
public class PSO {
    
    private final int maxIter;
    private final int sizePopulation;
    private final double alpha;
    private final double c1;
    private final double c2;
    
    public int GBestFitness;
    public int[] GBest;

    public PSO(int maxIter, int sizePopulation, double alpha, double c1, double c2) {
        this.maxIter = maxIter;
        this.sizePopulation = sizePopulation;
        this.alpha = alpha;
        this.c1 = c1;
        this.c2 = c2;
    }
 
    public int[] PSOAlgorithme(int sizeProblem){
        int nbrIter;
        List<Particule> population;
        nbrIter = 0;
        population = PSO.generatePopulation(this.sizePopulation, sizeProblem);
        getBestSolution(population);
        
        while (GBestFitness == sizeProblem && nbrIter < this.maxIter ){
            for (Particule particule : population){
                particule.updateVelocity(alpha, c1, c2, GBest);
                particule.moveParticule();
            }
            getBestSolution(population); 
            nbrIter++;
        }
        return this.GBest;
    }

    public void getBestSolution(List<Particule> population){      
                
        int size ,indexBestSolution;
        size = population.get(0).position.length;
        this.GBest = new int [size];
        indexBestSolution = 0;
        this.GBestFitness = population.get(0).PBestFitness;
        for ( int i = 1; i < population.size(); i++){
            if ( population.get(i).PBestFitness < this.GBestFitness ) {
                indexBestSolution = i;
                GBestFitness = population.get(i).PBestFitness;
            }
        }
        System.arraycopy(population.get(indexBestSolution).PBest, 0, this.GBest, 0, size);
    } 
    
    public static List<Particule> generatePopulation( int sizePopulation , int sizeProblem ){
        
        List<Particule> population = new ArrayList<Particule>();
        
        for ( int i = 0; i < sizePopulation; i++ ) 
            population.add(new Particule(sizeProblem));
                
        return population;
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
