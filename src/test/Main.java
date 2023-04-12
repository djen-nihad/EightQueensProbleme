package test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import solution.BFSsolution;
import solution.DFSsolution;
import solution.Heuristique1Solution;
import solution.Heuristique2Solution;


/**
 *
 * @author client
 */
public class Main {
    
    public static void main(String[] args) {
        
        long startTime , endTime;
        boolean found;
               

        Heuristique2Solution problem = new Heuristique2Solution(10);     
            
        startTime = System.currentTimeMillis();
        
        found = problem.solveN_Queens( problem.board );
        
        endTime = System.currentTimeMillis();
        if (found){
            problem.printTable(problem.board);     
            System.out.println(" Nombre de noueds Generes : " + problem.nbrNodeGener);
            System.out.println(" Nombre de noueds developpes : " + problem.nbrNodeDevelop);
            System.out.println(" Temps d'execution pour trouver la solution est : " + ( endTime - startTime ) + "ms");  
        }               
        else System.out.println("No solution !");    
        

        
    }
    
}
