import java.awt.*;

@FunctionalInterface
public interface Command {
    void run(Robot robot);
}
