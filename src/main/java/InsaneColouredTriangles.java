import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class InsaneColouredTriangles {

    @Test
    public void examples() {
        assertEquals('B', triangle("B"));
        assertEquals('R', triangle("GB"));
        assertEquals('R', triangle("RRR"));
        assertEquals('B', triangle("RGBG"));
        assertEquals('G', triangle("RBRGBRB"));
        assertEquals('G', triangle("RBRGBRBGGRRRBGBBBGG"));

        char[] chars = new char[100000];
        Arrays.fill(chars, 'R');
        assertEquals('R', triangle(new String(chars)));
    }

    private static final int SUM = 'R' + 'G' + 'B';

    public static char triangle(final String row) {
        char[] chars = row.toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (chars[j] != chars[j + 1]) {
                    chars[j] = (char) (SUM - chars[j] - chars[j + 1]);
                }
            }
        }
        return chars[0];
    }

}
