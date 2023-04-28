/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaHeurestique.PSO;

import metaHeurestique.OptimizationProblem;

/**
 *
 * @author client
 */
public class Particule extends OptimizationProblem {
    
    public int[] position;
    public double[] velocity;
    public int[] PBest;
    public int fitness;
    public int PBestFitness;
    
    Particule(int size){
        this.position = this.generSolutionAleo(size);
        this.fitness = this.fitness(this.position);
        this.PBest = new int [size];
        System.arraycopy(this.position, 0, this.PBest, 0, size);
        this.PBestFitness = this.fitness;
        // Initialisation déterministe de la vitesse
        this.velocity = new double[size];
        for(int i=0; i<size; i++){
            // Déterminer la direction de déplacement de la reine
            int dir = (Math.random() < 0.5) ? -1 : 1;
            // Générer une vitesse déterministe pour chaque reine
            this.velocity[i] = dir * (i+1);
        }
        
    }
   
    public void updateVelocity(double w , double c1 , double c2 , int[] Gbest){
        double r1 = Math.random();
        double r2 = Math.random();
        
        for ( int i = 0; i < this.velocity.length; i++)
            this.velocity[i] = w * this.velocity[i] + c1*r1*(this.PBest[i] - this.position[i]) + c2*r2*(Gbest[i] - this.position[i]);
        
    }
    
    public void moveParticule(){
        for ( int i = 0 ; i < this.position.length; i++){
            this.position[i] = ( this.position[i] + (int) this.velocity[i] ) % this.position.length;
            if ( this.position[i] < 0  )
               this.position[i] = this.position[i] + this.position.length; 
        }   
        this.fitness = this.fitness(this.position);
        if ( this.fitness < this.PBestFitness ) {
            this.PBestFitness = this.fitness;
            System.arraycopy(this.position, 0, this.PBest, 0, this.position.length);
        }
    }
    
    
}
