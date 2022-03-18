package com.example.server.utils;

public enum Status {

    STARTED("STARTED"),
    CHECK("CHECK"),
    CHECKMATE("CHECKMATE");

    private final String value;

    Status(final String newValue) {
        value = newValue;
    }

    public String getValue() { return value; }
}
