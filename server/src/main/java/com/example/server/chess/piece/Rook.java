package com.example.server.chess.piece;

import com.example.server.chess.Board;
import com.example.server.chess.Direction;
import com.example.server.chess.Position;
import com.example.server.utils.Color;

import java.io.Serializable;
import java.util.ArrayList;

public class Rook implements Piece, Serializable {
    private String pieceName;
    private Color color;
    private boolean moved;

    public Rook(Color color) {
        this.color = color;
        //this.htmlCode = "&#9820;";
        this.initName();
    }

    private void initName() {
        if (this.color == Color.WHITE) {
            this.pieceName = "whiteRook";
        }else {
            this.pieceName = "blackRook";
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
        directions.add(new Direction( 0, -1, 7));
        directions.add(new Direction( 1,  0, 7));
        directions.add(new Direction( 0,  1, 7));
        directions.add(new Direction(-1,  0, 7));
        return directions.toArray(new Direction[0]);
    }

}

