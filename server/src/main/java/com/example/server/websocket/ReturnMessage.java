package com.example.server.websocket;

import com.example.server.chess.Game;
import com.example.server.chess.Player;
import com.example.server.chess.Movement;

public class ReturnMessage {
    public Game game;
    public String type;
    public Player assignedPlayer;
    public Movement possibleMovements[];
    public String requestUUID;
}
