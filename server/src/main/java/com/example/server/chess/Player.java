package com.example.server.chess;

import java.io.Serializable;

public class Player implements Serializable {
//    private String uuid;
    private int color;
    private String username;

    public Player(String username) {
//        this.uuid = UUID.randomUUID().toString();
        this.color = 2;//a spectator until color is set
        this.username = username;
    }

//    public Player(String uuid) {
//        this.uuid = uuid;
//    }

//    public String getUUID() {
//        return this.uuid;
//    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getUsername(){
        return this.username;
    }
}
