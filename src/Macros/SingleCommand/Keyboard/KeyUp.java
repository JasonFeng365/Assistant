import java.awt.*;

public class KeyUp implements Command {
    private final int keycode, delay;
    public KeyUp(int keycode){
        this.keycode = keycode;
        delay = 0;
    }
    public KeyUp(int keycode, int delay){
        this.keycode = keycode;
        this.delay = delay;
    }

    @Override
    public void run(Robot robot) {
        robot.keyRelease(keycode);
        robot.delay(delay);
    }

    @Override
    public String toString() {
        return String.format("%d.%c.%d",keycode, MacroEvent.KEYUP, delay);
    }
}
