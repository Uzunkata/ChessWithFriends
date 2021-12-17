package com.example.server.chess.pieces;

import com.example.server.chess.board.Board;
import com.example.server.chess.board.BoardUtils;
import com.example.server.chess.board.Move;
import com.example.server.chess.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.example.server.chess.board.Move.*;

public class Pawn extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATES = {7, 8, 9, 16};

    public Pawn(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCoordinateOffset : CANDIDATE_MOVE_COORDINATES){


            int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getPawnDirection()*currentCoordinateOffset);
            final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

            if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                continue;
            }

            //TODO
            //check if pawn can move forward
            if(currentCoordinateOffset == 8 && !candidateDestinationTile.isTileOccupied()){
                legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));

                //check if pawn meets the condition for a jump TODO
            }else if(currentCoordinateOffset == 16 && this.isFirstMove() &&
                    // check if the pawn is on the starting row
                    (BoardUtils.SECOND_ROW[this.piecePosition] && this.pieceAlliance.isBlack()) ||
                    (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.pieceAlliance.isWhite())){

                //if yes,
                //this is the tile for double jump
                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getPawnDirection() * 8);
                //if the tile in front of the pawn, and the tile for jump are not occupied, we can move there
                if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied()
                        && !candidateDestinationTile.isTileOccupied()){

                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                }
            //check for pawn capture move on left
            }else if(currentCoordinateOffset == 7 &&
                    //check if the pawn capture move is outside the board
                    !(BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                    BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())){

                //check if the tile is occupied
                if(candidateDestinationTile.isTileOccupied()){
                    //we get te piece on it
                    final Piece pieceOnCandidate = candidateDestinationTile.getPiece();
                    //and check if it is ours or not
                    if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                        //if it isn't we can capture it TODO
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                }
            //check for pawn capture move on right
            }else if(currentCoordinateOffset == 9&&
                    //check if the pawn capture move is outside the board
                    !(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite() ||
                    BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())) {

                //check if the tile is occupied
                if (candidateDestinationTile.isTileOccupied()) {
                    //we get te piece on it
                    final Piece pieceOnCandidate = candidateDestinationTile.getPiece();
                    //and check if it is ours or not
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                        //if it isn't we can capture it TODO
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public boolean isColumnExclusion(int currentPosition, int candidateOffset) {
        return false;
    }
}
