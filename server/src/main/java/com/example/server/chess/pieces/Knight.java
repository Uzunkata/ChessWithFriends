package com.example.server.chess.pieces;

import com.example.server.chess.board.Board;
import com.example.server.chess.board.BoardUtils;
import com.example.server.chess.board.Move;
import com.example.server.chess.board.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.server.chess.board.Move.*;

public class Knight extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {

        List<Move> legalMoves = new ArrayList<>();

        //loop through all the possible moves
        for(final int currentCandidateOffset: CANDIDATE_MOVE_COORDINATES){

            //make move
            int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

            //check if the tile is in the scope of the board
            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){

                //check if our move is outside the board
                if(isColumnExclusion(this.piecePosition, currentCandidateOffset)){
                    continue;
                }

                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                //check if the tile we are trying to move to is occupied
                if(!candidateDestinationTile.isTileOccupied()){
                    //if not, this move is legal
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                }else{
                    //if yes, we check if the piece is ours or not
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAtDestinationAlliance = pieceAtDestination.getPieceAlliance();

                    //check if piece is ours
                    if(this.pieceAlliance != pieceAtDestinationAlliance){
                        //if it is not, we can move there
                        legalMoves.add(new AttackMove(board,this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public boolean isColumnExclusion(final int currentPosition, final int candidateOffset){

        boolean firstColumnExclusion = BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10 ||
                candidateOffset == 6 || candidateOffset == 15);

        boolean secondColumnExclusion = BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);

        boolean seventhColumnExclusion = BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffset == -6 || candidateOffset == 10);

        boolean eighthColumnExclusion = BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -15 || candidateOffset == -6 ||
                candidateOffset == 10 || candidateOffset == 17);

        return firstColumnExclusion || secondColumnExclusion || seventhColumnExclusion || eighthColumnExclusion;
    }

}
