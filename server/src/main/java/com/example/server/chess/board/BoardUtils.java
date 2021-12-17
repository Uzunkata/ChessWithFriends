package com.example.server.chess.board;

public class BoardUtils {

    //columns
    public static final boolean[] FIRST_COLUMN =  initColumn(0);
    public static final boolean[] SECOND_COLUMN =  initColumn(1);
    public static final boolean[] SEVENTH_COLUMN =  initColumn(6);
    public static final boolean[] EIGHTH_COLUMN =  initColumn(7);

    //rows TODO
    public static final boolean[] FIRST_ROW = null;
    public static final boolean[] SECOND_ROW = null;
    public static final boolean[] SEVENTH_ROW = null;
    public static final boolean[] EIGHTH_ROW = null;

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    private BoardUtils() {
        throw new RuntimeException("You cannot instantiate BoardUtils");
    }

    private static boolean[] initColumn(int columnNumber) {
        final boolean[] columns = new boolean[NUM_TILES];

        do{
            columns[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        }while(columnNumber < NUM_TILES);

        return columns;
    }

    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < NUM_TILES;
    }
}
