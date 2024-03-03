import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        FileIO fileIO = new FileIO();
        WhiteBall whiteBall = new WhiteBall();
        Game game = new Game();
        FileWriter output = new FileWriter("output.txt");

        String[] lines = fileIO.readFromFile(args[0]);
        String[] moveLines = fileIO.readFromFile(args[1]);

        ArrayList<ArrayList<String>> board = new ArrayList<>();
        ArrayList<String> moveList = new ArrayList<>();

        // taking board info from file
        for (String line : lines) {
            ArrayList<String> arrayString = new ArrayList<>();
            for (String c : line.split(" ")) {
                arrayString.add(c);
            }
            board.add(arrayString);
        }

        // taking move info from file
        for (String m : moveLines) {
            for (String k : m.split(" ")) {
                moveList.add(k);
            }
        }

        output.write("Game board:\n");
        for (ArrayList<String> list : board) {
            output.write(list.toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replace(",", ""));
            output.write("\n");
        }

        output.write("\n");
        output.write("Your movement is:\n");

        int total = 0;

        for (String m : moveList) {
            int rowIndex = -1;
            int columnIndex = 0;

            for (ArrayList<String> x : board) { // detecting white ball's place
                rowIndex++;
                if (x.contains("*")) {
                    columnIndex = x.indexOf("*");
                    break;
                }
            }

            if (game.gameOver(board)) { // ends the loop if ball falls into hole
                break;
            }

            output.write(m + " "); // writes played moves

            switch (m) {
                case "L":
                    total += whiteBall.moveLeft(board, columnIndex, rowIndex);
                    break;
                case "R":
                    total += whiteBall.moveRight(board, columnIndex, rowIndex);
                    break;
                case "U":
                    total += whiteBall.moveUp(board, columnIndex, rowIndex);
                    break;
                case "D":
                    total += whiteBall.moveDown(board, columnIndex, rowIndex);
                    break;
            }

        }
        output.write("\n\n");

        // write game info at the end
        output.write("Your output is:\n");
        for (ArrayList<String> list : board) {
            output.write(list.toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replace(",", ""));
            output.write("\n");
        }
        output.write("\n");
        if (game.gameOver(board)) {
            output.write("Game Over!\n");
        }
        output.write("Score: " + total);
        output.close();

    }
}

