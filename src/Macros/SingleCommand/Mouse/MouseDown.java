import java.awt.*;

public class MouseDown implements Command {
    private final int button, delay;
    public MouseDown(int button){
        this.button = button;
        delay = 0;
    }
    public MouseDown(int button, int delay){
        this.button = button;
        this.delay = delay;
    }

    @Override
    public void run(Robot robot) {
        robot.mousePress(button);
        robot.delay(delay);
    }
}
