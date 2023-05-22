import java.awt.*;

public class KeyDown implements Command {
    private final int keycode, delay;
    public KeyDown(int keycode){
        this.keycode = keycode;
        delay = 0;
    }
    public KeyDown(int keycode, int delay){
        this.keycode = keycode;
        this.delay = delay;
    }

    @Override
    public void run(Robot robot) {
        robot.keyPress(keycode);
        robot.delay(delay);
    }

    @Override
    public String toString() {
        return String.format("%d.%c.%d",keycode, MacroEvent.KEYDOWN, delay);
    }
}
