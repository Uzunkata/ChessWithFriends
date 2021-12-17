package com.example.server.chess.pieces;

public enum Alliance {
    WHITE{
        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public int getPawnDirection() {
            return -1;
        }
    },
    BLACK{
        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public int getPawnDirection() {
            return 1;
        }
    };
    public abstract int getPawnDirection();
    public abstract boolean isBlack();
    public abstract boolean isWhite();
}
