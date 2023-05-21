import java.awt.*;
import java.util.ArrayList;

public class TypeMouseMacro implements Macro {
    private final Command[] sequence;
    public TypeMouseMacro(Command[] sequence){
        this.sequence = sequence;
    }

    @Override
    public void execute(Robot robot) {
        for (Command c : sequence) c.run(robot);
    }
}
