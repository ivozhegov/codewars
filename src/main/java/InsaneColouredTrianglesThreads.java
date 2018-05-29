import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class InsaneColouredTrianglesThreads {

    @Test
    public void examples() {
//        assertEquals('B', triangle("B"));
//        assertEquals('R', triangle("GB"));
//        assertEquals('R', triangle("RRR"));
//        assertEquals('B', triangle("RGBG"));
//        assertEquals('G', triangle("RBRGBRB"));
//        assertEquals('G', triangle("RBRGBRBGGRRRBGBBBGG"));

        char[] chars = new char[100];
        Arrays.fill(chars, 'R');
        assertEquals('R', triangle(new String(chars)));
    }

    private static final int SUM = 'R' + 'G' + 'B';

    private static final int THREAD_POOL_SIZE = 5;

    private static volatile int[] indexes;

    private static char[] chars;

    public static char triangle(final String row) {
        chars = row.toCharArray();
        indexes = new int[chars.length];

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        for (int i = 0; i < chars.length - 1; i++) {
            executor.execute(new IterationThread(i, i - 1, chars.length - i));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        return chars[0];
    }

    private static class IterationThread implements Runnable {

        private int index;
        private int parentIndex;
        private int size;

        public IterationThread(int index, int parentIndex, int size) {
            this.index = index;
            this.parentIndex = parentIndex;
            this.size = size;
        }

        @Override
        public void run() {
            indexes[index] = 0;
            while (indexes[index] < size - 1) {
                if (parentIndex < 0 || indexes[index] < indexes[parentIndex] - 1) {
                    if (chars[indexes[index]] != chars[indexes[index] + 1]) {
                        chars[indexes[index]] = (char) (SUM - chars[indexes[index]] - chars[indexes[index] + 1]);
                    }
                    indexes[index]++;
                }
            }
        }
    }

}
