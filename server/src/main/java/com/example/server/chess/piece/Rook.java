package com.example.server.chess.piece;

import com.example.server.chess.Board;
import com.example.server.chess.Direction;
import com.example.server.chess.Position;

import java.io.Serializable;
import java.util.ArrayList;

public class Rook implements Piece, Serializable {
    private String htmlCode;
    private int color;
    private boolean moved;

    public Rook(int color) {
        this.color = color;
        //this.htmlCode = "&#9820;";
        this.initHtmlCode();
    }

    private void initHtmlCode() {
        if (this.color == Piece.WHITE) {
            this.htmlCode = "&#9814;";
        }else {
            this.htmlCode = "&#9820;";
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
        directions.add(new Direction( 0, -1, 7));
        directions.add(new Direction( 1,  0, 7));
        directions.add(new Direction( 0,  1, 7));
        directions.add(new Direction(-1,  0, 7));
        return directions.toArray(new Direction[0]);
    }

}

