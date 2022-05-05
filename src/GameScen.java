import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;


public class GameScen extends JPanel {
    private Car pleyer;
    private CustomRectangel[] obstacles;
    private ImageIcon I_no_have_idia;
    private ImageIcon I_no_hve_idia2;
    private int y_image = 0;
    public boolean startGame = true;
    private ImageIcon logo;
    private int score = 0;
    public boolean winer = true;
    public boolean play = false;


    public GameScen(int x, int y, int WHIDTH, int HIGHET) {

        this.setBounds(x, y, WHIDTH, HIGHET);
        //first screen  +button start +image +color gray in background
        this.logo = new ImageIcon("src/f1 logo final.png");

        //sholders
        this.I_no_have_idia = new ImageIcon("src/fian left.png");

        //player
        this.pleyer = new Car();
        this.mainGameLoop();

        //obstacles  +image+bug
        Random random = new Random();
        int lower = -80000;
        int maxer = 1;
        this.obstacles = new CustomRectangel[350];
        for (int i = 0; i < this.obstacles.length; i++) {
            CustomRectangel obstacle = null;
            do {
                int e = limit(random.nextInt(WHIDTH));
                //for (int j = 0; j < obstacles.length; j++) {
              //  }
                obstacle = new CustomRectangel(e, random.nextInt(maxer - lower) + lower, randomImage());//image
            } while (obstacle.CheckCollision(this.pleyer.getFront()));
            this.obstacles[i] = obstacle;

        }

        //game over +new screen

    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //main Color
        g.setColor(Color.gray);
        g.fillRect(0, 0, getWidth(), getHeight());
        //start
        if (startGame==true&&play==false) {
            g.drawImage(this.logo.getImage(), getWidth() / 2 - 200, getHeight() / 2 - 150, 400, 100, null);
            JButton start = new JButton("start game");
            start.setBounds(getWidth() / 2 - 50, getHeight() / 2, 100, 30);
            add(start);

                start.addActionListener((event) -> {
                    this.startGame = false;
                    this.play=true;
                    System.out.println("fg");
                    start.setVisible(false);

                });

        }


        //score
        g.setColor(Color.black);
        g.setFont(new Font("serif", Font.BOLD, 20));
        g.drawString("Score "+score, getWidth() / 2 - 30, 20);

        //player
        this.pleyer.paintComponent(g);
        //obstacle
        for (int i = 0; i < this.obstacles.length-5; i++) {
            String c = obstacles[i].getPhoto();
            //if (obstacles[i].CheckCollision(obstacles[i+5]))
            this.obstacles[i].paint(g, c);

        //restart

        if (play == false) {
           /* if (startGame==true){
                g.drawImage(this.logo.getImage(), getWidth() / 2 - 200, getHeight() / 2 - 150, 400, 100, null);
                JButton start = new JButton("start game");
                start.setBounds(getWidth() / 2 - 50, getHeight() / 2, 100, 30);

                start.addActionListener((event) -> {
                    this.play=true;
                    this.startGame = false;
                    System.out.println("2");

                });
                add(start);
            }*/
            if (winer == false) {
                this.setVisible(true);
                this.setLayout(null);
                g.setColor(Color.BLACK);
                g.setFont(new Font("serif", Font.BOLD, 50));
                g.drawString("Game over ", 140, 200);
                g.drawString(" enter to the restart ", getWidth()/2-230, 250);
                g.drawString("your Score " + score, 110, 300);
                JButton restart = new JButton("Restart");
                restart.setBounds(getWidth()/2-75, 350, 150, 100);
                this.add(restart);
            }
        }
        //win
        if (score > 100000) {
            score = 100000;
        }
        if (score == 100000) {
            play = false;
            winer = true;
            g.setColor(Color.black);
            g.setFont(new Font("serif", Font.BOLD, 50));
            g.drawString("you win", getWidth()/2-75, 270);
            g.drawString(" enter to the restart ", getWidth()/2-230, 325);
            JButton restart = new JButton("Restart");
            restart.setBounds(getWidth()/2-75, 350, 150, 100);
            this.add(restart);
            restart.addActionListener((event) -> {

                System.out.println("1");

            });
        }

        }
        // }


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

    //MoveDownObstackes
    public void moveDownObstacles() {
        if (play == true) {
            for (int i = 0; i < obstacles.length; i++) {
                try {
                    if(obstacles[i]==null)
                        System.out.print(" ");
                        else{
                    obstacles[i].MoveDownObstacles();
                        }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    //game over//collision + bug + if obstacle collision in another obstacle
    public void gameOver() {
        for (int i = 0; i < obstacles.length; i++) {
          //  obstacles[i].MoveDownObstacles();
            if (obstacles[i].CheckCollision(this.pleyer.getFront())) {

                play = false;
                winer=false;
            }
        }
    }

    public void mainGameLoop() {

        new Thread(() -> {
            PleyerMovment PleyerMovment = new PleyerMovment(this.pleyer);
            this.setFocusable(true);
            this.requestFocus();
            this.addKeyListener(PleyerMovment);
            while (true) {
                try {
                    try {
                        moveDownObstacles();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    if(play==true)
                        score++;

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
        String[] imageURL = new String[3];
        imageURL[0] = "src/alpina final.png";
        imageURL[1] = "src/red.png";
        imageURL[2] = "src/red bull f1.png";

        Random random = new Random();

        String a = imageURL[random.nextInt(3)];
        return a;
    }

}
