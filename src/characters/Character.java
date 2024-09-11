package characters;
import gameObjects.LocationName;

import java.lang.Math;

public class Character {
    private final String name;
    private LocationName location;

    // Constructor for Character class
    public Character(String name, LocationName location) {
        this.name = name;
        this.location = location;
    }

    // Gets the Character's name
    public String getName(){
        return this.name;
    }

    // Sets the current location of the character
    public void setLocation(LocationName location){
        this.location = location;
    }

    // Get the character's current location
    public LocationName getLocation(){
        return this.location;
    }

}
