package com.example.server.service;

import com.example.server.chess.websocket.GameHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameService {

    @Autowired
    GameHandler counterHandler;

}
