package com.example.server.chess;

import com.example.server.chess.piece.Piece;

import java.io.Serializable;

public class Square implements Serializable {
    Piece piece;
    Position position;

    public Square(Piece piece, Position position) {
        this.piece = piece;
        this.position = position;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
