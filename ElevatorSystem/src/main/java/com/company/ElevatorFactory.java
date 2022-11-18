package com.company;

import java.util.Objects;

public class ElevatorFactory {

    public Elevator getElevator(String elevatorName, int elevatorId) {

        if (Objects.equals("ElevatorImpl", elevatorName)) {
            return new ElevatorImpl(elevatorId);
        }
        return null;
    }
}
