public class MacroEvent {
    public enum Event{
        KEYDOWN, KEYUP, MOUSEDOWN, MOUSEUP
    }

    private final Event event;
    private final int key, delay;
    public MacroEvent(Event event, int key, int delay){
        this.event = event;
        this.key = key;
        this.delay = delay;
    }

    public Event getEvent() {
        return event;
    }

    public int getDelay() {
        return delay;
    }

    public int getKey() {
        return key;
    }
}
