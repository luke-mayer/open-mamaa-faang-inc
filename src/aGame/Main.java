package aGame;

import core.Game;

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.startupMenu();
        game.exitGame();
    }
}