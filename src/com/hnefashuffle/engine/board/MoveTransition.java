package com.hnefashuffle.engine.board;

public class MoveTransition {
    private Board transitionBoard;
    private Move move;
    private MoveStatus moveStatus;

    public MoveTransition(Board transitionBoard, Move move, MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }
}
