public class MacroMouseEvent extends MacroEvent {
    private final int x, y;

    public MacroMouseEvent(Event event, int key, int x, int y, int delay) {
        super(event, key, delay);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
