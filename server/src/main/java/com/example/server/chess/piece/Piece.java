package com.example.server.chess.piece;

import com.example.server.chess.Board;
import com.example.server.chess.Direction;
import com.example.server.chess.Position;

public interface Piece {
    public final int WHITE = 0;
    public final int BLACK = 1;

    int getColor();
    Direction[] getDirections(Board board, Position position);
    boolean hasMoved();
    void setMoved(boolean moved);

}
