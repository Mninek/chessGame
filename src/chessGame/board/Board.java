package chessGame.board;

import chessGame.Side;
import chessGame.pieces.*;

//class for the chessGame.board
public class Board {
    //one variable, b which stands for chessGame.board
    Tile[][] b = new Tile[8][8];

    //creates chessGame.board, sets all chessGame.pieces in correct position
    public Board(){
        setBoardUp();
    }

    //returns the chessGame.board.Tile of the x and y parameters
    public Tile getTile(int x, int y) {

        //if these values are present than it is out of bounds, and the program will exit
        if(x < 0 || x > 7 || y < 0 || y > 7){
            System.out.println("COORDINATES ARE OUT OF BOUNDS");
            System.exit(-1);
        }

        //returns appropriate tile
        return b[y][x];
    }

    //this function will set up the chessGame.board, and put the chessGame.pieces in their correct location
    public void setBoardUp(){
        //2 for loops to increment through the 2d array
        for(int i = 2; i < 6; i++){
            for(int j = 0; j < 8; j++){
                //all tiles that do not have chessGame.pieces are set to null
                b[i][j] = new Tile(j, i, null);
            }
        }

        //a for loop to set the pawns in their location
        for(int i = 0; i < 16; i++){
            if(i < 8) {
                b[1][i] = new Tile(i, 1, new Pawn(Side.WHITE));
            } else {
                b[6][i - 8] = new Tile(i-8, 6, new Pawn(Side.BLACK));
            }
        }


        //ADJUSTED TO TEST CASTLINE
        //individually set the rest of the chessGame.pieces into their places
        b[0][0] = new Tile(0, 0, new Rook(Side.WHITE));
        b[0][1] = new Tile(1, 0, null);//new Knight(Side.WHITE));
        b[0][2] = new Tile(2, 0,  null);//new Bishop(Side.WHITE));
        b[0][3] = new Tile(3, 0,  null);//new Queen(Side.WHITE));
        b[0][4] = new Tile(4, 0, new King(Side.WHITE));
        b[0][5] = new Tile(5, 0,  null);//new Bishop(Side.WHITE));
        b[0][6] = new Tile(6, 0,  null);//new Knight(Side.WHITE));
        b[0][7] = new Tile(7, 0, new Rook(Side.WHITE));

        //black chessGame.pieces
        b[7][0] = new Tile(0, 7, new Rook(Side.BLACK));
        b[7][1] = new Tile(1, 7,  null);//new Knight(Side.BLACK));
        b[7][2] = new Tile(2, 7,  null);//new Bishop(Side.BLACK));
        b[7][3] = new Tile(3, 7,  null);//new Queen(Side.BLACK));
        b[7][4] = new Tile(4, 7, new King(Side.BLACK));
        b[7][5] = new Tile(5, 7,  null);//new Bishop(Side.BLACK));
        b[7][6] = new Tile(6, 7,  null);//new Knight(Side.BLACK));
        b[7][7] = new Tile(7, 7, new Rook(Side.BLACK));
    }


}
