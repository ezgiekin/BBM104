import java.util.ArrayList;

public class WhiteBall {

    public int moveLeft(ArrayList<ArrayList<String>> array, int columnIndex, int rowIndex) {
        Game game = new Game();
        int score = 0;
        String leftCharacter;
        int newColumn;

        if (columnIndex == 0) { // check if it is on the left side of the board
            leftCharacter = array.get(rowIndex).get(array.get(0).size() - 1);
            newColumn = array.get(0).size() - 1;
        } else {
            leftCharacter = array.get(rowIndex).get(columnIndex - 1);
            newColumn = columnIndex - 1;
        }

        if (leftCharacter.equals("H")) {
            array.get(rowIndex).set(columnIndex, " ");

        } else if (leftCharacter.equals("W")) { // if there is wall move to opposite
            return moveRight(array, columnIndex, rowIndex);

        } else if (leftCharacter.equals("R") || leftCharacter.equals("Y") || leftCharacter.equals("B")) {
            array.get(rowIndex).set(columnIndex, "X");
            array.get(rowIndex).set(newColumn, "*");
            score = game.score(leftCharacter);

        } else {
            array.get(rowIndex).set(columnIndex, leftCharacter);
            array.get(rowIndex).set(newColumn, "*");
        }

        return score;

    }

    public int moveRight(ArrayList<ArrayList<String>> array, int columnIndex, int rowIndex) {
        Game game = new Game();
        int score = 0;
        String rightCharacter;
        int newColumn;

        if (columnIndex + 1 == array.get(0).size()) { // check if it is on the right side of the board
            rightCharacter = array.get(rowIndex).get(0);
            newColumn = 0;
        } else {
            rightCharacter = array.get(rowIndex).get(columnIndex + 1);
            newColumn = columnIndex + 1;
        }
        if (rightCharacter.equals("H")) {
            array.get(rowIndex).set(columnIndex, " ");


        } else if (rightCharacter.equals("W")) { // if there is wall move to opposite
            return moveLeft(array, columnIndex, rowIndex);

        } else if (rightCharacter.equals("R") || rightCharacter.equals("Y") || rightCharacter.equals("B")) {
            array.get(rowIndex).set(columnIndex, "X");
            array.get(rowIndex).set(newColumn, "*");
            score = game.score(rightCharacter);

        } else {
            array.get(rowIndex).set(columnIndex, rightCharacter);
            array.get(rowIndex).set(newColumn, "*");
        }

        return score;

    }


    public int moveUp(ArrayList<ArrayList<String>> array, int columnIndex, int rowIndex) {
        Game game = new Game();
        int score = 0;
        String upCharacter;
        int newRow;

        if (array.size() == rowIndex) { // check if it is on the upper side of the board
            upCharacter = array.get(rowIndex).get(columnIndex);
            newRow = rowIndex;
        } else {
            upCharacter = array.get(rowIndex - 1).get(columnIndex);
            newRow = rowIndex - 1;
        }
        if (upCharacter.equals("H")) {
            array.get(rowIndex).set(columnIndex, " ");

        } else if (upCharacter.equals("W")) { // if there is wall move to opposite
            return moveDown(array, columnIndex, rowIndex);

        } else if (upCharacter.equals("R") || upCharacter.equals("Y") || upCharacter.equals("B")) {
            array.get(rowIndex).set(columnIndex, "X");
            array.get(newRow).set(columnIndex, "*");
            score = game.score(upCharacter);

        } else {
            array.get(rowIndex).set(columnIndex, upCharacter);
            array.get(newRow).set(columnIndex, "*");
        }
        return score;


    }

    public int moveDown(ArrayList<ArrayList<String>> array, int columnIndex, int rowIndex) {
        Game game = new Game();
        int score = 0;
        String downCharacter;
        int newRow;

        if (array.size() == rowIndex + 1) { // check if it is on the lower side of the board
            downCharacter = array.get(0).get(columnIndex);
            newRow = 0;
        } else {
            downCharacter = array.get(rowIndex + 1).get(columnIndex);
            newRow = rowIndex + 1;
        }

        if (downCharacter.equals("H")) {
            array.get(rowIndex).set(columnIndex, " ");

        } else if (downCharacter.equals("W")) { // if there is wall move to opposite
            return moveUp(array, columnIndex, rowIndex);

        } else if (downCharacter.equals("R") || downCharacter.equals("Y") || downCharacter.equals("B")) {
            array.get(rowIndex).set(columnIndex, "X");
            array.get(newRow).set(columnIndex, "*");
            score = game.score(downCharacter);

        } else {
            array.get(rowIndex).set(columnIndex, downCharacter);
            array.get(newRow).set(columnIndex, "*");

        }
        return score;


    }
}
