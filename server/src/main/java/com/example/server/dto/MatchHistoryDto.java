package com.example.server.dto;

import com.example.server.utils.Status;

public class MatchHistoryDto {

    private String gameHash;
    private String player1;
    private String player2;
    private Status status;
    private String winner;

    public String getGameHash() {
        return gameHash;
    }

    public void setGameHash(String gameHash) {
        this.gameHash = gameHash;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
