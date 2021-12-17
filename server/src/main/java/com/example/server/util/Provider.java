package com.example.server.util;

public enum Provider {
    LOCAL, GOOGLE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
