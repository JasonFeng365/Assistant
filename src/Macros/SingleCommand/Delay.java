import java.awt.*;

public class Delay implements Command {
    private int milliseconds;
    public Delay(int milliseconds){
        this.milliseconds = milliseconds;
    }
    @Override
    public void run(Robot robot) {
        robot.delay(milliseconds);
    }
}
