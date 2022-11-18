package com.company;

import java.util.Arrays;

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

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int[] getTargetFloors() {
        return targetFloors;
    }

    public void setTargetFloors(int[] targetFloors) {
        this.targetFloors = targetFloors;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void setStopped(boolean isStopped) {
        this.isStopped = isStopped;
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "id=" + id +
                ", currentFloor=" + currentFloor +
                ", targetFloors=" + Arrays.toString(targetFloors) +
                ", direction=" + direction +
                ", isStopped=" + isStopped +
                '}';
    }
}
