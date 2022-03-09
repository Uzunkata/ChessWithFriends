package com.example.server.chess.piece;

import com.example.server.chess.Board;
import com.example.server.chess.Direction;
import com.example.server.chess.Position;

import java.io.Serializable;
import java.util.ArrayList;

public class Bishop implements Piece, Serializable {

    private String htmlCode;
    private int color;
    private boolean moved;

    public Bishop(int color) {
        this.color = color;
        this.initHtmlCode();
    }

    private void initHtmlCode() {
        if (this.color == Piece.WHITE) {
            this.htmlCode = "&#9815;";
        }else {
            this.htmlCode = "&#9821;";
        }
    }

    @Override
    public int getColor() {
        return this.color;
    }

    @Override
    public Direction[] getDirections(Board board, Position position) {
        ArrayList<Direction> directions = new ArrayList<Direction>();
        directions.add(new Direction(-1,  1, 7));
        directions.add(new Direction(-1, -1, 7));
        directions.add(new Direction( 1, -1, 7));
        directions.add(new Direction( 1,  1, 7));
        return directions.toArray(new Direction[0]);
    }

    @Override
    public boolean hasMoved() { return this.moved; }

    @Override
    public void setMoved(boolean moved) { this.moved = moved; }
}
