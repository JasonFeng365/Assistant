import java.awt.*;

public class KeyPress implements Command {
    private final KeyDown down;
    private final KeyUp up;
    private final int delay;

    public KeyPress(int keycode){
        down = new KeyDown(keycode);
        up = new KeyUp(keycode);
        delay = 0;
    }
    public KeyPress(int keycode, int delay){
        down = new KeyDown(keycode);
        up = new KeyUp(keycode);
        this.delay = delay;
    }
    @Override
    public void run(Robot robot) {
        down.run(robot);
        robot.delay(delay);
        up.run(robot);
        robot.delay(delay);
    }
}
