import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Assistant implements NativeKeyListener {
    static Robot robot;
    static boolean canUse=true;
    static LinkedList<Integer> prev = new LinkedList<>();
    static Map<String, String> map = StringMap.MAP;
    static{
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void backspace(int amount){
        for (int i = 0; i < amount; i++) press(KeyEvent.VK_BACK_SPACE);
    }

    static void press(int keycode){
        robot.keyPress(keycode);
        robot.delay(5);
        robot.keyRelease(keycode);
        robot.delay(5);
    }
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (!canUse) return;

        prev.add(e.getRawCode());

        if (prev.size() > StringMap.MAX_LENGTH) prev.pop();

        String res = getKey();
        if (res != null) {
            canUse = false;
            if (res.equals("ÜREFRESH")) {
                backspace(8);
                robot.delay(100);
                StringMap.refresh();
            } else if (res.equals("ÜEXIT")) {
                backspace(5);
                System.exit(0);
            }
            else copyAndPaste(res);
            prev.clear();
            canUse = true;
        }
    }

    public String getKey(){
        String construct = "";
        Iterator<Integer> iterator = prev.descendingIterator();
        while (iterator.hasNext()){
            construct = ((char)((int)iterator.next()))+construct;
            if (map.containsKey(construct)) return construct;
        }
        return null;
    }

    public void paste(){
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.delay(1);
        press(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(1);
    }

    public void copyAndPaste(String key){
        try {
            String string = map.get(key);
            Transferable prevContents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

            StringSelection selection = new StringSelection(string);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);


            backspace(key.length());
            paste();

            robot.delay(100);

            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(prevContents, null);
        } catch (Exception ignored) {}
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