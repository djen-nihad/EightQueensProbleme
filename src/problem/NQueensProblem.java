
package problem;

/**
 *
 * @author djenanenihad
 */

public class NQueensProblem {
    
    private int size;
    public boolean[][] board ;
    
    public  NQueensProblem( int size  ) {        
        this.size = size;
        board = new boolean [this.size][this.size];
        this.initBorad();              
    }

    public int getSize() {
        return size;
    }

    public void initBorad() {
    
        for(int i = 0; i< size; i++)
            for (int j = 0; j < size; j++)
                board[i][j]= false; 
        
    }    
}


