import java.awt.*;

public class MouseUp implements Command {
    private final int button, delay;
    public MouseUp(int button){
        this.button = button;
        delay = 0;
    }
    public MouseUp(int button, int delay){
        this.button = button;
        this.delay = delay;
    }

    @Override
    public void run(Robot robot) {
        robot.mousePress(button);
        robot.delay(delay);
    }
}
