import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class GameScen extends JPanel  {
    private Car pleyer;
    private CustomRectangel[] obstacles;


    public GameScen(int x, int y, int WHIDTH, int HIGHET) {

        this.setBounds(x, y, WHIDTH, HIGHET);
        //first screen  +button start +image +color gray in background

      /*  JButton start = new JButton("start game");
        start.setBounds(WHIDTH/2,0,100,50);
        add(start);*/



        //player
        this.pleyer = new Car();
        this.mainGameLoop();

        //obstacles  +image+bug
        Random random = new Random();
        int lower = -20000;
        int maxer = 1;
        this.obstacles = new CustomRectangel[100];
        for (int i = 0; i < this.obstacles.length; i++) {
            CustomRectangel obstacle = null;
            do {
                int e = limit(random.nextInt(WHIDTH));

                obstacle = new CustomRectangel(e, random.nextInt(maxer - lower) + lower, randomImage() );//image
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

        //border

        //player

        this.pleyer.paintComponent(g);
        //obstacle
        for (int i = 0; i < this.obstacles.length; i++) {
            String c =obstacles[i].getPhoto();
            this.obstacles[i].paint(g, c);
        }

    }

    //calcul limit
    public int limit(int a) {

        if (a < 50) {
            return 50;
        } else if (a > 235) {
            return 235;
        } else {
            return a;
        }
    }
     //MoveDownObstackes
    public void moveDownObstacles() {
        for (int i = 0; i < obstacles.length; i++) {
            try {
                obstacles[i].MoveDownObstacles();
            }catch (NullPointerException e){
                e.printStackTrace();
            }

        }
    }

    //game over//collision + bug + if obstacle collision in another obstacle
    public void gameOver() {
        for (int i = 0; i < obstacles.length; i++) {
            obstacles[i].MoveDownObstacles();
            if (obstacles[i].CheckCollision(this.pleyer.getFront())) {
                JLabel GameOver = new JLabel("Game Over!!!");
                GameOver.setBounds(120, 270, 150, 100);
                this.add(GameOver);

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
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }

                    gameOver();
                    repaint();
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public String randomImage(){
        String[] imageURL = new String[4];
        imageURL[0]="C:\\Users\\User\\IdeaProjects\\F1_2022\\src\\alpina final.png";
        imageURL[1]="C:\\Users\\User\\IdeaProjects\\F1_2022\\src\\mercedese f1.png";
        imageURL[2]="C:\\Users\\User\\IdeaProjects\\F1_2022\\src\\red.png";
        imageURL[3]="C:\\Users\\User\\IdeaProjects\\F1_2022\\src\\red bull f1.png";

        Random random = new Random();

        String a=imageURL[random.nextInt(4)];
        return a;
    }

}
