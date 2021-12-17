package com.example.server.chess.board;

import com.example.server.chess.pieces.Piece;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

    protected final int tileCoordinates;

    private Tile(final int tileCoordinates) {
        this.tileCoordinates = tileCoordinates;
    }

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for(int i = 0; i < BoardUtils.NUM_TILES; i++){
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return Collections.unmodifiableMap(emptyTileMap);
    }

    public static Tile createTile(final int tileCoordinates, final  Piece piece){
        return piece != null ? new OccupiedTile(tileCoordinates, piece) : EMPTY_TILES_CACHE.get(tileCoordinates);
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    //classes for immutability
    public static final class EmptyTile extends Tile {

        private EmptyTile(final int tileCoordinates) {
            super(tileCoordinates);
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    public static final class OccupiedTile extends Tile {

        private final Piece pieceOnTile;

        private OccupiedTile(int tileCoordinates, final Piece pieceOnTile) {
            super(tileCoordinates);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }
}
