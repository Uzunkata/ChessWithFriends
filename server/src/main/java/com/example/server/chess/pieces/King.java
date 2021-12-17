package com.example.server.chess.pieces;

import com.example.server.chess.board.Board;
import com.example.server.chess.board.BoardUtils;
import com.example.server.chess.board.Move;
import com.example.server.chess.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class King extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9,};


    public King(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        //loop through all the possible moves for the king
        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES){

            //declare the move
            int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

            //check if the move is outside the board
            if(isColumnExclusion(candidateDestinationCoordinate, currentCandidateOffset)){
                continue;
            }

            //check if the tile we are trying to move to is occupied
            if(!candidateDestinationTile.isTileOccupied()){
                    //if not, this move is legal
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                }else{
                    //if yes, we check if the piece is ours or not
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAtDestinationAlliance = pieceAtDestination.getPieceAlliance();

                    //check if piece is ours
                    if(this.pieceAlliance != pieceAtDestinationAlliance){
                        //if it is not, we can move there and capture it
                        legalMoves.add(new Move.AttackMove(board,this, candidateDestinationCoordinate, pieceAtDestination));
                    }

            }
            }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public boolean isColumnExclusion(int currentPosition, int candidateOffset) {
        boolean firstColumnExclusion = BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 ||
                candidateOffset == 7);

        boolean eighthColumnExclusion = BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 9 || candidateOffset == 1 ||
                candidateOffset == -7);

        boolean firstRowExclusion = BoardUtils.FIRST_ROW[currentPosition] && (candidateOffset == 8 || candidateOffset == 7 ||
                candidateOffset == 9);

        boolean eighthRowExclusion = BoardUtils.EIGHTH_ROW[currentPosition] && (candidateOffset == -8 || candidateOffset == -7 ||
                candidateOffset == -9);

        return firstColumnExclusion || eighthColumnExclusion || firstRowExclusion || eighthRowExclusion;
    }
}
