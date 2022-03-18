package com.example.server.model;

import com.example.server.utils.Status;

import javax.persistence.*;

@Entity
@Table(name="matchhistory")
public class MatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String gameHash;

    @OneToOne
    @JoinColumn(name="player1")
    private User player1;

    @OneToOne
    @JoinColumn(name="player2")
    private User player2;

    @Enumerated(EnumType.STRING)
    private Status status;
//    public static final int STARTED = 0;
//    public static final int CHECK = 1;
//    public static final int CHECKMATE = 2;

    @OneToOne
    @JoinColumn(name="winner")
    private User winner;

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        if(winner != null)
        this.winner = winner;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getGameHash() {
        return gameHash;
    }

    public void setGameHash(String gameHash) {
        this.gameHash = gameHash;
    }

    public User getPlayer1() {
        return player1;
    }

    public void setPlayer1(User player1) {
        this.player1 = player1;
    }

    public User getPlayer2() {
        return player2;
    }

    public void setPlayer2(User player2) {
        this.player2 = player2;
    }
}
