public class MacroBuilder {
    public static Macro parse(String string){
        if (string.length() == 1) return new PasteMacro(string);
        return null;
    }
}
