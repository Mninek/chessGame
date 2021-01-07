package chessGame;

public enum gameState {
    NEW_GAME,
    ON_GOING,
    GAME_OVER, //used to exit loop, once exit then see if black or white won
    BLACK_WON,
    WHITE_WON,
    STALE_MATE,
    DRAW
}
