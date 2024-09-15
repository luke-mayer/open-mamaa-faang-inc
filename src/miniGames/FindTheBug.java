package miniGames;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.ArrayList;
import java.util.Comparator;

public class FindTheBug{
    private static Phaser phaser;

    private final int arraySize;
    private int chancesRemaining;
    private int bugValue;
    private int numLives;
    private static boolean foundBug = false;
    private String resultStr,instructionsStr;
    private ArrayList<Integer> randArray;

    private JFrame frame;
    private JLabel chancesRemainingLabel;
    private JLabel resultLabel;

    private class ArrayButton extends JButton implements ActionListener{
        private final int value;

        public ArrayButton(int value){
            super();
            this.value = value;
            this.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(chancesRemaining > 0) {
                chancesRemaining--;
                chancesRemainingLabel.setText("Chances Remaining: " + chancesRemaining);


                if (value == bugValue) {
                    setText("BUG");
                    resultLabel.setText("*SUCCESS* - You located the BUG and fixed it. Close window to continue.");
                    foundBug = true;
                } else if (chancesRemaining < 1) {
                    setText(String.valueOf(value));
                    numLives--;
                    if (numLives > 0) {
                        instructionsStr = "The BUG moved to this new Array! Only " + numLives + " arrays left before it causes serious damage!";
                        resultStr = "*ERROR* - Failed to find the BUG in previous array!";
                        frame.setVisible(false);
                        frame.dispose();
                        generateFrame();
                    } else {
                        resultLabel.setText("*MAJOR ERROR* - Serious damage detected! Close window immediately!");
                    }
                } else {
                    setText(String.valueOf(value));
                }
            }
        }
    }

    public FindTheBug(int arraySize, int numLives){
        phaser = new Phaser(1);
        this.arraySize = arraySize;
        this.numLives = numLives;
        resultStr = "*WARNING* - BUG present in this array!";
        instructionsStr = "Find the bug in the array. You only have so many guesses before it escapes " +
                "to another array and you only have " + numLives + " arrays before it causes serious damage.";
        generateFrame();
        phaser.arriveAndAwaitAdvance();
    }

    // Generates the gui used to play the minigame
    private void generateFrame(){
        frame = new JFrame();

        JPanel bottomPanel = new JPanel();
        resultLabel = new JLabel(resultStr);
        JLabel bugLabel = new JLabel("The bug has a value of: 9999");
        JLabel spacerLabel = new JLabel("");
        JLabel spacerBottomLabel = new JLabel("");
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        bottomPanel.setLayout(new GridLayout(3, 0));
        resultLabel.setHorizontalAlignment(JLabel.CENTER);
        bugLabel.setHorizontalAlignment(JLabel.CENTER);
        bottomPanel.add(bugLabel);
        bottomPanel.add(spacerBottomLabel);
        bottomPanel.add(resultLabel);

        chancesRemaining = getNumChances();
        randArray = getRandArray();
        bugValue = getBugValue();

        JPanel arrayPanel = new JPanel();
        JPanel topPanel = new JPanel();

        JLabel instructionsLabel = new JLabel(instructionsStr);
        chancesRemainingLabel = new JLabel("Chances Remaining: " + chancesRemaining);
        bugLabel.setText("The bug has a value of: " + bugValue);

        instructionsLabel.setHorizontalAlignment(JLabel.CENTER);
        chancesRemainingLabel.setHorizontalAlignment(JLabel.CENTER);

        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        arrayPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        topPanel.setLayout(new GridLayout(3, 0));
        arrayPanel.setLayout(new GridLayout(2, 0));

        topPanel.add(instructionsLabel);
        topPanel.add(spacerLabel);
        topPanel.add(chancesRemainingLabel);

        // Generating index labels for the array buttons
        for(int i = 0; i < arraySize; i++){
            JLabel label = new JLabel(String.valueOf(i+1));
            label.setHorizontalAlignment(JLabel.CENTER);
            arrayPanel.add(label);
        }

        // Generating the array buttons
        for(int i = 0; i < arraySize; i++){
            ArrayButton button = new ArrayButton(randArray.get(i));
            button.setPreferredSize(new Dimension(70,6));
            arrayPanel.add(button);
        }

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(arrayPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Find The Bug");
        frame.pack();
        frame.setVisible(true);

        phaser.register();
        addCountDown();
    }

    // Makes it so that main() resumes on Frame close
    private void addCountDown(){
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e){
                phaser.arriveAndDeregister();
            }
        });
    }

    // Generates a sorted ArrayList of the indicated size filled with unique, random integers
    private ArrayList<Integer> getRandArray(){
        Random random = new Random();
        ArrayList<Integer> randArray = new ArrayList<>();
        for(int i = 0; i < arraySize; i++){
            int randInt;
            do{
                randInt = random.nextInt(9999) + 1; // Random int in range (1,9999)
            }while(randArray.contains(randInt)); // Assures no duplicates
            randArray.add(randInt);
        }
        randArray.sort(Comparator.naturalOrder());
        return randArray;
    }

    // Returns log base 2 rounded up
    private int getNumChances(){
        return (int)Math.ceil(Math.log(arraySize) / Math.log(2));
    }

    // Returns a random int from randArray
    private int getBugValue(){
        Random random = new Random();
        int randIndx = random.nextInt(arraySize);
        return randArray.get(randIndx);
    }

    // Returns true if player successfully found the bug
    public static boolean getFoundBug(){
        return foundBug;
    }
}
