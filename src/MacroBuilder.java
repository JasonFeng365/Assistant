import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MacroBuilder {
    //NativeKeyCode : KeyEventCode
    public static final Map<Integer, Integer> keyPressMap;
    static {
        keyPressMap = new HashMap<>();
        try {
            Scanner scanner = new Scanner(new File("KeyPressMap.txt"));
            while (scanner.hasNextLine()){
                String[] split = scanner.nextLine().split(" ");
                int key = Integer.parseInt(split[0]), value = Integer.parseInt(split[1]);

                keyPressMap.put(key, value);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

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

            int keycode = Integer.parseInt(split[0]);
            char event = split[1].charAt(0);
            int delay = Integer.parseInt(split[split.length-1]);

            if (split.length == 3) { //key
                return parseKeyboardCommand(keycode, event, delay);
            } else if (split.length == 5) { //click
                int x = Integer.parseInt(split[2]), y = Integer.parseInt(split[3]);
                return parseMouseCommand(keycode, event, x, y, delay);
            } else {
                System.out.println("Invalid sequence: " + string);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Command parseCommand(MacroEvent event){
        if (event instanceof MacroMouseEvent){
            MacroMouseEvent e = (MacroMouseEvent) event;
            return parseMouseCommand(e.getKey(), e.getEvent(), e.getX(), e.getY(), e.getDelay());
        } else {
            System.out.println((char)event.getKey());
            return parseKeyboardCommand(keyPressMap.get(event.getKey()), event.getEvent(), event.getDelay());
        }
    }

    private static Command parseKeyboardCommand(int keycode, char event, int delay){
        if (event == MacroEvent.KEYDOWN) return new KeyDown(keycode, delay);
        return new KeyUp(keycode, delay);
    }

    private static Command parseMouseCommand(int keycode, char event, int x, int y, int delay){
        if (event == MacroEvent.MOUSEDOWN) return new MouseDown(keycode,x, y, delay);
        return new MouseUp(keycode,x,y, delay);
    }

    public static Macro parse(ArrayList<MacroEvent> array){
        Command[] commands = new Command[array.size()-28];

        for (int i = 15; i < array.size()-13; i++) {
            commands[i-15] = parseCommand(array.get(i));
        }

        return new TypeMouseMacro(commands);
    }
}
