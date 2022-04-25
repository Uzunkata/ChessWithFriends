package com.example.server.chess.piece;

import com.example.server.chess.Board;
import com.example.server.chess.Direction;
import com.example.server.chess.Position;
import com.example.server.utils.Color;

public interface Piece {

    Color getColor();
    Direction[] getDirections(Board board, Position position);
    boolean hasMoved();
    void setMoved(boolean moved);
}
