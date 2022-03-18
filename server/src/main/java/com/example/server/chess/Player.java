package com.example.server.chess;

import com.example.server.utils.Color;

import java.io.Serializable;

public class Player implements Serializable {
//    private String uuid;
    private Color color;
    private String username;

    public Player(String username) {
//        this.uuid = UUID.randomUUID().toString();
        this.color = Color.SPECTATOR;//a spectator until color is set
        this.username = username;
    }

//    public Player(String uuid) {
//        this.uuid = uuid;
//    }

//    public String getUUID() {
//        return this.uuid;
//    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getUsername(){
        return this.username;
    }
}
