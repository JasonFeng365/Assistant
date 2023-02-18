import java.io.File;
import java.util.Scanner;
import java.util.TreeMap;

public class StringMap {
    public static final TreeMap<String, String> MAP = new TreeMap<>();
    public static int MAX_LENGTH = 0;
    static{
        refresh();
    }

    public static void refresh(){
        try{
            Scanner scanner = new Scanner(new File("Keys.txt"));
            MAP.clear();
            MAX_LENGTH = 0;
            while (scanner.hasNextLine()){
                String[] split = scanner.nextLine().split(" ");
                MAP.put(split[0].toUpperCase().replace('\\', 'Ü'), split[1]);
                if (split[0].length() > MAX_LENGTH) MAX_LENGTH = split[0].length();
            }
            System.out.println("Map refreshed!");
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
