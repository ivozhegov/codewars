import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class NextSmallerNumber {

    @Test
    public void basicTests() {
        assertEquals(12, nextSmaller(21));
        assertEquals(790, nextSmaller(907));
        assertEquals(513, nextSmaller(531));
        assertEquals(-1, nextSmaller(1027));
        assertEquals(414, nextSmaller(441));
        assertEquals(123456789, nextSmaller(123456798));
    }

    public static long nextSmaller(long n) {

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
                if (!result.startsWith("0")) {
                    return Long.valueOf(result);
                }
            }
        }

        return -1;
    }

    private static boolean processDigits(int[] digits) {
        int newFirstIndex = 0;
        for (int i = 1; i < digits.length; i++) {
            if (digits[i] < digits[0]) {
                if (newFirstIndex == 0 || digits[i] > digits[newFirstIndex]) {
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
        
        Integer[] boxDigits = new Integer[digits.length];
        for (int i = 0; i < digits.length; i++) {
            boxDigits[i] = digits[i];
        }

        Arrays.sort(boxDigits, 1, digits.length, Collections.reverseOrder());

        for (int i = 0; i < digits.length; i++) {
            digits[i] = boxDigits[i];
        }
        return true;
    }

}
