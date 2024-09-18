package gameObjects;

public enum Action {
    END_DAY("Finish up your work and end the day"),
    CUBICLE_HOLE("Look through the small opening"),
    DESK_DRAWER("Look in the desk drawer"), // The Container in the player's cubicle
    COMPUTER_LOGIN("Login to the computer"),
    LOOK_SAFE("Look in the safe"), // Action used when returning SSD and floppy to server room safe

    FEED_AI("Feed Clyde and Toby"),
    FIND_THE_BUG("Find the bug in your software"),
    ;

    private final String actionStr;

    Action(String actionStr){
        this.actionStr = actionStr;
    }

    @Override
    public String toString(){
        return actionStr;
    }
}
