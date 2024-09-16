package testing;

import core.Game;
import characters.*;
import gameObjects.*;
import miniGames.FindTheBug;

public class TestMethods {

    /*
    // Tests the player inventory
    public static void testInventory(){
        Game game = new Game();
        game.player = new Player("testPlayer", LocationName.CUBICLE);
        game.player.addToInventory("item 1");
        game.player.addToInventory("item 2");
        game.player.addToInventory("item 3");
        game.player.addToInventory("item 4");
        game.player.addToInventory("item 5");
        game.player.addToInventory("item 6");

        int choice = -1;

        do{
            System.out.println("(1) Fake prompt 1");
            System.out.println("(2) Fake prompt 2");
            choice = game.userChoice("->", 2);
        }while(choice < 1);

        String testNotExist = game.player.removeItem("Does Not Exist") == -1 ? "Does not exist worked" : "Does not exist did not work";
        System.out.println(testNotExist);
        game.player.removeItem("item 3");
        game.printInventory();
        game.exitGame();
    }
     */

    /*
    // Tests the player objectives
    public static void testObjectives(){
        Game game = new Game();
        game.player = new Player("testPlayer", LocationName.CUBICLE);
        game.player.addToObjectives("obj 1");
        game.player.addToObjectives("obj 2");
        game.player.addToObjectives("obj 3");
        game.player.addToObjectives("obj 4");
        game.player.addToObjectives("obj 5");
        game.player.addToObjectives("obj 6");

        int choice = -1;

        do{
            System.out.println("(1) Fake prompt 1");
            System.out.println("(2) Fake prompt 2");
            choice = game.userChoice("->", 2);
        }while(choice < 1);

        String testNotExist = game.player.removeObjective("Does Not Exist") == -1 ? "Does not exist worked" : "Does not exist did not work";
        System.out.println(testNotExist);
        game.player.removeObjective("obj 3");
        game.printObjectives();
        game.exitGame();
    }
     */

    // Tests exploration loop
    public static void testExploration(){
        Game game = new Game("John TestName", LocationName.CUBICLE);

        game.explorationLoop();
        System.out.println("Reached a story stop");
    }

    // Tests the FindTheBug minigame
    public static void testFindTheBug(){
        System.out.println("Before FindTheBug");
        new FindTheBug(16,3);
        System.out.println("After FindTheBug made");
        System.out.println("After FindTheBug");
        System.out.println("Did I find the bug?: " + FindTheBug.getFoundBug());
    }

    // Tests the open safe mechanic in the game
    public static void testOpenSafe(){
        Game game = new Game("John TestName", LocationName.SERVER_ROOM);
        game.openSafe();
    }

    // Tests pulling in a javaFX project
    public static void testJavaFX(){

    }

    public static void main(String[] args) {
        testOpenSafe();
    }
}
