import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class NextBiggerNumber {

    @Test
    public void basicTests() {
//        assertEquals(21, nextBiggerNumber(12));
        assertEquals(531, nextBiggerNumber(513));
        assertEquals(441, nextBiggerNumber(414));
        assertEquals(414, nextBiggerNumber(144));
        assertEquals(2071, nextBiggerNumber(2017));
        assertEquals(2107, nextBiggerNumber(2071));
        assertEquals(2170, nextBiggerNumber(2107));
        assertEquals(2701, nextBiggerNumber(2170));
        assertEquals(2710, nextBiggerNumber(2701));
        assertEquals(7012, nextBiggerNumber(2710));
    }

    public static long nextBiggerNumber(long n) {

        String s = String.valueOf(n);
        int length = s.length();

        for (int i = 2; i <= length; i++) {

            int[] digits = new int[i];
            for (int j = i - 1; j >= 0; j--) {
                digits[j] = Character.getNumericValue(s.charAt(length - i + j));
            }

            if (processDigits(digits)) {
                String result = s.substring(0, length - i);
                for (int digit : digits) {
                    result += digit;
                }
                return Long.valueOf(result);
            }
        }

        return -1;
    }

    private static boolean processDigits(int[] digits) {
        int newFirstIndex = 0;
        for (int i = 1; i < digits.length; i++) {
            if (digits[i] > digits[0]) {
                if (newFirstIndex == 0 || digits[i] < digits[newFirstIndex]) {
                    newFirstIndex = i;
                }
            }
        }
        if (newFirstIndex == 0) {
            return false;
        }

        int temp = digits[0];
        digits[0] = digits[newFirstIndex];
        digits[newFirstIndex] = temp;

        Arrays.sort(digits, 1, digits.length);
        return true;
    }

}
