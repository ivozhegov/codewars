import static org.junit.Assert.*;
import org.junit.Test;

public class IrrationalBaseConverter {

    @Test
    public void test1() {
        assertEquals("103", converter(13, 0, Math.PI));
        assertEquals("103.010", converter(13, 3, Math.PI));
        assertEquals("1101", converter(13, 0, 2));
        assertEquals("-1101", converter(-13, 0, 2));
    }

    private static final String FACTORS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String converter(double n, int decimals, double base) {

        if (base > FACTORS.length()) {
            throw new IllegalArgumentException("base to big");
        }

        String result = "";

        if (n < 0) {
            result += "-";
            n = -n;
        }

        double initN = n;

        int pow = 0;
        while (n >= base) {
            n /= base;
            pow++;
        }

        n = initN;
        for (int p = pow; p >= 0 - decimals; p--) {
            int factor = (int) (n / Math.pow(base, p));
            n = n - factor * Math.pow(base, p);
            if (p == -1) {
                result += ".";
            }
            result += FACTORS.charAt(factor);
        }

        return result;
    }



}
