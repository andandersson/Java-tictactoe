package game;

import javax.swing.*;
import java.awt.*;

public class LS extends javax.swing.JFrame {

    public static final int EMPTY    = 0;
    public static final int HUMAN    = 1;
    public static final int COMPUTER = 2;
    public static final int PLAYERTWO = 3;

    public static final int CONTINUE     = 6;

    public static final int SIZE = 3;
    private int[][] board = new int[SIZE][SIZE];  // The marks on the board
    private javax.swing.JButton[][] jB;           // The buttons of the board
//private static long count = 0;

    private static int turn = HUMAN;                    // HUMAN starts the game

    private static int [][] pieceCounter = new int[SIZE][SIZE];
    static int []UsedSquares = new int[9];  //keeping the placements in an array

    public LS() {

        // Close the window when the user exits
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        initBoard();      // Set up the board
    }

    // Initalize the board
    private void initBoard(){
        // Create a 3*3 gridlayput to hold the buttons
        java.awt.GridLayout layout = new GridLayout(3, 3);
        getContentPane().setLayout(layout);
        if(turn == HUMAN) {

        }

        // The board is a 3*3 grid of buttons
        jB = new Button[SIZE][SIZE];
        for (int i=0; i<SIZE; i++) {
            for (int j=0; j<SIZE; j++) {

                // Create a new button and add an actionListerner to it
                jB[i][j] = new Button(i,j);
                // Add an action listener to the button to handle mouse clicks
                jB[i][j].addActionListener(new java.awt.event.ActionListener() {

                    public void actionPerformed(java.awt.event.ActionEvent act) {

                        jBAction(act);

//    							System.out.println("actionPerformed(java.awt.event.ActionEvent act");

                    }
                });
                add(jB[i][j]);   // Add the buttons to the GridLayout

                board[i][j] = EMPTY;     // Initialize all marks on the board to empty
            }
        }
        // Pack the GridLayout and make it visible
        pack();
    }

    int checkSquare(int row, int col) {

        return pieceCounter[row][col];
    }

    boolean fullBoard(){

    }
    private void jBAction(java.awt.event.ActionEvent act) {

        Button thisButton = (Button) act.getSource();   // Get the button clicked on
        int i = thisButton.get_i();
        int j = thisButton.get_j();
        int squareCheck =checkSquare(i, j);
        int turnCount = 0;

        if(squareCheck == 0 && turn == HUMAN ){

            //Squarecheck is checking if the square is empty. this will
            //make placing in ockupied squares impossible

            thisButton.setIcon(new ImageIcon("Pictures/X.png"));
            pieceCounter[i][j] = HUMAN;


            int CounterOne = 0;
            for( int k = 0; k<= pieceCounter.length-1; k++){
                for (int l = 0; l<=pieceCounter[k].length-1; l++) {
                    if( pieceCounter[k][l] == HUMAN) {
                        UsedSquares[CounterOne] = HUMAN;

                    }
                    CounterOne ++;

                }
            }

            turn = (turn == HUMAN) ? COMPUTER : HUMAN;	//changing turn to computer
        }

//    	    	}

        if(turn == COMPUTER) {

            MoveInfo ComputerTurn = new MoveInfo();
            ComputerTurn.board = this.UsedSquares;
            ComputerTurn.COMP = this.COMPUTER;
            ComputerTurn.HUMAN = this.HUMAN;

            MoveInfo ComputerMove = ComputerTurn.findCompMove();

            int ComputerMovePos =ComputerMove.move;
            UsedSquares[ComputerMovePos] = COMPUTER;

            int[][] tempArray = new int[SIZE][SIZE]; //array for storing the computers moves on the board
            int Counter = 0;
            for( int k = 0; k<= tempArray.length-1; k++){
                for (int l = 0; l<=tempArray[k].length-1; l++) {
                    if( Counter== ComputerMovePos) {
                        jB[k][l].setIcon(new ImageIcon("Pictures/O.png"));
                        pieceCounter[k][l] = COMPUTER;
                    }
                    Counter ++;

                }
            }

            turn = (turn == HUMAN) ? COMPUTER : HUMAN;

            ComputerTurn = null;


            if(checkResult() != CONTINUE ){
                gameOver();



            }



        }
    }

    void gameOver() {
        //checking if we have awinner yet
        int[][] gameOverArray = new int[SIZE][SIZE];
        for( int k = 0; k<= gameOverArray.length-1; k++){
            for (int l = 0; l<=gameOverArray[k].length-1; l++) {

                jB[k][l].setEnabled(false);

            }
        }
        JFrame NoMorePlaying = new JFrame();
        JOptionPane.showMessageDialog(NoMorePlaying, "Game over!");

    }

    int checkIfWinner(int player){
        //checking rows, columns and diagonals, a copy of the method in MoveInfo

        int p = player; //1

        int b[] = this.UsedSquares;

        if(b[0] ==p && b[1] == p && b[2] == p)	{
            System.out.println("player: "+ player +" first return");
            return 1;

        }
        if (b[3] ==p && b[4] == p && b[5] == p){
            System.out.println("player: "+ player +" second return");
            return 1;

        }

        if (b[6] ==p && b[7] == p && b[8] == p){
            System.out.println("player: "+ player +" third return");
            return 1;

        }

        for(int q = 0; q<= 2; q++) {
            if (b[q]==p  && b[q+3]==p  && b[q+6]==p){
                System.out.println("player: "+ player +" fourth return");
                return 1;
            }
        }
        if(b[0]==p && b[4]==p && b[8]==p) {
            return 1;
        }
        if (b[2]==p && b[4]==p && b[6]==p) {
            return 1;
        }


        return 0;

    }



    int checkResult() {
        if(checkIfWinner(HUMAN) ==1 || checkIfWinner(COMPUTER) ==1){
            System.out.println("checkIfWinner(HUMAN): "+checkIfWinner(HUMAN)+ " checkIfWinner(COMPUTER): "+checkIfWinner(COMPUTER));
            //datorn vinner när två i rad;
            return 0;
        }
        return CONTINUE;

    }


    public static void main (String [] args){
        LS lsGUI = new LS(); //constructor, anropar initBoard
        for(int j=0; j<= pieceCounter.length-1; j++ ){
            for (int i = 0; i<= pieceCounter[j].length -1; i ++){
                pieceCounter[i][j] = 0;
            }
        }
        for (int k = 0; k<= UsedSquares.length-1; k++) {
            UsedSquares[k] = 0;
        }

        String threadName = Thread.currentThread().getName();
        lsGUI.setVisible(true);

        java.awt.EventQueue.invokeLater (new Runnable() {

            public void run() {

                while ( (Thread.currentThread().getName() == threadName) &&
                        (lsGUI.checkResult() == lsGUI.CONTINUE) ){

                    try {

                        Thread.sleep(100);  // Sleep for 100 millisecond, wait for button press
                    }
                    catch (InterruptedException e) { };
                }
            }
        });

    }



}