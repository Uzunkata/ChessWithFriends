package com.example.server.chess;

import java.io.Serializable;

public class Movement implements Serializable {
    //starting position
    private Position position1;
    //ending position
    private Position position2;

    public Movement(Position position1, Position position2) {
        this.position1 = position1;
        this.position2 = position2;
    }

    public Position getPosition1() {
        return this.position1;
    }

    public Position getPosition2() {
        return this.position2;
    }

    //compares 2 different moves to see if they are the same
    @Override
    public boolean equals(Object object) {
        Movement movement = (Movement) object;
        return this.position1.getX() == movement.getPosition1().getX() &&
                this.position1.getY() == movement.getPosition1().getY() &&
                this.position2.getX() == movement.getPosition2().getX() &&
                this.position2.getY() == movement.getPosition2().getY();
    }

    @Override
    public String toString() {
        return "Movement: "+this.position1+" => "+this.position2;
    }

}
