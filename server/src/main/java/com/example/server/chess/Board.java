package com.example.server.chess;

import com.example.server.chess.piece.*;
import com.example.server.utils.Color;

import java.io.Serializable;

public class Board implements Serializable {
    //all the rows of the board
    private Row[] rows;

    public Board() {
        this.initBoard();
    }

//place all the chess peaces on the board in their starting position
    private void initBoard() {
        this.rows = new Row[8];
        this.rows[0] = new Row(new Piece[]{
                new Rook(Color.BLACK),
                new Knight(Color.BLACK),
                new Bishop(Color.BLACK),
                new Queen(Color.BLACK),
                new King(Color.BLACK),
                new Bishop(Color.BLACK),
                new Knight(Color.BLACK),
                new Rook(Color.BLACK)
        }, 0);
        this.rows[1] = new Row(new Piece[]{
                new Pawn(Color.BLACK),
                new Pawn(Color.BLACK),
                new Pawn(Color.BLACK),
                new Pawn(Color.BLACK),
                new Pawn(Color.BLACK),
                new Pawn(Color.BLACK),
                new Pawn(Color.BLACK),
                new Pawn(Color.BLACK)
        }, 1);
        for (int i = 2; i < 6; i++) {
            this.rows[i] = new Row(i);
        }
        this.rows[6] = new Row(new Piece[]{
                new Pawn(Color.WHITE),
                new Pawn(Color.WHITE),
                new Pawn(Color.WHITE),
                new Pawn(Color.WHITE),
                new Pawn(Color.WHITE),
                new Pawn(Color.WHITE),
                new Pawn(Color.WHITE),
                new Pawn(Color.WHITE)
        }, 6);
        this.rows[7] = new Row(new Piece[]{
                new Rook(Color.WHITE),
                new Knight(Color.WHITE),
                new Bishop(Color.WHITE),
                new Queen(Color.WHITE),
                new King(Color.WHITE),
                new Bishop(Color.WHITE),
                new Knight(Color.WHITE),
                new Rook(Color.WHITE)
        }, 7);
    }

    public Row[] getRows() {
        return this.rows;
    }

    //returns the peace on the given position
    public Piece getPieceAt(Position position) {
        if (!position.isWithinBoard()) {
            return null;
        }
        return this.rows[position.getY()].getSquares()[position.getX()].getPiece();
    }

//    public Position getKingPosition(Color color) {
//        for (int x=0; x<=7; x++) {
//            for (int y=0; y<=7; y++) {
//                Piece piece = this.rows[y].getSquares()[x].getPiece();
//                if (piece != null && piece.getClass().equals(King.class) && piece.getColor() == color) {
//                    return new Position(x, y);
//                }
//            }
//        }
//        return null;
//    }

    //set a single piece in the given position
    public void setPieceAt(Position position, Piece piece) {
        this.rows[position.getY()].getSquares()[position.getX()].setPiece(piece);
    }

    //print a readable table to console for debugging purposes
    @Override
    public String toString() {
        String boardString = "\n";
        for (int x=0; x<=7; x++) {
            String line = "";
            for (int y=0; y<=7; y++) {
                Piece piece = this.getPieceAt(new Position(y, x));
                if (piece == null) {
                    line += " _";
                }else {
                    String className = piece.getClass().getName().replace("com.example.server.chess.piece.", "");
                    String classFirstChar = className.substring(0, 1);
                    if (className.equals("Knight")) {
                        classFirstChar = classFirstChar.toLowerCase();
                    }
                    line += " " + classFirstChar;
                }
            }
            boardString += "\n " + line;
        }
        return boardString + "\n ";
    }
}
