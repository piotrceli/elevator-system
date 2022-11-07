package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static ElevatorSystem elevatorSystem;

    public static void main(String[] args) {

        Thread thread = null;
        int elevatorsNumber = 8;
        try {
            System.out.println("\nPick number of elevators (range: 1-16):");
            elevatorsNumber = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException ime) {
            System.out.println("Wrong input. Number of elevators set to 8.");
            scanner.nextLine();
        }
        elevatorSystem = new ElevatorSystem(elevatorsNumber);
        System.out.println();
        boolean quit = false;
        printMenu();
        while (!quit) {
            int action = -1;
            try {
                Thread.sleep(300);
                System.out.println("\nPick action: ");
                action = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException ime) {
                scanner.nextLine();
            }
            catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            switch (action) {
                case 0:
                    printMenu();
                    break;

                case 1:
                    elevatorSystem.viewStatus();
                    break;

                case 2:
                    elevatorSystem.simulateStep();
                    elevatorSystem.viewStatus();
                    break;

                case 3:
                    pickUpElevator();
                    break;

                case 4:
                    selectFloor();
                    break;

                case 5:
                    updateElevatorStatus();
                    break;

                case 6:
                    elevatorSystem.setAutoSimulated(!elevatorSystem.isAutoSimulated());
                    if (elevatorSystem.isAutoSimulated()) {
                        thread = new Thread(() -> {
                            System.out.println("Auto Simulation ON");
                            while (!Thread.currentThread().isInterrupted()) {
                                try {
                                    elevatorSystem.simulateStep();
                                    elevatorSystem.viewStatus();
                                    TimeUnit.SECONDS.sleep(7);
                                } catch (InterruptedException e) {
                                    System.out.println("Auto Simulation OFF");
                                    Thread.currentThread().interrupt();
                                }
                            }
                        });
                        thread.setDaemon(true);
                        thread.start();
                    } else if (thread != null) {
                        thread.interrupt();
                    }
                    break;

                case 9:
                    quit = true;
                    System.out.println("Shutting down");
                    break;

                default:
                    System.out.println("Pick correct Action Number (from the Menu)");
                    printMenu();
                    break;
            }
        }
    }

    private static void printMenu() {
        System.out.println("MENU:");
        System.out.println("0 - Show Menu");
        System.out.println("1 - View Elevator System's Status");
        System.out.println("2 - Simulate Step and Show Updated Status");
        System.out.println("3 - Pick Up Elevator");
        System.out.println("4 - Select Floor");
        System.out.println("5 - Update Elevator's Status");
        System.out.println("6 - ON/OFF Auto Simulation");
        System.out.println("9 - Shut Down Application");
    }

    private static void selectFloor() {
        System.out.println("Choose elevator by id (insert elevator's id):");
        int elevatorId = handleInputAndException();
        System.out.println("Enter target floor: ");
        int targetFloor = handleInputAndException();
        elevatorSystem.selectFloor(elevatorId, targetFloor);
    }

    private static void pickUpElevator() {
        System.out.println("Enter passenger's floor: ");
        int passengerFloor = handleInputAndException();
        System.out.println("Enter driving direction: ");
        int direction = scanner.nextInt();
        elevatorSystem.pickUpElevator(passengerFloor, direction);
    }

    private static void updateElevatorStatus() {
        System.out.println("Choose elevator by id (insert elevator's id):");
        int elevatorId = handleInputAndException();
        System.out.println("Enter modified current floor: ");
        int currentFloor = handleInputAndException();
        System.out.println("Enter modified target floor: ");
        int targetFloor = handleInputAndException();
        elevatorSystem.updateElevatorStatus(elevatorId, currentFloor, targetFloor);
    }

    private static int handleInputAndException() {
        int result;
        try {
            result = scanner.nextInt();
            return result;
        } catch (InputMismatchException ime) {
            System.out.println("Wrong input type. Insert whole number (integer) below:");
        }
        scanner.nextLine();
        return handleInputAndException();
    }
}