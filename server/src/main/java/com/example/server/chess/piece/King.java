package com.example.server.chess.piece;

import com.example.server.chess.Board;
import com.example.server.chess.Direction;
import com.example.server.chess.Position;
import com.example.server.utils.Color;

import java.io.Serializable;
import java.util.ArrayList;

public class King implements Piece, Serializable {
    private String pieceName;
    private Color color;
    private boolean moved;

    public King(Color color) {
        this.color = color;
        this.initHtmlCode();
        this.moved = false;
    }

    private void initHtmlCode() {
        if (this.color == Color.WHITE) {
            this.pieceName = "whiteKing";
        }else {
            this.pieceName = "blackKing";
        }
    }

    public Color getColor() {
        return this.color;
    }

    public Direction[] getDirections(Board board, Position position) {
        ArrayList<Direction> directions = new ArrayList<Direction>();
        directions.add(new Direction( 0, -1, 1));
        directions.add(new Direction( 1,  0, 1));
        directions.add(new Direction( 0,  1, 1));
        directions.add(new Direction(-1,  0, 1));
        directions.add(new Direction(-1, -1, 1));
        directions.add(new Direction( 1, -1, 1));
        directions.add(new Direction(-1,  1, 1));
        directions.add(new Direction( 1,  1, 1));
        directions.addAll(this.getCastlingDirectionsIfAllowed(board, position));
        return directions.toArray(new Direction[0]);
    }

    public boolean hasMoved() {
        return this.moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    /*
     * CASTLING
     * it must be that king’s very first move
     * it must be that rook’s very first move
     * there cannot be any pieces between the king and rook to move
     * the king may not be in check or pass through check
     */
    private ArrayList<Direction> getCastlingDirectionsIfAllowed(Board board, Position position) {
        ArrayList<Direction> directions = new ArrayList<Direction>();
        Piece piece = board.getPieceAt(position);
        Piece pieceAtRookPositionLeft = board.getPieceAt(new Position(position.getY(), 0));
        Piece pieceAtRookPositionRight = board.getPieceAt(new Position(position.getY(), 7));
        if (pieceAtRookPositionRight != null &&
                pieceAtRookPositionRight.getClass().equals(Rook.class) &&
                ((Rook)pieceAtRookPositionRight).hasMoved() == false &&
                ((King)piece).hasMoved() == false &&
                board.getPieceAt(new Position(5, position.getY())) == null &&
                board.getPieceAt(new Position(6, position.getY())) == null ) {
            directions.add(new Direction(2, 0, 1));
        }
        if (pieceAtRookPositionLeft != null &&
                pieceAtRookPositionLeft.getClass().equals(Rook.class) &&
                ((Rook)pieceAtRookPositionLeft).hasMoved() == false &&
                ((King)piece).hasMoved() == false &&
                board.getPieceAt(new Position(3, position.getY())) == null &&
                board.getPieceAt(new Position(2, position.getY())) == null &&
                board.getPieceAt(new Position(1, position.getY())) == null) {
            directions.add(new Direction(-2, 0, 1));
        }
        return directions;
    }



}
