package chessGame.pieces;

import chessGame.Side;
import chessGame.board.*;

//abstract class for chessGame.pieces.Piece, since all chessGame.pieces have some similarities
public abstract class Piece {
    //contains bool for if dead or alive (defaults to alive) and an enum for which color
    private boolean dead = false;
    protected Side color;

    //piece created by setting color, black or white
    public Piece(Side color){
        this.color = color;
    }

    //returns the color
    public Side getColor(){
        return color;
    }

    //sets the color
    public void setColor(Side color){
        this.color = color;
    }

    //returns if the unit is dead
    public boolean getDead(){
        return dead;
    }

    //sets if unit is dead or not
    public void setDead(boolean dead){
        this.dead = dead;
    }

    public abstract boolean validMove(Board b, Tile start, Tile end);

}
