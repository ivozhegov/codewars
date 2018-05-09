import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SnailTest {

    @Test
    public void SnailTest1() {
        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int[] r = {1, 2, 3, 6, 9, 8, 7, 4, 5};
        test(array, r);
    }

    public String int2dToString(int[][] a) {
        return Arrays.stream(a).map(row -> Arrays.toString(row)).collect(Collectors.joining("\n"));
    }

    public void test(int[][] array, int[] result) {
        String text = int2dToString(array) + " should be sorted to " + Arrays.toString(result);
        System.out.println(text);
        Assert.assertArrayEquals( result, snail(array));
    }

    public static int[] snail(int[][] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i].length != array[i + 1].length) {
                throw new IllegalArgumentException();
            }
        }
        int startY = 0;
        int endY = array.length - 1;
        int startX = 0;
        int endX = array[0].length - 1;

        int[] result = new int[array.length * array[0].length];
        int counter = 0;

        int y, x;

        while(true) {
            for (x = startX; x <= endX; x++) result[counter++] = array[startY][x];
            if (counter == result.length) break;
            startY++;

            for (y = startY; y <= endY; y++) result[counter++] = array[y][endX];
            if (counter == result.length) break;
            endX--;

            for (x = endX; x >= startX; x--) result[counter++] = array[endY][x];
            if (counter == result.length) break;
            endY--;

            for (y = endY; y >= startY; y--) result[counter++] = array[y][startX];
            if (counter == result.length) break;
            startX++;
        }

        return result;
    }
}
