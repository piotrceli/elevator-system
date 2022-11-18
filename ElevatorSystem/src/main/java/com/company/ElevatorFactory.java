package com.company;

import java.util.Objects;

public class ElevatorFactory {

    public Elevator getElevator(String elevatorName, int elevatorId){

        if (Objects.equals(elevatorName, null)){
          return null;
        }
        if (elevatorName.equals("ElevatorImpl")){
            return new ElevatorImpl(elevatorId);
        }
        return null;
    }
}
