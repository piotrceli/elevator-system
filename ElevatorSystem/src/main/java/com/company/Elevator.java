package com.company;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Elevator {

    private final int id;
    private int currentFloor;
    private int[] targetFloors;
    private int direction;
    private boolean isStopped;

    public Elevator(int id) {
        this.id = id;
        this.currentFloor = 0;
        this.targetFloors = new int[11];
        this.direction = 0;
        this.isStopped = false;
    }
}
