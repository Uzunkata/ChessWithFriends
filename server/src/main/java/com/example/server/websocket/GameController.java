package com.example.server.websocket;

import com.example.server.chess.Game;
import com.example.server.chess.Player;
import com.example.server.chess.Position;
import com.example.server.Storage;
import com.example.server.chess.Movement;
import com.example.server.chess.piece.Piece;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;

public class GameController {
    private static final Logger logger = LogManager.getLogger(GameController.class);

    public GameController() {

    }

    public void addGame(String gameUUID) {
        Game game = new Game(gameUUID);
        logger.info(game.getBoard());
        Storage.put(game, gameUUID);
    }

    public Game getGameByUUID(String uuid) {
        return (Game) Storage.get(uuid);
    }

    public Player joinGame(String gameUUID, String playerUUID) {
        Game game = this.getGameByUUID(gameUUID);
        if (game == null) {
            return null;
        }
        Player player = game.getPlayerByUUID(playerUUID);
        if (player != null) {
            if (game.getPlayer1().getUUID().equals(playerUUID)) {
                player.setColor(Piece.WHITE);
            }else if (game.getPlayer2().getUUID().equals(playerUUID)) {
                player.setColor(Piece.BLACK);
            }
        }else {
            if (game.getPlayer1() == null) {
                player = new Player();
                player.setColor(Piece.WHITE);
                game.setPlayer1(player);
            }else if (game.getPlayer2() == null) {
                player = new Player();
                player.setColor(Piece.BLACK);
                game.setPlayer2(player);
            }else {
                player = new Player();
                player.setColor(2);
            }
        }
        Storage.put(game, gameUUID);
        return player;
    }

    public boolean movePiece(String gameUUID, Movement movement) throws Exception {
        Game game = this.getGameByUUID(gameUUID);
        boolean moved = game.movePiece(movement);
        logger.info(game.getBoard());
        Storage.put(game, gameUUID);
        return moved;
    }

    public void doPromote(String gameUUID, String promoteTo) throws Exception {
        Game game = this.getGameByUUID(gameUUID);
        game.doPromote(promoteTo);
        logger.info(game.getBoard());
        Storage.put(game, gameUUID);
    }

    public Movement[] requestPossibleMovements(String gameUUID, Position position) {
        Game game = this.getGameByUUID(gameUUID);
        Piece piece = game.getBoard().getPieceAt(position);
        if (piece.getColor() == game.getTurnColor()) {
            Movement allMovements[] = game.getAllPossibleMovements(position);
            ArrayList<Movement> allPossibleMovements = new ArrayList<Movement>();
            for (Movement movement: allMovements) {
                if (!game.isOnCheckAfterMovement(movement)) {
                    allPossibleMovements.add(movement);
                }
            }
            return (Movement[])allPossibleMovements.toArray(new Movement[allPossibleMovements.size()]);
        }
        return null;
    }

}

