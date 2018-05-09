import org.junit.Test;

import static org.junit.Assert.*;

public class MysteryFunction {

    @Test
    public void mystery() throws Exception {
        assertEquals( "mystery(6) ", 5, MysteryFunction.mystery( 6 ) );
        assertEquals( "mystery(9) ", 13, MysteryFunction.mystery( 9 ) );
        assertEquals( "mystery(19) ", 26, MysteryFunction.mystery( 19 ) );
    }

    @Test
    public void mysteryInv() throws Exception {
        assertEquals( "mysteryInv(5)", 6, MysteryFunction.mysteryInv( 5 ) );
        assertEquals( "mysteryInv(13)", 9, MysteryFunction.mysteryInv( 13 ) );
        assertEquals( "mysteryInv(26)", 19, MysteryFunction.mysteryInv( 26 ) );
    }

    private static final String[] T0 = {""};

    private static String[] getT(int n) {
        if (n == 0) {
            return T0;
        }
        String[] prev = getT(n - 1);
        String[] current = new String[prev.length * 2];
        for (int i = 0; i < prev.length; i++) {
            current[i] = "0" + prev[i];
        }
        for (int i = prev.length - 1; i >= 0; i--) {
            current[current.length - i - 1] = "1" + prev[i];
        }
        return current;
    }

    private static long getNumber(String binaryRepr) {
        long result = 0;
        char[] bits = binaryRepr.toCharArray();
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] == '1') {
                result += Math.pow(2, bits.length - i - 1);
            }
        }
        return result;
    }

    private static String getBinaryRepr(long number) {
        String result = "";
        do {
            result = number % 2 + result;
            number = number / 2;
        } while (number > 0);
        return result;
    }

    public static long mystery(long n) {
//        int bitsCount = (int)Math.ceil(Math.log(n)/Math.log(2));
//        String binaryRepr = getT(bitsCount)[(int)n];
//        return getNumber(binaryRepr);
        return n ^ (n >> 1);
    }

    public static long mysteryInv(long n) {
//        int bitsCount = (int)Math.ceil(Math.log(n)/Math.log(2));
//        String[] t = getT(bitsCount);
//        String binaryRepr = getBinaryRepr(n);
//        for (int i = 0; i < t.length; i++) {
//            if (binaryRepr.equals(t[i])) {
//                return i;
//            }
//        }
//        throw new IllegalStateException();
        long mask = n;
        while (mask != 0) {
            mask = mask >> 1;
            n = n ^ mask;
        }
        return n;
    }

    public static String nameOfMystery() {
        return "";
    }

}
