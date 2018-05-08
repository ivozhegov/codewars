import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class BoggleTest {

    final private static char[][] board = {
            {'R','A','R','R','E','A','R','A'},
            {'R','A','R','R','N','L','E','C'},
            {'R','A','R','R','I','A','I','S'},
            {'R','A','R','R','B','Y','O','R'},
            {'R','A','R','R','N','L','E','C'},
            {'R','A','R','R','I','A','I','S'},
            {'R','A','R','R','I','A','I','S'},
            {'R','A','R','R','B','Y','O','R'}
    };

    private static String[]  toCheck   = {"C", "EAR","EARS","BAILER","RSCAREIOYBAILNEA" ,"CEREAL" ,"ROBES"};
    private static boolean[] expecteds = {true, true, false, true,    true,               false,    false };

    @Test
    public void sampleTests() {
        for (int i=0 ; i < toCheck.length ; i++) {
            assertEquals(toCheck[i], expecteds[i], new Boggle(deepCopy(board), toCheck[i]).check() );
        }
    }

    @Test
    public void test() {
        StringBuilder check = new StringBuilder();
        for (int y = board.length - 1; y >= 0; y--) {
            for (int x = board[y].length - 1; x >= 0; x--) {
                check.append(board[y][x]);
            }
            y--;
            for (int x = 0; x < board[y].length; x++) {
                check.append(board[y][x]);
            }
        }
        assertTrue(new Boggle(deepCopy(board), check.append(1).toString()).check() );
    }

    private char[][] deepCopy(char[][] arr) {
        return Arrays.stream(arr)
                .map( a -> Arrays.copyOf(a, a.length) )
                .toArray(char[][]::new);
    }

}
