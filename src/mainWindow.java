
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import metaHeurestique.Genetic.Chromosome;
import metaHeurestique.Genetic.Genetic;
import metaHeurestique.PSO.PSO;
import solution.BFSsolution;
import solution.DFSsolution;
import solution.Heuristique1Solution;
import solution.Heuristique2Solution;
import solution.Solution;

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
    
    private Boolean showResult;
        
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
    private Genetic solutionWithGA;
    private PSO solutionWithPSO;
    private long nbrNouedGenere;
    private long nbrNouedDevelop;
    private long tempsExecution;
    long startTime, endTime;
    
    
    private JPanel parcours_Aveugle;
    private JPanel parcours_Metaheurstique;
    
    private String[] Aveugle = {"Parcours en largeur (BFS)", "Parcours en profondeur (DFS)",
        "Algorithme avec heurstique 1" ,"Algorithme avec heurstique 2" };
    private String[] metaheurstique = {"Algorithme Genetique", "Algorithme d'Optimisation par essaims particulaires"};
    
    private JComboBox<String> listAveugle;
    private JComboBox<String> listMeta;
    
    private String algorithmeSelectionne = "Parcours en largeur (BFS)";
    
    private JRadioButton radioButton1 =  new JRadioButton("Aveugle  ");
    private JRadioButton radioButton2 = new JRadioButton("Metaheurstique   ");
    private JLabel LABEL_methodeSelecionne;
    private JPanel methodePanel;
    
    private JLabel LABEL_parametrages;
    private JLabel LABEL_readMaxIter;
    private JLabel LABEL_sizePop;
    private JLabel LABEL_CrossoverRate;
    private JLabel LABEL_MutationRate;
    private JLabel LABEL_FitnessValue;
    
    
    private JLabel LABEL_Resualt;
    private JLabel LABEL_Fitness;
    
    private int  MAX_ITER;
    private int SIZE_POP;
        
    private double MUTATION_RATE;
    private double CROSSOVER_RATE; 
    
    private Chromosome chromosomeSolution;
    private int[] particuleSolution;

    private double ALPHA;
    private double C1;
    private double C2;
    
    private int fitness;
    
    private JLabel LABEL_readAlpha;
    private JLabel LABEL_C1;
    private JLabel LABEL_C2;
    
    private JTextField input_alpha;
    private JTextField input_c1;
    private JTextField input_c2;
    
    private JTextField input_crosooverRate;
    private JTextField input_mutationRate;
    private JTextField input_maxIter;  
    private JTextField input_sizePop;
    
    public mainWindow() {
        
        super(mainWindow.WINDOW_TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setBackground(Color.WHITE);
        this.showStatique = false;
        this.showResult = false;
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
                if ( showResult) mainWindow.this.removeResut();
                mainWindow.this.size = (int) spinner.getValue();
                mainWindow.this.solutionFounded = false;                  
                mainWindow.this.changeDimesion();
                
            }
        });
      
        this.BUTTON_Lancer.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
                mainWindow.this.BUTTON_Lancer.setEnabled(false);
              
                mainWindow.this.boardBool = new boolean[mainWindow.this.size][mainWindow.this.size]; 
                                
                JOptionPane.showMessageDialog(null, "Il est possible que cela prenne un peu de temps, veuillez patienter s'il vous plaît.\n", "Recherche en cours", JOptionPane.INFORMATION_MESSAGE);
               
                if (mainWindow.this.algorithmeSelectionne.equals("Parcours en largeur (BFS)")) {                      
                    
                    mainWindow.this.AppliqueBFS();
                      
                } else if (mainWindow.this.algorithmeSelectionne.equals("Parcours en profondeur (DFS)")) {
                    
                     mainWindow.this.AppliqueDFS();
                                                
                } else if (mainWindow.this.algorithmeSelectionne.equals("Algorithme avec heurstique 1")) {
                     
                    mainWindow.this.AppliqueH1();
                    
                } else if (mainWindow.this.algorithmeSelectionne.equals("Algorithme avec heurstique 2" )) {
                   
                    mainWindow.this.AppliqueH2();
                }
                else if (mainWindow.this.algorithmeSelectionne.equals("Algorithme Genetique" )){
                    mainWindow.this.MAX_ITER = Integer.parseInt( mainWindow.this.input_maxIter.getText());
                    mainWindow.this.SIZE_POP = Integer.parseInt( mainWindow.this.input_sizePop.getText());
                    mainWindow.this.CROSSOVER_RATE = Double.parseDouble(mainWindow.this.input_crosooverRate.getText());
                    mainWindow.this.MUTATION_RATE = Double.parseDouble(mainWindow.this.input_mutationRate.getText());
                    
                    mainWindow.this.AppliqueGA();
                }
                else if (mainWindow.this.algorithmeSelectionne.equals("Algorithme d'Optimisation par essaims particulaires" )){
                    mainWindow.this.MAX_ITER = Integer.parseInt( mainWindow.this.input_maxIter.getText());
                    mainWindow.this.SIZE_POP = Integer.parseInt( mainWindow.this.input_sizePop.getText());
                    mainWindow.this.ALPHA = Double.parseDouble(mainWindow.this.input_alpha.getText());
                    mainWindow.this.C1 = Double.parseDouble(mainWindow.this.input_c1.getText());
                    mainWindow.this.C2 = Double.parseDouble(mainWindow.this.input_c2.getText());
                    mainWindow.this.AppliquePSO();
                }
                if ( mainWindow.this.showStatique ) mainWindow.this.removeStatistique();
                if ( mainWindow.this.showResult ) mainWindow.this.removeResut();
                if ( mainWindow.this.radioButton1.isSelected() ){
                    if (mainWindow.this.solutionFounded){                      
                        mainWindow.this.showStatistique(); 
                        mainWindow.this.showSolution();
                    }
                    else JOptionPane.showMessageDialog(null, " Solution non trouvee ! ", "Erreur", JOptionPane.ERROR_MESSAGE);                    
                }
                else {
                    mainWindow.this.showResualt();
                    mainWindow.this.showSolution();                    
                }
                
                
                mainWindow.this.BUTTON_Lancer.setEnabled(true);
                
                mainWindow.this.revalidate();
                mainWindow.this.repaint();
                
                
             

            }
        });
        
        this.radioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.this.parcours_Aveugle.setVisible(true);
                mainWindow.this.parcours_Metaheurstique.setVisible(false);
                mainWindow.this.algorithmeSelectionne = "Parcours en largeur (BFS)";
                if ( mainWindow.this.showStatique ) mainWindow.this.removeStatistique();
                if ( mainWindow.this.showResult ) mainWindow.this.removeResut();
                mainWindow.this.revalidate();
                mainWindow.this.repaint();
                mainWindow.this.BUTTON_Lancer.setBounds(1000 + 100, 500, 150, 35);
                mainWindow.this.invisibleParametresGenetic();
                mainWindow.this.invisibleParametresPSO();
            }
        });

        this.radioButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               mainWindow.this.LABEL_parametrages.setVisible(true);
               mainWindow.this.parcours_Aveugle.setVisible(false);
               mainWindow.this.parcours_Metaheurstique.setVisible(true);
               mainWindow.this.algorithmeSelectionne = "Algorithme Genetique";
               if ( mainWindow.this.showStatique ) mainWindow.this.removeStatistique();
               if ( mainWindow.this.showResult )  mainWindow.this.removeResut();
               mainWindow.this.revalidate();
               mainWindow.this.repaint();  
               mainWindow.this.BUTTON_Lancer.setBounds(1000 + 100, 750, 150, 35);
               mainWindow.this.invisibleParametresPSO();
               mainWindow.this.visibleParametresGenetic();               
               if (mainWindow.this.algorithmeSelectionne.equals("Algorithme d'Optimisation par essaims particulaires")){
                   mainWindow.this.invisibleParametresGenetic();
                   mainWindow.this.visibleParametresPSO();
               }
            }
        });
        
        this.listAveugle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainWindow.this.algorithmeSelectionne = (String) listAveugle.getSelectedItem();
                System.out.println("Algorithme sélectionnée : " + algorithmeSelectionne);
            }
        });
        
        this.listMeta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainWindow.this.algorithmeSelectionne = (String) listMeta.getSelectedItem();
                System.out.println("Algorithme sélectionnée : " + algorithmeSelectionne);
                if (mainWindow.this.algorithmeSelectionne.equals("Algorithme Genetique")){
                    mainWindow.this.invisibleParametresPSO();
                    mainWindow.this.visibleParametresGenetic();
                }                    
                else{
                    mainWindow.this.invisibleParametresGenetic();
                    mainWindow.this.visibleParametresPSO();
                }
            }
        });
       
                
    }
    
    private void showSpinner(){
        this.LABEL_ReadN = new JLabel(" Donne la taille  ");
        this.LABEL_ReadN.setBounds(mainWindow.BOARD_WIDTH  + mainWindow.MARGIN_TEXT, 100, 800, 100);
        this.LABEL_ReadN.setFont(this.FONT_Body);
        this.add(this.LABEL_ReadN);
        
        this.model = new SpinnerNumberModel(8, 8, 32, 1);
        this.spinner = new JSpinner(model);
        this.spinner.setFont(this.FONT_Body);
        this.spinner.setBounds(mainWindow.BOARD_WIDTH + 240, 135, 70, 30);
        this.add(this.spinner);
    }
    
    private void showSelectionneMethod(){
        this.LABEL_methodeSelecionne = new JLabel("Selectionne une methode");
        this.LABEL_methodeSelecionne.setBounds(mainWindow.BOARD_WIDTH + 60, 170, 900, 100);
        this.LABEL_methodeSelecionne.setFont(FONT_Body);
        this.add(this.LABEL_methodeSelecionne);        
            
        ButtonGroup groupMethode = new ButtonGroup();
        groupMethode.add(radioButton1);
        groupMethode.add(radioButton2);
        
        this.methodePanel = new JPanel();
        methodePanel.setLayout(new BoxLayout(methodePanel, BoxLayout.Y_AXIS));
        
        methodePanel.add(radioButton1);
        methodePanel.add(radioButton2);
        methodePanel.setBounds(mainWindow.BOARD_WIDTH + 150, 250 , 900, 100);
        radioButton1.setFont(FONT_Body);
        radioButton2.setFont(FONT_Body);
        methodePanel.setOpaque(false);
        radioButton1.setSelected(true);
        this.add(this.methodePanel);
    }
    
    private void showTitreOption(){
        this.LABEL_Option = new JLabel(" Options ");
        this.LABEL_Option.setFont(this.FONT_Title);
        this.LABEL_Option.setBounds(mainWindow.BOARD_WIDTH + mainWindow.MARGIN_TITLE, 50 , 800, 100 );
        this.add(this.LABEL_Option);
    }
    
    private void showListAveugle(){
        this.listAveugle = new JComboBox<>(this.Aveugle);
        this.listAveugle.setFont(FONT_Body);
        this.parcours_Aveugle = new JPanel();
        parcours_Aveugle.setBounds(mainWindow.BOARD_WIDTH + 60, 180 + 60 + 50 +  80, 400, 200);
        this.parcours_Aveugle.setOpaque(false);
        this.listAveugle.setAlignmentX(Component.LEFT_ALIGNMENT); // Ajout de cette ligne pour aligner le JComboBox à gauche
        this.parcours_Aveugle.add(this.listAveugle);
        this.add(this.parcours_Aveugle);
        
    }
    
    private void showListMeta(){
        this.listMeta = new JComboBox<>(this.metaheurstique);
        this.listMeta.setSelectedIndex(0);
        this.listMeta.setFont(FONT_Body);
        this.parcours_Metaheurstique = new JPanel();
        parcours_Metaheurstique.setBounds(mainWindow.BOARD_WIDTH + 60, 180 + 60 + 50 +  80, 600, 200);
        this.parcours_Metaheurstique.setOpaque(false);
        this.listAveugle.setAlignmentX(Component.LEFT_ALIGNMENT); 
        this.parcours_Metaheurstique.add(this.listMeta);
        this.add(this.parcours_Metaheurstique);
        
    }
    
    private void showOption(){
        
        this.showTitreOption();
        
        this.showSpinner();
        
        this.showSelectionneMethod();
         
        this.LABEL_parcourType = new JLabel(" Choix du l'Algorithme :  ");
        this.LABEL_parcourType.setBounds(mainWindow.BOARD_WIDTH + 60, 220 + 80, 900, 100);
        this.LABEL_parcourType.setFont(this.FONT_Body);
        this.add(this.LABEL_parcourType);
 
        this.showListAveugle();
        this.showListMeta();
        this.parcours_Metaheurstique.setVisible(false);
        
        this.showTitreParmetragesOption();
        
        this.LABEL_parametrages.setVisible(false);
        
        this.showParametresGenetic();
        
        this.invisibleParametresGenetic();
        
        this.showParametresPSO();
        
        this.invisibleParametresPSO();
        
        this.buttonLancer();
        
        

}
  
    private void showTitreParmetragesOption(){
        this.LABEL_parametrages = new JLabel(" Fixer les paramètres du l'algorithme : ");
        this.LABEL_parametrages.setFont(this.FONT_Body);
        this.LABEL_parametrages.setBounds(mainWindow.BOARD_WIDTH + 60, 400 , 800, 100 );
        this.add(this.LABEL_parametrages);
    }
    
    private void showParametresPSO(){
       this.LABEL_readMaxIter.setVisible(true);
       this.input_maxIter.setVisible(true);
       this.LABEL_sizePop.setVisible(true);
       this.input_sizePop.setVisible(true);
       
       this.LABEL_readAlpha = new JLabel(" Taux d'apprentissage :  ");
        this.LABEL_readAlpha.setBounds(mainWindow.BOARD_WIDTH + 100, 490 , 600, 200);
        this.LABEL_readAlpha.setFont(this.FONT_Body);
        this.add(this.LABEL_readAlpha);
        
        this.input_alpha = new JTextField();
        this.input_alpha.setBounds(mainWindow.BOARD_WIDTH + 320, 570 , 200, 30);
        this.input_alpha.setFont(this.FONT_Body);
        this.input_alpha.setText("0.5");
        this.add(this.input_alpha);
        
        this.LABEL_C1 = new JLabel(" C1 :  ");
        this.LABEL_C1.setBounds(mainWindow.BOARD_WIDTH + 100, 530 , 600, 200);
        this.LABEL_C1.setFont(this.FONT_Body);
        this.add(this.LABEL_C1);
        
        this.input_c1 = new JTextField();
        this.input_c1.setBounds(mainWindow.BOARD_WIDTH + 320, 610 , 200, 30);
        this.input_c1.setFont(this.FONT_Body);
        this.input_c1.setText("0.9");
        this.add(this.input_c1);
        
        this.LABEL_C2 = new JLabel(" C2 :  ");
        this.LABEL_C2.setBounds(mainWindow.BOARD_WIDTH + 100, 570 , 600, 200);
        this.LABEL_C2.setFont(this.FONT_Body);
        this.add(this.LABEL_C2);
        
        this.input_c2 = new JTextField();
        this.input_c2.setBounds(mainWindow.BOARD_WIDTH + 320, 650 , 200, 30);
        this.input_c2.setFont(this.FONT_Body);
        this.input_c2.setText("0.9");
        this.add(this.input_c2);
       
       
       
    }
    
    private void invisibleParametresPSO(){
        this.LABEL_readMaxIter.setVisible(false);
        this.input_maxIter.setVisible(false);
        this.LABEL_sizePop.setVisible(false);
        this.input_sizePop.setVisible(false);
        this.LABEL_C1.setVisible(false);
        this.LABEL_readAlpha.setVisible(false);
        this.input_alpha.setVisible(false);
        this.input_c1.setVisible(false);
        this.LABEL_C2.setVisible(false);
        this.input_c2.setVisible(false);
        

        
        
    }
    
    private void visibleParametresPSO(){
       this.LABEL_readMaxIter.setVisible(true);
       this.input_maxIter.setVisible(true);
       this.LABEL_sizePop.setVisible(true);
       this.input_sizePop.setVisible(true);
       this.LABEL_C1.setVisible(true);
       this.LABEL_readAlpha.setVisible(true);
       this.input_alpha.setVisible(true);
       this.input_c1.setVisible(true);
       this.LABEL_C2.setVisible(true);
       this.input_c2.setVisible(true);
    }
    
    private void showParametresGenetic(){
        
        this.LABEL_readMaxIter = new JLabel(" Nombre d'iteration :  ");
        this.LABEL_readMaxIter.setBounds(mainWindow.BOARD_WIDTH + 100, 410 , 600, 200);
        this.LABEL_readMaxIter.setFont(this.FONT_Body);
        this.add(this.LABEL_readMaxIter);
        
        this.input_maxIter = new JTextField();
        this.input_maxIter.setBounds(mainWindow.BOARD_WIDTH + 320, 490 , 200, 30);
        this.input_maxIter.setFont(this.FONT_Body);
        this.input_maxIter.setText("1000");
        this.add(this.input_maxIter);
        
        this.LABEL_sizePop = new JLabel(" Taille population :  ");
        this.LABEL_sizePop.setBounds(mainWindow.BOARD_WIDTH + 100, 450 , 600, 200);
        this.LABEL_sizePop.setFont(this.FONT_Body);
        this.add(this.LABEL_sizePop);
        
        this.input_sizePop = new JTextField();
        this.input_sizePop.setBounds(mainWindow.BOARD_WIDTH + 320, 530 , 200, 30);
        this.input_sizePop.setFont(this.FONT_Body);
        this.input_sizePop.setText("50");
        this.add(this.input_sizePop);
        
        this.LABEL_CrossoverRate = new JLabel(" Taux du croissement :  ");
        this.LABEL_CrossoverRate.setBounds(mainWindow.BOARD_WIDTH + 100, 490 , 600, 200);
        this.LABEL_CrossoverRate.setFont(this.FONT_Body);
        this.add(this.LABEL_CrossoverRate);
        
        this.input_crosooverRate = new JTextField();
        this.input_crosooverRate.setBounds(mainWindow.BOARD_WIDTH + 320, 570 , 200, 30);
        this.input_crosooverRate.setFont(this.FONT_Body);
        this.input_crosooverRate.setText("0.3");
        this.add(this.input_crosooverRate);
        
        this.LABEL_MutationRate = new JLabel(" Taux du mutation :  ");
        this.LABEL_MutationRate.setBounds(mainWindow.BOARD_WIDTH + 100, 530 , 600, 200);
        this.LABEL_MutationRate.setFont(this.FONT_Body);
        this.add(this.LABEL_MutationRate);
        
        this.input_mutationRate = new JTextField();
        this.input_mutationRate.setBounds(mainWindow.BOARD_WIDTH + 320, 610 , 200, 30);
        this.input_mutationRate.setFont(this.FONT_Body);
        this.input_mutationRate.setText("1");
        this.add(this.input_mutationRate);
        
               
    }
    
    private void invisibleParametresGenetic(){
        this.LABEL_readMaxIter.setVisible(false);
        this.LABEL_sizePop.setVisible(false);
        this.LABEL_CrossoverRate.setVisible(false);
        this.LABEL_MutationRate.setVisible(false);
        this.input_maxIter.setVisible(false);
        this.input_sizePop.setVisible(false);
        this.input_crosooverRate.setVisible(false);
        this.input_mutationRate.setVisible(false);
        
    }
    
    private void visibleParametresGenetic(){
        this.LABEL_readMaxIter.setVisible(true);
        this.LABEL_sizePop.setVisible(true);
        this.LABEL_CrossoverRate.setVisible(true);
        this.LABEL_MutationRate.setVisible(true);
        this.input_maxIter.setVisible(true);
        this.input_sizePop.setVisible(true);
        this.input_crosooverRate.setVisible(true);
        this.input_mutationRate.setVisible(true);
    }
    
    private void buttonLancer(){
        this.BUTTON_Lancer = new JButton();
        this.BUTTON_Lancer.setText(" Lancer ");
        this.BUTTON_Lancer.setFont(this.FONT_Body);        
        if ( this.radioButton1.isSelected()) this.BUTTON_Lancer.setBounds(1000 + 100, 500, 150, 35);
        else this.BUTTON_Lancer.setBounds(1000 + 100, 1000, 150, 35);
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
        mainWindow.this.solutionFounded = mainWindow.this.solutionWithDFS.solveN_Queens(mainWindow.this.solutionWithDFS.board); 
        mainWindow.this.endTime = System.currentTimeMillis();
        Solution.copyMatrix(mainWindow.this.solutionWithDFS.board, boardBool);
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
    
    private void AppliqueGA(){
        
        mainWindow.this.solutionWithGA = new Genetic(MAX_ITER, SIZE_POP , MUTATION_RATE, CROSSOVER_RATE);
        mainWindow.this.startTime = System.currentTimeMillis();
        mainWindow.this.chromosomeSolution = mainWindow.this.solutionWithGA.geneticAlgorithm(mainWindow.this.size);
        mainWindow.this.endTime = System.currentTimeMillis();
        mainWindow.this.tempsExecution = mainWindow.this.endTime - mainWindow.this.startTime;  
        mainWindow.this.fitness = mainWindow.this.chromosomeSolution.fitness;
        mainWindow.this.tranformeMatrix(chromosomeSolution.genes);
 
    }
    
    private void AppliquePSO(){
        
        mainWindow.this.solutionWithPSO = new PSO(MAX_ITER, SIZE_POP , ALPHA, C1, C2);
        mainWindow.this.startTime = System.currentTimeMillis();
        mainWindow.this.particuleSolution = mainWindow.this.solutionWithPSO.PSOAlgorithme(mainWindow.this.size);
        mainWindow.this.endTime = System.currentTimeMillis();
        mainWindow.this.tempsExecution = mainWindow.this.endTime - mainWindow.this.startTime;  
        mainWindow.this.fitness = mainWindow.this.solutionWithPSO.GBestFitness;
        mainWindow.this.tranformeMatrix(particuleSolution);

    }
    
    private void showSolution(){
        
        mainWindow.this.remove(mainWindow.this.board);                
        mainWindow.this.board = new Board( mainWindow.this.boardBool , mainWindow.this.size, mainWindow.BOARD_WIDTH);                
        mainWindow.this.add(mainWindow.this.board);
        mainWindow.this.revalidate();
        mainWindow.this.repaint();  
    }
    
    private void showResualt(){
        
        mainWindow.this.showResult = true;

        // Ajouter Title 
        mainWindow.this.LABEL_Resualt = new JLabel(" Résultat : ");
        mainWindow.this.LABEL_Resualt.setFont(this.FONT_Title);
        mainWindow.this.LABEL_Resualt.setBounds(1000 + 20, 1000 - 220, 800, 100);
        mainWindow.this.add(mainWindow.this.LABEL_Resualt);
        
        // Les informations
        mainWindow.this.LABEL_TempsExecution = new JLabel(" Temps d'execution  :  ");
        mainWindow.this.LABEL_TempsExecution.setBounds(1000 + 60, 1000 - 180, 400, 100);
        mainWindow.this.LABEL_TempsExecution.setFont(FONT_Body);
        
        mainWindow.this.add(mainWindow.this.LABEL_TempsExecution);

        mainWindow.this.LABEL_TempsExecutionValue = new JLabel(Long.toString(mainWindow.this.tempsExecution) + " ms");
        mainWindow.this.LABEL_TempsExecutionValue.setBounds(1000 + 400, 1000 - 180, 800, 100);
        mainWindow.this.LABEL_TempsExecutionValue.setFont(FONT_Body);
        
        mainWindow.this.add(mainWindow.this.LABEL_TempsExecutionValue);
        
        mainWindow.this.LABEL_Fitness = new JLabel(" Fitness  :  ");
        mainWindow.this.LABEL_Fitness.setBounds(1000 + 60, 1000 - 130, 400, 100);
        mainWindow.this.LABEL_Fitness.setFont(FONT_Body);
        
        mainWindow.this.add(mainWindow.this.LABEL_Fitness);
        
        mainWindow.this.LABEL_FitnessValue = new JLabel(Long.toString(mainWindow.this.fitness));
        mainWindow.this.LABEL_FitnessValue.setBounds(1000 + 400, 1000 - 130, 800, 100);
        mainWindow.this.LABEL_FitnessValue.setFont(FONT_Body);
        
        mainWindow.this.add(mainWindow.this.LABEL_FitnessValue);
        
        
        
        
    }
    
    private void removeResut(){
       mainWindow.this.showStatique = false; 
       mainWindow.this.remove(this.LABEL_Resualt);
       mainWindow.this.remove(this.LABEL_TempsExecution);
       mainWindow.this.remove(this.LABEL_TempsExecutionValue);
       mainWindow.this.remove(this.LABEL_Fitness);
       mainWindow.this.remove(this.LABEL_FitnessValue);
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
    
    private void tranformeMatrix(int [] solution){
        mainWindow.this.boardBool = new boolean[mainWindow.this.size][mainWindow.this.size];
        for(int i = 0; i < solution.length; i++ )
            mainWindow.this.boardBool[i][solution[i]] = true;
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
