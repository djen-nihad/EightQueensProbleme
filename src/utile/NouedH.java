/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utile;

import java.util.LinkedList;

/**
 *
 * @author client
 */
public class NouedH extends Noued {
    
    public int f;  // f(n) = g(n) + h(n)
    
    public NouedH(boolean[][] etat, int row , int col , int size , int h   ){
        super( etat , row , col , size);
        this.f = size - row + h ;      
    }

    // constructeur pour creer le 1 er nouds qui represente la matrice vide
    public NouedH(int size) {
        super(size);
    }
    
    public NouedH(boolean[][] etat, int row , int col , int size  ){
        super( etat , row , col , size);
        this.f = this.estimationH1(size)  + size - row - 1 ;
            
    }
    
    private int estimationH1(int size ){
        
        int i , j , row , col , safe , diagonal;
        int[] position = new int[this.etat.length];
        safe = 0;
        boolean isSafe;
        for ( i = 0; i < this.etat.length; i++ )
            for ( j = 0; j < size; j++)
                if ( this.etat[i][j] ) {
                    position[i] = j;
                    break;
                }
        
        for ( i = this.etat.length; i < size; i++ )
            for (  j = 0; j < size; j++){
                isSafe = true;
                for ( int k = 0; k < this.etat.length && isSafe; k++)  {
                    if ( position[k] == j ){ isSafe=false; continue;}
                    
                    // verifier diagonal superieur a droit 
             
                   diagonal = j + ( i - k );                    
                    if ( diagonal >=0 && diagonal < size && this.etat[k][diagonal] == true ) { isSafe=false; continue;}
                             
                    // verifier diagonal superieur a gauche
                    
                    diagonal = j - (i - k) ;                  
                    
                     if (diagonal  >= 0 && diagonal < size && this.etat[k][ diagonal ] == true) { isSafe=false; continue;}            
                }
                if(isSafe)safe++;
            }             
        return safe ;
    }
    
    public void addNouedSortH(LinkedList<NouedH> list){
        int i = 0;
        while ( i < list.size() && list.get(i).f < this.f ) i++;
        list.add( i , this );  
    }   
    
}




/**
package utile;

import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author client
 */
/**
public class NouedH extends Noued {
    
    public int h;
   
    
    public NouedH(int size) {
        super(size);
        this.h = 0;
    }
    
    public NouedH(boolean[][] etat, int row , int col , int size  ){
        super( etat , row , col , size);
        this.h = this.estimationH1(size);
            
    }
    
    public NouedH(boolean[][] etat, int row , int col , int size , int h   ){
        super( etat , row , col , size);
        this.h = row + h ;      
    }
    
    public void addNouedSort(LinkedList<NouedH> list){
        int i = 0;
        while ( i < list.size() && list.get(i).h < this.h) i++;
        list.add( i , this );  
    }    
    
    private int estimationH1(int size ){
        
        int i , j , row , col , attack , diagonal;
        int[] position = new int[this.etat.length];
        attack = 0;
     
        for ( i = 0; i < this.etat.length; i++ )
            for ( j = 0; j < size; j++)
                if ( this.etat[i][j] ) {
                    position[i] = j;
                    continue;
                }
        
        for ( i = this.etat.length; i < size; i++ )
            for (  j = 0; j < size; j++)                
                for ( int k = 0; k < this.etat.length; k++) {
                    if ( position[k] == j ) attack++;
                    
                    // verifier diagonal superieur a droit 
             
                    if ( i >= j ) diagonal = k - ( i - j );
                    else diagonal = k + j - i ;
                    
                    if ( diagonal >=0 && diagonal < size && this.etat[k][diagonal] == true ) attack++;
                             
                    // verifier diagonal superieur a gauche
                    
                    diagonal = i + j - k;                    
                    
                     if (diagonal  >= 0 && diagonal < size && this.etat[k][ diagonal ] == true) attack++;             
                }                
        return size * ( size - this.etat.length ) - attack ;
    }
    

    
}
**/