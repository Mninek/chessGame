package chessGame.pieces;

import chessGame.Side;
import chessGame.board.*;

import static java.lang.Math.abs;

//class for pawn piece
public class Pawn extends Piece {
    //firstMove boolean because pawns can move differently on their first move.
    private boolean firstMove = true;

    //this boolean lets you know if a pawn can be captured enpassant - meaning this pawn is directly next to an enemy pawn that moved JUST moved 2 squares
    //DEAL WITH THIS BY EVERYTIME A MOVE IS DONE GO AND CHECK OPPOSING PAWNS AND SET ENPASS TO FALSE
    boolean enPass = false;

    //constructor
    public Pawn(Side color) {
        super(color);
    }

    public boolean getEnPass(){
        return enPass;
    }

    @Override
    //determines if the move is valid
    public boolean validMove(Board b, Tile start, Tile end) {

        //cannot move to a square with the same color, so return false;
        if(end.getPiece() != null && end.getPiece().getColor() == this.color){
            return false;
        }

        //cannot move to the same Tile
        if(start == end){
            return false;
        }

        //gets the x and y coordinates for both start and end tiles.
        int startX = start.getX(), startY = start.getY();
        int endX = end.getX(), endY = end.getY();

        //pawns can only move in a straight line (unless capturing, in which case they can move up 1) so first check for that
        if(startX != endX){

            //if color is white, pawns have to move up the board, so will be capturing at a y 1 greater than current y
            if(color == Side.WHITE){

                //pawn moves up 1, which is correct. all other causes are false
                if(startY + 1 == endY) {

                    //if the difference between the start and end pos are 1, and the square is null (no piece), it is valid
                    if(abs(startX - endX) == 1 && end.getPiece() != null){
                        return true;
                    }
                }


            }

            //if color is black, pawns move down the board
            if(color == Side.BLACK){

                //pawn moves down 1, which is correct. all other causes are false
                if(startY - 1 == endY) {

                    //if the difference between the start and end pos are 1, and the square is null (no piece), it is valid
                    if(abs(startX - endY) == 1 && end.getPiece() != null){
                        return true;
                    }
                }
            }
        }

        //second case, startX and endX are equal, so we only care about Y.
        else {

            //if white, pawns move up the board.
            if(color == Side.WHITE){

                //if pawn moves up 1 and there is no piece, then we can move here
                if(startY + 1 == endY && end.getPiece() == null){
                    return true;
                }

                //if its pawns first move, it is moving up 2 squares, and there are no pieces in the way then it can make the move
                else if(firstMove && startY + 2 == endY && end.getPiece() == null && b.getTile(endX, startY + 1).getPiece() == null){
                    return true;
                }
            }

            //if black, pawns move down the board.
            if(color == Side.BLACK){
                //if pawn moves up 1 and there is no piece, then we can move here
                if(startY -1 == endY && end.getPiece() == null){
                    return true;
                }

                //if its pawns first move, it is moving up 2 squares, and there are no pieces in the way then it can make the move
                else if(firstMove && startY - 2 == endY && end.getPiece() == null && b.getTile(endX, startY - 1).getPiece() == null){
                    return true;
                }
            }
        }

        //all other cases are false, so return false
        return false;
    }
}