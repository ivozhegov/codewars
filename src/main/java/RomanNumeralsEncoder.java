import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RomanNumeralsEncoder {

    @Test
    public void shouldCovertToRoman() {
        RomanNumeralsEncoder conversion = new RomanNumeralsEncoder();
        assertEquals("solution(1) should equal to I", "I", conversion.solution(1));
        assertEquals("solution(4) should equal to IV", "IV", conversion.solution(4));
        assertEquals("solution(6) should equal to VI", "VI", conversion.solution(6));
        assertEquals("solution(2008) should equal to MMVIII", "MMVIII", conversion.solution(2008));
        assertEquals("solution(1666) should equal to MDCLXVI", "MDCLXVI", conversion.solution(1666));
    }

    /**
     * Symbol    Value
     * I          1
     * V          5
     * X          10
     * L          50
     * C          100
     * D          500
     * M          1,000
     *
     * 2008 = MMVIII.
     * 1666 = MDCLXVI.
     */

    public String solution(int n) {
        String result = "";
        int powOfTen = 0;
        do {
            powOfTen++;
            int digit = n % (int)Math.pow(10, powOfTen) / (int)Math.pow(10, powOfTen - 1);
            result = getValue(digit, powOfTen) + result;
        } while (n / (int)Math.pow(10, powOfTen) > 0);

        return result;
    }

    private String getValue(int digit, int powOfTen) {
        switch (powOfTen) {
            case 1: {
                return getValue(digit, "I", "V", "X");
            }
            case 2: {
                return getValue(digit, "X", "L", "C");
            }
            case 3: {
                return getValue(digit, "C", "D", "M");
            }
            case 4: {
                switch (digit) {
                    case 1: return "M";
                    case 2: return "MM";
                    case 3: return "MMM";
                }
            }
        }
        throw new IllegalArgumentException();
    }

    private String getValue(int digit, String one, String five, String ten) {
        switch (digit) {
            case 0: return "";
            case 1: return one;
            case 2: return one + one;
            case 3: return one + one + one;
            case 4: return one + five;
            case 5: return five;
            case 6: return five + one;
            case 7: return five + one + one;
            case 8: return five + one + one + one;
            case 9: return one + ten;
        }
        throw new IllegalArgumentException();
    }

}
