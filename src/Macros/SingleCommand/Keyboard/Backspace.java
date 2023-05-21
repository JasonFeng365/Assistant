import java.awt.*;
import java.awt.event.KeyEvent;

public class Backspace implements Command {
    private final int spaces;
    private static final KeyPress press = new KeyPress(KeyEvent.VK_BACK_SPACE, 1);
    public Backspace(int spaces){
        this.spaces = spaces;
    }

    @Override
    public void run(Robot robot) {
        for (int i = 0; i < spaces; i++) press.run(robot);
    }

    public static void backSpace(Robot robot, int amount){
        for (int i = 0; i < amount; i++) press.run(robot);
    }
}
