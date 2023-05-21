import java.util.Arrays;

public class MacroBuilder {
    public static Macro parse(String string){
        if (string.length() == 1) return new PasteMacro(string);
        String[] substrings = string.split(" ");
        Command[] commands = new Command[substrings.length];

        for (int i = 0; i < substrings.length; i++)
            commands[i] = parseCommand(substrings[i]);

        return new TypeMouseMacro(commands);
    }

    /*String formats
    Click: code/key.d/u/c.x.y.delay ->  5
    Key: code/key.d/u/c.delay       ->  3
     */
    private static Command parseCommand(String string){
        try {
            String[] split = string.split("\\.");
            if (split.length == 3) { //key

            } else if (split.length == 5) { //click
                int key =
            } else {
                System.out.println("Invalid sequence: " + string);
                return null;
            }
        } catch (Exception ignored){ return null;}
    }
}
