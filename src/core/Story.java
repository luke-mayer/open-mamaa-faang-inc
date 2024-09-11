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
        printLine("Sam: How are you feeling on your first day?");
        printSeparator(SEP_LENGTH);
        System.out.println();
        System.out.println("(1) I'm feeling good. Thanks for asking!");
        System.out.println("(2) Fantastic! I'm super excited to get to work!");
        System.out.println("(3) Well, now that you ask, I've actually been better.");
        System.out.println("    This job wasn't my first choice.");
        System.out.println();
        System.out.print("-> ");
        delayMs(2000);
        System.out.println();
        printSeparator(SEP_LENGTH);
        System.out.println();
        printLine("*He interrupts just as you're about to respond*");
        System.out.println();
        printLine("Sam: Great! Now just for paperwork reasons, remind me how to spell your name again?");
    }

    // Second half of intro up until the player can choose to explore their cubicle or start working
    public static void introPart2(){
        System.out.println();
        printLine("Sam: Perfect! I always double check. Never know if there’s a silent *chzk* or ");
        printLine("     *phlegm* in there somewhere.");
        printLine("Sam: Now why don’t you take a moment to get acquainted with your new home away from home.");
        printLine("Sam: Just swing by my office when you are ready for your first assignment.");
        System.out.println();
        printLine("*You watch Sam leave and head into his office. What would you like to do?*");
        System.out.println("()");
        System.out.println("()");
    }
}
