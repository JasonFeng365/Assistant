import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Assistant implements NativeKeyListener {
    static Robot robot;
    static boolean canUse=true;
    static LinkedList<Integer> prev = new LinkedList<>();
    static Map<String, Macro> map = StringMap.MAP;
    static{
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        if (!canUse) return;

        prev.add(e.getRawCode());

        if (prev.size() > StringMap.MAX_LENGTH) prev.pop();

        Macro macro = getMacroAndBackspace();
        if (macro != null) {
            canUse = false;
            macro.execute(robot);
            prev.clear();
            canUse = true;
        }
    }

    public Macro getMacroAndBackspace(){
        String construct = "";
        Iterator<Integer> iterator = prev.descendingIterator();
        while (iterator.hasNext()){
            construct = ((char)((int)iterator.next()))+construct;
            if (map.containsKey(construct)) {
                Backspace.backSpace(robot, construct.length());
                return map.get(construct);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);
            GlobalScreen.registerNativeHook();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new Assistant());
    }
    public void nativeKeyReleased(NativeKeyEvent e) {}
    public void nativeKeyTyped(NativeKeyEvent e) {}

}