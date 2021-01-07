package chessGame;

import chessGame.board.*;
import chessGame.pieces.*;
import chessGame.player.Player;

//object to set each move
public class Move {
    private Player player;
    private Tile start, end;
    private Piece movingPiece;

    public Move(Player player, Tile start, Tile end, Piece movingPiece){
        this.player = player;
        this.start = start;
        this.end = end;
        this.movingPiece = movingPiece;
    }

    public Tile getStart(){
        return start;
    }

    public void setStart(Tile start){
        this.start = start;
    }

    public Tile getEnd(){
        return end;
    }

    public void setEnd(){
        this.end = end;
    }

    public Piece getMovingPiece(){
        return movingPiece;
    }

    public void setMovingPiece(Piece piece){
        movingPiece = piece;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public Player getPlayer(){
        return player;
    }

}