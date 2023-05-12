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
    
    private int stagnation_count ;
    private int seuil;
    
    public int GBestFitness;
    public int[] GBest;

    public PSO(int maxIter, int sizePopulation, double alpha, double c1, double c2) {
        this.maxIter = maxIter;
        this.sizePopulation = sizePopulation;
        this.alpha = alpha;
        this.c1 = c1;
        this.c2 = c2;
        this.stagnation_count = 0;
        this.seuil = 1;
        
    }
 
    public int[] PSOAlgorithme(int sizeProblem){
        int nbrIter , bestFitnessPred;
        List<Particule> population;
        Particule bestParticule;
        nbrIter = 0;
        population = PSO.generatePopulation(this.sizePopulation, sizeProblem);
        bestParticule = getBestSolution(population);
        bestFitnessPred = this.GBestFitness;
        while (GBestFitness != 0 && nbrIter < this.maxIter ){
            for (Particule particule : population){
                particule.updateVelocity(alpha, c1, c2, GBest);
                particule.moveParticule();
                if (particule.PBestFitness < this.GBestFitness ) {
                    this.GBestFitness = particule.PBestFitness;
                    bestParticule = particule;
                }
            }
            // mettre a jour globale best
            System.arraycopy(bestParticule.PBest, 0, this.GBest, 0, bestParticule.position.length);
            nbrIter++;
            if ( this.GBestFitness - bestFitnessPred < this.seuil )
                this.stagnation_count++;
            else this.stagnation_count = 0;
            if ( this.stagnation_count == 1 ){
                population = perturbation(population);
                this.stagnation_count = 0;
            }
            bestFitnessPred = this.GBestFitness;
        }
      
        return this.GBest;
    }
    
    
    public List<Particule> perturbation ( List<Particule> population){
        int size = population.get(0).position.length;
        int pos1 , pos2 , temp;
        for ( Particule particule : population ){
            particule.position = particule.reparerSolution(particule.position);
            pos1 = (int) (Math.random() * size);
            pos2 = (int) (Math.random() * size);
            temp = particule.position[pos1];
            particule.position[pos1] = particule.position[pos2];
            particule.position[pos2] = temp;  
            
            particule.fitness = particule.fitness(particule.position);
            if ( particule.fitness < particule.PBestFitness ) {
                particule.PBestFitness = particule.fitness;
                System.arraycopy(particule.position, 0, particule.PBest, 0, particule.position.length);
            }     
        }        
        return population;
    }
    
    public List<Particule> reparation ( List<Particule> population){
         for ( Particule particule : population ){
             particule.position = particule.reparerSolution(particule.position);
             particule.fitness = particule.fitness(particule.position);
             if ( particule.fitness < particule.PBestFitness ) {
                particule.PBestFitness = particule.fitness;
                System.arraycopy(particule.position, 0, particule.PBest, 0, particule.position.length);
            } 
         }
         return population;
     } 

    public Particule getBestSolution(List<Particule> population){      
                
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
        return population.get(indexBestSolution);
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
