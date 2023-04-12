import java.awt.*;
import javax.swing.*;

public class Board extends JPanel {
    public int gridSize;
    public int boardSize;
    public boolean[][] board;
    
    private Color lightSquareColor = new Color(204, 204, 204);
    private Color darkSquareColor = new Color(0, 0, 0);

    public Board( boolean[][] board , int gridSize, int boardSize) {        
        this.gridSize = gridSize;
        this.boardSize = boardSize;
        this.board = board;
        setPreferredSize(new Dimension(boardSize + 20 , boardSize + 20 ));
        this.setBackground(Color.white);
        
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Color squareColor = (row + col) % 2 == 0 ? lightSquareColor : darkSquareColor;
                int x = col * boardSize / gridSize;
                int y = row * boardSize / gridSize;
                g.setColor(squareColor);
                g.fillRect(x, y, boardSize / gridSize, boardSize / gridSize); 
                if ( this.board != null && board[row][col]) { 
                    System.out.println( " row = " + row + " col = " + col);
                    drawQueen(g, row, col);
                }
            }
        }
    }
    
    public void drawQueen(Graphics g, int row, int col) {        
        Image queenImg;        
        String imageName = (row + col) % 2 == 0 ? "queen_dark.png" : "queen_light.png" ;
        
        queenImg = new ImageIcon(getClass().getResource("images/" + imageName)).getImage(); // Charge l'image de la reine
        int x = col * boardSize / gridSize;
        int y = row * boardSize / gridSize;
        g.drawImage(queenImg, x, y, boardSize / gridSize, boardSize / gridSize, this); // Dessine l'image de la reine à la position spécifiée
    }
    
  
    
}
