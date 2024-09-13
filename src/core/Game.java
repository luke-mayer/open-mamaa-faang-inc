package core;

import java.io.Serializable;
import java.util.Iterator;

import characters.*;
import gameObjects.*;
import static core.Story.*;
import static core.GameUtility.*;

public class Game implements Serializable {

    private int currentDay;
    private boolean isRunning;

    public Player player; // Change to private after development
    public characters.NPC sam, deb;

    // Constructs a new Game
    public Game(){
        currentDay = 0;
        isRunning = true;
        sam = new NPC("Sam Altmuckerburg", LocationName.SAM_OFFICE,5);
        deb = new NPC("Deborah <TBD>", LocationName.ELEVATOR, 3);

        // Initializing starting game objects
        initializeContainersItems();
        initializeLocationsActions();
        initializeStoryStops();
        initializeThings();
    }

    // Initializes starting containers and Items present in the beginning of the game
    public void initializeContainersItems(){
        Container container;
        // Cubicle desk drawer and items
        container = new Container("Cubicle Desk Drawer", "To be filled in later", Action.DESK_DRAWER);
        container.addItem(new Item("Paperclip","To be filled in later"));
        container.addItem(new Item("Stapler","To be filled in later"));
        container.addItem(new Item("Gum","To be filled in later"));
    }

    // Initializes the starting Locations and Actions present in the beginning of the game
    public void initializeLocationsActions(){
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

    // Initializes the starting storyStops present in the beginning of the game
    public void initializeStoryStops(){
        Story.addStoryStop(LocationName.SAM_OFFICE);
    }

    // Initializes the starting Things present in the beginning of the game
    public void initializeThings(){
        new Thing("Cubicle Hole","*It looks like someone cut a square out of the cubicle with a box cutter. \n" +
                "Must be part of their move to an open concept they talked about a few years ago. \n" +
                "You see someone a few years older than you hunched over their desk, blankly staring at their screen while steadily typing. \n" +
                "Is that drool in the corner of their mouth?*");
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
            Iterator<Item> iterator = player.getInventory();
            while(iterator.hasNext()) {
                System.out.println(" - " + iterator.next().getName());
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
            Iterator<Objective> iterator = player.getObjectives();
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

    // Removes an item from your inventory and stows it in the provided Container
    // ToDo
    // Can cause issues if player accesses menu, inventory, or objectives while mid decision
    public void stowInContainer(Container container){
        int choice = 0, printedIndx = 0;

        do {
            clearConsole();
            printSeparator(30);
            System.out.print("INVENTORY");
            printSeparator(30);
            System.out.println();
            if (player.inventoryIsEmpty() && choice != -1) {
                System.out.println("No way! I'm never parting ways with my dust bunnies!");
                System.out.println("*You do not have any items to stow and your dust bunnies shall never leave your side*");
            } else {
                do {
                    System.out.println("*Choose an item to stow*");
                    // Printing all the items in the player's inventory
                    for (int i = 0; i < player.numInventoryItems(); i++) {
                        Item item = player.getItemFromInventory(i);
                        printedIndx = i + 1;
                        System.out.println("(" + printedIndx + ") " + item.getName());
                    }
                    System.out.println("(" + ++printedIndx + ") Leave");

                    choice = userChoice("->", printedIndx);
                }while(choice < 1);
                if(choice == printedIndx){ // If player chose to leave without stowing anything
                    return;
                }else{
                    Item item = player.getItemFromInventory(choice - 1);
                    player.removeItem(item);
                    container.addItem(item);

                    clearConsole();
                    printSeparator(30);
                    System.out.print("INVENTORY");
                    printSeparator(30);
                    System.out.println();
                    if(player.numInventoryItems() > 0){ // If player's inventory still has items
                        printedIndx = 1;
                        System.out.println("(" + printedIndx + ") Stow another item");
                    }else{
                        printedIndx = 0;
                    }
                }
            }
            System.out.println("(" + ++printedIndx + ") Take an item");
            System.out.println("(" + ++printedIndx + ") Leave");

            choice = userChoice("->", printedIndx);
        }while(choice < 1);

        if(printedIndx > 2){ //If player's inventory has items
            switch(choice){
                case 1:
                    stowInContainer(container);
                    break;
                case 2:
                    takeFromContainer(container);
                    break;
            }
        }else if(choice == 2){ // If container is empty
            takeFromContainer(container);
        }
    }

    // Take an item from a container and stow it
    // ToDo
    // Can cause issues if player accesses menu, inventory, or objectives while mid decision
    public void takeFromContainer(Container container){
        int choice = 0, printedIndx = 0;

        do {
            clearConsole();
            printSeparator(30);
            System.out.print(container.getName());
            printSeparator(30);
            System.out.println();
            if (container.numItems() < 1 && choice != -1) {
                System.out.println("Heck yeah! Another dust bunny for my collection!");
                System.out.println("*There are no items to take, but congrats on the dust bunny*");
            } else {
                do {
                    System.out.println("*Choose an item to take*");
                    // Printing all the items in this container
                    for (int i = 0; i < container.numItems(); i++) {
                        Item item = container.getItem(i);
                        printedIndx = i + 1;
                        System.out.println("(" + printedIndx + ") " + item.getName());
                    }
                    System.out.println("(" + ++printedIndx + ") Leave");

                    choice = userChoice("->", printedIndx);
                }while(choice < 1);
                if(choice == printedIndx){ // If player chose to leave without taking anything
                    return;
                }else{
                    Item item = container.getItem(choice - 1);
                    container.removeItem(item);
                    player.addToInventory(item);

                    clearConsole();
                    printSeparator(30);
                    System.out.print(container.getName());
                    printSeparator(30);
                    System.out.println();
                    if(container.numItems() > 0){ // If container still has items
                        printedIndx = 1;
                        System.out.println("(" + printedIndx + ") Take another item");
                    }else{
                        printedIndx = 0;
                    }
                }
            }
            System.out.println("(" + ++printedIndx + ") Stow an item from your inventory");
            System.out.println("(" + ++printedIndx + ") Leave");

            choice = userChoice("->", printedIndx);
        }while(choice < 1);

        if(printedIndx > 2){ //If container has items
            switch(choice){
                case 1:
                    takeFromContainer(container);
                    break;
                case 2:
                    stowInContainer(container);
                    break;
            }
        }else if(choice == 2){ // If container is empty
            stowInContainer(container);
        }
    }

    // Prints the contents of a Container and gives the player the option to take an item or put an item
    public void openContainer(Action action){
        int choice;
        Container container = Container.getContainer(action);

        clearConsole();
        printSeparator(30);
        System.out.print(container.getName());
        printSeparator(30);
        System.out.println();
        if(container.numItems() > 0) {
            for(int i = 0; i < container.numItems(); i++){
                String itemName = container.getItem(i).getName();
                System.out.println(" - " + itemName);
            }
        }else{
            System.out.println(" - A whole bunch of nothing");
        }

        do {
            System.out.println("Would you like to take an item or stow an item from your inventory?");
            System.out.println("*WARNING - Taking or stowing an item may have consequences in certain circumstances*");
            System.out.println("(1) Take an item and add it to your inventory");
            System.out.println("(2) Add an item from your inventory");
            System.out.println("(3) Best to leave things as is");
            choice = userChoice("->", 3);
        }while(choice < 1);

        switch(choice){
            case 1:
                takeFromContainer(container);
                break;
            case 2:
                stowInContainer(container);
                break;
        }

    }

    // Performs the given action by calling the associated method
    public void performAction(Action action){
        switch(action){
            case Action.CUBICLE_HOLE:
                readDescription("Cubicle Hole");
                break;
            case Action.DESK_DRAWER:
                openContainer(action);
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
            printSeparator(30);
            System.out.print(curLocation.getLocationName());
            printSeparator(30);
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
            printSeparator(30);
            System.out.print(curLocation.getLocationName());
            printSeparator(30);
            System.out.println();
            System.out.println("*Choose a location to move to or choose to stay where you are*");
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
            LocationName newLocation = curLocation.getNeighbor(choice - 1);
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


    // Starts a new game, includes intro and player name selection
    public void startGame(){
        boolean nameConfirmed = false;
        String name;

        clearConsole();
        printHeading("Welcome to OpenMAMMAFAANG inc.");
        delayMs(2000);
        anyInput();
        introPart1(); // Story up to user inputting name
        printSeparator(SEP_LENGTH);
        scan.nextLine(); // Clears input

        int choice;
        // Prompts user for player name until they confirm it
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
        introPart2();
    }

    // Runs through Day 1 of the game
    public void dayOne(){
        int choice;

        nextDay();
        // Initializing starting game objects
        initializeContainersItems();
        initializeLocationsActions();
        initializeStoryStops();
        initializeThings();

        dayOneIntro(); // Opening game notes and welcome message
        clearConsole();
        printDay();
        delayMs(1000);
        anyInput();

        // Exploration will stop and story will continue at first visit to Sam's Office
        addStoryStop(LocationName.SAM_OFFICE);
        explorationLoop(); // Exploration until player visits Sam's Office
        removeStoryStop(player.getLocation()); // Removes Sam's office

        clearConsole();
        printSeparator(30);
        System.out.print(player.getLocation());
        printSeparator(30);
        dayOneSamOffice();


        do{
            printSeparator(SEP_LENGTH);
            System.out.println();
            System.out.println("(1) I'm on it boss! You can count on me!");
            System.out.println("(2) Okay.");
            System.out.println("(3) Sorry... feed the...?");

            choice = userChoice("->",3);
            if(choice < 1){
                System.out.println("*Sam just asked you to feed the A.I. models*");
            }
        }while(choice < 1);

        if(choice == 1){
            printLine("Good lad! Just head downstairs. Deb will show you how it's done.");
            printLine("*Sam appreciated your eagerness to please and lack of annoying questions*");
            sam.improveOpinion(1);
        }else if(choice == 3){
            printLine("Just head downstairs. Deb will answer your questions. Now chop chop!");
        }
        player.addToObjectives(Objective.FEED_AI);
    }
}
