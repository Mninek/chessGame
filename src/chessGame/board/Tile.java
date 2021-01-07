package chessGame.board;

import chessGame.pieces.*;

//chessGame.board.Tile class, this will fill up the 8x8 chessGame.board
public class Tile {
    //tile is made up of x and y coordinates, and a piece (can be NULL, meaning no piece on that tile)
    private Piece piece;
    private int x;
    private int y;

    //constructor initializes the coordinates and piece
    public Tile(int x, int y, Piece piece){
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    //sets the piece parameter
    public void setPiece(Piece piece){
        this.piece = piece;
    }

    //returns piece parameter
    public Piece getPiece(){
        return piece;
    }

    //sets x coordinate
    public void setX(int x){
        this.x = x;
    }

    //returns x coordinate
    public int getX(){
        return x;
    }

    //sets y coordinate
    public void setY(int y){
        this.y = y;
    }

    //returns x coordinate
    public int getY(){
        return y;
    }
}
