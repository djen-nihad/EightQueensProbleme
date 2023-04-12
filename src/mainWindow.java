
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import solution.BFSsolution;
import solution.DFSsolution;
import solution.Heuristique1Solution;
import solution.Heuristique2Solution;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author client
 */
public class mainWindow extends javax.swing.JFrame {

    /**
     * Creates new form mainWindow
     */
    //Title
    private static final String WINDOW_TITLE = "Slove N-queen problem ";
    
    private static final int BOARD_WIDTH = 1000;
    private static final int MARGIN_TITLE = 20;
    private static final int MARGIN_TEXT = 60;
    
    //FONT
    private Font FONT_Body = new Font("Times New Roman", Font.PLAIN, 24);
    private Font FONT_Title = new Font("Times New Roman", Font.BOLD, 32);
    
    // LABBEL    
    private JLabel LABEL_TempsExecutionValue;
    private JLabel LABEL_NbrNouedGenerValue;
    private JLabel LABEL_NbrNouedDevelopValue;
    private JLabel LABEL_Option;
    private JLabel LABEL_ReadN;
    private JLabel LABEL_Title;
    private JLabel LABEL_parcourType;
    
    //BUTTON
    private JButton BUTTON_Lancer;    
    
    // Statistiques
    
    private JLabel LABEL_Statistique;
    private JLabel LABEL_TempsExecution;
    private JLabel LABEL_NbrNouedGener;
    private JLabel LABEL_NbrNouedDevelop;
    private Boolean showStatique;
        
    //SPINNER
    private SpinnerModel model;
    private JSpinner spinner;
    
    //Board
    private Board board;
    private int size = 8;
    
    public boolean solutionFounded;
    public boolean arreterExecution;
    
    
    // Pour resoudre le problem
    private boolean[][] boardBool;
    private BFSsolution solutionWithBFS;
    private DFSsolution solutionWithDFS;
    private Heuristique1Solution solutionWithH1;
    private Heuristique2Solution solutionWithH2;
    private long nbrNouedGenere;
    private long nbrNouedDevelop;
    private long tempsExecution;
    long startTime, endTime;
    
    private JPanel parcours;
    private JRadioButton option1 = new JRadioButton("Parcours en largeur (BFS)");
    private JRadioButton option2 = new JRadioButton("Parcours en profondeur (DFS)");
    private JRadioButton option3 = new JRadioButton("Heuristique 1 ");
    private JRadioButton option4 = new JRadioButton("Heuristique 2 ");
    
    public mainWindow() {
        
        super(mainWindow.WINDOW_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setBackground(Color.WHITE);
        this.showStatique = false;
        this.showTitle();
        this.showOption();
        this.listeners();
        this.showBoard();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
       
        
    }


    
    
    private void listeners(){
        
        this.BUTTON_Lancer.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                //    main.this.BUTTON_Lancer.setBackground(Color.magenta);
            }

            public void mouseExited(MouseEvent evt) {
                mainWindow.this.BUTTON_Lancer.setBackground(UIManager.getColor("control"));
            }
        });
      
        this.spinner.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {

                if ( showStatique) mainWindow.this.removeStatistique();
                mainWindow.this.size = (int) spinner.getValue();
                mainWindow.this.solutionFounded = false;                  
                mainWindow.this.changeDimesion();
                
            }
        });
      
        this.BUTTON_Lancer.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
                mainWindow.this.BUTTON_Lancer.setEnabled(false);
              
                mainWindow.this.boardBool = new boolean[mainWindow.this.size][mainWindow.this.size]; 
                
                
                JOptionPane.showMessageDialog(null, "En train de chercher une solution, veuillez patienter...", "Recherche en cours", JOptionPane.INFORMATION_MESSAGE);
               
                if (mainWindow.this.option1.isSelected()) {                      
                    
                    mainWindow.this.AppliqueBFS();
                      
                } else if (mainWindow.this.option2.isSelected()) {
                    
                     mainWindow.this.AppliqueDFS();
                                                
                } else if (mainWindow.this.option3.isSelected()) {
                     
                    mainWindow.this.AppliqueH1();
                    
                } else if (mainWindow.this.option4.isSelected()) {
                   
                    mainWindow.this.AppliqueH2();
                }
                
                if (mainWindow.this.solutionFounded) {
                    
                  
                    mainWindow.this.showSolution();
                    
                }
                if (mainWindow.this.solutionFounded){
                    if ( mainWindow.this.showStatique ) mainWindow.this.removeStatistique();
                    mainWindow.this.showStatistique(); 
                    mainWindow.this.showSolution();
                }
                else JOptionPane.showMessageDialog(null, " Solution non trouvee ! ", "Erreur", JOptionPane.ERROR_MESSAGE);
                
                mainWindow.this.BUTTON_Lancer.setEnabled(true);
                
                mainWindow.this.revalidate();
                mainWindow.this.repaint();
                
                
             

            }
        });
         
       
        
    }
    
    private void showOption(){
        
        this.LABEL_Option = new JLabel(" Options ");
        this.LABEL_Option.setFont(this.FONT_Title);
        this.LABEL_Option.setBounds(mainWindow.BOARD_WIDTH + mainWindow.MARGIN_TITLE, 50 , 800, 100 );
        this.add(this.LABEL_Option);
        
        this.LABEL_ReadN = new JLabel(" Donne la taille  ");
        this.LABEL_ReadN.setBounds(mainWindow.BOARD_WIDTH  + mainWindow.MARGIN_TEXT, 100, 800, 100);
        this.LABEL_ReadN.setFont(this.FONT_Body);
        this.add(this.LABEL_ReadN);
        
        this.model = new SpinnerNumberModel(8, 8, 32, 1);
        this.spinner = new JSpinner(model);
        this.spinner.setFont(this.FONT_Body);
        this.spinner.setBounds(mainWindow.BOARD_WIDTH + 240, 135, 70, 30);
        this.add(this.spinner);
        
        this.LABEL_parcourType = new JLabel(" Type du parcours  ");
        this.LABEL_parcourType.setBounds(mainWindow.BOARD_WIDTH + 60, 140 + 40, 900, 100);
        this.LABEL_parcourType.setFont(this.FONT_Body);
        this.add(this.LABEL_parcourType);

        ButtonGroup group = new ButtonGroup();
        group.add(option1);
        group.add(option2);
        group.add(option3);
        group.add(option4);
        option1.setSelected(true);
        this.parcours = new JPanel();
        parcours.setLayout(new BoxLayout(parcours, BoxLayout.Y_AXIS));
        parcours.add(option1);
        parcours.add(option2);
        parcours.add(option3);
        parcours.add(option4);
        parcours.setBounds(mainWindow.BOARD_WIDTH + 100, 180 + 80, 800, 200);
        option1.setFont(FONT_Body);
        option2.setFont(FONT_Body);
        option3.setFont(FONT_Body);
        option4.setFont(FONT_Body);
        option1.setOpaque(false);
        option2.setOpaque(false);
        option3.setOpaque(false);
        option4.setOpaque(false);
        parcours.setOpaque(false);
        this.add(this.parcours);
        
        this.BUTTON_Lancer = new JButton();
        this.BUTTON_Lancer.setText(" Lancer ");
        this.BUTTON_Lancer.setFont(this.FONT_Body);
        this.BUTTON_Lancer.setBounds(1000 + 100, 270 + 180, 150, 35);
        this.BUTTON_Lancer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(this.BUTTON_Lancer);
       
}

    private void showBoard(){
        
        this.board = new Board( null , this.size, BOARD_WIDTH);
        this.add(this.board);
        
    }
    
    private void changeDimesion(){

        mainWindow.this.remove(mainWindow.this.board);
                
        mainWindow.this.board = new Board( null , mainWindow.this.size, mainWindow.BOARD_WIDTH);
                
        mainWindow.this.add(mainWindow.this.board);
        mainWindow.this.revalidate();
        mainWindow.this.repaint();        
    }
    
    private void showTitle() {
        this.LABEL_Title = new JLabel(" Probleme de N - Reines ");
        this.LABEL_Title.setFont(this.FONT_Title);
        this.LABEL_Title.setBounds(1000 + 150, 0, 800, 100);
        this.LABEL_Title.setForeground(Color.BLUE); // définir la couleur du texte
        this.LABEL_Title.setHorizontalAlignment(JLabel.CENTER);
        this.add(this.LABEL_Title);
    }

    private void AppliqueBFS(){
        
        mainWindow.this.solutionWithBFS = new BFSsolution(mainWindow.this.size);                    
        mainWindow.this.startTime = System.currentTimeMillis();
        mainWindow.this.solutionFounded = mainWindow.this.solutionWithBFS.solveN_Queens(mainWindow.this.boardBool);                    
        mainWindow.this.endTime = System.currentTimeMillis();
        mainWindow.this.tempsExecution = mainWindow.this.endTime - mainWindow.this.startTime;                    
        mainWindow.this.nbrNouedGenere = solutionWithBFS.nbrNodeGener;
        mainWindow.this.nbrNouedDevelop = solutionWithBFS.nbrNodeDevelop;
        
    }
    
    private void AppliqueDFS(){
       
        mainWindow.this.solutionWithDFS = new DFSsolution(mainWindow.this.size);                    
        mainWindow.this.startTime = System.currentTimeMillis();
        mainWindow.this.solutionFounded = mainWindow.this.solutionWithDFS.solveN_Queens(mainWindow.this.boardBool); 
        solutionWithDFS.printBorad(boardBool);
        mainWindow.this.endTime = System.currentTimeMillis();
        mainWindow.this.tempsExecution = mainWindow.this.endTime - mainWindow.this.startTime;                    
        mainWindow.this.nbrNouedGenere = solutionWithDFS.nbrNodeGener;
        mainWindow.this.nbrNouedDevelop = solutionWithDFS.nbrNodeDevelop;
        
        
    }
    
    private void AppliqueH1(){
        
        mainWindow.this.solutionWithH1 = new Heuristique1Solution(mainWindow.this.size);
        mainWindow.this.startTime = System.currentTimeMillis();
        mainWindow.this.solutionFounded = mainWindow.this.solutionWithH1.solveN_Queens(mainWindow.this.boardBool);
        mainWindow.this.endTime = System.currentTimeMillis();
        mainWindow.this.tempsExecution = mainWindow.this.endTime - mainWindow.this.startTime;                    
        mainWindow.this.nbrNouedGenere = solutionWithH1.nbrNodeGener;
        mainWindow.this.nbrNouedDevelop = solutionWithH1.nbrNodeDevelop;
    }
    
    private void AppliqueH2(){
        
        mainWindow.this.solutionWithH2 = new Heuristique2Solution(mainWindow.this.size);
        mainWindow.this.startTime = System.currentTimeMillis();
        mainWindow.this.solutionFounded = mainWindow.this.solutionWithH2.solveN_Queens(mainWindow.this.boardBool);
        mainWindow.this.endTime = System.currentTimeMillis();
        mainWindow.this.tempsExecution = mainWindow.this.endTime - mainWindow.this.startTime;                    
        mainWindow.this.nbrNouedGenere = solutionWithH2.nbrNodeGener;
        mainWindow.this.nbrNouedDevelop = solutionWithH2.nbrNodeDevelop;
    }
    
    private void showSolution(){
        
        mainWindow.this.remove(mainWindow.this.board);                
        mainWindow.this.board = new Board( mainWindow.this.boardBool , mainWindow.this.size, mainWindow.BOARD_WIDTH);                
        mainWindow.this.add(mainWindow.this.board);
        mainWindow.this.revalidate();
        mainWindow.this.repaint();  
    }
    
    private void showStatistique() {
        
        mainWindow.this.showStatique = true;

        // Ajouter Title 
        mainWindow.this.LABEL_Statistique = new JLabel(" Statistiques ");
        mainWindow.this.LABEL_Statistique.setFont(this.FONT_Title);
        mainWindow.this.LABEL_Statistique.setBounds(1000 + 20, 1000 - 320, 800, 100);

        // Les informations
        mainWindow.this.LABEL_TempsExecution = new JLabel(" Temps d'execution  :  ");
        mainWindow.this.LABEL_TempsExecution.setBounds(1000 + 60, 1000 - 250, 400, 100);
        mainWindow.this.LABEL_TempsExecution.setFont(FONT_Body);

        mainWindow.this.LABEL_TempsExecutionValue = new JLabel(Long.toString(this.tempsExecution) + " ms");
        mainWindow.this.LABEL_TempsExecutionValue.setBounds(1000 + 400, 1000 - 250, 800, 100);
        mainWindow.this.LABEL_TempsExecutionValue.setFont(FONT_Body);

        this.LABEL_NbrNouedGener = new JLabel(" Nombre de noueds generer  :  ");
        this.LABEL_NbrNouedGener.setBounds(1000 + 60, 1000 - 200, 800, 100);
        this.LABEL_NbrNouedGener.setFont(FONT_Body);

        this.LABEL_NbrNouedGenerValue = new JLabel(Long.toString(this.nbrNouedGenere) + " noueds");
        this.LABEL_NbrNouedGenerValue.setBounds(1000 + 400, 1000 - 200, 400, 100);
        this.LABEL_NbrNouedGenerValue.setFont(FONT_Body);

        this.LABEL_NbrNouedDevelop = new JLabel(" Nombre de noueds develope  :  ");
        this.LABEL_NbrNouedDevelop.setBounds(1000 + 60, 1000 - 150, 800, 100);
        this.LABEL_NbrNouedDevelop.setFont(FONT_Body);

        this.LABEL_NbrNouedDevelopValue = new JLabel(Long.toString(this.nbrNouedDevelop) + " noueds");
        this.LABEL_NbrNouedDevelopValue.setBounds(1000 + 400, 1000 - 150, 800, 100);
        this.LABEL_NbrNouedDevelopValue.setFont(FONT_Body);

        mainWindow.this.add(this.LABEL_Statistique);
        mainWindow.this.add(this.LABEL_TempsExecution);
        mainWindow.this.add(this.LABEL_TempsExecutionValue);
        mainWindow.this.add(this.LABEL_NbrNouedGener);
        mainWindow.this.add(this.LABEL_NbrNouedGenerValue);
        mainWindow.this.add(this.LABEL_NbrNouedDevelop);
        mainWindow.this.add(this.LABEL_NbrNouedDevelopValue);

    }
    
    private void removeStatistique(){
        
        mainWindow.this.showStatique = false;
        mainWindow.this.remove(this.LABEL_Statistique);
        mainWindow.this.remove(this.LABEL_TempsExecution);
        mainWindow.this.remove(this.LABEL_TempsExecutionValue);
        mainWindow.this.remove(this.LABEL_NbrNouedGener);
        mainWindow.this.remove(this.LABEL_NbrNouedGenerValue);
        mainWindow.this.remove(this.LABEL_NbrNouedDevelop);
        mainWindow.this.remove(this.LABEL_NbrNouedDevelopValue);      
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 950, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 614, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
      
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
