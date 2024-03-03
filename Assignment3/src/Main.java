public class Main {
    public static void main(String[] args) throws Exception {
        LibrarySystem librarySystem = new LibrarySystem();
        FileIO.cleanFile(args[1]);
        librarySystem.executeCommands(args);
    }
}