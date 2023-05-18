import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class StringMap {
    public static final Map<String, String> MAP = new TreeMap<>();
    public static int MAX_LENGTH = 0;
    static{
        refresh();
    }

    public static void refresh(){
        try{
            BufferedReader scanner = new BufferedReader(new InputStreamReader(
                    new FileInputStream("Keys.txt"), StandardCharsets.UTF_8));
            MAP.clear();
            MAX_LENGTH = 0;
            String line = scanner.readLine();
            while (line!=null){
                String[] split = line.split(" ");
                String key = split[0].toUpperCase().replace('\\', 'Ü');
                String value = split[1];
                MAP.put(key, value);
                System.out.println(line);
                if (split[0].length() > MAX_LENGTH) MAX_LENGTH = split[0].length();

                line = scanner.readLine();
            }
            System.out.println("Map refreshed!");
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
