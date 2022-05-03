import java.awt.*;

public class CustomRectangel extends Canvas {
    private int x;
    private int y;
    final int width=50;
    final int heighet=120;
     public String photo;

        public int limit(int a){
            if(a<50) {
                return 50;
            }else if (a>235) {
                return 235;
            }else {
                return a;
            }
        }



    public int getWidth() {
        return width;
    }

    public int getX(){return this.x;}
    public int getY(){return this.y;}

    public int getHeighet() {
        return heighet;
    }

    private Color color;


    public void paint(Graphics Graphics, String pic) {
        //Graphics.fillRect(this.x, this.y, this.width, this.heighet);
        //Graphics.setColor(this.color);
        Toolkit tool = Toolkit.getDefaultToolkit();
        Image i = tool.getImage(pic);
        Graphics.drawImage(i,this.x+1,this.y,this.width,this.heighet,this);
    }

    public CustomRectangel(int x, int y, String guy) {

        this.x = x;
        this.y = y;
        this.photo = guy;

    }

    public void MoveRighetCar() {
        this.x = x + 5;
        this.x=limit(x);

    }

    public void MoveLeftCar() {
        this.x = x - 5;
        this.x=limit(x);
    }

    public void MoveUpCar() {
        this.y = y - 5;
        limit(x);
    }

    public void MoveDownCar() {
        this.y = y + 5;
        limit(x);
    }

    public void MoveRighetObstacles() {
        this.x++;

    }

    public void MoveLeftObstacles() {
        this.x--;
    }

    public void MoveUpObstacles() {
        this.y--;
    }

    public void MoveDownObstacles() {
        this.y++;

    }

    public boolean CheckCollision(CustomRectangel other) {
        boolean collision = false;
        Rectangle thisRectangel = new Rectangle(this.x, this.y, this.width, this.heighet);
        Rectangle otherRectangel = new Rectangle(other.x, other.y, other.width, other.heighet);
        if (thisRectangel.intersects(otherRectangel)) {
            collision = true;
        }
        return collision;
    }/*
    Image img = new ImageIcon("C:\Nouveau dossier\shaiSadna\.idea\photo\red bull f1.png");
    public void paint(Graphics g){
        g.drawImage(img,this.x, this.y, this.width,this.heighet,this);


    }*/
    public void paint(Graphics g){
        g.setColor(this.color);
        g.fillRect(this.x,this.y,this.width,this.heighet);
    }
}
