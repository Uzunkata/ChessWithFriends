package com.example.server.chess.piece;

import com.example.server.chess.Board;
import com.example.server.chess.Direction;
import com.example.server.chess.Position;
import com.example.server.utils.Color;

import java.io.Serializable;
import java.util.ArrayList;

public class Knight implements Piece, Serializable {
    private String pieceName;
    private Color color;
    private boolean moved;

    public Knight(Color color) {
        this.color = color;
        this.initHtmlCode();
    }

    private void initHtmlCode() {
        if (this.color == Color.WHITE) {
            this.pieceName = "whiteKnight";
        }else {
            this.pieceName = "blackKnight";
        }
    }

    public Color getColor() {
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
        directions.add(new Direction(-1, -2, 1));
        directions.add(new Direction(-2, -1, 1));
        directions.add(new Direction(-2,  1, 1));
        directions.add(new Direction(-1,  2, 1));
        directions.add(new Direction( 1,  2, 1));
        directions.add(new Direction( 2,  1, 1));
        directions.add(new Direction( 2, -1, 1));
        directions.add(new Direction( 1, -2, 1));
        return directions.toArray(new Direction[0]);
    }

}
