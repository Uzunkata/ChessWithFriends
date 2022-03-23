package com.example.server.chess.piece;

import com.example.server.chess.Board;
import com.example.server.chess.Direction;
import com.example.server.chess.Position;
import com.example.server.utils.Color;

import java.io.Serializable;
import java.util.ArrayList;

public class Bishop implements Piece, Serializable {

    private String htmlCode;
    private Color color;
    private boolean moved;

    public Bishop(Color color) {
        this.color = color;
        this.initHtmlCode();
    }

    private void initHtmlCode() {
        if (this.color == Color.WHITE) {
            this.htmlCode = "<img src='https://upload.wikimedia.org/wikipedia/commons/thumb/b/b1/Chess_blt45.svg/1024px-Chess_blt45.svg.png'>";
            //<img src='https://upload.wikimedia.org/wikipedia/commons/thumb/b/b1/Chess_blt45.svg/1024px-Chess_blt45.svg.png'>
        }else {
            this.htmlCode = "&#9821;";
        }
    }

    @Override
    public Color getColor() {
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
