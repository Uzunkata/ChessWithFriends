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

public class Bishop extends Piece{


    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-9, -7, 7, 9};

    public Bishop(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        //loop through all the vectors
        for(final int candidateCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES){

            int candidateDestinationCoordinate = this.piecePosition;

            //while the tile is in the scope of the board
            while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){

                //check if we our move is outside the board
                if(isColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)){
                    break;
                }

                //make move
                candidateDestinationCoordinate += candidateCoordinateOffset;

                //check if the tile is in the scope of the board
                if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){

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
                        //if it is, we cant move beyond there
                        break;
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public boolean isColumnExclusion(final int currentPosition, final int candidateOffset){
        boolean firstColumnExclusion = BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7);

        boolean eighthColumnExclusion = BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 9);

        return firstColumnExclusion || eighthColumnExclusion;
    }
}
