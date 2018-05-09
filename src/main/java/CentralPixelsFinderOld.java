import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CentralPixelsFinderOld {

    int[] pixels;
    int width, height;

    public void set(int width, int height, int[] pixels) {
        this.width = width;
        this.height = height;
        this.pixels = pixels;
    }

    @Test
    public void Example_In_The_Picture() throws Exception
    {
        int[] image_data = {
                1,1,4,4,4,4,2,2,2,2,
                1,1,1,1,2,2,2,2,2,2,
                1,1,1,1,2,2,2,2,2,2,
                1,1,1,1,1,3,2,2,2,2,
                1,1,1,1,1,3,3,3,2,2,
                1,1,1,1,1,1,3,3,3,3
        };
        set(10, 6, image_data);

        // Only one red pixel has the maximum depth of 3:
        int[] red_ctr = { 32 };
        assertEquals(answer_matches( central_pixels(1), red_ctr ), true);

        // Multiple blue pixels have the maximum depth of 2:
        int[] blue_ctr = { 16,17,18,26,27,28,38 };
        assertEquals(answer_matches( central_pixels(2), blue_ctr ), true);

        // All the green pixels have depth 1, so they are all "central":
        int[] green_ctr = { 35,45,46,47,56,57,58,59 };
        assertEquals(answer_matches( central_pixels(3), green_ctr ), true);

        // Similarly, all the purple pixels have depth 1:
        int[] purple_ctr = { 2,3,4,5 };
        assertEquals(answer_matches( central_pixels(4), purple_ctr ), true);

        // There are no pixels with colour 5:
        int[] non_existent_ctr = { };
        assertEquals(answer_matches( central_pixels(5), non_existent_ctr ), true);
    }

    /* ---------------------------------------------------------------------------------- */
// Check whether actual and expected arrays are equal, disregarding the order of elements.

    public static boolean answer_matches(int[] actual, int[] expected)
    {
        if( actual.length != expected.length )
            return false;

        Arrays.sort(actual);
        Arrays.sort(expected);

        for(int i=0; i<actual.length; i++)
            if(actual[i] != expected[i] )
                return false;

        return true;
    }

    public int[] central_pixels(int colour) {

        List<Integer> maxDepthIndexes = new ArrayList<>();
        List<Integer> borderIndexes = new ArrayList<>();

        for (int i = 0; i < pixels.length; i++) {
            if (pixels[i] == colour) {
                if (isBorderIndex(i)) {
                    borderIndexes.add(i);
                } else {
                    maxDepthIndexes.add(i);
                }
            }
        }

        if (maxDepthIndexes.isEmpty()) {
            maxDepthIndexes = borderIndexes;
        } else {
            boolean done = false;
            do {
                List<Integer> nearBorderIndexes = new ArrayList<>();
                for (int i : maxDepthIndexes) {
                    if (isNearBorderIndex(borderIndexes, i)) {
                        nearBorderIndexes.add(i);
                    }
                }
                maxDepthIndexes.removeAll(nearBorderIndexes);

                if (maxDepthIndexes.isEmpty()) {
                    maxDepthIndexes = nearBorderIndexes;
                    done = true;
                } else {
                    borderIndexes = nearBorderIndexes;
                }

            } while (!done);
        }

        int[] result = new int[maxDepthIndexes.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = maxDepthIndexes.get(i);
        }
        return result;
    }

    private boolean isBorderIndex(int currentIndex) {
        int neighborIndex;
        neighborIndex = getLeftPixelIndex(currentIndex);
        if (neighborIndex == -1 || pixels[neighborIndex] != pixels[currentIndex]) {
            return true;
        }
        neighborIndex = getRightPixelIndex(currentIndex);
        if (neighborIndex == -1 || pixels[neighborIndex] != pixels[currentIndex]) {
            return true;
        }
        neighborIndex = getUpPixelIndex(currentIndex);
        if (neighborIndex == -1 || pixels[neighborIndex] != pixels[currentIndex]) {
            return true;
        }
        neighborIndex = getDownPixelIndex(currentIndex);
        if (neighborIndex == -1 || pixels[neighborIndex] != pixels[currentIndex]) {
            return true;
        }
        return false;
    }

    private boolean isNearBorderIndex(List<Integer> borderIndexes, int currentIndex) {
        int l = getLeftPixelIndex(currentIndex);
        int r = getRightPixelIndex(currentIndex);
        int u = getUpPixelIndex(currentIndex);
        int d = getDownPixelIndex(currentIndex);
        for (int i : borderIndexes) {
            if (i == l || i == r || i == u || i == d) {
                return true;
            }
        }
        return false;
    }

    private int getLeftPixelIndex(int currentIndex) {
        if (currentIndex % width == 0) {
            return -1;
        }
        return currentIndex - 1;
    }

    private int getRightPixelIndex(int currentIndex) {
        if ((currentIndex + 1) % width == 0) {
            return -1;
        }
        return currentIndex + 1;
    }

    private int getUpPixelIndex(int currentIndex) {
        if (currentIndex < width) {
            return -1;
        }
        return currentIndex - width;
    }

    private int getDownPixelIndex(int currentIndex) {
        if (currentIndex > pixels.length - width) {
            return -1;
        }
        return currentIndex + width;
    }

}
