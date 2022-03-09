package com.example.server.model;

import javax.persistence.*;

@Entity
@Table(name="gameStorage")
public class GameStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
