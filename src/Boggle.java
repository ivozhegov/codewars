import java.util.*;

public class Boggle {

    private char[][] board;
    private String word;

    public Boggle(final char[][] board, final String word) {
        this.board = board;
        this.word = word;
    }

    public boolean check() {
        if (word.length() > board.length * board[0].length) {
            return false;
        }
        return check(0, -1, -1, new HashSet<>());
    }

    private boolean check(int letterIndex, int pointY, int pointX, Set<String> usedLetters) {
        if (letterIndex == word.length()) {
            return true;
        }

        int yFrom = 0;
        int yTo = board.length - 1;
        int xFrom = 0;
        int xTo = board[0].length - 1;

        if (pointY >= 0 && pointX >=0) {
            yFrom = Math.max(yFrom, pointY - 1);
            yTo = Math.min(yTo, pointY + 1);
            xFrom = Math.max(xFrom, pointX - 1);
            xTo = Math.min(xTo, pointX + 1);
        }

        for (int y = yFrom; y <= yTo; y++) {
            for (int x = xFrom; x <= xTo; x++) {
                if (word.charAt(letterIndex) == board[y][x] && !usedLetters.contains(y + "-" + x)) {
                    Set<String> usedLettersClone = new HashSet<>(usedLetters);
                    usedLettersClone.add(y + "-" + x);
                    if (check(letterIndex + 1, y, x, usedLettersClone)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
