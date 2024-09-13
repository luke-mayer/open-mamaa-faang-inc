package gameObjects;

public enum LocationName {
    CUBICLE("Your Cubicle"),
    SAM_OFFICE("Sam's Office"),
    STAIRS("The Stairs"),
    ELEVATOR("The Elevator"),
    BASEMENT("The Basement"), // Where the AI models reside
    ;

    private final String locationStr;

    LocationName(String actionStr){
        this.locationStr = actionStr;
    }

    @Override
    public String toString(){
        return locationStr;
    }
}
