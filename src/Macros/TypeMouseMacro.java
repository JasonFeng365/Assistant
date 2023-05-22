import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public class TypeMouseMacro implements Macro {
    private final Command[] sequence;
    public TypeMouseMacro(Command[] sequence){
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        System.out.println(Arrays.toString(sequence));

        if (sequence.length == 0) return "";
        if (sequence.length == 1) return sequence[0].toString();

        StringBuilder builder = new StringBuilder();
        builder.append(sequence[0]);
        for (int i = 1; i < sequence.length; i++)
            builder.append(' ').append(sequence[i]);

        return builder.toString();
    }

    @Override
    public void execute(Robot robot) {
        for (Command c : sequence) {
            try {
                c.run(robot);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                System.err.println(c);
            }
        }
    }
}
