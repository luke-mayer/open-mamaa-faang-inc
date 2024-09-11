package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import characters.*;
import gameObjects.*;
import characters.Character;
import static core.Story.*;
import static core.GameUtility.*;

public class Game implements Serializable {

    private int currentDay;
    private boolean isRunning;

    public Player player; // Change to private after development
    public characters.NPC sam;

    // Constructs a new Game
    public Game(){
        currentDay = 0;
        isRunning = true;
        sam = new NPC("Sam Altmuckerburg", LocationName.SAM_OFFICE,3);

        // Initial Things
        new Thing("Cubicle Hole","*It looks like someone cut a square out of the cubicle with a box cutter. \n" +
                "Must be part of their move to an open concept they talked about a few years ago. \n" +
                "You see someone a few years older than you hunched over their desk, blankly staring at their screen while steadily typing. \n" +
                "Is that drool in the corner of their mouth?*");

        // Initial storyStops
        Story.addStoryStop(LocationName.SAM_OFFICE);

        // Initial locations with their neighbors and actions
        Location location;

        location = new Location(LocationName.CUBICLE, "*A cramped, gray cubicle which has a small opening on one side.\n" +
                "There is a tiny desk with a computer and a single drawer.*");
        location.addAction(Action.CUBICLE_HOLE);
        location.addAction(Action.DESK_DRAWER);
        location.addAction(Action.COMPUTER_LOGIN);
        location.addNeighbor(LocationName.SAM_OFFICE);

        location = new Location(LocationName.SAM_OFFICE,"*A relatively bland office. More roomy than your cubicle though.*");
        location.addNeighbor(LocationName.CUBICLE);

        location = new Location(LocationName.BASEMENT,"*A hallway that connects to the Server room...\n" +
                "They should really do something about this flickering light.*");
        location.addNeighbor(LocationName.STAIRS);
    }

    // Increments the day
    public void nextDay(){
        currentDay += 1;
    }

    // Prints a heading with the current day
    public void printDay(){
        clearConsole();
        String dayTitle = "DAY " + currentDay;
        printHeading(dayTitle);
    }

    // Gets the user input from standard input and returns it as int, returns -1 if choices need to be reprinted.
    public int userChoice(String prompt, int numChoices){
        int choice;

        do{
            System.out.print(prompt + " ");
            String userIn = scan.nextLine().strip().toLowerCase();
            switch (userIn) {
                case "m", "menu", "pause":
                    pauseMenu();
                    return -1;
                case "i", "inventory":
                    printInventory();
                    return -1;
                case "o", "objectives":
                    printObjectives();
                    return -1;
                default:
                    try {
                        choice = Integer.parseInt(userIn);
                    } catch (Exception e) {
                        choice = -1;
                        System.out.println("Please enter a number corresponding to a presented choice or:");
                        System.out.println(" - \"M\" to access the PAUSE MENU.");
                        System.out.println(" - \"I\" to access the INVENTORY.");
                        System.out.println(" - \"O\" to access the OBJECTIVES.");
                    }
            }
        }while(choice < 1 || choice > numChoices);
        System.out.println();
        return choice;
    }

    // Prints the pause menu
    public void pauseMenu(){
        int choice;

        do{
            clearConsole();
            printHeading("MENU");
            System.out.println("DAY: " + currentDay);
            System.out.println();
            System.out.println("(1) Continue");
            System.out.println("(2) Inventory");
            System.out.println("(3) Objectives");
            System.out.println("(4) Restart Day");
            System.out.println("(5) Save & Exit Game");

            choice = userChoice("->", 5);
        }while(choice < 1);

        switch(choice){
            case 1:
                break;
            case 2:
                printInventory();
                break;
            case 3:
                printObjectives();
                break;
            case 4:
                restartDay();
            case 5:
                exitGame();
        }

    }

    // Prints the current contents of the player's inventory
    public void printInventory(){
        clearConsole();
        printSeparator(30);
        System.out.print("INVENTORY");
        printSeparator(30);
        System.out.println();
        if(!player.inventoryIsEmpty()) {
            Iterator<String> iterator = player.getInventory();
            while(iterator.hasNext()) {
                System.out.println(" - " + iterator.next());
            }
        }else{
            System.out.println(" - Inventory is empty.");
        }
        System.out.println();
        anyInput();
    }

    // Prints the player's current objectives
    public void printObjectives(){
        clearConsole();
        printSeparator(30);
        System.out.print("OBJECTIVES");
        printSeparator(30);
        System.out.println();
        if(!player.objectivesIsEmpty()) {
            Iterator<String> iterator = player.getObjectives();
            while(iterator.hasNext()) {
                System.out.println(" - " + iterator.next());
            }
        }else{
            System.out.println("You do not currently have any objectives.");
        }
        System.out.println();
        anyInput();
    }

    // Continues the game
    public void continueGame(){
        // ToDo
    }

    // Saves the game
    public void saveGame(){
        // ToDo
    }

    // Restarts the current day loading the most recent save
    public void restartDay(){
        // ToDo
    }

    // Exits game (Try to find a way to save game in the future)
    public void exitGame(){
        saveGame();
        scan.close();
        // ToDo
        // Maybe exit(0);
        isRunning = false;
    }

    // Reads the description that corresponds with the provided Thing
    public void readDescription(String name){
        Thing thing = Thing.getThing(name);
        printLine(thing.getDescription());
        anyInput();
    }

    // Performs the given action by calling the associated method
    public void performAction(Action action){
        switch(action){
            case Action.CUBICLE_HOLE:
                readDescription("Cubicle Hole");
                break;
            case Action.DESK_DRAWER:
                // openContainer();
                System.out.println("Temp DESK_DRAWER action");
                break;
            case Action.COMPUTER_LOGIN:
                // loginComputer();
                System.out.println("Temp COMPUTER_LOGIN action");
                break;
        }
    }

    // Displays actions the player can take
    public void chooseAction(){
        Location curLocation = Location.getLocation(player.getLocation()); // Getting Location object of player's location
        int choice, printedIndx;

        do {
            printSeparator(SEP_LENGTH);
            System.out.println();
            System.out.println("*Take an action or leave to go to another location*");
            printedIndx = 0;
            // Printing all the actions in this location
            for (int i = 0; i < curLocation.numActions(); i++) {
                Action action = curLocation.getAction(i);
                printedIndx = i + 1;
                System.out.println("(" + printedIndx + ") " + action);
            }
            printedIndx += 1;
            System.out.println("(" + printedIndx + ") Leave");

            choice = userChoice("->", printedIndx);
        }while(choice < 1);

        if(choice != printedIndx){ // Choose an action
            Action action = curLocation.getAction(choice - 1);
            performAction(action);
        }
    }

    // Allows player to choose a location to move to
    public void moveLocations(){
        Location curLocation = Location.getLocation(player.getLocation()); // Getting Location object of player's location
        int choice, printedIndx;

        do {
            printSeparator(SEP_LENGTH);
            System.out.println();
            System.out.println("*Choose a location to move to or stay*");
            printedIndx = 0;
            // Printing all the actions in this location
            for (int i = 0; i < curLocation.numNeighbors(); i++) {
                LocationName neighbor = curLocation.getNeighbor(i);
                printedIndx = i + 1;
                System.out.println("(" + printedIndx + ") " + neighbor);
            }
            printedIndx += 1;
            System.out.println("(" + printedIndx + ") Stay");

            choice = userChoice("->", printedIndx);
        }while(choice < 1);

        if(choice != printedIndx){ // Choose an action
            LocationName newLocation = curLocation.getNeighbor(printedIndx);
            player.setLocation(newLocation);
        }
    }

    // The exploration loop, stops when a storyStop is reached
    public void explorationLoop(){
        while(!containsStoryStop(player.getLocation())){
            chooseAction(); // Allows player to choose an available action in the current location
            moveLocations(); // Allows player to move to a neighboring location
        }
    }


    // Starts a new game
    public void startGame(){
        boolean nameConfirmed = false;
        String name;

        clearConsole();
        printHeading("Welcome to OpenMAMMAFAANG inc.");
        delayMs(2000);
        anyInput();
        nextDay();
        printDay();
        delayMs(1000);
        introPart1(); // Story up to user inputting name
        printSeparator(SEP_LENGTH);
        scan.nextLine(); // Clears input

        int choice;
        // Prompts user for name until they confirm it
        do{
            System.out.print("\nEnter your name: ");
            name = scan.nextLine();
            System.out.println();
            System.out.println("Confirm your name is: " + name);
            System.out.println("(1) Yes. *CONFIRM*");
            System.out.println("(2) No. *REDO NAME*");
            choice = userChoice("->", 2);
            if(choice == 1){
                nameConfirmed = true;
            }
        }while(!nameConfirmed);

        player = new Player(name, LocationName.CUBICLE); // Creating Player with given name
    }
}
