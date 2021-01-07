import chessGame.Side;
import chessGame.board.Tile;
import chessGame.gameState;
import chessGame.player.*;
import chessGame.*;
import chessGame.GUI.*;

import javax.swing.*;
import java.awt.*;

public class main {

    public static void main(String[] args) throws InterruptedException {
        gameGUI gui = new gameGUI(new Human(Side.WHITE), new Human(Side.BLACK));

        JFrame f = new JFrame("CHESS");
        f.add(gui.getGamePanel());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        f.setPreferredSize(new Dimension(1060,800));
        f.pack();
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.setMinimumSize(new Dimension(1060,800));
        f.setVisible(true);
        //game.gameMove(game.getCurrPlayer(), new Move(game.getCurrPlayer(), game.getBoard().getTile(3,1),
                //game.getBoard().getTile(3,2), game.getBoard().getTile(3,1).getPiece()));
        //gui.updateBoard(game.getBoard());
        gui.drawPieces();

        //game loop
        while(gui.getGs() != gameState.GAME_OVER){

            /*
            //if getMoving is true, that means we need to make a move happen
            if(gui.getMoving()){

                //CREATE CIRCLES FOR WHERE PIECE CAN VALIDLY GO, TO SEE WHY LOGIC IS MESSED UP

                //try to make a game move (which updates board), then update board in gui and redraw, then switch players
                if(gui.game.gameMove(gui.game.getCurrPlayer(), gui.getMove())) {
                    gui.game.switchPlayers();
                    gui.resetMove();

                    System.out.println("safe");
                    gui.updateBoard(gui.game.getBoard());
                    gui.drawPieces();
                }
            }
            */


        }

        System.out.println("GAME OVER");
    }
}
