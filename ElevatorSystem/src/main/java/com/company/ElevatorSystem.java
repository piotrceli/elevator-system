package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ElevatorSystem implements ElevatorSimulator {

    private final List<Elevator> elevators = new ArrayList<>();
    private boolean isAutoSimulated = false;

    public ElevatorSystem(int elevatorsNumber) {
        generateElevators(elevatorsNumber);
    }

    public List<Elevator> getElevators() {
        return Collections.unmodifiableList(elevators);
    }

    private void generateElevators(int elevatorsNumber) {
        if (elevatorsNumber > 16) {
            System.out.println("Maximum number of elevators is 16. Value set to 16.");
            elevatorsNumber = 16;
        } else if (elevatorsNumber < 1) {
            System.out.println("Minimum number of elevators is 1. Value set to 1.");
            elevatorsNumber = 1;
        }
        for (int i = 1; i <= elevatorsNumber; i++) {
            elevators.add(new Elevator(i));
        }
    }

    @Override
    public void viewStatus() {
        System.out.println("==============================================================================================================");
        elevators.forEach(System.out::println);
        System.out.println("==============================================================================================================");
    }

    @Override
    public void simulateStep() {
        for (Elevator elevator : elevators) {
            int direction = elevator.getDirection();
            int currentFloor = elevator.getCurrentFloor();

            if (!elevator.isStopped()) {
                if (direction > 0 && isGoingUpwards(elevator)) {
                    currentFloor++;
                } else if (direction > 0 && isGoingDownwards(elevator)) {
                    direction = -1;
                    currentFloor--;
                } else if (direction < 0 && isGoingDownwards(elevator)) {
                    currentFloor--;
                } else if (direction < 0 && isGoingUpwards(elevator)) {
                    direction = 1;
                    currentFloor++;
                }
                elevator.setDirection(direction);
                elevator.setCurrentFloor(currentFloor);
            }
            if (areCurrentFloorAndTargetFloorEquals(elevator) || elevator.isStopped()) {
                elevator.setStopped(!elevator.isStopped());
            }
            elevator.getTargetFloors()[elevator.getCurrentFloor()] = 0;
            if (areTargetFloorsEmpty(elevator)) {
                elevator.setDirection(0);
            }
        }
    }

    private boolean areCurrentFloorAndTargetFloorEquals(Elevator elevator) {
        return elevator.getTargetFloors()[elevator.getCurrentFloor()] == 1;
    }

    private boolean areTargetFloorsEmpty(Elevator elevator) {
        return Arrays.stream(elevator.getTargetFloors()).noneMatch(floor -> floor == 1);
    }

    private boolean isGoingUpwards(Elevator elevator) {
        return Arrays.stream(elevator.getTargetFloors(), elevator.getCurrentFloor(), elevator.getTargetFloors().length)
                .anyMatch(floor -> floor == 1);
    }

    private boolean isGoingDownwards(Elevator elevator) {
        return Arrays.stream(elevator.getTargetFloors(), 0, elevator.getCurrentFloor())
                .anyMatch(floor -> floor == 1);
    }

    @Override
    public void updateElevatorStatus(int elevatorId, int currentFloor, int targetFloor) {
        if (currentFloor > 10 || currentFloor < 0) {
            System.out.println("Current floor must be between 0 and 10.");
            return;
        }
        if (targetFloor > 10 || targetFloor < 0) {
            System.out.println("Target floor must be between 0 and 10.");
            return;
        }
        if (elevatorId < 1 || elevatorId > elevators.size()) {
            System.out.println("Incorrect elevator's id. Pick elevator's id ranging from 1 to " + elevators.size());
            return;
        }
        Elevator elevator = elevators.get(elevatorId - 1);
        elevator.setCurrentFloor(currentFloor);
        elevator.setTargetFloors(new int[11]);
        if (elevator.getCurrentFloor() == targetFloor) {
            elevator.setDirection(0);
        } else {
            setDirectionOfElevator(elevator, targetFloor);
            elevator.getTargetFloors()[targetFloor] = 1;
        }
    }

    @Override
    public void selectFloor(int elevatorId, int targetFloor) {
        if (targetFloor > 10 || targetFloor < 0) {
            System.out.println("Target floor must be between 0 and 10.");
            return;
        }
        if (elevatorId < 1 || elevatorId > elevators.size()) {
            System.out.println("Incorrect elevator's id. Pick elevator's id ranging from 1 to " + elevators.size());
            return;
        }
        Elevator elevator = elevators.get(elevatorId - 1);
        setTargetFloorAndDirection(elevator, targetFloor);
    }

    private void setTargetFloorAndDirection(Elevator elevator, int targetFloor) {
        if (elevator.getCurrentFloor() != targetFloor) {
            elevator.getTargetFloors()[targetFloor] = 1;
            if (elevator.getDirection() == 0) {
                setDirectionOfElevator(elevator, targetFloor);
            }
        }
    }

    private void setDirectionOfElevator(Elevator elevator, int targetFloor) {
        if (elevator == null) {
            return;
        }
        int direction = elevator.getCurrentFloor() > targetFloor ? -1 : 1;
        elevator.setDirection(direction);
    }

    @Override
    public void pickUpPassenger(int passengerFloor, int direction) {
        if (passengerFloor > 10 || passengerFloor < 0) {
            System.out.println("Passenger must be on the floor ranging from 0 to 10.");
            return;
        }
        if (direction == 0) {
            System.out.println("Chosen direction can not be 0. Pick positive (upwards) or negative number (downwards).");
            return;
        }

        Elevator elevator;
        int levelDifference = Integer.MAX_VALUE;
        if (direction > 0) {
            elevator = chooseElevatorWhenGoingUpwards(passengerFloor, levelDifference);
        } else {
            elevator = chooseElevatorWhenGoingDownwards(passengerFloor, levelDifference);
        }
        if (elevator == null) {
            elevator = chooseStillElevator(passengerFloor, levelDifference);
        }
        if (elevator == null) {
            elevator = chooseFromRemainingElevators(passengerFloor, levelDifference);
        }
        setTargetFloorAndDirection(elevator, passengerFloor);
    }

    private Elevator chooseElevatorWhenGoingUpwards(int passengerFloor, int levelDifference) {
        Elevator elevator = null;
        for (Elevator e : elevators) {
            if (e.getDirection() > 0 &&
                    e.getCurrentFloor() <= passengerFloor) {
                if (passengerFloor - e.getCurrentFloor() < levelDifference) {
                    elevator = e;
                    levelDifference = passengerFloor - e.getCurrentFloor();
                }
            }
        }
        return elevator;
    }

    private Elevator chooseElevatorWhenGoingDownwards(int passengerFloor, int levelDifference) {
        Elevator elevator = null;
        for (Elevator e : elevators) {
            if (e.getDirection() < 0 && e.getCurrentFloor() >= passengerFloor) {
                if (e.getCurrentFloor() - passengerFloor < levelDifference) {
                    elevator = e;
                    levelDifference = e.getCurrentFloor() - passengerFloor;
                }
            }
        }
        return elevator;
    }

    private Elevator chooseStillElevator(int passengerFloor, int levelDifference) {
        Elevator elevator = null;
        for (Elevator e : elevators) {
            if (e.getDirection() == 0) {
                if (Math.abs(passengerFloor - e.getCurrentFloor()) < levelDifference) {
                    elevator = e;
                    levelDifference = Math.abs(passengerFloor - e.getCurrentFloor());
                }
            }
        }
        return elevator;
    }

    private Elevator chooseFromRemainingElevators(int passengerFloor, int levelDifference) {
        Elevator elevator = null;
        for (Elevator e : elevators) {
            if (e.getDirection() != 0) {
                if (Math.abs(passengerFloor - pickLastFloorInSpecificDirection(e)) < levelDifference) {
                    elevator = e;
                    levelDifference = Math.abs(passengerFloor - pickLastFloorInSpecificDirection(e));
                }
            }
        }
        return elevator;
    }

    private int pickLastFloorInSpecificDirection(Elevator elevator) {
        if (elevator.getDirection() < 0) {
            for (int i = 0; i < elevator.getTargetFloors().length; i++) {
                if (elevator.getTargetFloors()[i] == 1) {
                    return i;
                }
            }
        } else if (elevator.getDirection() > 0) {
            for (int i = elevator.getTargetFloors().length - 1; i >= 0; i--) {
                if (elevator.getTargetFloors()[i] == 1) {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean isAutoSimulated() {
        return isAutoSimulated;
    }

    public void setAutoSimulated(boolean autoSimulated) {
        isAutoSimulated = autoSimulated;
    }
}



























