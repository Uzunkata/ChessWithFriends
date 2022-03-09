package com.example.server.chess;

import com.example.server.chess.piece.Piece;

import java.io.Serializable;

public class Row implements Serializable {
    Square[] squares;

    public Row(Piece[] pieces, int y) {
        this.squares = new Square[8];
        int x=0;
        for (Piece piece: pieces) {
            this.squares[x] = new Square(piece, new Position(x,y));
            x++;
        }
    }

    public Row(int y) {
        this.squares = new Square[8];
        for (int x=0; x<8; x++) {
            this.squares[x] = new Square(null, new Position(x,y));
        }
    }

    public Square[] getSquares() {
        return this.squares;
    }
}
