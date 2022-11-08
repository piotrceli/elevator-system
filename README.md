# Elevator System
Elevator System is an elevators' simulator project.

## Used Technologies: 
* Java 
* JUnit

## Contents:
1. General assumptions
2. Available functionalities
3. How to use the simulator

### 1. General assumptions
The system is used to service up to 16 elevators. 
A building has floors ranging from 0 to 10.
Each elevator has the floors presented by an int[] array - targetFloors.
If the floor is one of the targets, corresponding array's element obtains value of 1.
Each elevator may have several selected target floors at the same time.
After arriving at the target floor (targetFloors[i] = 1), the elevator stops on this floor for one simulation step (simulateStep()). It is shown with a field called isStopped.
Current elevator's direction is presented by a field direction:
* positive number - elevator is going upwards
* negative number - elevator is going downwards
* 0 - elevator stays in the place and does not have any target floors

### 2. Available functionalities
* #### viewStatus() - Prints status of elevators in the form below.
Elevator{id=1, currentFloor=5, targetFloors=[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0], direction=0, isStopped=true}

Elevator{id=2, currentFloor=4, targetFloors=[0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1], direction=1, isStopped=false}

Elevator{id=3, currentFloor=8, targetFloors=[0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1], direction=-1, isStopped=false}

Elevator{id=4, currentFloor=5, targetFloors=[1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0], direction=1, isStopped=false}

Elevator{id=5, currentFloor=4, targetFloors=[1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0], direction=-1, isStopped=true}

* #### simulateStep() - Makes one simulation step.
The elevators are going in the right direction and are stopping on the target floors for one simulation step.
If there is a need the elevators switch their directions after arriving at the last target floor of the primary direction.

* #### updateElevatorStatus() - Update of the current floor (currentFloor) and a choice of one target floor for the selected elevator.

* #### selectFloor() - Selection of the target floor for the selected elevator (simulation of using buttons inside of the elevator).

* #### pickUpPassenger() - Picking up a passenger that waits on a specific floor. The functionality takes into account travel's direction.
The elevator will be selected in the order described below.
1. The closest elevator that is going in the same direction and can pick up the passenger without changing the direction.
2. The closest elevator that is not being used.
3. The elevators that are going in the different direction or the elevators that are going in the same direction, but they are already past passenger's floor (passengerFloor).
In this case, the elevator whose last target floor for the current direction is the closest to the passenger's floor will be chosen.

* #### Automatic simulation
Option to ON/OFF an automatic simulation. The simulation makes simulation steps every 7 seconds.
The simulation does not block other functionalities.

### 3. How to use the simulator
Run the application by main method in Main class.
User's interaction is performed with use of Scanner class.
The console prints options from the menu and gives information about the next available steps.

MENU:
* 0 - Show Menu
* 1 - View Elevator System's Status
* 2 - Simulate Step and Show Updated Status
* 3 - Pick Up Elevator
* 4 - Select Floor
* 5 - Update Elevator's Status
* 6 - ON/OFF Auto Simulation
* 9 - Shut Down Application

The simulator contains a basic validation in case of entering the wrong type of data or data inconsistent with the application's assumptions.


