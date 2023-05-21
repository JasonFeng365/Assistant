import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class PasteMacro implements Macro {
    private static final TypeMouseMacro paste;
    static {
        Command[] commandArray = {new KeyDown(KeyEvent.VK_CONTROL),
                new KeyPress('V'), new KeyUp(KeyEvent.VK_CONTROL)};
        paste = new TypeMouseMacro(commandArray);
    }
    private final String data;
    public PasteMacro(String toPaste){
        data = toPaste;
    }

    @Override
    public void execute(Robot robot){
        try {
            Transferable prevContents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

            StringSelection selection = new StringSelection(data);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);

            paste.execute(robot);
            robot.delay(100);

            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(prevContents, null);
        } catch (Exception ignored){}
    }
}
