package com.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.MethodName.class)
class ElevatorSystemTest {

    private ElevatorSystem elevatorSystem;
    private List<Elevator> elevators;
    private Elevator elevator1;
    private Elevator elevator2;
    private Elevator elevator3;
    private Elevator elevator4;
    private Elevator elevator5;

    @BeforeEach
    void setUp() {
        elevatorSystem = new ElevatorSystem(5);
        elevators = elevatorSystem.getElevators();
        elevator1 = elevators.get(0);
        elevator2 = elevators.get(1);
        elevator3 = elevators.get(2);
        elevator4 = elevators.get(3);
        elevator5 = elevators.get(4);

        // set default status for elevators
        elevator1.setDirection(1);
        elevator1.setCurrentFloor(0);
        elevator1.setTargetFloors(new int[]{0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0});

        elevator2.setDirection(1);
        elevator2.setCurrentFloor(2);
        elevator2.setTargetFloors(new int[]{0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0});

        elevator3.setDirection(0);
        elevator3.setCurrentFloor(5);
        elevator3.setTargetFloors(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});

        elevator4.setDirection(-1);
        elevator4.setCurrentFloor(10);
        elevator4.setTargetFloors(new int[]{0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0});

        elevator5.setDirection(-1);
        elevator5.setCurrentFloor(7);
        elevator5.setTargetFloors(new int[]{1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0});
    }

    @Test
    void shouldUpdateElevatorStatus_whenCurrentFloorDiffersTargetFloor() {

        // given
        int elevatorId = 1;
        int currentFloor = 3;
        int targetFloor = 7;

        // when
        elevatorSystem.updateElevatorStatus(elevatorId, currentFloor, targetFloor);

        // then
        assertEquals(elevator1.getCurrentFloor(), 3);
        assertEquals(elevator1.getTargetFloors()[7], 1);
        assertEquals(elevator1.getDirection(), 1);
    }

    @Test
    void shouldUpdateElevatorStatus_whenCurrentFloorEqualsTargetFloor() {

        // given
        int elevatorId = 1;
        int currentFloor = 7;
        int targetFloor = 7;

        // when
        elevatorSystem.updateElevatorStatus(elevatorId, currentFloor, targetFloor);

        // then
        assertEquals(elevator1.getCurrentFloor(), 7);
        assertEquals(elevator1.getTargetFloors()[7], 0);
        assertEquals(elevator1.getDirection(), 0);
    }

    @Test
    void shouldSelectFloor_whenElevatorGoesInTheSameDirection_givenElevatorIsGoingUpwards() {

        // given
        int elevatorId = 1;
        int targetFloor = 5;

        // when
        elevatorSystem.selectFloor(elevatorId, targetFloor);

        // then
        assertEquals(elevator1.getCurrentFloor(), 0);
        assertEquals(elevator1.getTargetFloors()[5], 1);
        assertEquals(elevator1.getDirection(), 1);
    }

    @Test
    void shouldSelectFloor_whenElevatorGoesInTheSameDirection_givenElevatorIsGoingDownwards() {

        // given
        int elevatorId = 5;
        int targetFloor = 1;

        // when
        elevatorSystem.selectFloor(elevatorId, targetFloor);

        // then
        assertEquals(elevator5.getCurrentFloor(), 7);
        assertEquals(elevator5.getTargetFloors()[1], 1);
        assertEquals(elevator5.getDirection(), -1);
    }

    @Test
    void shouldSelectFloorAndSetDirectionDownwards_givenElevatorIsStill() {

        // given
        int elevatorId = 3;
        int targetFloor = 1;

        // when
        elevatorSystem.selectFloor(elevatorId, targetFloor);

        // then
        assertEquals(elevator3.getCurrentFloor(), 5);
        assertEquals(elevator3.getTargetFloors()[1], 1);
        assertEquals(elevator3.getDirection(), -1);
    }

    @Test
    void shouldSelectFloorAndSetDirectionUpwards_givenElevatorIsStill() {

        // given
        int elevatorId = 3;
        int targetFloor = 9;

        // when
        elevatorSystem.selectFloor(elevatorId, targetFloor);

        // then
        assertEquals(elevator3.getCurrentFloor(), 5);
        assertEquals(elevator3.getTargetFloors()[9], 1);
        assertEquals(elevator3.getDirection(), 1);
    }

    @Test
    void shouldSelectFloor_whenElevatorIsGoingInDifferentDirection_givenElevatorIsGoingDownwards() {

        // given
        int elevatorId = 5;
        int targetFloor = 10;

        // when
        elevatorSystem.selectFloor(elevatorId, targetFloor);

        // then
        assertEquals(elevator5.getCurrentFloor(), 7);
        assertEquals(elevator5.getTargetFloors()[10], 1);
        assertEquals(elevator5.getDirection(), -1);
    }

    @Test
    void shouldSelectFloor_whenElevatorIsGoingInDifferentDirection_givenElevatorIsGoingUpwards() {

        // given
        int elevatorId = 2;
        int targetFloor = 0;

        // when
        elevatorSystem.selectFloor(elevatorId, targetFloor);

        // then
        assertEquals(elevator2.getCurrentFloor(), 2);
        assertEquals(elevator2.getTargetFloors()[0], 1);
        assertEquals(elevator2.getDirection(), 1);
    }

    @Test
    void shouldSimulateStep_whenElevatorIsGoingInSpecificDirection() {

        // when
        elevatorSystem.simulateStep();

        // then
        assertEquals(elevator1.getCurrentFloor(), 1);
        assertEquals(elevator2.getCurrentFloor(), 3);
        assertEquals(elevator4.getCurrentFloor(), 9);
        assertEquals(elevator5.getCurrentFloor(), 6);
    }

    @Test
    void shouldStopElevator_whenElevatorIsOnTargetFloor_givenElevatorIsGoingDownwards() {

        // when
        for (int i = 0; i < 3; i++) {
            elevatorSystem.simulateStep();
        }

        // then
        assertEquals(elevator4.getCurrentFloor(), 8);
        assertEquals(elevator4.getTargetFloors()[8], 0);
    }

    @Test
    void shouldStopElevator_whenElevatorIsOnTargetFloor_givenElevatorIsGoingUpwards() {

        // when
        for (int i = 0; i < 4; i++) {
            elevatorSystem.simulateStep();
        }

        // then
        assertEquals(elevator1.getCurrentFloor(), 3);
        assertEquals(elevator1.getTargetFloors()[3], 0);
    }

    @Test
    void shouldStopElevatorAndStartDriving_whenElevatorPassesTargetFloor_givenElevatorIsGoingDownwards() {

        // when
        for (int i = 0; i < 4; i++) {
            elevatorSystem.simulateStep();
        }

        // then
        assertEquals(elevator4.getCurrentFloor(), 7);
        assertEquals(elevator4.getTargetFloors()[8], 0);
    }

    @Test
    void shouldStopElevatorAndStartDriving_whenElevatorPassesTargetFloor_givenElevatorIsGoingUpwards() {

        // when
        for (int i = 0; i < 5; i++) {
            elevatorSystem.simulateStep();
        }

        // then
        assertEquals(elevator1.getCurrentFloor(), 4);
        assertEquals(elevator1.getTargetFloors()[3], 0);
        assertEquals(elevator1.getTargetFloors()[4], 0);
    }

    @Test
    void shouldChangeDirectionOfElevator_whenElevatorReachedLastFloor_givenElevatorIsGoingUpwards() {

        // given
        elevator1.setDirection(1);
        elevator1.setCurrentFloor(6);
        elevator1.setTargetFloors(new int[]{0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0});

        int[] resultTargetFloors = {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0};

        // when
        for (int i = 0; i < 9; i++) {
            elevatorSystem.simulateStep();
        }

        // then
        assertEquals(elevator1.getCurrentFloor(), 4);
        assertEquals(elevator1.getDirection(), -1);
        assertArrayEquals(elevator1.getTargetFloors(), resultTargetFloors);
    }

    @Test
    void shouldChangeDirectionOfElevator_whenElevatorReachedLastFloor_givenElevatorIsGoingDownwards() {

        // given
        elevator1.setDirection(-1);
        elevator1.setCurrentFloor(6);
        elevator1.setTargetFloors(new int[]{0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0});

        int[] resultTargetFloors = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0};

        // when
        for (int i = 0; i < 10; i++) {
            elevatorSystem.simulateStep();
        }

        // then
        assertEquals(elevator1.getCurrentFloor(), 8);
        assertEquals(elevator1.getDirection(), 1);
        assertArrayEquals(elevator1.getTargetFloors(), resultTargetFloors);
    }

    @Test
    void shouldResetDirectionAndStopElevator_whenElevatorReachedAllTargetFloors() {

        // given
        elevator1.setDirection(-1);
        elevator1.setCurrentFloor(6);
        elevator1.setTargetFloors(new int[]{0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0});

        int[] resultTargetFloors = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        // when
        for (int i = 0; i < 11; i++) {
            elevatorSystem.simulateStep();
        }

        // then
        assertEquals(elevator1.getCurrentFloor(), 9);
        assertEquals(elevator1.getDirection(), 0);
        assertArrayEquals(elevator1.getTargetFloors(), resultTargetFloors);
        assertTrue(elevator1.isStopped());
    }

    @Test
    void shouldPickUpClosestElevator_whenElevatorWithSameDirectionExists_givenDirectionIsUpwards() {

        // given
        int direction = 1;
        int passengerFloor = 7;

        // when
        elevatorSystem.pickUpElevator(passengerFloor, direction);

        // then
        assertEquals(elevator1.getTargetFloors()[7], 0);
        assertEquals(elevator2.getTargetFloors()[7], 1);
        assertEquals(elevator3.getTargetFloors()[7], 0);
        assertEquals(elevator4.getTargetFloors()[7], 0);
        assertEquals(elevator5.getTargetFloors()[7], 0);
    }

    @Test
    void shouldPickUpClosestElevator_whenElevatorWithSameDirectionExists_givenDirectionIsDownwards() {

        // given
        int direction = -1;
        int passengerFloor = 5;

        // when
        elevatorSystem.pickUpElevator(passengerFloor, direction);

        // then
        assertEquals(elevator1.getTargetFloors()[5], 0);
        assertEquals(elevator2.getTargetFloors()[5], 0);
        assertEquals(elevator3.getTargetFloors()[5], 0);
        assertEquals(elevator4.getTargetFloors()[5], 0);
        assertEquals(elevator5.getTargetFloors()[5], 1);
    }

    @Test
    void shouldPickUpClosestElevator_whenAllElevatorsAreStill_givenDirectionIsUpwards() {

        // given
        int direction = 1;
        int passengerFloor = 8;
        elevator1.setDirection(0);
        elevator2.setDirection(0);
        elevator3.setDirection(0);
        elevator4.setDirection(0);
        elevator5.setDirection(0);
        elevator1.getTargetFloors()[8] = 0;
        elevator2.getTargetFloors()[8] = 0;
        elevator3.getTargetFloors()[8] = 0;
        elevator4.getTargetFloors()[8] = 0;
        elevator5.getTargetFloors()[8] = 0;

        // when
        elevatorSystem.pickUpElevator(passengerFloor, direction);

        // then
        assertEquals(elevator1.getTargetFloors()[8], 0);
        assertEquals(elevator2.getTargetFloors()[8], 0);
        assertEquals(elevator3.getTargetFloors()[8], 0);
        assertEquals(elevator4.getTargetFloors()[8], 0);
        assertEquals(elevator5.getTargetFloors()[8], 1);
    }

    @Test
    void shouldPickUpClosestElevator_whenAllElevatorsAreStill_givenDirectionIsDownwards() {

        // given
        int direction = -1;
        int passengerFloor = 4;
        elevator1.setDirection(0);
        elevator2.setDirection(0);
        elevator3.setDirection(0);
        elevator4.setDirection(0);
        elevator5.setDirection(0);
        elevator1.getTargetFloors()[4] = 0;
        elevator2.getTargetFloors()[4] = 0;
        elevator3.getTargetFloors()[4] = 0;
        elevator4.getTargetFloors()[4] = 0;
        elevator5.getTargetFloors()[4] = 0;

        // when
        elevatorSystem.pickUpElevator(passengerFloor, direction);

        // then
        assertEquals(elevator1.getTargetFloors()[4], 0);
        assertEquals(elevator2.getTargetFloors()[4], 0);
        assertEquals(elevator3.getTargetFloors()[4], 1);
        assertEquals(elevator4.getTargetFloors()[4], 0);
        assertEquals(elevator5.getTargetFloors()[4], 0);
    }

    @Test
    void shouldPickUpStillElevator_whenElevatorsGoingInSameDirectionAlreadyPassedPassengerFloor_givenDirectionIsUpwards() {

        // given
        int direction = 1;
        int passengerFloor = 3;
        elevator1.setCurrentFloor(4);
        elevator2.setCurrentFloor(7);
        elevator3.getTargetFloors()[3] = 0;

        // when
        elevatorSystem.pickUpElevator(passengerFloor, direction);

        // then
        assertEquals(elevator3.getTargetFloors()[3], 1);
    }

    @Test
    void shouldPickUpStillElevator_whenElevatorsGoingInSameDirectionAlreadyPassedPassengerFloor_givenDirectionIsDownwards() {

        // given
        int direction = -1;
        int passengerFloor = 8;
        elevator4.setCurrentFloor(7);
        elevator5.setCurrentFloor(7);
        elevator3.getTargetFloors()[8] = 0;

        // when
        elevatorSystem.pickUpElevator(passengerFloor, direction);

        // then
        assertEquals(elevator3.getTargetFloors()[8], 1);
    }

    @Test
    void shouldPickUpElevatorWithSmallestLevelDifferenceWithLastTargetFloorInCurrentDirection_givenDirectionIsUpwards() {

        // given
        int direction = 1;
        int passengerFloor = 6;

        // setting up elevators
        elevator3.setDirection(1);
        elevator3.getTargetFloors()[10] = 1;
        elevator3.setCurrentFloor(7);

        elevator1.setCurrentFloor(7);

        elevator2.setCurrentFloor(9);
        elevator2.getTargetFloors()[10] = 1;

        elevator1.getTargetFloors()[6] = 0;
        elevator2.getTargetFloors()[6] = 0;
        elevator3.getTargetFloors()[6] = 0;
        elevator4.getTargetFloors()[6] = 0;
        elevator5.getTargetFloors()[6] = 0;

        // when
        elevatorSystem.pickUpElevator(passengerFloor, direction);

        // then
        assertEquals(elevator1.getTargetFloors()[6], 1);
    }

    @Test
    void shouldPickUpElevatorWithSmallestLevelDifferenceWithLastTargetFloorInCurrentDirection_givenDirectionIsDownwards() {

        // given
        int direction = -1;
        int passengerFloor = 6;

        // setting up elevators
        elevator3.setDirection(1);
        elevator3.getTargetFloors()[10] = 1;
        elevator3.setCurrentFloor(7);

        elevator2.getTargetFloors()[7] = 1;

        elevator4.setCurrentFloor(5);

        elevator5.setCurrentFloor(4);

        elevator1.getTargetFloors()[6] = 0;
        elevator2.getTargetFloors()[6] = 0;
        elevator3.getTargetFloors()[6] = 0;
        elevator4.getTargetFloors()[6] = 0;
        elevator5.getTargetFloors()[6] = 0;

        // when
        elevatorSystem.pickUpElevator(passengerFloor, direction);

        // then
        assertEquals(elevator2.getTargetFloors()[6], 1);
    }
}



















