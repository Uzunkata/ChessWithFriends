package com.example.server.model;

import javax.persistence.*;

@Entity
@Table(name = "recipient")
public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 128)
    private String email;

    @Column(length = 128)
    private String subject;

    @Column(length = 1000)
    private String text;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public String getEmail() {
        return email;
    }

    public String getText() {
        return text;
    }

    public String getSubject() {
        return subject;
    }

    public User getUser() {
        return user;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setText(String bonusText) {
        this.text = bonusText;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setUser(User user) {
        this.user = user;
    }
    //getter & setter
}

