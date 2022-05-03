import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class GameScen extends JPanel  {
    private Car pleyer;
    private CustomRectangel[] obstacles;
    private ImageIcon mercedes;
    private ImageIcon redBull;
    private ImageIcon alfaRomeo;
    private ImageIcon alpine;

    public GameScen(int x, int y, int WHIDTH, int HIGHET) {

        this.setBounds(x, y, WHIDTH, HIGHET);
        //first scrin
        //button start+image+color gray

        JButton start = new JButton("start game");
        start.setBounds(WHIDTH/2,0,100,50);
        add(start);



        //player
        this.pleyer = new Car();
        this.mainGameLoop();

        //obstacles+image
        Random random = new Random();
        int lower = -20000;
        int maxer = 1;
        this.obstacles = new CustomRectangel[100];
        for (int i = 0; i < this.obstacles.length; i++) {
            CustomRectangel obstacle = null;
            do {
                int e = limit(random.nextInt(WHIDTH));

                obstacle = new CustomRectangel(e, random.nextInt(maxer - lower) + lower, "C:\\Nouveau dossier\\shaiSadna\\.idea\\photo\\red.png");
            } while (obstacle.CheckCollision(this.pleyer.getFront()));
            this.obstacles[i] = obstacle;

        }
        //game over++

    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //main Color
        g.setColor(Color.gray);
        g.fillRect(0, 0, getWidth(), getHeight());

        //border

        String c = "C:\\Nouveau dossier\\shaiSadna\\.idea\\photo\\mercedese f1.png";
        //player
        this.pleyer.paintComponent(g);
        //obstacl
        for (int i = 0; i < this.obstacles.length; i++) {
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
            obstacles[i].MoveDownObstacles();
        }
    }

    //game over//collision
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
                  moveDownObstacles();
                    gameOver();
                    repaint();
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
