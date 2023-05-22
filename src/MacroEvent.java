public class MacroEvent {
    public static final char KEYDOWN = 'K', KEYUP = 'k', MOUSEDOWN = 'M', MOUSEUP = 'm';

    private final char event;
    private final int key, delay;
    public MacroEvent(char event, int key, int delay){
        this.event = event;
        this.key = key;
        this.delay = delay;
    }

    public char getEvent() {
        return event;
    }

    public int getDelay() {
        return delay;
    }

    public int getKey() {
        return key;
    }

    @Override
    public String toString() {
        return String.format("%d.%c.%d ", getKey(), getEvent(), getDelay());
    }
}
