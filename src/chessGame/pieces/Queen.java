package chessGame.pieces;

import chessGame.Side;
import chessGame.board.*;

public class Queen extends Piece {

    public Queen(Side color){
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

        //the queen moves as either a bishop or a rook, so if one of those two pieces moves are valid, then the queens move is valid.
        if(new Rook(color).validMove(b, start, end) || new Bishop(color).validMove(b, start, end)){
            return true;
        }

        //otherwise, false
        return false;
    }
}
