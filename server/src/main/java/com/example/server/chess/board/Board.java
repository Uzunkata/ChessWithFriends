package com.example.server.chess.board;

import com.example.server.chess.pieces.*;
import com.google.common.collect.ImmutableList;

import java.util.*;

//TODO
public class Board {

    private final List<Tile> gameBoard;
    private final Collection<Piece> activeWhitePieces;
    private final Collection<Piece> activeBlackPieces;

    public Board(Builder builder) {
        this.gameBoard = createGameBoard(builder);
        this.activeWhitePieces = calculateActivePeaces(this.gameBoard, Alliance.WHITE);
        this.activeBlackPieces = calculateActivePeaces(this.gameBoard, Alliance.WHITE);
    }

    private static Collection<Piece> calculateActivePeaces(List<Tile> gameBoard, Alliance alliance) {

        final List<Piece> activePeaces = new ArrayList<>();

        for(final Tile tile : gameBoard){
            if(tile.isTileOccupied()){
                final Piece piece = tile.getPiece();

                if(piece.getPieceAlliance() == alliance){
                    activePeaces.add(piece);
                }
            }
        }

        return Collections.unmodifiableList(activePeaces);
    }

    public Tile getTile(final int tileCoordinate){
        return gameBoard.get(tileCoordinate);
    }

    //populate the board
    private static List<Tile> createGameBoard(Builder builder){

        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];

        for(int i=0; i < BoardUtils.NUM_TILES; i++){
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }

        //this is from guava (google api)
        return ImmutableList.copyOf(tiles);
    }

    public static Board createStandardBoard(){
        final Builder builder = new Builder();

        builder.setPeace(new Rook(0, Alliance.BLACK));
        builder.setPeace(new Knight(1, Alliance.BLACK));
        builder.setPeace(new Bishop(2, Alliance.BLACK));
        builder.setPeace(new Queen(3, Alliance.BLACK));
        builder.setPeace(new King(4, Alliance.BLACK));
        builder.setPeace(new Bishop(5, Alliance.BLACK));
        builder.setPeace(new Knight(6, Alliance.BLACK));
        builder.setPeace(new Rook(7, Alliance.BLACK));
        builder.setPeace(new Pawn(8, Alliance.BLACK));
        builder.setPeace(new Pawn(9, Alliance.BLACK));
        builder.setPeace(new Pawn(10, Alliance.BLACK));
        builder.setPeace(new Pawn(11, Alliance.BLACK));
        builder.setPeace(new Pawn(12, Alliance.BLACK));
        builder.setPeace(new Pawn(13, Alliance.BLACK));
        builder.setPeace(new Pawn(14, Alliance.BLACK));
        builder.setPeace(new Pawn(15, Alliance.BLACK));

        builder.setPeace(new Pawn(48, Alliance.WHITE));
        builder.setPeace(new Pawn(49, Alliance.WHITE));
        builder.setPeace(new Pawn(50, Alliance.WHITE));
        builder.setPeace(new Pawn(51, Alliance.WHITE));
        builder.setPeace(new Pawn(52, Alliance.WHITE));
        builder.setPeace(new Pawn(53, Alliance.WHITE));
        builder.setPeace(new Pawn(54, Alliance.WHITE));
        builder.setPeace(new Pawn(55, Alliance.WHITE));
        builder.setPeace(new Rook(56, Alliance.WHITE));
        builder.setPeace(new Knight(57, Alliance.WHITE));
        builder.setPeace(new Bishop(58, Alliance.WHITE));
        builder.setPeace(new Queen(59, Alliance.WHITE));
        builder.setPeace(new King(60, Alliance.WHITE));
        builder.setPeace(new Bishop(61, Alliance.WHITE));
        builder.setPeace(new Knight(62, Alliance.WHITE));
        builder.setPeace(new Rook(63, Alliance.WHITE));

        builder.setMoveMaker(Alliance.WHITE);

        return builder.build();
    }

    public static class Builder {

        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;

        public Builder(){

        }

        public Builder setPeace(final Piece piece){
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker (final Alliance nextMoveMaker){
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build(){
            return new Board(this);
        }

    }
}
