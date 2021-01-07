package chessGame.pieces;

import chessGame.Side;
import chessGame.board.*;

import static java.lang.Math.abs;

public class Bishop extends Piece {

    public Bishop(Side color) {
        super(color);
    }

    @Override
    public boolean validMove(Board b, Tile start, Tile end) {

        //cannot move to a square with the same color, so return false;
        if(end.getPiece() != null && end.getPiece().color == this.color){
            return false;
        }

        //cannot move to the same Tile
        if(start == end){
            return false;
        }

        //gets the x and y coordinates for both start and end tiles.
        int startX = start.getX(), startY = start.getY();
        int endX = end.getX(), endY = end.getY();

        //bishops move diagonally, so we just have to see if the difference between start and end x and y's are equal.
        if(abs(startX - endX) == abs(startY - endY)){

            //these variables will let us go 1 square at a time to see if we run into obstacles
            int nextX = startX, nextY = startY;
            //we now have to make sure no pieces are in the way. loop until startX == endX
            while(startX != endX){

                //either increments or decrements startX
                if(startX > endX){
                    startX--;
                } else {
                    startX++;
                }

                //same logic
                if(startY > endY){
                    startY--;
                } else {
                    startY++;
                }

                if ((startX != endX && b.getTile(startX, startY).getPiece() != null) || (startX == endX &&
                        b.getTile(startX, startY).getPiece() != null && b.getTile(startX, startY).getPiece().getColor() == this.color)){
                    return false;
                }
            }

            //if loop successfully completes, return true
            return true;
        }


        return false;
    }
}
