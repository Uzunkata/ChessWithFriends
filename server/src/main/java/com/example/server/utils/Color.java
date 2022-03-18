package com.example.server.utils;

public enum Color {
    WHITE("WHITE"),
    BLACK("BLACK"),
    SPECTATOR("SPECTATOR");

    private final String value;

    Color(final String newValue) {
        value = newValue;
    }

    public String getValue() { return value; }
}
