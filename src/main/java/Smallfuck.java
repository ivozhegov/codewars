import org.junit.Test;

import static org.junit.Assert.*;

public class Smallfuck {

    @Test
    public void test() {
        assertEquals("10101100", interpreter("*", "00101100"));
        assertEquals("01001100", interpreter(">*>*", "00101100"));
        assertEquals("11010011", interpreter("*>*>*>*>*>*>*>*", "00101100"));
        assertEquals("11111111", interpreter("*>*>>*>>>*>*", "00101100"));
        assertEquals("00000000", interpreter(">>>>>*<*<<*", "00101100"));
        assertEquals("000", interpreter("[[]*>*>*>]", "000"));
        assertEquals("11111111", interpreter("*[>*]", "00000000"));
    }

    public static String interpreter(String code, String tape) {
        char[] codeArray = code.toCharArray();
        char[] tapeArray = tape.toCharArray();
        int codeIndex = 0;
        int tapeIndex = 0;

        while (tapeIndex >= 0 && tapeIndex < tapeArray.length && codeIndex < codeArray.length) {

            switch(codeArray[codeIndex]) {
                case '>': tapeIndex++; break;
                case '<': tapeIndex--; break;
                case '*': flip(tapeArray, tapeIndex); break;
                case '[': codeIndex = jumpForward(codeArray, codeIndex, tapeArray[tapeIndex]); break;
                case ']': codeIndex = jumpBack(codeArray, codeIndex, tapeArray[tapeIndex]); break;
                default: break;
            }

            codeIndex++;
        }

        return new String(tapeArray);
    }

    public static void flip(char[] tapeArray, int i) {
        tapeArray[i] = tapeArray[i] == '0' ? '1' : '0';
    }

    public static int jumpForward(char[] codeArray, int codeIndex, char currentCell) {
        if (currentCell == '0') {
            int openCount = 1;
            while (codeIndex < codeArray.length && (codeArray[codeIndex] != ']' || openCount != 0)) {
                codeIndex++;
                if (codeArray[codeIndex] == '[') {
                    openCount++;
                } else if (codeArray[codeIndex] == ']') {
                    openCount--;
                }
            }
        }
        return codeIndex;
    }
    public static int jumpBack(char[] codeArray, int codeIndex, char currentCell) {
        if (currentCell == '1') {
            int closeCount = 1;
            while (codeIndex >= 0 && (codeArray[codeIndex] != '[' || closeCount != 0)) {
                codeIndex--;
                if (codeArray[codeIndex] == '[') {
                    closeCount--;
                } else if (codeArray[codeIndex] == ']') {
                    closeCount++;
                }
            }
        }
        return codeIndex;
    }

}
