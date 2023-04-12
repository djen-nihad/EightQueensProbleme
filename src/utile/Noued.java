/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utile;

/**
 *
 * @author client
 */
public class Noued {
        
    public int row;
    public boolean[][] etat;
        
        
    public Noued(int size){
            this.row = 0;
            etat = new boolean[1][size];
            for(int i = 0; i < size; i++)
                this.etat[0][i] = false;
        }

        public Noued(boolean[][] etat, int row , int col , int size  ) {            
            this.etat = new boolean[ row + 1 ][size];
            this.row = row;
            for ( int i = 0; i < row ; i++ )
                for ( int j = 0 ; j < size ; j++)
                    this.etat[i][j] = etat[i][j];
            
            for ( int j = 0; j < size; j++)
                this.etat[row][j] = false; 
            
            this.etat[row][col] = true;          
        }    
    }        
