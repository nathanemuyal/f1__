import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PleyerMovment implements KeyListener {
    private Car pleyer;

    public PleyerMovment(Car car) {
        this.pleyer = car;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {

            case KeyEvent.VK_LEFT:
                this.pleyer.MoveLeft();

                break;
            case KeyEvent.VK_RIGHT:
                this.pleyer.MoveRighet();

                break;

        }

    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }

}
