import java.awt.*;

public class MouseUp implements Command {
    private final int button, delay, x, y;
    public MouseUp(int button, int x, int y){
        this.button = button;
        delay = 0;
        this.x = x;
        this.y = y;
    }
    public MouseUp(int button, int x, int y, int delay){
        this.button = button;
        this.delay = delay;
        this.x = x;
        this.y = y;
    }

    @Override
    public void run(Robot robot) {
        robot.delay(delay);
        robot.mouseMove(x, y);
        robot.delay(100);
        robot.mouseRelease(button);
    }

    @Override
    public String toString() {
        return String.format("%d.%c.%d.%d.%d",button, MacroEvent.MOUSEUP, x, y, delay);
    }
}
