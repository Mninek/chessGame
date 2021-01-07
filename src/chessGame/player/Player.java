package chessGame.player;

import chessGame.Side;

//class for the chessGame.player object
public abstract class Player {
    protected Side color;

    public Player(Side color){
        this.color = color;
    }

    public void setColor(Side color){
        this.color = color;
    }

    public Side getColor(){
        return color;
    }
}
