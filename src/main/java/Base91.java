import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;

import static org.junit.Assert.assertEquals;

// https://www.codewars.com/kata/base91-encoding-and-decoding/train/java
public class Base91 {

    @Test
    public void fixedTests() {
        assertEquals(Base91.encode("test"), "fPNKd");
//        assertEquals(Base91.encode("Hello World!"), ">OwJh>Io0Tv!8PE");
//        assertEquals(Base91.decode("fPNKd"), "test");
//        assertEquals(Base91.decode(">OwJh>Io0Tv!8PE"), "Hello World!");
    }

    private static final String TRANSLATION = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$%&()*+,./:;<=>?@[]^_`{|}~\"";

    public static String encode(String data) {
        byte[] bytes = data.getBytes(StandardCharsets.ISO_8859_1);

        BitSet bits = new BitSet();

        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < 8; j++) {
                if ((bytes[i] & 1 << (8 - j - 1)) > 0) {
                    bits.set(i * 8 + j);
                }
            }
        }

        return new String(); // do it!
    }

    public static String decode(String data) {
        return new String(); // do it!
    }

}
