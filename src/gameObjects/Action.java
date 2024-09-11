package gameObjects;

public enum Action {
    CUBICLE_HOLE("Look through the small opening"),
    DESK_DRAWER("Look in the desk drawer"),
    COMPUTER_LOGIN("Login to the computer"),
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
