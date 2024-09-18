package miniGames.catchTheCopilot;

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Phaser;

public class CatchTheCopilot extends JFrame {
    private static Phaser phaser;

    public CatchTheCopilot() {
        phaser = new Phaser(1);
        add(new Model());
        startGame();
        phaser.arriveAndAwaitAdvance();
    }

    // Returns true if copilot is caught, false otherwise
    public static boolean getCaughtTheCopilot(){
        return Model.getCaughtCopilot();
    }

    // Makes it so that main() resumes on Frame close
    private void addDeregister(){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e){
                phaser.arriveAndDeregister();
                dispose();
            }
        });
    }

    public void startGame(){
        setTitle("Catch The Copilot");
        setSize(800,830);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        phaser.register();
        addDeregister();
    }

    public static void main(String[] args){
        CatchTheCopilot catchTheCopilot = new CatchTheCopilot();
        System.out.println("Did we catch copilot?: " + Model.getCaughtCopilot());
    }
}
