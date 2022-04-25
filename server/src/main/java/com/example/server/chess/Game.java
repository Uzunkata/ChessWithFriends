package com.example.server.chess;


import com.example.server.chess.piece.*;
import com.example.server.utils.Status;
import com.example.server.utils.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Game implements Serializable {
    private Board board;
    private Player player1;
    private Player player2;
    private String winner;
    private String uuid;
    private Status status;
    private Color turnColor;
    private boolean isPromotion;
    private Movement lastMovement;
    private Piece lastPieceAtPosition1;
    private Piece lastPieceAtPosition2;
    private Date dateStarted;


//    ArrayList<Position> debuging = new ArrayList<>();

//    public static final int STARTED = 0;
//    public static final int CHECK = 1;
//    public static final int CHECKMATE = 2;

    public Game(String uuid) {
        this.board = new Board();
        this.uuid = uuid;
        this.status = Status.STARTED;
        this.turnColor = Color.WHITE;
        this.isPromotion = false;
        this.dateStarted = new Date();
    }

    public Board getBoard() {
        return this.board;
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public void setPlayer1(Player player) {
        this.player1 = player;
    }

    public void setPlayer2(Player player) {
        this.player2 = player;
    }

    public String getWinner() {
        return winner;
    }

    public Player getPlayerByUsername(String username) {
        if (this.player1 != null && this.player1.getUsername().equals(username)) {
            return player1;
        }else if (this.player2 != null && this.player2.getUsername().equals(username)) {
            return player2;
        }else {
            return null;
        }
    }

    public Player getPlayerByColor(Color color){
        if (this.player1 != null && this.player1.getColor() == color) {
            return player1;
        }else if (this.player2 != null && this.player2.getColor() == color) {
            return player2;
        }else {
            return null;
        }
    }

    public String getUUID() {
        return uuid;
    }

    //promote the given piece
    public void doPromote(String pieceName) throws Exception {
        Position pawnPosition = this.getPawnToPromotePosition();
        Pawn pawn = (Pawn) this.board.getPieceAt(pawnPosition);
        Piece piecePromotion = this.getNewPieceByName(pieceName, pawn.getColor());
        this.isPromotion = false;
        this.switchTurnColor();
        this.board.setPieceAt(pawnPosition, piecePromotion);
        this.updateStatus();
    }

    //find the the promotion of the pawn
    public Piece getNewPieceByName(String name, Color color) {
        if (name.equals("Queen")) {
            return new Queen(color);
        } else if (name.equals("Knight")) {
            return new Knight(color);
        } else if (name.equals("Rook")) {
            return new Rook(color);
        } else if (name.equals("Bishop")) {
            return new Bishop(color);
        }
        return null;
    }

    //Checks for a pawn from any team on the board, that is on a promotion tile, and returns its position if there is one,
    //returns null if there is none
    public Position getPawnToPromotePosition() {
        Position positions[] = this.getAllPiecesPositions(null);
        for (Position position: positions) {
            Piece piece = this.board.getPieceAt(position);
            if (piece.getClass().equals(Pawn.class) &&
                    piece.getColor() == Color.WHITE &&
                    position.getY() == 0) {
                return position;
            } else if (piece.getClass().equals(Pawn.class) &&
                    piece.getColor() == Color.BLACK &&
                    position.getY() == 7) {
                return position;
            }
        }
        return null;
    }

    public Status getStatus() {
        return this.status;
    }

    private void updateStatus() throws Exception {
        if (this.isOnCheckMate(Color.BLACK)) {
            this.status = Status.CHECKMATE;
            this.winner = getPlayerByColor(Color.WHITE).getUsername();
        }else if (this.isOnCheckMate(Color.WHITE)) {
            this.status = Status.CHECKMATE;
            this.winner = getPlayerByColor(Color.BLACK).getUsername();
        } else if (this.isOnCheck(Color.WHITE) || this.isOnCheck(Color.BLACK)) {
            this.status = Status.CHECK;
        } else {
            this.status = Status.STARTED;
        }
    }

    private void updateIsPromotion(Movement movement) {
        Piece piece = this.board.getPieceAt(movement.getPosition2());
        if (piece == null) {
            this.isPromotion = false;
            return;
        }
        if (piece.getClass().equals(Pawn.class) &&
                piece.getColor() == Color.WHITE &&
                movement.getPosition2().getY() == 0) {
            this.switchTurnColor();
            this.isPromotion = true;
            return;
        } else if (piece.getClass().equals(Pawn.class) &&
                piece.getColor() == Color.BLACK &&
                movement.getPosition2().getY() == 7) {
            this.switchTurnColor();
            this.isPromotion = true;
            return;
        }
        this.isPromotion = false;
    }

    public Color getTurnColor() {
        return this.turnColor;
    }

    public boolean movePiece(Movement movement) throws Exception {
        return this.movePiece(movement, true);
    }

    //this methods moves the pieces
    //if the update status is true, that means we want to keep the move as an official one
    //however if the status is false, that means the move is made for testing, or calculating purposes
    public boolean movePiece(Movement movement, boolean updateStatus) throws Exception{
        Piece piece = this.board.getPieceAt(movement.getPosition1());
        if (piece.getColor() != this.turnColor) {
            return false;
        }
        Movement possibleMovements[] = this.getAllPossibleMovements(movement.getPosition1());
        for(Movement possibleMovement: possibleMovements) {
            //if the move is possible
            // and if player is not, or will not be checked
            if (movement.equals(possibleMovement) &&
                    !this.isOnCheckAfterMovement(movement) &&
                    this.status != Status.CHECKMATE) {
                //if the move is a castling
                if (this.isCastling(movement)) {
                    this.doCastling(movement);
                } else {
                    this.board.setPieceAt(movement.getPosition1(), null);
                    this.board.setPieceAt(movement.getPosition2(), piece);
                }
                Piece pieceAfterMove = this.board.getPieceAt(movement.getPosition2());
                pieceAfterMove.setMoved(true);
                this.switchTurnColor();
                this.isOnCheck(this.getEnemyColor(piece.getColor()));
                if (updateStatus) {
                    this.updateStatus();
                    this.updateIsPromotion(movement);
                }
                return true;
            }
        }
        return false;
    }

    public boolean isPromotion() {
        return this.isPromotion;
    }

    //returns true if the given move is castling
    private boolean isCastling(Movement movement) {
        if (this.board.getPieceAt(movement.getPosition1()).getClass().equals(King.class) &&
                (movement.getPosition1().getX() == movement.getPosition2().getX() - 2 ||
                        movement.getPosition1().getX() == movement.getPosition2().getX() + 2)) {
            return true;
        }
        return false;
    }

    private void doCastling(Movement movement) {
        int y = movement.getPosition2().getY();
        if (movement.getPosition1().getX() == movement.getPosition2().getX() + 2) {
            //castling left
            Rook rook = (Rook) this.board.getPieceAt(new Position(0, y));
            this.board.setPieceAt(new Position(0, y), null);
            King king = (King) this.board.getPieceAt(movement.getPosition1());
            this.board.setPieceAt(movement.getPosition1(), null);
            this.board.setPieceAt(new Position(2, y), king);
            this.board.setPieceAt(new Position(3, y), rook);
        }else if (movement.getPosition1().getX() == movement.getPosition2().getX() - 2) {
            //castling right
            Rook rook = (Rook) this.board.getPieceAt(new Position(7, y));
            this.board.setPieceAt(new Position(7, y), null);
            King king = (King) this.board.getPieceAt(movement.getPosition1());
            this.board.setPieceAt(movement.getPosition1(), null);
            this.board.setPieceAt(new Position(5, y), rook);
            this.board.setPieceAt(new Position(6, y), king);
        }
    }

    private void switchTurnColor() {
        if (this.turnColor == Color.WHITE) {
            this.turnColor = Color.BLACK;
        } else {
            this.turnColor = Color.WHITE;
        }
    }

    public void undoMove() {
        this.board.setPieceAt(this.lastMovement.getPosition1(), this.lastPieceAtPosition1);
        this.board.setPieceAt(this.lastMovement.getPosition2(), this.lastPieceAtPosition2);
        this.switchTurnColor();
        this.lastPieceAtPosition1.setMoved(false);
    }

    private void saveInfoForUndo(Movement movement) {
        this.lastMovement = movement;
        this.lastPieceAtPosition1 = this.board.getPieceAt(movement.getPosition1());
        this.lastPieceAtPosition2 = this.board.getPieceAt(movement.getPosition2());
    }

    //if there is a piece on the given position
    //returns an array of all the possible movements for the piece on the given position
    public Movement[] getAllPossibleMovements(Position position) {
        ArrayList<Movement> movements = new ArrayList<Movement>();
        Piece piece = this.board.getPieceAt(position);
        if (piece != null) {
            Direction directions[] = piece.getDirections(this.board, position);
            //loops through all the possible directions for the piece on the given position
            for (Direction direction: directions) {
                //the original starting position
                Position positionFrom = position;
                //the closest position for this direction
                Position positionTo = new Position(positionFrom.getX()+direction.getX(), positionFrom.getY()+direction.getY());
                int i = 0;
                int limit = direction.getLimit();
                while (this.canMoveTo(piece, positionTo) && i<limit) {
                    Piece pieceAtDestination = this.board.getPieceAt(positionTo);
                    //stop the calculation for this direction if there is an enemy piece there
                    //and set the new limit for this direction to the position the enemy piece is on
                    //and breaks the while loop
                    if (pieceAtDestination != null && piece.getColor()!=pieceAtDestination.getColor()) {
                        limit = i;
                    }
                    //add a new possible movement for the piece on the given position
                    movements.add(new Movement(position, positionTo));
                    //a hypothetical move so that we can explore if the piece can go even further in this direction
                    positionFrom = positionTo;
                    //set the next destination to the next closest tile for the given direction
                    positionTo = new Position(positionFrom.getX()+direction.getX(), positionFrom.getY()+direction.getY());
                    i++;
                }
            }
        }
        return movements.toArray(new Movement[0]);
    }

    //returns a boolean that indicates if the given piece can move to the given position
    private boolean canMoveTo(Piece piece, Position position) {
        if (!position.isWithinBoard()) {
            return false;
        }
        Piece pieceAtDestination = this.board.getPieceAt(position);
        //if there is no piece, or piece with a different color
        if (pieceAtDestination == null ||
                piece.getColor() != pieceAtDestination.getColor() ||
                //or it is castling
                (piece.getClass().equals(King.class) &&
                        pieceAtDestination.getClass().equals(Rook.class) &&
                        !piece.hasMoved() &&
                        !pieceAtDestination.hasMoved())) {
            return true;
        } else {
            return false;
        }
    }

    //returns all pieces on the board from the given color
    //if the given color is null, return all piece positions
    public Position[] getAllPiecesPositions(Color color) {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (int x=0; x<=7; x++) {
            for (int y=0; y<=7; y++) {
                Piece piece = this.board.getPieceAt(new Position(x, y));
                if (piece != null && (color == null || piece.getColor() == color)) {
                    positions.add(new Position(x, y));
                }
            }
        }
        return positions.toArray(new Position[0]);
    }

    private Color getEnemyColor(Color color) {
        if (color == Color.WHITE) return Color.BLACK;
        else return Color.WHITE;
    }

    //makes the move, after which cheks if there is a check,
    //and then reverses the move
    public boolean isOnCheckAfterMovement(Movement movement) {
        Color color = this.board.getPieceAt(movement.getPosition1()).getColor();
        Piece pieceAtPosition1 = this.board.getPieceAt(movement.getPosition1());
        Piece pieceAtPosition2 = this.board.getPieceAt(movement.getPosition2());
        this.board.setPieceAt(movement.getPosition1(), null);
        this.board.setPieceAt(movement.getPosition2(), pieceAtPosition1);
        boolean isOnCheckAfterMovement = this.isOnCheck(color);
        this.board.setPieceAt(movement.getPosition1(), pieceAtPosition1);
        this.board.setPieceAt(movement.getPosition2(), pieceAtPosition2);
        return isOnCheckAfterMovement;
    }

    //returns true if the given player color is on check mate
    private boolean isOnCheckMate(Color color) throws Exception {
        Position allPositions[] = this.getAllPiecesPositions(color);
        //loops trough all positions on the board, that are under control of the given player (color)
        for (Position position: allPositions) {
            //get all the possible moves of the piece on the given position
            Movement movements[] = this.getAllPossibleMovements(position);
            //loops trough all the possible moves of the piece on the given position
            //and sees if the player is being checked after the move
            for (Movement movement: movements) {
                this.saveInfoForUndo(movement);
                boolean moved = this.movePiece(movement, false);
                boolean isOnCheck = this.isOnCheck(color);
                //reverse the move
                if (moved) this.undoMove();
                //return false if he can make the move, because he will not be checked after it
                //if he is still checked, continue the loop
                if (!isOnCheck){
                    System.out.println();
                    return false;
                }
            }
        }
//        this.status = Status.CHECKMATE;
//        this.winner = getPlayerByColor(color);
        return true;
    }

    //returns true if the given player color is being checked
    private boolean isOnCheck(Color color) {
        Color enemyColor = this.getEnemyColor(color);
        Position allEnemyPositions[] = this.getAllPiecesPositions(enemyColor);
        //loop trough all the pieces on the board from the enemy color
        //and returns true if any of them is threatening the king
        for (Position enemyPosition: allEnemyPositions) {
            if (this.pieceCanHitEnemyKing(enemyPosition)){
                this.status = Status.CHECK;
                return true;
            }
        }
        return false;
    }

    //returns true if the given piece threatens the enemy king
    private boolean pieceCanHitEnemyKing (Position position) {
        Piece piece = this.board.getPieceAt(position);
        Movement possibleMovements[] = this.getAllPossibleMovements(position);
        //loop trough all the possible moves of the enemy piece at the given position
        //and return true if it threatens the king
        for (Movement possibleMovement: possibleMovements) {
            Piece targetPiece = this.board.getPieceAt(possibleMovement.getPosition2());
            if (targetPiece != null && targetPiece.getClass().equals(King.class) &&
                    targetPiece.getColor() != piece.getColor()) {
                return true;
            }
        }
        return false;
    }

}

