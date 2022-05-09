import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameScen extends JPanel {
    private Car pleyer;
    boolean obstaclesCreated = false;
    private ImageIcon shoulders_left;
    private ImageIcon shoulders_right;
    private int y_image = 0;
    public boolean startGame = true;
    private ImageIcon logo;
    private int score = 0;
    public boolean winer = true;
    public boolean play = false;
    public boolean button_restart = true;
    public int y_shoulder = 0;

    private final JButton start = new JButton("start game");
    private final JButton restart = new JButton("Play again");


    public GameScen(int x, int y, int WHIDTH, int HIGHET, Color color) {

        this.setBounds(x, y, WHIDTH, HIGHET);
        //first screen  +button start +image +color gray in background
        this.logo = new ImageIcon("src/f1 logo final.png");

        //shoulders
        this.shoulders_left = new ImageIcon("src/fian left.png");
        this.shoulders_right = new ImageIcon("src/final right.png");

        //player
        this.pleyer = new Car();
        this.mainGameLoop();


    }

    public CustomRectangel[] obstacles = new CustomRectangel[120];

    public void createObstacles() {
        for (int i = 0; i < obstacles.length; i++) {
            CustomRectangel obstacle = new CustomRectangel(randomImage());

            do {
                randomPlace(obstacle);
            }
            while (obstaclesAreTouching(obstacle, i));

            obstacles[i] = obstacle;
        }
        obstaclesCreated = true;

    }

    private boolean obstaclesAreTouching(CustomRectangel obstacle, int index) {
        for (int i = 0; i < index; i++) {
            if (obstacle.CheckCollision(obstacles[i])) {
                return true;
            }
        }

        return false;
    }

    private void randomPlace(CustomRectangel obstacle) {
        Random rnd = new Random();
        int lower = -30000;
        int maxer = 1;
        ;
        int e = limit(rnd.nextInt(500));
        obstacle.setX(e);
        obstacle.setY(rnd.nextInt(maxer - lower) + lower);

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //main Color
        g.setColor(Color.gray);
        g.fillRect(0, 0, getWidth(), getHeight());
        //start

        if (startGame == true && play == false) {
            g.drawImage(this.logo.getImage(), getWidth() / 2 - 200, getHeight() / 2 - 150, 400, 100, null);

            start.setBounds(getWidth() / 2 - 50, getHeight() / 2, 100, 30);
            add(start);


            start.addActionListener((event) -> {

                this.startGame = false;
                this.play = true;
                start.setVisible(false);

            });

        }

        if (play == true) {
            //score
            g.setColor(Color.black);
            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Score " + score, getWidth() / 2 - 30, 20);


            //shoulders
            g.drawImage(this.shoulders_left.getImage(), 0, y_shoulder, 70, 220, null);
            g.drawImage(this.shoulders_left.getImage(), 0, y_shoulder + 300 + 220, 70, 220, null);

            g.drawImage(this.shoulders_right.getImage(), getWidth() - 70, y_shoulder + 150, 70, 220, null);
            g.drawImage(this.shoulders_right.getImage(), getWidth() - 70, y_shoulder + 450 + 220, 70, 220, null);

            shoulders();

            //player
            this.pleyer.paintComponent(g);

        }
        //obstacle

        for (int i = 0; i < this.obstacles.length - 5; i++) {
            String c = obstacles[i].getPhoto();
            this.obstacles[i].paint(g, c);


            //restart

            if (play == false) {
                if (winer == false) {
                    this.setVisible(true);
                    this.setLayout(null);
                    g.setColor(Color.red);
                    g.setFont(new Font("serif", Font.BOLD, 70));
                    g.drawString("Game over ", getWidth() / 2 - 170, 300);
                    g.setColor(Color.orange);
                    g.setFont(new Font("serif", Font.TRUETYPE_FONT, 40));
                    g.drawString("your Score :" + score, 110, 60);

                    reset_button();

                }

            }
        }
        //win
        if (score > 80) {
            score = 80;
        }
        if (score == 80) {
            play = false;
            winer = true;
            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD, 50));
            g.drawString("you win !", getWidth() / 2 - 100, 270);


            reset_button();


        }


    }


    //calcul limit
    public int limit(int a) {

        if (a < 50) {
            return 50;
        } else if (a > getWidth() - 120) {
            return getWidth() - 120;
        } else {
            return a;
        }
    }

    //MoveDownObstaces
    public void moveDownObstacles() {
        if (play == true) {
            for (int i = 0; i < obstacles.length; i++) {
                try {
                    if (obstacles[i] == null)
                        System.out.print(" ");
                    else {
                        obstacles[i].MoveDownObstacles();
                        if (obstacles[i].getY() == 450)
                            score++;
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    //game over
    public void gameOver() {
        for (int i = 0; i < obstacles.length; i++) {
            //  obstacles[i].MoveDownObstacles();
            if (obstacles[i].CheckCollision(this.pleyer.getFront())) {

                play = false;
                winer = false;
            }
        }
    }

    public void mainGameLoop() {

        new Thread(() -> {
            PleyerMovment PleyerMovment = new PleyerMovment(this.pleyer);
            this.setFocusable(true);
            this.requestFocus();
            this.addKeyListener(PleyerMovment);
            createObstacles();
            while (true) {
                try {
                    try {
                        moveDownObstacles();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                    gameOver();
                    repaint();
                    Thread.sleep(5);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public String randomImage() {
        String[] imageURL = new String[4];
        imageURL[0] = "src/alpina final.png";
        imageURL[1] = "src/red.png";
        imageURL[2] = "src/red bull f1.png";
        imageURL[3] = "src/mercedese f1.png";

        Random random = new Random();

        String a = imageURL[random.nextInt(4)];
        return a;
    }

    public void reset_button() {
        if (button_restart = true) {
            restart.setVisible(true);
            button_restart = false;
        }

        restart.setBounds(getWidth() / 2 - 75, 350, 150, 100);
        this.add(restart);
        restart.addActionListener((event) -> {
            //  new_obstacles();
            createObstacles();
            score = 0;
            play = true;
            winer = true;
            restart.setVisible(false);
            button_restart = true;

        });

    }

    public void shoulders() {
        new Thread(() -> {
            if (play == true) {
                y_shoulder++;
                repaint();
                if (y_shoulder > 1000) {
                    y_shoulder = -1000;
                }
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }

}
