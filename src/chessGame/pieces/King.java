package chessGame.pieces;

import chessGame.Side;
import chessGame.board.*;

import static java.lang.Math.abs;

public class King extends Piece{
    //if king is in check, other piece must block, checking piece must be captured, or king msut move
    private boolean inCheck = false;

    //castled or not
    private boolean castled = false;
    //if the valid move is castling move this will be set true FOR ONE TURN so the game knows to update two pieces' locations
    private boolean currentlyCastling = false;
    //bool for if it has moved yet
    private boolean hasMoved = false;

    public King(Side color) {
        super(color);
    }

    public void updatedHasMoved(){
        hasMoved = true;
    }

    public boolean getHasMoved(){
        return hasMoved;
    }

    //only runs once, since you can only castle once
    public void castled(){
        castled = true;
    }

    public boolean getCastled(){
        return castled;
    }

    public void setCheck(boolean check){
        inCheck = check;
    }

    public boolean getCheck(){
        return inCheck;
    }

    public boolean getCurrentlyCastling(){
        return currentlyCastling;
    }

    public void resetCurrentlyCastling(){
        this.currentlyCastling = false;
    }

    public boolean squareAttacked(Board b, Tile end){
        //we have to make sure no ENEMY unit can attack the king
        //easiest way, iterate through board and check every enemy piece and see if it can move to the kings new square
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){

                //get whatever piece is on current square and see if it is opposite color (we don't care if it is same color since
                //piece cannot attack own king
                Piece tempPiece = b.getTile(j, i).getPiece();
                if(tempPiece != null && tempPiece.getColor() != color){

                    //see if the piece's current square can attack the kings final square after moving
                    //if it can move there, then return false since king cannot move into check
                    if(tempPiece instanceof King || tempPiece.validMove(b, b.getTile(j, i), end)){
                        return false;
                    }

                    //if it can attack the square king passes through

                }

            }
        }

        //if doesn't fall in the loops then return true
        return true;
    }

    //checks if castling is valid
    public boolean canCastle(Board b, Tile start, Tile end){
        //if the King has moved, return false
        if(this.hasMoved){
            return false;
        }

        //temp variable for piece
        Piece temp;


        //castling can occur on 4 squares, so we will just check each individual square and the respective rook
        //white castling pos
        if(start.getX() == 4 && start.getY() == 0){

            //white castling kingside, and no pieces in the way
            if(end.getX() ==6 && end.getY() == 0 && end.getPiece() == null && b.getTile(5, 0).getPiece() == null){

                //for castling to happen, proper rook can't have moved yet. So check if there is a piece, if it is rook, and if it has not moved
                temp = b.getTile(7, 0).getPiece();
                if(temp == null || !(temp instanceof Rook) || ((Rook) temp).getHasMoved() ){

                    return false;
                }

                //for castling to happen, the king cannot move through check (no on can attack f1) or enter check, so will check for both of those.
                //end is final location, [0][5] is f1 or square king passes through. Return false if either is attacked
                if(squareAttacked(b, end) || squareAttacked(b, b.getTile(5, 0))){

                    return false;
                }


                //sets the parameter currently castling to true, because the king is castling
                System.out.println("set to true");
                this.currentlyCastling = true;
                return true;
            }

            //white castling queenside and no pieces in the way
            if(end.getX() == 2 && end.getY() == 0 && end.getPiece() == null && b.getTile(3, 0).getPiece() == null &&
                    b.getTile(1,0).getPiece() == null){

                temp = b.getTile(0, 0).getPiece();
                if(temp == null || !(temp instanceof Rook) || ((Rook) temp).getHasMoved() ){
                    return false;
                }

                //similar logic as before just different squares
                if(squareAttacked(b, end) || squareAttacked(b, b.getTile(3, 0))){
                    return false;
                }

                //sets the parameter currently castling to true, because the king is csatling
                System.out.println("set to true");
                this.currentlyCastling = true;
                return true;
            }
        }

        //black castling pos
        if(start.getX() == 4 && start.getY() == 7){

            //black castling kingside, and no pieces in the way
            if(end.getX() ==6 && end.getY() == 7 && end.getPiece() == null && b.getTile(5, 7).getPiece() == null){

                temp = b.getTile(7, 7).getPiece();
                if(temp == null || !(temp instanceof Rook) || ((Rook) temp).getHasMoved() ){
                    return false;
                }

                //for castling to happen, the king cannot move through check (no on can attack f8) or enter check, so will check for both of those.
                //end is final location, [7][5] is f1 or square king passes through. Return false if either is attacked
                if(squareAttacked(b, end) || squareAttacked(b, b.getTile(5, 7))){
                    return false;
                }

                //sets the parameter currently castling to true, because the king is castling
                System.out.println("set to true");
                this.currentlyCastling = true;
                return true;
            }

            //black castling queenside and no pieces in the way
            if(end.getX() == 2 && end.getY() == 7 && end.getPiece() == null && b.getTile(3, 7).getPiece() == null &&
                    b.getTile(1, 7).getPiece() == null){

                temp = b.getTile(0, 7).getPiece();
                if(temp == null || !(temp instanceof Rook) || ((Rook) temp).getHasMoved() ){
                    return false;
                }

                //similar logic as before just different squares
                if(squareAttacked(b, end) || squareAttacked(b, b.getTile(3, 7))){
                    return false;
                }

                //sets the parameter currently castling to true, because the king is castling
                System.out.println("set to true");
                this.currentlyCastling = true;
                return true;
            }

        }

        System.out.println("BIG FALSE");
        return false;
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

        //king can only move 1 square in any direction
        if(abs(startX - endX) <= 1 || abs(startY - endY) <= 1){

            //helper function checks if any black unit can move to this square
            if(squareAttacked(b, end)){
                return false;
            }

            //if the iteration doesn't fail then the king can move
            return true;
        }

        //next step, king can move differently if he is castling, so check if it is a valid castle
        if(canCastle(b, start, end)){
            System.out.println("can castle");
            return true;
        }

        //otherwise return false
        return false;
    }

    //function will let us know if the king is in checkmate (implement logic later, for now just resign)
    public boolean isMated(){
        return false;
    }

}
