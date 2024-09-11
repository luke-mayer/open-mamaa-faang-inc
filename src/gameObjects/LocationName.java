package gameObjects;

public enum LocationName {
    CUBICLE("Your cubicle"),
    SAM_OFFICE("Sam's office"),
    STAIRS("The stairs"),
    ELEVATOR("The elevator"),
    BASEMENT("The basement"), // Where the AI models reside
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
