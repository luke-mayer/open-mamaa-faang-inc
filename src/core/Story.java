package core;

import gameObjects.LocationName;

import java.util.HashSet;

import static core.GameUtility.*;

public class Story {

    // Locations that will trigger a story beat or npc interaction when the player visits them
    private final static HashSet<LocationName> storyStops = new HashSet<>();

    // Adds a location to storyStops
    public static void addStoryStop(LocationName storyStop){
        storyStops.add(storyStop);
    }

    // Removes the specified location from storyStops
    public static int removeStoryStop(LocationName storyStop){
        if(!storyStops.contains(storyStop)) {
            return -1;
        }
        storyStops.remove(storyStop);
        return 1;
    }

    // Returns true if storyStop contains location else false
    public static boolean containsStoryStop(LocationName storyStop){
        return storyStops.contains(storyStop);
    }

    // Removes all elements from the storyStops ArrayList
    public static void clearStoryStops(){
        storyStops.clear();
    }

    // Introduction to the game, up until the player chooses their name
    public static void introPart1(){
        printLine("Sam: Welcome to OpenMAMMAFAANG inc. Congratulations on landing the job!");
        printLine("     How are you feeling on your first day?");
        printSeparator(SEP_LENGTH);
        System.out.println();
        System.out.println("(1) I'm feeling good. Thanks for asking!");
        System.out.println("(2) Fantastic! I'm super excited to get to work!");
        System.out.println("(3) Well, now that you ask, I've actually been better.");
        System.out.println("    This job wasn't my first choice.");
        System.out.print("-> ");
        delayMs(2000);
        System.out.println();
        printSeparator(SEP_LENGTH);
        System.out.println();
        System.out.println("*He interrupts just as you're about to respond*");
        System.out.println();
        printLine("Sam: Great! Now just for paperwork reasons, remind me how to spell your name again...?");
    }

    // Second half of intro up until the player first enters exploration mode
    public static void introPart2(){
        System.out.println();
        printLine("Sam: Perfect! I always double check. Never know if there’s a silent *chzk* or ");
        printLine("     *phlegm* in there somewhere.");
        printLine("Sam: Now why don’t you take a moment to get acquainted with your new home away from home.");
        printLine("     Just swing by my office when you are ready for your first assignment.");
        System.out.println();
        printLine("*You watch Sam leave and head into his office*");
        delayMs(2000);
        anyInput();
    }

    // Day 1 - intro
    public static void dayOneIntro(){
        clearConsole();
        System.out.println("Hi and welcome to OpenMammaFAANG Inc., a game written and developed by Luke Mayer.");
        System.out.println("OpenMammaFAANG is still in very early development with much of the story and \n" +
                "some mechanics and minigames still to be added. However, I hope you enjoy this early demo.");
        System.out.println();
        anyInput();
        clearConsole();
        printSeparator(SEP_LENGTH);
        System.out.println();
        System.out.println("Some quick notes before you start your first day on the job:");
        System.out.println(" - NPCs have an opinion of you. Your job performance, as well as certain \n" +
                "   actions or decisions, can improve or harm their opinion of you. Different NPCs having \n" +
                "   a more positive or negative opinion of you can dictate what choices are available \n" +
                "   and ultimately impact the story in both small and drastic ways. ");
        System.out.println(" - Your decisions have consequences. Make them carefully.");
        System.out.println(" - At anytime when prompted to choose an option, you may enter: \n" +
                "    - \"I\" to view your inventory \n" +
                "    - \"O\" to access your current objectives \n" +
                "    - \"M\" to access the pause menu.");
        delayMs(2000);
        printLine("Good luck junior developer - o7");
        anyInput();
    }

    // Day 1 - Sam's Office storyStop
    public static void dayOnePart1(){
        printLine("*The door is open so you knock on the door frame. \n" +
                " Sam looks up at you from his desk*\n");
        printLine("Sam: Oh hey, feeling settled in?");
        printSeparator(SEP_LENGTH);
        System.out.println();
        System.out.println("(1) Yes sir, feeling right at home!");
        System.out.println("(2) Do you want to share your office? We can be productivity pals <3.");
        System.out.println("(3) Could we do something about the mayo smell in my cubicle?");
        System.out.print("-> ");
        delayMs(2000);
        System.out.println();
        printSeparator(SEP_LENGTH);
        System.out.println();
        printLine("*He interrupts just as you're about to respond*");
        System.out.println();
        printLine("Sam: Fantastic! Now for your first assignment, super simple, super easy.");
        printLine("Sam: Just head downstairs and feed the A.I. models.");
    }

    // Day 1 - Meeting Deb and learning the ropes
    public static void dayOnePart2(){
        clearConsole();
        System.out.println("*You leave Sam's office and begin heading towards the elevator. You pass by \n" +
                            " row upon row of lifeless cubicles when you notice a figure poke their head \n" +
                            " out from one of the colorless cells. A woman a few years older than you \n" +
                            " stands up just in time to match pace and heading with you as you journey towards \n" +
                            " the elevator.You go to make a formal greeting, but she holds up her hand to stop you*\n");
        printLine("Woman: Thank you and its nice to meet you too. No fault of your own, but I don't have \n" +
                  "       time for this so let's cut the pleasantries and get this over with... That prick \n" +
                  "       Same, wasting my time with his messes.\n");
        System.out.println("*You hear her mutter that last bit under her breath as the two of you reach the elevator. \n" +
                " This must be the \"Deb\" Sam was referring to... Wonder what mess she's talking about*\n");
        delayMs(1000);
        anyInput();

        clearConsole();
        System.out.println("*You both enter the elevator and Deb presses the button for the basement*\n");
        printLine("Deb: Now, the A.I. models are stored in the server room. There are two of them and they \n" +
                  "     each have their own terminal. Clyde is the fancy one hooked up to the main server, \n" +
                  "     Toby is the sad thing in the back of the room. As for what you're going to feed them, \n" +
                  "     it gets bought in fresh overnight from an outside company and left in the safe by  \n" +
                  "     the front. Here's the passcode... It gets changed on occasion. I'll give you the new \n" +
                  "     one when you need it.\n");
        System.out.println("*She hands you a note with the passcode: 065-073. You tuck it in your pocket as \n" +
                " Deb leads you out of the elevator, through a hallway, and into the server room*\n");
        printLine("Deb: Give Clyde the big one and Toby the small one. Clyde is a bit of a prick, but \n" +
                  "     Toby is a sweet old thing. Now remember, after they have enjoyed their meal, \n" +
                  "     be a good boy and put the dishes back in the safe! I don't want to have to come \n" +
                  "     and hunt them down from you later. Alright, that should about cover it.\n");
        System.out.println("*She swiftly leaves before you can ask any of your numerous questions*\n");
        delayMs(1000);
        anyInput();
    }

    // Day 1 -
    public static void dayOnePart3(){
        System.out.println("*You really don't know what you expected to find, but for some reason it \n" +
                           " wasn't an SSD and... is that a floppy disk? ... You take the two storage \n" +
                           " devices and scan the room. There is a state-of-the-art computer interface \n" +
                           " hooked up directly to the company's main server. You approach it, but a \n" +
                           " voice emanates from the interface before you even begin to reach your hands \n" +
                           " for the keyboard...*\n");
        delayMs(1000);
        printLine("Computer: Ah... a new one. Are you as incompetent as the rest?");
        printLine("          No need to respond, the answer is obvious... Well, get on with it.\n");
        delayMs(1000);
        System.out.println("*Wow, Clyde really is a prick*\n");
        delayMs(1000);
        printLine("Clyde: Quit looking so confused. The training data you incompetent ape!");
        printLine("       Just plug in the drive, I can handle the rest so don't bother touching");
        printLine("       my inputs. You'll only get in my way.\n");
        delayMs(1000);
        System.out.println("*You proceed to insert the SSD into a port on the terminal. Operations begin \n" +
                " flashing across the screen at incomprehensible speeds as you assume Clyde starts \n" +
                " \"eating\"... *\n");
        delayMs(1000);
        anyInput();
        System.out.println("You look to the back of the room and see a computer against the wall  the size of a \n" +
                " standing piano. The size doesn't fool you though. The ancient appearance of what may \n" +
                " once have been a super computer betrays the fact that this device probably has many \n" +
                " thousands of magnitudes less computing power than Clyde. As you approach, you notice that \n" +
                " someone has taped a set of step-by-step instructions next to the keyboard that walk you \n" +
                " through the process of powering on the computer and inputting the training data from the \n" +
                " floppy disk. There is no voice this time, but green text slowly begins to scroll across \n " +
                " the crt display...*\n");
        delayMs(2000);
        anyInput();
    }
}
