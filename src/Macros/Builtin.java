import java.util.Map;

public class Builtin {
    private static final String[] commandNames;
    private static final Macro[] macros;
    public static final int maxLength;
    static{
        commandNames = new String[]{
                "\\refresh",
                "\\exit",
        };
        macros = new Macro[]{
                robot -> StringMap.refresh(),
                robot -> System.exit(0),
        };
        int max = 0;
        for (String s : commandNames) if (s.length()>max) max = s.length();
        maxLength = max;
    }

    public static void addCommandsToMap(Map<String, Macro> map){
        for (int i = 0; i < commandNames.length; i++)
            map.put(commandNames[i].toUpperCase().replace('\\', 'Ü'), macros[i]);
    }
}
