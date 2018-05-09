import static org.junit.Assert.*;
import org.junit.Test;

public class SumSquaredDivisors {

    @Test
    public void test1() {
        assertEquals("[[1, 1], [42, 2500], [246, 84100]]", SumSquaredDivisors.listSquared(1, 250));
    }
    @Test
    public void test2() {
        assertEquals("[[42, 2500], [246, 84100]]", SumSquaredDivisors.listSquared(42, 250));
    }
    @Test
    public void test3() {
        assertEquals("[[287, 84100]]", SumSquaredDivisors.listSquared(250, 500));
    }

    public static String listSquared(long m, long n) {

        String result = "[";
        String separator = "";

        for (long i = m; i <= n; i++) {
            long sum = getSumOfSquaredDivisors(i);
            if (sum > 0 && Math.sqrt(sum) % 1 == 0) {
                result += separator + "[" + i + ", " + sum + "]";
                separator = ", ";
            }
        }

        return result + "]";
    }

    private static long getSumOfSquaredDivisors(long i) {
        long result = 0;
        for (int j = 1; j <= i; j++) {
            if (i % j == 0) {
                result += j * j;
            }
        }
        return result;
    }

}
