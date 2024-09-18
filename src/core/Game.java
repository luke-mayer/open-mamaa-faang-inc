package core;

import java.io.Serializable;
import java.util.Iterator;

import characters.*;
import gameObjects.*;
import miniGames.FindTheBug;
import miniGames.catchTheCopilot.CatchTheCopilot;

import static core.Story.*;
import static core.GameUtility.*;

public class Game implements Serializable {

    private int currentDay;
    private String safeCode;
    private boolean endDay;

    private Player player; // Change to private after development
    public characters.NPC sam, deb;

    // Constructs a new Game
    public Game(){
        currentDay = 0;
        endDay = false;
        sam = new NPC("Sam Altmuckerburg", LocationName.SAM_OFFICE,5);
        deb = new NPC("Deborah <TBD>", LocationName.ELEVATOR, 3);
    }

    // Constructor for testing
    public Game(String playerName, LocationName playerLocation){
        currentDay = 0;
        endDay = false;
        sam = new NPC("Sam Altmuckerburg", LocationName.SAM_OFFICE,5);
        deb = new NPC("Deborah <TBD>", LocationName.ELEVATOR, 3);
        player = new Player(playerName,playerLocation);

        // Initializing starting game objects
        initializeContainersItems();
        initializeLocationsActions();
        initializeStoryStops();
        initializeThings();

        safeCode = "065-073";
    }

    // Starts the game, choose between playing dayOne or one of the mini-games
    public void startupMenu(){
        int choice;

        clearConsole();
        printHeading("OpenMammaFAANG inc.");
        System.out.println();
        System.out.println("*This game is currently in development. Demo the beginning of the game \n" +
                " or try out some of the mini-games that are integrated into the main game*\n");

        do {
            System.out.println("(1) Experience the first day working at OpenMammaFAANG inc.");
            System.out.println("(2) Demo the Find The Bug mini-game");
            System.out.println("(3) Demo the Catch The Copilot mini-game");
            System.out.println("(4) EXIT");
            choice = userChoice("->", 4);

            switch(choice){
                case 1:
                    dayOne();
                    break;
                case 2:
                    demoFindTheBug();
                    break;
                case 3:
                    demoCatchTheCopilot();
                    break;
            }
        }while(choice != 4 && choice != 1);
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
                " There is a tiny desk with a computer and a single drawer.*");
        location.addAction(Action.CUBICLE_HOLE);
        location.addAction(Action.DESK_DRAWER);
        location.addAction(Action.COMPUTER_LOGIN);
        location.addNeighbor(LocationName.SAM_OFFICE);

        location = new Location(LocationName.SAM_OFFICE,"*A relatively bland office. More roomy than your cubicle though.*");
        location.addNeighbor(LocationName.CUBICLE);

        location = new Location(LocationName.BASEMENT,"*A hallway that connects to the Server room...\n" +
                " They should really do something about this flickering light.*");
    }

    // Initializes the starting storyStops present in the beginning of the game
    public void initializeStoryStops(){
        Story.addStoryStop(LocationName.SAM_OFFICE);
    }

    // Initializes the starting Things present in the beginning of the game
    public void initializeThings(){
        new Thing("Cubicle Hole","*It looks like someone cut a square out of the cubicle with a box cutter. \n" +
                " Must be part of their move to an open concept they talked about a few years ago. \n" +
                " You see someone a few years older than you hunched over their desk, blankly staring \n" +
                " at their screen while steadily typing. Is that drool in the corner of their mouth?*");
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

    // Prints a small heading with the current location
    public void printLocation(){
        printSeparator(30);
        System.out.print(player.getLocation());
        printSeparator(30);
        System.out.println();
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
        System.exit(0);
        // ToDo
        // Maybe exit(0);
    }

    // Reads the description that corresponds with the provided Thing
    public void readDescription(String name){
        Thing thing = Thing.getThing(name);
        System.out.println(thing.getDescription());
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
            System.out.println("(2) Stow an item from your inventory");
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
            case Action.LOOK_SAFE:
                openContainer(action);
                break;
            case Action.COMPUTER_LOGIN:
                // loginComputer();
                System.out.println("Temp COMPUTER_LOGIN action");
                break;
            case Action.END_DAY:
                endDay = true;
        }
    }

    // Displays actions the player can take
    public void chooseAction(){
        Location curLocation = Location.getLocation(player.getLocation()); // Getting Location object of player's location
        int choice, printedIndx;

        do {
            printLocation();
            System.out.println(Location.getLocation(player.getLocation()).getDescription());
            printSeparator(SEP_LENGTH + player.getLocation().toString().length());
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
            printLocation();
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
        while(!containsStoryStop(player.getLocation()) && !endDay){
            chooseAction(); // Allows player to choose an available action in the current location
            moveLocations(); // Allows player to move to a neighboring location
        }
    }

    // Checks if user inputs correct safeCode. Returns "true" is correct, "false" is incorrect,
    // and "menu" if user accessed inventory, objective, or the pause menu
    public String openSafeInput(String prompt){
        System.out.print(prompt + " ");
        String userIn = scan.nextLine().strip().toLowerCase();
        switch (userIn) {
            case "m", "menu", "pause":
                pauseMenu();
                return "menu";
            case "i", "inventory":
                printInventory();
                return "menu";
            case "o", "objectives":
                printObjectives();
                return "menu";
            default:
                String alternate = safeCode.replace("-","");
                if(userIn.equals(safeCode) || userIn.equals(alternate)){
                    return "true";
                }else{
                    return "false";
                }
        }
    }

    // Initiates an attempt at opening the safe in the Server Room
    public void openSafe(){
        String userSafeCode;
        do {
            clearConsole();
            printLocation();
            System.out.println("\n*Input the passcode please*");
            userSafeCode = openSafeInput("->");
            if(userSafeCode.equals("false")){
                System.out.println("*INCORRECT*");
                System.out.println("*Hint: Check your inventory*");
                anyInput();
            }
        }while(!userSafeCode.equals("true"));
        System.out.println("*CORRECT*\n");
    }

    // Starts a new game, includes intro and player name selection
    public void startGame(){
        boolean nameConfirmed = false;
        String name;

        printDay();
        delayMs(1000);
        printHeading("Welcome to OpenMammaFAANG inc.");
        delayMs(1000);
        anyInput();
        introPart1(); // Story up to user inputting name
        printSeparator(SEP_LENGTH);
        //scan.nextLine(); // Clears input

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
        boolean fedAI = false, completedFindBug = false, foundBug = false;
        Item clydeSSD, tobyFloppy;

        nextDay();

        // Initializing starting game objects
        initializeContainersItems();
        initializeLocationsActions();
        initializeStoryStops();
        initializeThings();

        dayOneIntro(); // Opening game notes and welcome message
        clearConsole();
        startGame();

        // Exploration will stop and story will continue at first visit to Sam's Office
        addStoryStop(LocationName.SAM_OFFICE);
        clearConsole();
        explorationLoop(); // Exploration until player visits Sam's Office
        removeStoryStop(player.getLocation()); // Removes Sam's office

        // Day 1 Part 1
        clearConsole();
        printLocation();
        dayOnePart1();

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
            printLine("Sam: Good lad! Just head downstairs. Deb will show you how it's done.\n");
            System.out.println("*Sam appreciated your eagerness to please and lack of annoying questions*");
            sam.improveOpinion(1);
        }else if(choice == 3){
            printLine("Sam: Just head downstairs. Deb will answer your questions. Now chop chop!");
        }
        delayMs(1000);
        anyInput();
        player.addToObjectives(Objective.FEED_AI);

        // Day 1 Part 2
        dayOnePart2();
        safeCode = "065-073"; // Setting the safe code
        player.addToInventory(new Item("A note with the numbers \"065-073\"",
                "A note with the numbers \"065-073\""));
        player.setLocation(LocationName.SERVER_ROOM);

        // Day 1 Part 3
        clearConsole();
        printLocation();
        System.out.println("*You notice the safe Deb referred to built into the wall just inside the \n" +
                " doorway. You approach it and interact with the display on the front*\n");
        anyInput();

        openSafe(); // Opens the safe

        clydeSSD = new Item("Clyde's SSD", "The SSD that holds training " +
                "data for Clyde");
        tobyFloppy = new Item("Toby's floppy disk", "The floppy disk that " +
                "holds Toby's training data");
        player.addToInventory(clydeSSD);
        player.addToInventory(tobyFloppy);
        delayMs(1000);
        anyInput();

        clearConsole();
        printLocation();
        dayOnePart3();

        do {
            clearConsole();
            printLocation();
            printLine("Computer: Hi! I'm Toby. It's so nice to meet you!");
            printSeparator(SEP_LENGTH);
            System.out.println();
            System.out.println("(1) Hi Toby! I'm " + player.getName() + " it's nice to meet you too!");
            System.out.println("(2) Shut off the computer and leave");
            choice = userChoice("->", 2);
        }while(choice < 1);

        // If you talk to Toby
        if(choice == 1){
           // talkWithTobyDayOne();
            System.out.println("***CONVERSATION NOT YET IMPLEMENTED IN DEMO***");
            anyInput();
        }

        System.out.println("\n*You shut off the computer and think that you should probably head back\n" +
                " to your cubicle to finish up work for the day*");
        anyInput();

        player.removeObjective(Objective.FEED_AI);
        fedAI = true;

        // Updated accessible locations
        updateLocationsDayOne();
        addStoryStop(LocationName.CUBICLE);
        clearConsole();
        explorationLoop();
        removeStoryStop(LocationName.CUBICLE);

        // If player forgets to put back the SSD and Floppy, Deb is upset
        if(player.inventoryContainsItem(clydeSSD) || player.inventoryContainsItem(tobyFloppy)){
            clearConsole();
            printLocation();
            System.out.println("*You start to wrap up your work for the day when you notice Deb storm \n" +
                    " across the office towards your cubicle*");
            delayMs(1000);
            printLine("Deb: Now listen here! I realize it's your first day on the job, but that doesn't excuse \n" +
                      "     not following basic instructions. I can see the 8 inch floppy disk in your back \n" +
                      "     pocket. You didn't put the floppy disk and ssd back in the safe. If I didn't notice \n" +
                      "     it would have been my ass that got blamed when they couldn't find them tomorrow. Now \n" +
                      "     give them here!\n");
            System.out.println("*You hand her the drives and she storms off. You get the sense that you \n" +
                    " made a poor first impression on Deb*");
            deb.worsenOpinion(2);
            printSeparator(SEP_LENGTH);
            System.out.println();
            player.removeItem(clydeSSD);
            player.removeItem(tobyFloppy);
            anyInput();
        }

        clearConsole();
        printLocation();
        System.out.println("*You sit down at your computer when Sam pokes his head into your cubicle*\n");
        delayMs(1000);
        printLine("Sam: Hey there! Quick task for ya before you head home. You see, theres a bug running \n" +
                "     rampant in our code base right now. Can you see if you can track it down ASAP? \n" +
                "     You can head home for the day after that. The task is on your dashboard.\n");
        System.out.println("*You pull up your dashboard, take a look at the error logs, and start \n" +
                " searching the code base for this bug*\n");
        delayMs(1000);
        anyInput();

        // Find The Bug mini-game
        FindTheBug findTheBug = new FindTheBug(16,3);
        if(FindTheBug.getFoundBug()){
            System.out.println("*Sam will be happy that the bug was found*");
            sam.improveOpinion(1);
        }else{
            System.out.println("*Sam won't be happy that we weren't able to find the bug*");
            sam.worsenOpinion(2);
        }
        System.out.println("*You pack up and head home for the day*");
        anyInput();
        clearConsole();
        printHeading("THANK YOU");
        System.out.println("Thank you for playing this demo of OpenMammaFAANG inc.!");
        anyInput();
        exitGame();
    }

    // If player chooses to talk with Toby more on day 1
    public static void talkWithTobyDayOne(){
        // ToDo
    }

    // Updates accessible locations near the end of day 1
    public static void updateLocationsDayOne(){
        Location location;

        location = new Location(LocationName.SERVER_ROOM, "The heart of the company and " +
                "the home of Clyde and Toby");
        location.addNeighbor(LocationName.BASEMENT);
        location.addAction(Action.LOOK_SAFE);

        new Container("Server Room Safe","A safe in the server room used to securely " +
                "store A.I. training data", Action.LOOK_SAFE);

        location = Location.getLocation(LocationName.BASEMENT);
        location.addNeighbor(LocationName.SERVER_ROOM);
        location.addNeighbor(LocationName.ELEVATOR);

        location = new Location(LocationName.ELEVATOR, "Just an elevator");
        location.addNeighbor(LocationName.BASEMENT);
        location.addNeighbor(LocationName.CUBICLE);

        location = Location.getLocation(LocationName.CUBICLE);
        location.addNeighbor(LocationName.ELEVATOR);
        location.addAction(Action.END_DAY);
    }

    // Demo FindTheBug mini-game
    public static void demoFindTheBug(){
        clearConsole();
        printHeading("FIND THE BUG");
        System.out.println("*This is a demo of the mini-game Find The Bug. Your objective is to locate \n" +
                           " the bug in the array before it can do serious damage. Good luck!*");
        delayMs(1000);
        anyInput();
        new FindTheBug(16,3);
        if(FindTheBug.getFoundBug()){
            System.out.println("*Congratulations! You found the bug and beat the mini-game! Did you get lucky? \n" +
                    " Or did you figure out the trick?*\n");
            delayMs(1000);
            anyInput();
        }else{
            System.out.println("*Good try, think about if there is a better strategy you could try*");
            delayMs(1000);
            anyInput();
        }
    }

    // Demo CatchTheCopilot mini-game
    public static void demoCatchTheCopilot(){
        clearConsole();
        printHeading("CATCH THE COPILOT");
        System.out.println("*This is a demo of the mini-game Catch The Copilot. Your objective is to catch the \n" +
                " Copilot that has escaped from its home drive. Catch it so it can keep producing \n" +
                " broken code for us! Good luck!*");
        delayMs(1000);
        anyInput();
        new CatchTheCopilot();
        if(CatchTheCopilot.getCaughtTheCopilot()){
            System.out.println("*Congratulations! You caught the escaped Copilot! Was it difficult? Or too easy \n");
            delayMs(1000);
            anyInput();
        }else{
            System.out.println("*Good try, think about if there is a better strategy you could try*");
            delayMs(1000);
            anyInput();
        }
    }
}
