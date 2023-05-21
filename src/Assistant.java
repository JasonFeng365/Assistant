import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Assistant implements NativeKeyListener, NativeMouseListener {
    private final Robot robot;
    private boolean canUse=true, recording;
    private final LinkedList<Integer> prev = new LinkedList<>();
    private final Map<String, Macro> map = StringMap.MAP;

    private long prevTime, curTime;
    private final Queue<MacroEvent> eventQueue = new LinkedList<>();

    private int getDelay(){
        curTime = System.currentTimeMillis();
        int delay = (int)(curTime - prevTime);
        prevTime = curTime;
        return delay;
    }

    public Assistant(Robot robot){
        this.robot = robot;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
        if (recording) eventQueue.add(new MacroEvent(MacroEvent.Event.KEYDOWN, e.getRawCode(), getDelay()));

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
            if (construct.equals("ÜRECORD")){
                recording = !recording;
                prevTime = System.currentTimeMillis();
                return null;
            }

            if (!recording && map.containsKey(construct)) {
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
            GlobalScreen.addNativeKeyListener(new Assistant(new Robot()));

        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (recording) eventQueue.add(new MacroEvent(MacroEvent.Event.KEYUP, e.getRawCode(), getDelay()));
    }
    public void nativeKeyTyped(NativeKeyEvent e) {}

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {

        if (recording) eventQueue.add(
                new MacroMouseEvent(MacroEvent.Event.MOUSEDOWN, e.getButton(), e.getX(), e.getY(), getDelay()));
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {}

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        if (recording) eventQueue.add(
                new MacroMouseEvent(MacroEvent.Event.MOUSEUP, e.getButton(), e.getX(), e.getY(), getDelay()));
    }
}