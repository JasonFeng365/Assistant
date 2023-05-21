import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class StringMap {
    public static final Map<String, Macro> MAP = new TreeMap<>();
    public static int MAX_LENGTH = 0;
    static{
        refresh();
    }

    public static void refresh(){
        try{
            BufferedReader scanner = new BufferedReader(new InputStreamReader(
                    new FileInputStream("Keys.txt"), StandardCharsets.UTF_8));
            MAP.clear();
            Builtin.addCommandsToMap(MAP);
            MAX_LENGTH = Builtin.maxLength;
            String line = scanner.readLine();
            while (line!=null){
                int split = line.indexOf(' ');
                String key = line.substring(0, split).toUpperCase().replace('\\', 'Ü');
                String value = line.substring(split+1);
                MAP.put(key, MacroBuilder.parse(value));
                System.out.println(line);
                if (key.length() > MAX_LENGTH) MAX_LENGTH = key.length();

                line = scanner.readLine();
            }
            System.out.println("Map refreshed!");
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
