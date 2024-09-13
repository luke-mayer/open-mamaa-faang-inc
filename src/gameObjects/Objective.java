package gameObjects;

public enum Objective {
    FEED_AI("Feed the A.I. models in the Server Room (located in the Basement)", Action.FEED_AI),
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
