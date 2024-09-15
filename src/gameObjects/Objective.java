package gameObjects;

public enum Objective {
    FEED_AI("Feed the A.I. models in the Server Room (located in the Basement)",
            Action.FEED_AI),
    FIND_THE_BUG("Find the BUG running amuck in the company codebase (you can do this " +
            "from your cubicle", Action.FIND_THE_BUG),
    ;

    private final String objectiveStr;
    private final Action action;

    Objective(String objectiveStr, Action action){
        this.objectiveStr = objectiveStr;
        this.action = action;
    }

    // Basic getter for the associated action
    public Action getAction(){
        return this.action;
    }

    @Override
    public String toString(){
        return objectiveStr;
    }
}
