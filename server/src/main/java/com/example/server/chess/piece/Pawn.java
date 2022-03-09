package com.example.server.chess.piece;

import com.example.server.chess.Board;
import com.example.server.chess.Direction;
import com.example.server.chess.Position;

import java.io.Serializable;
import java.util.ArrayList;

public class Pawn implements Piece, Serializable {
    private String htmlCode;
    private int color;
    private boolean moved;

    public Pawn(int color) {
        this.color = color;
        this.initHtmlCode();
    }

    private void initHtmlCode() {
        if (this.color == Piece.WHITE) {
            this.htmlCode = "&#9817;";
        }else {
            this.htmlCode = "&#9823;";
        }
    }

    public int getColor() {
        return this.color;
    }

    public boolean hasMoved() {
        return this.moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public Direction[] getDirections(Board board, Position position) {
        ArrayList<Direction> directions = new ArrayList<Direction>();
        int delta = 1;
        if (this.color == Piece.BLACK) {
            delta = -1;
        }
        if (((position.getY() == 6 && this.getColor() == Piece.WHITE) ||
                (position.getY() == 1 && this.getColor() == Piece.BLACK)) &&
                board.getPieceAt(new Position(position.getX(), position.getY()-(2*delta))) == null &&
                board.getPieceAt(new Position(position.getX(), position.getY()-(1*delta))) == null) {
            directions.add(new Direction(0, -1*delta, 2));//move straight two squares
        }else if (board.getPieceAt(new Position(position.getX(), position.getY()-(1*delta))) == null) {
            directions.add(new Direction(0, -1*delta, 1));//move straight
        }
        Position positionTo = new Position(position.getX()-1, (position.getY()-(1*delta)));
        if (positionTo.isWithinBoard() && board.getPieceAt(positionTo) != null
                && board.getPieceAt(positionTo).getColor() != this.color) {
            directions.add(new Direction(-1, -1*delta, 1));//move diagonal left when enemy is on the way
        }
        positionTo = new Position(position.getX()+1, (position.getY()-(1*delta)));
        if (positionTo.isWithinBoard() && board.getPieceAt(positionTo) != null
                && board.getPieceAt(positionTo).getColor() != this.color) {
            directions.add(new Direction(1, -1*delta, 1));//move diagonal right when enemy is on the way
        }
        return directions.toArray(new Direction[0]);
    }

}
