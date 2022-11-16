package com.company;

public interface ElevatorSimulator {

    void viewStatus();

    void simulateStep();

    void updateElevatorStatus(int elevatorId, int currentFloor, int targetFloor);

    void selectFloor(int elevatorId, int targetFloor);

    void pickUpPassenger(int passengerFloor, int direction);
}
