/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metaHeurestique;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author client
 */
public class OptimizationProblem {
    
    public int fitness(int[] solution){
        int diagonal;
        int enAttack = 0;
        for(int row = 0; row < solution.length; row++){
            for(int i = 0; i < row; i++){
                diagonal = row - i + solution[row]; //Verfier Diagonale Superieur a droite
                if( diagonal == solution[i]){
                    enAttack++;
                    continue;
                }
                diagonal = solution[row] - ( row - i );//Verfier Diagonale Superieur a gauche
                if(diagonal == solution[i]){
                    enAttack++;
                    continue;
                }
                if( solution[i] == solution[row]){ // verifier la colonne
                    enAttack++;
                }
            }
        }
        return enAttack;
    }
        
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

}
