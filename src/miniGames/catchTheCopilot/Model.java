package miniGames.catchTheCopilot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Model extends JPanel implements ActionListener {
    private static boolean caughtCopilot = false;

    private Dimension d;
    private final Font smallFont = new Font("Arial", Font.BOLD, 18);
    private boolean inGame = false;
    private boolean dying = false;
    private boolean caught = false;

    private static String start = "Catch copilot and watch out for his malware - Press SPACE to start";;

    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 32;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final int MAX_MALWARE = 16;
    private final int PLAYER_SPEED = 6;
    private final int COPILOT_SPEED = 6;

    private int N_MALWARE = 12;
    private int lives, score;
    private int[] dx, dy, c_dx,c_dy;
    private int[] malware_x, malware_y, malware_dx, malware_dy, malwareSpeed;

    private Image heart, malware, copilot;
    private Image up, down, left, right;

    private int player_x, player_y, playerd_x, playerd_y;
    private int req_dx, req_dy;

    private int copilot_x, copilot_y, copilot_dx, copilot_dy;

    // 0 = obstruction, 1 = left_b, 2 = top_b, 4 = right_b, 8 = bottom_b, 16 = free_space
    private final int[] levelData = {
            19 ,18 ,18 ,18 ,18 ,18 ,18 ,22 ,0  ,19 ,22 ,0  ,19 ,18 ,18 ,18 ,22 ,0  ,19 ,18 ,18 ,18 ,18 ,18 ,22 ,0  ,19 ,18 ,18 ,18 ,18 ,22,
            17 ,16 ,16 ,16 ,16 ,16 ,16 ,20 ,0  ,17 ,20 ,0  ,17 ,16 ,24 ,16 ,20 ,0  ,25 ,24 ,16 ,16 ,24 ,16 ,20 ,0  ,17 ,16 ,24 ,16 ,16 ,20,
            17 ,16 ,16 ,16 ,16 ,16 ,16 ,20 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,0  ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,16 ,20,
            17 ,16 ,16 ,16 ,24 ,16 ,16 ,20 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,19 ,18 ,16 ,20 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,16 ,20,
            17 ,16 ,16 ,20 ,0  ,17 ,16 ,20 ,0  ,17 ,16 ,18 ,16 ,20 ,0  ,17 ,20 ,0  ,25 ,24 ,16 ,20 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,16 ,20,
            17 ,16 ,16 ,20 ,0  ,17 ,16 ,20 ,0  ,17 ,16 ,24 ,16 ,20 ,0  ,17 ,20 ,0  ,0  ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,16 ,20,
            17 ,16 ,16 ,20 ,0  ,17 ,16 ,16 ,18 ,16 ,20 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,19 ,18 ,16 ,20 ,0  ,17 ,16 ,18 ,16 ,20 ,0  ,17 ,16 ,20,
            25 ,24 ,24 ,28 ,0  ,25 ,24 ,24 ,24 ,24 ,28 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,25 ,24 ,24 ,28 ,0  ,17 ,16 ,24 ,24 ,28 ,0  ,17 ,16 ,20,
            0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,0  ,0  ,0  ,0  ,0  ,17 ,20 ,0  ,0  ,0  ,0  ,17 ,16 ,20,
            19 ,18 ,18 ,22 ,0  ,19 ,18 ,18 ,18 ,18 ,22 ,0  ,17 ,20 ,0  ,17 ,16 ,18 ,18 ,18 ,18 ,18 ,18 ,16 ,16 ,18 ,18 ,22 ,0  ,17 ,16 ,20,
            17 ,16 ,16 ,20 ,0  ,17 ,16 ,24 ,16 ,16 ,20 ,0  ,17 ,20 ,0  ,25 ,24 ,24 ,24 ,24 ,24 ,24 ,16 ,16 ,24 ,24 ,16 ,20 ,0  ,17 ,16 ,20,
            17 ,16 ,16 ,20 ,0  ,17 ,20 ,0  ,17 ,16 ,20 ,0  ,17 ,20 ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,17 ,20 ,0  ,0  ,17 ,16 ,18 ,16 ,16 ,20,
            17 ,16 ,16 ,20 ,0  ,17 ,20 ,0  ,17 ,16 ,16 ,18 ,16 ,20 ,0  ,19 ,18 ,18 ,18 ,18 ,18 ,18 ,16 ,16 ,22 ,0  ,17 ,16 ,24 ,16 ,16 ,20,
            17 ,16 ,16 ,20 ,0  ,17 ,20 ,0  ,25 ,24 ,24 ,24 ,24 ,28 ,0  ,25 ,16 ,16 ,24 ,24 ,24 ,24 ,24 ,16 ,20 ,0  ,17 ,20 ,0  ,17 ,16 ,20,
            17 ,16 ,16 ,20 ,0  ,17 ,20 ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,17 ,20 ,0  ,0  ,0  ,0  ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,16 ,20,
            17 ,16 ,16 ,16 ,18 ,16 ,16 ,18 ,18 ,18 ,18 ,18 ,22 ,0  ,19 ,18 ,16 ,16 ,18 ,18 ,18 ,22 ,0  ,17 ,20 ,0  ,25 ,28 ,0  ,17 ,16 ,20,
            17 ,16 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,16 ,20 ,0  ,17 ,16 ,24 ,24 ,24 ,24 ,16 ,20 ,0  ,17 ,20 ,0  ,0  ,0  ,0  ,17 ,16 ,20,
            17 ,20 ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,0  ,0  ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,19 ,22 ,0  ,17 ,16 ,20,
            17 ,20 ,0  ,19 ,18 ,18 ,18 ,18 ,18 ,22 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,19 ,22 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,16 ,20,
            17 ,20 ,0  ,17 ,16 ,24 ,16 ,16 ,24 ,28 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,25 ,16 ,20,
            17 ,20 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,0  ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,16 ,18 ,16 ,20 ,0  ,17 ,20 ,0  ,0  ,17 ,20,
            17 ,20 ,0  ,17 ,16 ,18 ,16 ,16 ,18 ,22 ,0  ,17 ,16 ,18 ,16 ,20 ,0  ,17 ,20 ,0  ,17 ,16 ,24 ,24 ,28 ,0  ,17 ,16 ,22 ,0  ,17 ,20,
            17 ,20 ,0  ,17 ,16 ,24 ,16 ,16 ,24 ,28 ,0  ,17 ,16 ,24 ,16 ,20 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,0  ,0  ,0  ,17 ,16 ,20 ,0  ,17 ,20,
            17 ,20 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,0  ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,16 ,18 ,18 ,18 ,18 ,16 ,16 ,20 ,0  ,17 ,20,
            17 ,16 ,18 ,16 ,16 ,18 ,16 ,16 ,18 ,18 ,18 ,16 ,20 ,0  ,17 ,16 ,18 ,16 ,16 ,18 ,16 ,16 ,24 ,24 ,16 ,16 ,24 ,16 ,20 ,0  ,17 ,20,
            17 ,16 ,24 ,24 ,24 ,24 ,16 ,16 ,24 ,24 ,24 ,16 ,20 ,0  ,25 ,24 ,24 ,24 ,16 ,16 ,24 ,28 ,0  ,0  ,17 ,20 ,0  ,17 ,20 ,0  ,17 ,20,
            17 ,20 ,0  ,0  ,0  ,0  ,17 ,20 ,0  ,0  ,0  ,17 ,20 ,0  ,0  ,0  ,0  ,0  ,17 ,20 ,0  ,0  ,0  ,19 ,16 ,20 ,0  ,17 ,20 ,0  ,17 ,20,
            17 ,20 ,0  ,19 ,18 ,18 ,16 ,16 ,18 ,18 ,18 ,16 ,20 ,0  ,19 ,18 ,18 ,18 ,16 ,16 ,18 ,18 ,18 ,16 ,16 ,28 ,0  ,17 ,16 ,18 ,16 ,20,
            17 ,20 ,0  ,25 ,24 ,24 ,24 ,16 ,16 ,24 ,24 ,24 ,28 ,0  ,25 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,16 ,20 ,0  ,0  ,17 ,16 ,16 ,16 ,20,
            17 ,20 ,0  ,0  ,0  ,0  ,0  ,17 ,20 ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,0  ,17 ,16 ,22 ,0  ,17 ,16 ,16 ,16 ,20,
            17 ,16 ,18 ,18 ,18 ,18 ,18 ,16 ,16 ,18 ,18 ,18 ,18 ,18 ,18 ,22 ,0  ,19 ,18 ,18 ,18 ,18 ,18 ,16 ,16 ,16 ,18 ,16 ,16 ,16 ,16 ,20,
            25 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,28 ,0  ,25 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,24 ,28
    };

    private final int[] validSpeeds = {4, 4, 3, 6, 6, 8};
    private final int maxSpeed = 8;

    private int currentSpeed = 4;
    private int[] screenData;
    private Timer timer;

    public Model() {
        loadImages();
        initVariables();
        addKeyListener(new TAdapter());
        setFocusable(true);
        initGame();
    }

    public static boolean getCaughtCopilot(){
        return caughtCopilot;
    }


    private void loadImages() {
        down = new ImageIcon("C:\\Users\\lemay\\IdeaProjects\\textGameDemo\\assets\\newDownT.gif").getImage();
        up = new ImageIcon("/C:\\Users\\lemay\\IdeaProjects\\textGameDemo\\assets\\newUpT.gif").getImage();
        left = new ImageIcon("/C:\\Users\\lemay\\IdeaProjects\\textGameDemo\\assets\\newLeftT.gif").getImage();
        right = new ImageIcon("/C:\\Users\\lemay\\IdeaProjects\\textGameDemo\\assets\\newRightT.gif").getImage();
        malware = new ImageIcon("/C:\\Users\\lemay\\IdeaProjects\\textGameDemo\\assets\\malware.gif").getImage();
        heart = new ImageIcon("/C:\\Users\\lemay\\IdeaProjects\\textGameDemo\\assets\\heart.png").getImage();
        copilot = new ImageIcon("/C:\\Users\\lemay\\IdeaProjects\\textGameDemo\\assets\\copilot.gif").getImage();

    }
    private void initVariables() {

        screenData = new int[N_BLOCKS * N_BLOCKS];
        d = new Dimension(800, 800);
        malware_x = new int[MAX_MALWARE];
        malware_dx = new int[MAX_MALWARE];
        malware_y = new int[MAX_MALWARE];
        malware_dy = new int[MAX_MALWARE];
        malwareSpeed = new int[MAX_MALWARE];
        dx = new int[4];
        dy = new int[4];
        c_dx = new int[4];
        c_dy = new int[4];

        timer = new Timer(40, this);
        timer.start();
    }

    private void playGame(Graphics2D g2d) {

        if(dying){
            death();
        }else if(caught){
            caughtCopilot();
        }else{
            movePlayer();
            drawPlayer(g2d);
            moveMalware(g2d);
            moveCopilot(g2d);
            checkMaze();
        }
    }

    private void showIntroScreen(Graphics2D g2d) {
        Color red = new Color(202, 22, 47);
        g2d.setColor(red);
        g2d.drawString(start, (SCREEN_SIZE)/10, 350);
    }

    private void drawScore(Graphics2D g) {
        g.setFont(smallFont);
        g.setColor(new Color(5, 181, 79));
        String s = "Score: " + score;
        g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);

        for (int i = 0; i < lives; i++) {
            g.drawImage(heart, i * 28 + 8, SCREEN_SIZE + 1, this);
        }
    }

    private void caughtCopilot(){
        System.out.println("Caught copilot");
        start = "*SUCCESS* - You caught the copilot! Close the window to continue";
        caughtCopilot = true;
        inGame = false;
        continueLevel();
    }

    private void checkMaze() {

        int i = 0;
        boolean finished = true;

        while (i < N_BLOCKS * N_BLOCKS && finished) {

            if ((screenData[i]) != 0) {
                finished = false;
            }

            i++;
        }

        if (finished) {

            score += 50;

            if (N_MALWARE < MAX_MALWARE) {
                N_MALWARE++;
            }

            if (currentSpeed < maxSpeed) {
                currentSpeed++;
            }

            initLevel();
        }
    }

    private void death() {

        lives--;

        if (lives == 0) {
            inGame = false;
            start = "*ERROR* SYSTEM FAILURE - Copilot evaded you.";
        }

        continueLevel();
    }

    private void moveCopilot(Graphics2D g2d){
        int pos;
        int count;

        if (copilot_x % BLOCK_SIZE == 0 && copilot_y % BLOCK_SIZE == 0) {
            pos = copilot_x / BLOCK_SIZE + N_BLOCKS * (copilot_y / BLOCK_SIZE);

            count = 0;

            if ((screenData[pos] & 1) == 0 && copilot_dx != 1) {
                c_dx[count] = -1;
                c_dy[count] = 0;
                count++;
            }

            if ((screenData[pos] & 2) == 0 && copilot_dy != 1) {
                c_dx[count] = 0;
                c_dy[count] = -1;
                count++;
            }

            if ((screenData[pos] & 4) == 0 && copilot_dx != -1) {
                c_dx[count] = 1;
                c_dy[count] = 0;
                count++;
            }

            if ((screenData[pos] & 8) == 0 && copilot_dy != -1) {
                c_dx[count] = 0;
                c_dy[count] = 1;
                count++;
            }

            if (count == 0) {

                if ((screenData[pos] & 15) == 15) {
                    copilot_dx = 0;
                    copilot_dy = 0;
                } else {
                    copilot_dx = -copilot_dx;
                    copilot_dy = -copilot_dy;
                }

            } else {

                count = (int) (Math.random() * count);

                if (count > 3) {
                    count = 3;
                }

                copilot_dx = c_dx[count];
                copilot_dy = c_dy[count];
            }
        }

        copilot_x = copilot_x + (copilot_dx * COPILOT_SPEED);
        copilot_y = copilot_y + (copilot_dy * COPILOT_SPEED);
        drawCopilot(g2d, copilot_x + 1, copilot_y + 1);

        if (player_x > (copilot_x - 12) && player_x < (copilot_x + 12)
                && player_y > (copilot_y - 12) && player_y < (copilot_y + 12)
                && inGame) {

            caught = true;
        }

    }

    private void drawCopilot(Graphics2D g2d, int x, int y) {
        g2d.drawImage(copilot, x, y, this);
    }

    private void moveMalware(Graphics2D g2d) {

        int pos;
        int count;

        for (int i = 0; i < N_MALWARE; i++) {
            if (malware_x[i] % BLOCK_SIZE == 0 && malware_y[i] % BLOCK_SIZE == 0) {
                pos = malware_x[i] / BLOCK_SIZE + N_BLOCKS * (malware_y[i] / BLOCK_SIZE);

                count = 0;

                if ((screenData[pos] & 1) == 0 && malware_dx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && malware_dy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && malware_dx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && malware_dy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        malware_dx[i] = 0;
                        malware_dy[i] = 0;
                    } else {
                        malware_dx[i] = -malware_dx[i];
                        malware_dy[i] = -malware_dy[i];
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    malware_dx[i] = dx[count];
                    malware_dy[i] = dy[count];
                }

            }

            malware_x[i] = malware_x[i] + (malware_dx[i] * malwareSpeed[i]);
            malware_y[i] = malware_y[i] + (malware_dy[i] * malwareSpeed[i]);
            drawMalware(g2d, malware_x[i] + 1, malware_y[i] + 1);

            if (player_x > (malware_x[i] - 12) && player_x < (malware_x[i] + 12)
                    && player_y > (malware_y[i] - 12) && player_y < (malware_y[i] + 12)
                    && inGame) {

                dying = true;
            }
        }
    }

    private void drawMalware(Graphics2D g2d, int x, int y) {
        g2d.drawImage(malware, x, y, this);
    }

    private void movePlayer() {

        int pos, ch;

        if (player_x % BLOCK_SIZE == 0 && player_y % BLOCK_SIZE == 0) {
            pos = player_x / BLOCK_SIZE + N_BLOCKS * (player_y / BLOCK_SIZE);
            ch = screenData[pos];

            if ((ch & 16) != 0) {
                screenData[pos] = (ch & 15);
                score++;
            }

            if (req_dx != 0 || req_dy != 0) {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    playerd_x = req_dx;
                    playerd_y = req_dy;
                }
            }

            // Check for standstill
            if ((playerd_x == -1 && playerd_y == 0 && (ch & 1) != 0)
                    || (playerd_x == 1 && playerd_y == 0 && (ch & 4) != 0)
                    || (playerd_x == 0 && playerd_y == -1 && (ch & 2) != 0)
                    || (playerd_x == 0 && playerd_y == 1 && (ch & 8) != 0)) {
                playerd_x = 0;
                playerd_y = 0;
            }
        }
        player_x = player_x + PLAYER_SPEED * playerd_x;
        player_y = player_y + PLAYER_SPEED * playerd_y;
    }

    private void drawPlayer(Graphics2D g2d) {

        if (req_dx == -1) {
            g2d.drawImage(left, player_x + 1, player_y + 1, this);
        } else if (req_dx == 1) {
            g2d.drawImage(right, player_x + 1, player_y + 1, this);
        } else if (req_dy == -1) {
            g2d.drawImage(up, player_x + 1, player_y + 1, this);
        } else {
            g2d.drawImage(down, player_x + 1, player_y + 1, this);
        }
    }

    private void drawMaze(Graphics2D g2d) {
        Color ogBlue = new Color(0,72,251);
        Color green = new Color(26, 83, 27);

        short i = 0;
        int x, y;

        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

                g2d.setColor(green);
                g2d.setStroke(new BasicStroke(5));

                if ((levelData[i] == 0)) {
                    g2d.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                }

                if ((screenData[i] & 1) != 0) {
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 2) != 0) {
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[i] & 4) != 0) {
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 8) != 0) {
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                i++;
            }
        }
    }

    private void initGame() {

        lives = 3;
        score = 0;
        initLevel();
        N_MALWARE = 12;
        currentSpeed = 4;
    }

    private void initLevel() {

        int i;
        for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
            screenData[i] = levelData[i];
        }

        continueLevel();
    }

    private void continueLevel() {

        int dx = 1;
        int random;

        for (int i = 0; i < N_MALWARE * 3 / 4; i++) {

            malware_y[i] = 15 * BLOCK_SIZE; //start position
            malware_x[i] = 15 * BLOCK_SIZE;
            malware_dy[i] = 0;
            malware_dx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed) {
                random = currentSpeed;
            }

            malwareSpeed[i] = validSpeeds[random];
        }

        for (int i = N_MALWARE * 3 / 4; i < N_MALWARE; i++) {

            malware_y[i] = 2 * BLOCK_SIZE; //start position
            malware_x[i] = 2 * BLOCK_SIZE;
            malware_dy[i] = 0;
            malware_dx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed) {
                random = currentSpeed;
            }

            malwareSpeed[i] = validSpeeds[random];
        }

        copilot_x = 2 * BLOCK_SIZE;  //start position
        copilot_y = 2 * BLOCK_SIZE;
        copilot_dx = 1;	//reset direction move
        copilot_dy = 0;

        player_x = 29 * BLOCK_SIZE;  //start position
        player_y = 29 * BLOCK_SIZE;
        playerd_x = 0;	//reset direction move
        playerd_y = 0;
        req_dx = 0;		// reset direction controls
        req_dy = 0;
        dying = false;
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);

        if (inGame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }


    //controls
    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (inGame) {
                if (key == KeyEvent.VK_LEFT) {
                    req_dx = -1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    req_dx = 1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_UP) {
                    req_dx = 0;
                    req_dy = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    req_dx = 0;
                    req_dy = 1;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                }
            } else {
                if (key == KeyEvent.VK_SPACE) {
                    inGame = true;
                    initGame();
                }
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
