import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class CentralPixelsFinder {

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

        Integer[] depths = new Integer[pixels.length];

        int maxDepth = 1;
        int maxDepthCount = 0;

        for (int i = 0; i < pixels.length; i++) {
            if (pixels[i] == colour) {
                depths[i] = getNeighbourDepthLeftUp(depths, i) + 1;
            }
        }

        for (int i = depths.length - 1; i >= 0; i--) {
            if (depths[i] != null) {
                Integer neighbourDepth = getNeighbourDepthRightDown(depths, i);
                if (depths[i] > neighbourDepth + 1) {
                    depths[i] = neighbourDepth + 1;
                }
                if (maxDepth == depths[i]) {
                    maxDepthCount++;
                }
                if (maxDepth < depths[i]) {
                    maxDepth = depths[i];
                    maxDepthCount = 1;
                }
            }
        }


        int[] result = new int[maxDepthCount];
        int index = 0;
        for (int i = 0; i < depths.length; i++) {
            if (depths[i] != null && depths[i] == maxDepth) {
                result[index++] = i;
            }
        }
        return result;
    }

    private int getNeighbourDepthLeftUp(Integer[] depths, int currentIndex) {
        int l = getLeftPixelIndex(currentIndex);
        if (l == -1 || depths[l] == null) return 0;
        int u = getUpPixelIndex(currentIndex);
        if (u == -1 || depths[u] == null) return 0;
        return Math.min(depths[l], depths[u]);
    }

    private int getNeighbourDepthRightDown(Integer[] depths, int currentIndex) {
        int r = getRightPixelIndex(currentIndex);
        if (r == -1 || depths[r] == null) return 0;
        int d = getDownPixelIndex(currentIndex);
        if (d == -1 || depths[d] == null) return 0;
        return Math.min(depths[r], depths[d]);
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
        if (currentIndex >= pixels.length - width) {
            return -1;
        }
        return currentIndex + width;
    }

}
