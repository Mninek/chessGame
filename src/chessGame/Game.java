package chessGame;

import chessGame.board.*;
import chessGame.pieces.*;
import chessGame.player.*;

//class where the actual game takes place.
public class Game {
    private Player player1, player2, currPlayer;
    private Board b = new Board();
    private gameState status = gameState.ON_GOING;

    public Game(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;

        //set who the current chessGame.player is (whoever is white)
        if(player1.getColor() == Side.WHITE){
            currPlayer = player1;
        } else {
            currPlayer = player2;
        }

        //set the board up
        b.setBoardUp();
    }

    //function that switches who the current player is
    public void switchPlayers(){
        if(currPlayer == player1){
            currPlayer = player2;
        } else {
            currPlayer = player1;
        }
    }

    //checks if the opposing king is in check. I check all squares because of discovered checks
    public void kingInCheck(){

        //check if any white pieces are attacking black king
        if(currPlayer.getColor() == Side.WHITE){

        }
    }

    public gameState getStatus(){
        return status;
    }

    public Board getBoard(){
        return b;
    }

    public Player getCurrPlayer(){
        return currPlayer;
    }

    //this function handles making a move
    public boolean gameMove(Player p, Move move){
        //gets the current piece that is being moved
        Piece currPiece = move.getStart().getPiece();

        //return false if no piece
        if(currPiece == null){
            return false;
        }

        //if wrong color piece is selected return false
        if(currPiece.getColor() != currPlayer.getColor()){
            return false;
        }

        //player making move is not correct player
        if(p != currPlayer){
            return false;
        }

        //check if the move is a valid move for the current piece we are selecting, if not return false
        if (!currPiece.validMove(b, move.getStart(), move.getEnd())) {
            return false;
        }

        //check if end piece has opposing piece, we know piece cannot be of same color because of valid move. If it is there, set the piece to dead
        if(move.getEnd().getPiece() != null){
            move.getEnd().getPiece().setDead(true);
        }

        //check if piece moving is a rook, then case as a rook so we can access specific methods
        //we have to do this because castling is only valid if the rook has not moved yet.
        if(currPiece instanceof Rook && !((Rook) currPiece).getHasMoved()){
            ((Rook) currPiece).updatedHasMoved();
        }

        //check if king has moved. If he hasn't yet, then see if he is currently castling (only time in the game where he can)
        if(currPiece instanceof King && !((King) currPiece).getHasMoved()){
            ((King) currPiece).updatedHasMoved();
            System.out.println(((King) currPiece).getCurrentlyCastling() + " This ting");
            //if king hasn't moved and is now moving,
            if(currPiece instanceof King && ((King) currPiece).getCurrentlyCastling()){
                System.out.println("CURRENTLY CASTLING");
                //four castling cases, so we will view all.
                //white castling (y = 0)
                if(move.getEnd().getY() == 0){
                    System.out.println("white");
                    //queenside
                    if(move.getEnd().getX() == 2){
                        System.out.println("QUEENSIDE");
                        //places king in correct place
                        move.getEnd().setPiece(currPiece);
                        move.getStart().setPiece(null);

                        //places rook in correct place
                        this.b.getTile(3, 0).setPiece(this.b.getTile(0,0).getPiece());
                        this.b.getTile(0,0).setPiece(null);

                    }

                    //kingside
                    if(move.getEnd().getX() == 6){

                        //places king in correct place
                        move.getEnd().setPiece(currPiece);
                        move.getStart().setPiece(null);

                        //places rook in correct place
                        this.b.getTile(5, 0).setPiece(this.b.getTile(7,0).getPiece());
                        this.b.getTile(7,0).setPiece(null);
                    }
                }
            }

            //black castling
            else if(move.getEnd().getY() == 7){
                System.out.println("black");
                //queenside
                if(move.getEnd().getX() == 2){
                    System.out.println("QUEENSIDE");
                    //places king in correct place
                    move.getEnd().setPiece(currPiece);
                    move.getStart().setPiece(null);

                    //places rook in correct place
                    this.b.getTile(3, 7).setPiece(this.b.getTile(0,7).getPiece());
                    this.b.getTile(0,7).setPiece(null);

                }

                //kingside
                if(move.getEnd().getX() == 6){
                    System.out.println("Kingside");
                    //places king in correct place
                    move.getEnd().setPiece(currPiece);
                    move.getStart().setPiece(null);

                    //places rook in correct place
                    this.b.getTile(5, 7).setPiece(this.b.getTile(7,7).getPiece());
                    this.b.getTile(7,7).setPiece(null);
                }

            }

            //now that king has castled, we reset currently castling
            ((King) currPiece).resetCurrentlyCastling();
        }

        //update the piece moving
        else {
            move.getEnd().setPiece(currPiece);
            move.getStart().setPiece(null);
        }

        //actually update the move on the game board
        b.getTile(move.getStart().getX(), move.getStart().getY()).setPiece(null);
        b.getTile(move.getEnd().getX(), move.getEnd().getY()).setPiece(currPiece);

        //game move is valid, so we change player color
        switchPlayers();
        return true;
    }

}