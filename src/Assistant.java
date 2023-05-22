import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Assistant implements NativeKeyListener, NativeMouseListener {
    private final Robot robot;
    private boolean canUse=true, recording;
    private final LinkedList<Integer> prev = new LinkedList<>();
    private final Map<String, Macro> map = StringMap.MAP;

    private long prevTime;
    private final ArrayList<MacroEvent> eventQueue = new ArrayList<>();

    private int getDelay(){
        long curTime = System.currentTimeMillis();
        int delay = (int)(curTime - prevTime);
        prevTime = curTime;
        return delay;
    }


    public Assistant(Robot robot){
        this.robot = robot;
    }

    public void nativeKeyPressed(NativeKeyEvent e) {
//        System.out.println(e.getKeyChar());
//        System.out.println(e.getKeyCode());
//        System.out.println(e.getRawCode());

        if (recording) eventQueue.add(new MacroEvent(MacroEvent.KEYDOWN, e.getKeyCode(), getDelay()));

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
                Backspace.backSpace(robot, 7);
                recording = !recording;
                prevTime = System.currentTimeMillis();

                if (!recording) {
                    System.out.println(MacroBuilder.parse(eventQueue));
                    eventQueue.clear();
                } else {
                    System.out.println("Recording");
                }
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
            Assistant assistant = new Assistant(new Robot());
            GlobalScreen.addNativeKeyListener(assistant);
            GlobalScreen.addNativeMouseListener(assistant);

        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (recording) eventQueue.add(new MacroEvent(MacroEvent.KEYUP, e.getKeyCode(), getDelay()));
    }
    public void nativeKeyTyped(NativeKeyEvent e) {}

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {}

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
//        NativeMouseEvent.BUTTON2_MASK
//        InputEvent.MASK
//        System.out.println(e.getButton());
        if (recording) eventQueue.add(
                new MacroMouseEvent(MacroEvent.MOUSEDOWN, 1<<(e.getButton()+9), e.getX(), e.getY(), getDelay()));
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        if (recording) eventQueue.add(
                new MacroMouseEvent(MacroEvent.MOUSEUP, 1<<(e.getButton()+9), e.getX(), e.getY(), getDelay()));
    }
}