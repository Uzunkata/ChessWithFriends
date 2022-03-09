package com.example.server.chess;

import java.io.Serializable;
import java.util.UUID;

public class Player implements Serializable {
    private String uuid;
    private int color;

    public Player() {
        this.uuid = UUID.randomUUID().toString();
        this.color = 2;//a spectator until color is set
    }

    public Player(String uuid) {
        this.uuid = uuid;
    }

    public String getUUID() {
        return this.uuid;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
