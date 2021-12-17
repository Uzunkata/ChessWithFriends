package com.example.server.chess.pieces;

import com.example.server.chess.board.Board;
import com.example.server.chess.board.Move;

import java.util.Collection;
import java.util.List;

public abstract class Piece {

    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove;

    public Piece( final int piecePosition, final Alliance pieceAlliance) {
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        //TODO
        this.isFirstMove = false;
    }

    //black or white
    public Alliance getPieceAlliance(){
        return this.pieceAlliance;
    }

    //returns a collection of all possible moves
    public abstract Collection<Move> calculateLegalMoves(final Board board);

    //if the piece is trying to "teleport" for one side to the other, this method will stop it
    public abstract boolean isColumnExclusion(final int currentPosition, final int candidateOffset);

    public boolean isFirstMove(){
        return this.isFirstMove;
    }

    public int getPiecePosition(){
        return this.piecePosition;
    }
}
