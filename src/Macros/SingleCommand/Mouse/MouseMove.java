import java.awt.*;

public class MouseMove implements Command {
    private final int x, y, delay;
    public MouseMove(int x, int y){
        this.x = x;
        this.y = y;
        delay = 0;
    }
    public MouseMove(int x, int y, int delay){
        this.x = x;
        this.y = y;
        this.delay = delay;
    }
    @Override
    public void run(Robot robot) {
        robot.mouseMove(x, y);
        robot.delay(delay);
    }
}
