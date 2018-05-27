import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class Ascii85 {

    public static final Charset ascii = StandardCharsets.ISO_8859_1;

    public final String[] decoded = {
            "easy",
            "somewhat difficult"
    };

    public final String[] encoded = {
            "<~ARTY*~>",
            "<~F)Po,GA(E,+Co1uAnbatCif~>"
    };

    @Test
    public void testEncode_toAscii85 () {
        for (int i = 0; i < encoded.length; ++i)
            assertEquals (encoded[i], Ascii85.toAscii85 (decoded[i].getBytes (ascii)));
    }

    @Test
    public void testEncode_toAscii85_bytes() {
        assertEquals ("<~!!~>", Ascii85.toAscii85 (new byte[] {0}));
        assertEquals ("<~zzzz!!$l,6dfZNr;JWjTH-m+FSsBI18<%0oMAHW_146&kW]~>", Ascii85.toAscii85 (new byte[] {0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x6D,(byte)0x9D,0x43,(byte)0xD2,0x69,0x03,(byte)0xFC,(byte)0xFE,0x37,0x6B,(byte)0xA0,0x1D,0x51,0x50,0x74,(byte)0xFC,(byte)0xCD,0x3E,0x32,(byte)0xA2,(byte)0xCD,(byte)0xC1,(byte)0xF4,0x50,0x52,(byte)0xF3,(byte)0xC1,(byte)0x80,0x30,(byte)0xE7,(byte)0xE8,0x3F}));
    }

    @Test
    public void testDecode_fromAscii85 () {
        for (int i = 0; i < decoded.length; ++i)
            assertEquals (decoded[i], new String (Ascii85.fromAscii85 (encoded[i]), ascii));
    }

    private static final int ASCII_BUFFER_SIZE = 4;
    private static final int ASCII_85_BUFFER_SIZE = 5;
    private static final int BASE = 85;
    private static final int ADDING = 33;
    private static final byte EMPTY_ENCODING_BYTE = 0;
    private static final char EMPTY_DECODING_CHAR = 'u';
    private static final String BEGIN = "<~";
    private static final String END = "~>";
    private static final String NULL_LONG = "!!!!!";
    private static final String NULL_SHORT = "z";

    public static String toAscii85 (byte[] data) {
        StringBuilder result = new StringBuilder();

        int removeBytes = 0;

        byte[] buffer = new byte[ASCII_BUFFER_SIZE];
        for (int i = 0; i < data.length; i = i + buffer.length) {
            if (i + buffer.length <= data.length) {
                System.arraycopy(data, i, buffer, 0, buffer.length);
            } else {
                int lastBytesLength = data.length - i;
                System.arraycopy(data, i, buffer, 0, lastBytesLength);
                Arrays.fill(buffer, lastBytesLength, buffer.length, EMPTY_ENCODING_BYTE);
                removeBytes = buffer.length - lastBytesLength;
            }
            long value = getUnsignedValue(buffer);
            result.append(toAscii85(value, removeBytes));
        }
        return BEGIN + result.toString() + END;
    }

    private static long getUnsignedValue(byte[] buffer) {
        int value = 0;
        for (int i = 0; i < buffer.length; i++) {
            int b = buffer[i] & 0xFF;
            b <<= 8 * (buffer.length - i - 1);
            value |= b;
        }
        return value & 0xFFFFFFFFL;
    }

    private static String toAscii85(long data, int removeBytes) {
        char[] resultChars = new char[ASCII_85_BUFFER_SIZE];
        for (int i = resultChars.length - 1; i >= 0; i--) {
            resultChars[i] = (char) (data % BASE + ADDING);
            data = data / BASE;
        }
        String result = new String(resultChars);
        result = result.substring(0, result.length() - removeBytes);
        if (result.equals(NULL_LONG)) {
            result = NULL_SHORT;
        }
        return result;
    }

    public static byte[] fromAscii85 (String data) {
        data = data
                .substring(BEGIN.length(), data.length() - END.length())
                .replaceAll("\\s+", "")
                .replaceAll(NULL_SHORT, NULL_LONG);

        int removeBytes = 0;
        while (data.length() % ASCII_85_BUFFER_SIZE != 0) {
            data += EMPTY_DECODING_CHAR;
            removeBytes++;
        }

        char[] chars = data.toCharArray();

        byte[] result = new byte[chars.length / ASCII_85_BUFFER_SIZE * ASCII_BUFFER_SIZE - removeBytes];

        char[] buffer = new char[ASCII_85_BUFFER_SIZE];
        for (int i = 0; i < chars.length; i = i + buffer.length) {
            System.arraycopy(chars, i, buffer, 0, buffer.length);
            long unsignedValue = fromAscii85(buffer);
            byte[] subResult = getBytes(unsignedValue);
            int copyLength = subResult.length;
            if (i + buffer.length == chars.length) {
                copyLength -= removeBytes;
            }
            System.arraycopy(subResult, 0, result, i / buffer.length * subResult.length, copyLength);
        }
        return result;
    }

    private static long fromAscii85(char[] buffer) {
        long result = 0;
        for (int i = 0; i < buffer.length; i++) {
            result += (buffer[i] - ADDING) * Math.pow(BASE, buffer.length - i - 1);
        }
        return result;
    }

    private static byte[] getBytes(long unsignedValue) {
        byte[] buffer = new byte[ASCII_BUFFER_SIZE];
        for (int i = 0; i < buffer.length; i++) {
            int shift = 8 * (buffer.length - i - 1);
            buffer[i] = (byte) ((unsignedValue & 0xFF << shift) >> shift);
        }
        return buffer;
    }

}
