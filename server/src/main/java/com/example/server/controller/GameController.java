package com.example.server.controller;

import com.example.server.chess.Storage;
import com.example.server.chess.Game;
import com.example.server.chess.Movement;
import com.example.server.chess.Player;
import com.example.server.chess.Position;
import com.example.server.chess.piece.Piece;
import com.example.server.model.MatchHistory;
import com.example.server.repository.MatchHistoryRepository;
import com.example.server.repository.UserRepository;
import com.example.server.utils.Color;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class GameController {

//    @Autowired
//    private static MatchHistoryRepository matchHistoryRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final MatchHistoryRepository matchHistoryRepository;

    private static final Logger logger = LogManager.getLogger(GameController.class);


    public void addGame(String gameUUID) {
        Game game = new Game(gameUUID);
        logger.info(game.getBoard());
        Storage.put(game, gameUUID);

        MatchHistory matchHistory = new MatchHistory();
        matchHistory.setGameHash(gameUUID);
        matchHistory.setStatus(game.getStatus());
        matchHistoryRepository.save(matchHistory);
    }

    public Game getGameByUUID(String uuid) {
        return (Game) Storage.get(uuid);
    }

    public Player joinGame(String gameUUID, String username) {
        Game game = this.getGameByUUID(gameUUID);
        MatchHistory matchHistory = matchHistoryRepository.findByGameHash(gameUUID);
        if (game == null) {
            return null;
        }
        Player player = game.getPlayerByUsername(username);
        if (player != null) {
            if (game.getPlayer1().getUsername().equals(username)) {
                player.setColor(Color.WHITE);
            }else if (game.getPlayer2().getUsername().equals(username)) {
                player.setColor(Color.BLACK);
            }
        }else {
            if (game.getPlayer1() == null) {
                player = new Player(username);
                player.setColor(Color.WHITE);
                game.setPlayer1(player);
                matchHistory.setPlayer1(userRepository.findByUsername(player.getUsername()));
                matchHistoryRepository.save(matchHistory);
                System.out.println(game.getPlayer1().getUsername());
            }else if (game.getPlayer2() == null) {
                player = new Player(username);
                player.setColor(Color.BLACK);
                game.setPlayer2(player);
                matchHistory.setPlayer2(userRepository.findByUsername(player.getUsername()));
                matchHistoryRepository.save(matchHistory);
                System.out.println(game.getPlayer2().getUsername());
            }else {
                player = new Player(username);
                player.setColor(Color.SPECTATOR);
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

        MatchHistory matchHistory = matchHistoryRepository.findByGameHash(gameUUID);
        matchHistory.setStatus(game.getStatus());
//        User winnerUser = userRepository.findByUsername(game.getWinner().getUsername());
//        if(winnerUser!= null)
//            matchHistory.setWinner(winnerUser);
        matchHistory.setWinner(userRepository.findByUsername(game.getWinner()));

        matchHistoryRepository.save(matchHistory);
        return moved;
    }

    public void doPromote(String gameUUID, String promoteTo) throws Exception {
        Game game = this.getGameByUUID(gameUUID);
        game.doPromote(promoteTo);
        logger.info(game.getBoard());
        Storage.put(game, gameUUID);
        MatchHistory matchHistory = matchHistoryRepository.findByGameHash(gameUUID);
        matchHistory.setStatus(game.getStatus());
//        User winnerUser = userRepository.findByUsername(game.getWinner().getUsername());
//        if(winnerUser!= null)
//            matchHistory.setWinner(winnerUser);

        matchHistoryRepository.save(matchHistory);
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

