package com.example.server.dto;

import com.example.server.chess.Movement;

public class Message {
    public String action;
    public Movement movement;
    public String gameUUID;
    public String username;
    public String requestUUID;
    public String promoteTo;
}
