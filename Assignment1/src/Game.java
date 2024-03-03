import java.util.ArrayList;

public class Game {
    public boolean gameOver(ArrayList<ArrayList<String>> list) { // check if ball disappears
        for (ArrayList<String> x : list) {
            if (x.contains(" ")) {
                return true;
            }
        }
        return false;
    }

    public int score(String character) {
        int point = 0;
        switch (character) {
            case "R":
                point = 10;
                break;
            case "Y":
                point = 5;
                break;
            case "B":
                point = -5;
                break;
        }
        return point;

    }
}