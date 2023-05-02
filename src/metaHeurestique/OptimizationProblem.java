/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaHeurestique;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author client
 */
public class OptimizationProblem {
    
    public int[] generSolutionAleo(int n ){        
        List<Integer> liste = new ArrayList<Integer>();
        // remplir la liste avec des entiers de 0 à n-1
        for (int i = 0; i < n; i++) {
            liste.add(i);
        }        
        // mélanger aléatoirement les éléments de la liste
        Collections.shuffle(liste);
        // convertir la liste en un tableau d'entiers
       return liste.stream().mapToInt(Integer::intValue).toArray();   
    } 
    
    public int fitness(int[] solution){
        int diagonal;
        int enAttack = 0;
        boolean attack;
        for(int row = 0; row < solution.length; row++){
            attack = false;
            for(int i = 0; i < row && !attack; i++){
                diagonal = row - i + solution[row]; //Verfier Diagonale Superieur a droite
                if( diagonal == solution[i]){
                    enAttack++;
                    attack = true;
                    continue;
                }
                diagonal = solution[row] - ( row - i );//Verfier Diagonale Superieur a gauche
                if(diagonal == solution[i]){
                    enAttack++;
                    attack = true;
                    continue;
                }
                if( solution[i] == solution[row]){ // verifier la colonne
                    enAttack++;
                    attack = true;
                }
            }
        }
        return enAttack;
    }
        
    
    public int[] reparerSolution(int [] solution){
        boolean existe[];
        int col;
        List<Integer> colonneEnConflit = new ArrayList<Integer>();
        existe = new boolean[solution.length];
        Arrays.fill(existe, false);
        for ( int i = 0; i < existe.length; i++)
            existe[i]= false;
        for ( int i = 0; i < solution.length; i++){
           col = solution[i];
           if (existe[col]) colonneEnConflit.add(i);
           else existe[col] = true;
        }
        for (int i = 0; i < solution.length && ! colonneEnConflit.isEmpty(); i++){
            if ( !existe[i] ) {
                col = colonneEnConflit.remove(0);
                solution[col] = i;
            }
        }
        return solution;
        
        
    }
//    
//    
//    
//    public int fitness ( int[] solution){        
//        int n = solution.length;
//        int numAttacks = 0;
//        for (int i = 0; i < n - 1; i++) {
//            for (int j = i + 1; j < n; j++) {
//            // Check if two queens are on the same column
//                if (solution[i] == solution[j]) 
//                  numAttacks++;
//            
//            // Check if two queens are on the same diagonal
//                else if (Math.abs(i - j) == Math.abs(solution[i] - solution[j])) 
//                     numAttacks++;            
//        }
//    }
//    return numAttacks;     
//    }
//    
}