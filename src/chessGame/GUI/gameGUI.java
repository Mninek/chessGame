package chessGame.GUI;

import chessGame.*;
import chessGame.board.*;
import chessGame.pieces.*;
import chessGame.player.Player;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//I added the game components in the GUI because I had threading issues
public class gameGUI {

    //variables for game. Game is public so we can access
    public Game game;
    private Player player1, player2;
    private Board boardObj;
    private Piece movingPiece;

    //private variable for game state, this will be checked every iteration and updated if something happens
    //also 2 tiles so that I can pass which tiles make up a move and the board and move
    private Tile start, end;
    private Move move;
    private gameState gs;
    private boolean moving = false;

    //private variables, a button 'field' a jpanel object with 2 pixel gap between components and a jpanel object for the chess board (9 includes A-H and 1-8)
    private JPanel gamePanel = new JPanel(new BorderLayout(2,2));
    private JPanel board = new JPanel(new GridLayout(0,9));
    private JButton[][] tileButtons = new JButton[8][8];

    //control game buttons
    private JButton newGame = new JButton("New Game");
    private JButton drawGame = new JButton("Draw");
    private JButton resignGame = new JButton("Resign");

    //scroll list for FEN of moves
    private JTextArea FEN = new JTextArea();
    private JScrollPane scrollFEN = new JScrollPane(FEN, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    //private class that will handle all actions within the game GUI (moving pieces, selecting resign, draw, new game, etc. etc.
    private class buttonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //check if it is one of the main buttons
            if(e.getSource() == newGame){
                gs = gameState.NEW_GAME;
            }

            //check if game is drawn
            if(e.getSource() == drawGame){
                gs = gameState.DRAW;
            }

            //check if game is resigned
            if(e.getSource() == resignGame){
                //temp only have it as white won
                gs = gameState.GAME_OVER;
            }

            //iterates through button map to see which is pressed
            for(int y = 0; y < 8; y++){
                for(int x = 0; x < 8; x++){

                    //checks if source is the current tileButton we are checking
                    if(e.getSource() == tileButtons[y][x]){
                        //System.out.println("x = " + x + " y = " + y);

                        //set either start tile or end tile based on what is pressed
                        if(start == null){
                            start = boardObj.getTile(x, y);
                            movingPiece = start.getPiece();

                            //if no piece is moving, then we can ignore and set start back to null to repeat start selection tile
                            //also if we select the wrong color piece it resets start until correct color is selected.
                            if(movingPiece == null || movingPiece.getColor() != game.getCurrPlayer().getColor()){
                                start = null;
                            }
                            else {
                                drawValid(start, movingPiece);
                            }


                        } else {
                            //if we reselect the same color of piece, we set it to be our new start tile
                            if(boardObj.getTile(x,y).getPiece() != null && boardObj.getTile(x,y).getPiece().getColor() == movingPiece.getColor()){
                                start = boardObj.getTile(x,y);
                            }
                            //else we finish the move by getting the end tile.
                            else {

                                //create move + debugging info
                                end = boardObj.getTile(x,y);
                                move = new Move(game.getCurrPlayer(), start, end, movingPiece);
                                /*
                                System.out.println("DEBUGGING INFO: START_X " + start.getX() + " start Y "+  start.getY() +
                                        " end x " + end.getX() + " end y " + end.getY() + " for loop y " + y + " for loop x " + x);
                                        */

                                game.gameMove(game.getCurrPlayer(), move);

                                //update board
                                updateBoard(game.getBoard());

                                //reset variables and draw the pieces
                                start = null;
                                end = null;
                                drawPieces();

                            }
                        }
                    }
                }
            }


        }
    }

    public gameGUI(Player player1, Player player2){

        game = new Game(player1, player2);
        this.player1 = player1;
        this.player2 = player2;

        //sets board to whatever is passed in and initializes game state
        this.boardObj = new Board();
        this.gs = gameState.ON_GOING;

        //sets the edge borders to 5 pixels
        gamePanel.setBorder(new EmptyBorder(0,5,5,5));

        //create toolbar to handle all commands/buttons. Set toolbar so it cannot be moved
        JToolBar gameTB = new JToolBar();
        gameTB.setFloatable(false);

        //sets the button sizes
        newGame.setPreferredSize(new Dimension(100,40));
        drawGame.setPreferredSize(new Dimension(100, 40));
        resignGame.setPreferredSize(new Dimension(100,40));

        //add the toolbar to the panel, at the PAGE_START(top of page) location. add the buttons to this toolbar and pad/place as needed
        gamePanel.add(gameTB, BorderLayout.PAGE_START);
        gameTB.add(newGame);
        gameTB.add(Box.createHorizontalGlue());
        gameTB.add(drawGame);
        gameTB.addSeparator(new Dimension(50,10));
        gameTB.add(resignGame);

        //set the text area for FEN notation on the right side, and make it text area so user cannot edit it.
        FEN.setEditable(false);
        scrollFEN.setPreferredSize(new Dimension(250, 100));
        gamePanel.add(scrollFEN, BorderLayout.LINE_END);

        //now sets the actual chess board onto the panel
        board.setBorder(new LineBorder(Color.BLACK));
        gamePanel.add(board);

        //2 for loops to go through all 9 positions. The last row and column have the grid positions(i.e. d4)
        //decrements so that the tileButtons corresponds directly to the tile[][] in the Board class
        for(int i = 7; i >= 0; i--){
            for(int j = 0; j < 8; j++){
                JButton b = new JButton();
                b.setMargin(new Insets(0,0,0,0));
                b.setPreferredSize(new Dimension(64,64));

                if( (i % 2 == 1 && j % 2 == 0) || (i % 2 == 0 && j % 2 == 1)){
                    b.setBackground(Color.WHITE);
                } else {
                    //i set the black squares as a dark green so that the pieces are visible
                    b.setBackground(new Color(56, 176, 106));
                }
                tileButtons[i][j] = b;
                board.add(tileButtons[i][j]);
            }
            //add the 1 to 8 numbers on the right side, centered
            //DEFAULT FONT IS DIALOG
            board.add(new JLabel(Integer.toString(8-i), SwingConstants.CENTER));
        }

        //adds alphabets for the columns
        String row = "ABCDEFGH";
        for(int i = 0; i < 8; i++){
            board.add(new JLabel(row.substring(i, i+1), SwingConstants.CENTER));
        }

        //creates a button listener to handle when buttons are clicked and adds it to all buttons
        buttonHandler bHandle = new buttonHandler();
        addListeners(bHandle);


    }

    //updates FEN display with the current move
    public void updateFEN(Move move){

    }

    //gets and sets moving variable
    public boolean getMoving(){
        return moving;
    }

    public void setMoving(boolean move){
        moving = move;
    }

    public void updateBoard(Board boardObj){
        this.boardObj = boardObj;
    }

    //this will return a move based on buttons pressed, player is initially null
    public Move getMove(){

        //creates a new move
        move = new Move(game.getCurrPlayer(), start, end, start.getPiece());

        //returns move
        return move;
    }

    //resets start and end to null, and resets moving boolean
    public void resetMove(){
        start = null;
        end = null;
        setMoving(false);
    }

    //adds button listeners to all buttons on this gui. private because only called in this class
    private void addListeners(buttonHandler bHandle){

        //iterates through board buttons
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                tileButtons[i][j].addActionListener(bHandle);
            }
        }

        //adds action listeners for 3 clickable buttons
        newGame.addActionListener(bHandle);
        drawGame.addActionListener(bHandle);
        resignGame.addActionListener(bHandle);
    }

    //setter and getter for the current game state
    public void setGs(gameState gs){
        this.gs = gs;
    }

    public gameState getGs(){
        return gs;
    }

    //function that draws a visual representation of valid moves.
    public void drawValid(Tile start, Piece startingPiece){
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                Tile currTile = boardObj.getTile(x,y);
                if(startingPiece.validMove(boardObj, start, currTile)){
                    if (currTile.getPiece() == null){
                        tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\redcircle.png"));
                    } else {
                        Piece currPiece = boardObj.getTile(x, y).getPiece();
                        if (currPiece.getColor() == Side.BLACK) {
                            if (currPiece instanceof Pawn) {
                                tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\blackPawnCircle.png"));
                            } else if (currPiece instanceof Rook) {
                                tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\blackRookCircle.png"));
                            } else if (currPiece instanceof Bishop) {
                                tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\blackBishopCircle.png"));
                            } else if (currPiece instanceof Knight) {
                                tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\blackKnightCircle.png"));
                            } else if (currPiece instanceof Queen) {
                                tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\blackQueenCircle.png"));
                            } else if (currPiece instanceof King) {
                                tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\blackKingCircle.png"));
                            }
                        } else {
                            if (currPiece instanceof Pawn) {
                                tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\whitePawnCircle.png"));
                            } else if (currPiece instanceof Rook) {
                                tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\whiteRookCircle.png"));
                            } else if (currPiece instanceof Bishop) {
                                tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\whiteBishopCircle.png"));
                            } else if (currPiece instanceof Knight) {
                                tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\whiteKnightCircle.png"));
                            } else if (currPiece instanceof Queen) {
                                tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\whiteQueenCircle.png"));
                            } else if (currPiece instanceof King) {
                                tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\whiteKingCircle.png"));
                            }
                        }
                    }
                }
            }
        }
    }

    //function draws all pieces
    public void drawPieces(){

        //this iterates through the board and draws all pieces that it sees.
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){

                //gets current piece
                Tile currTile = boardObj.getTile(x,y);

                //if piece exists (skips all nulls)
                if(currTile.getPiece() != null){
                    Piece piece = currTile.getPiece();

                    //if piece is pawn
                    if(piece instanceof Pawn){

                        //draws either white or black pawn
                        if(piece.getColor() == Side.BLACK){
                            tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\blackPawn.png"));
                        } else {
                            tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\whitePawn.png"));
                        }
                    }

                    //if piece is rook
                    if(piece instanceof Rook){

                        //draws either white or black pawn
                        if(piece.getColor() == Side.BLACK){
                            tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\blackRook.png"));
                        } else {
                            tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\whiteRook.png"));
                        }
                    }

                    //if piece is Knight
                    if(piece instanceof Knight){

                        //draws either white or black pawn
                        if(piece.getColor() == Side.BLACK){
                            tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\blackKnight.png"));
                        } else {
                            tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\whiteKnight.png"));
                        }
                    }

                    //if piece is Bishop
                    if(piece instanceof Bishop){

                        //draws either white or black pawn
                        if(piece.getColor() == Side.BLACK){
                            tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\blackBishop.png"));
                        } else {
                            tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\whiteBishop.png"));
                        }
                    }

                    //if piece is pawn
                    if(piece instanceof Queen){

                        //draws either white or black pawn
                        if(piece.getColor() == Side.BLACK){
                            tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\blackQueen.png"));
                        } else {
                            tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\whiteQueen.png"));
                        }
                    }

                    //if piece is King
                    if(piece instanceof King){

                        //draws either white or black pawn
                        if(piece.getColor() == Side.BLACK){
                            tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\blackKing.png"));
                        } else {
                            tileButtons[y][x].setIcon(new ImageIcon("chessPieces\\whiteKing.png"));
                        }
                    }
                }

                else if(currTile.getPiece() == null && tileButtons[y][x].getIcon() != null){
                    tileButtons[y][x].setIcon(null);
                }

            }
        }

    }

    //returns the panel so it can be displayed
    public JPanel getGamePanel(){
        return gamePanel;
    }

}
