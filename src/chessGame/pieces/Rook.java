package chessGame.pieces;

import chessGame.Side;
import chessGame.board.*;

public class Rook extends Piece {
    //boolean to see if rook moved yet, king can only castle if the rook hasn't moved
    private boolean hasMoved = false;

    public Rook(Side color) {
        super(color);
    }

    public void updatedHasMoved(){
        hasMoved = true;
    }

    public boolean getHasMoved(){
        return hasMoved;
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

        //rooks can only move vertically OR horizontally, so we first have to make sure one of these is done.
        if(startX == endX || startY == endY){

            //while loop to make sure no pieces are in the way
            while(startX != endX && startY != endY){

                //check to see if x coordinate has to be incremented or decremented depending on direction of movement
                if(startX > endX){
                    startX--;
                } else if(startX < endX){
                    startX++;
                }

                //same logic
                if(startY > endY){
                    startY--;
                } else if(startY < endY){
                    startY++;
                }

                //if a piece is in the way return false
                if(b.getTile(startX, startY).getPiece() != null || (startX == endX &&
                        b.getTile(startX, startY).getPiece() != null && b.getTile(startX, startY).getPiece().getColor() != this.color)){
                    return false;
                }
            }

            //loop successfully completes so return true
            return true;
        }

        return false;
    }
}
