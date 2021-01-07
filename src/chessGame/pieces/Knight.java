package chessGame.pieces;

import chessGame.Side;
import chessGame.board.*;
import static java.lang.Math.abs;

public class Knight extends Piece {

    public Knight(Side color) {
        super(color);
    }

    @Override
    public boolean validMove(Board b, Tile start, Tile end) {

        //cannot move to a square with the same color, so return false;
        if(end.getPiece() != null && end.getPiece().color == this.color){
            return false;
        }

        //cant move to own square
        if(start == end){
            return false;
        }

        //cannot move to the same Tile
        if(start == end){
            return false;
        }

        //gets the x and y coordinates for both start and end tiles.
        int startX = start.getX(), startY = start.getY();
        int endX = end.getX(), endY = end.getY();

        //a knight can either move 2 squares left or right and 1 square up or down, or 1 square left or right and 2 up or down.
        if((abs(startX - endX) == 2 && abs(startY - endY) == 1 )|| (abs(startX - endX) == 1 && abs(startY - endY) == 2)){
            return true;
        }

        //in all other cases we can just return false. Never have to worry about going through a piece because knight jumps over pieces.
        return false;
    }
}
