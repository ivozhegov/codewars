import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.BitSet;

import static org.junit.Assert.assertEquals;

// https://www.codewars.com/kata/base91-encoding-and-decoding/train/java
public class Base91 {

    @Test
    public void fixedTests() {
        assertEquals(Base91.encode("4393.8155143565573871.10879432889359666.119262405256050.0194797005245485.6647486392836828.86782573046849.672210333246131.0404891014378875.2691487523724869.0657657252168234.84484544509"), "C2x.sg>Gh2T*Ek$S97R.Bk4o7IQ*KCmT[4uxvn?zB2q@[*FTew;adjoMQJ::q6FT37Wx@h%zR2r@mC{QUzIb%khdLIX<F[gSu!AbdjKSwJ6+~*)Tb%{xllISrIW<rvKUk!>xbjHBB2B.TvFT\"1ex@hNBQJs@OCbRC2=ExnfdLIV<*NgSd%!xAkqMQJY<C+`Q97>xmlqMgJS*iC8R;4FE&kOuR2n/y6{Q5A");
        assertEquals(Base91.encode("9395.0867581536284745.4592178691644318.8051142120574782.8051686804648559.1914175233811861.70381530095121635.37726941492384159.66693352933455801.73995565380853828.20102129163935570.779295408137"), "H2wbtgGux2=:%N$Sp!lE&k%XgJk/1N8RW%{xVo<iQJC.OCXQ5tQb2ikM7I.(J[gSL)mxunRd7IG?iC|Qq!1Enl/zM1o@PvbR!7_,Ak>G7IY<9*kT~1obllLBs1>:8YbRE2!x\"j2o;Ir@xNgSYztE&kTdFKT<[*KUB2!xUoASR2<:qCbRT%AbWobdx2C.qC*TD2{xvnYMs1,(pN7RpwZ.UoXdh2:(.@lTYzwb%kGu816=A");
        assertEquals(Base91.encode("test"), "fPNKd");
        assertEquals(Base91.encode("Hello World!"), ">OwJh>Io0Tv!8PE");
        assertEquals(Base91.decode("fPNKd"), "test");
        assertEquals(Base91.decode(">OwJh>Io0Tv!8PE"), "Hello World!");
        assertEquals(Base91.decode("H2wbtgGux2=:%N$Sp!lE&k%XgJk/1N8RW%{xVo<iQJC.OCXQ5tQb2ikM7I.(J[gSL)mxunRd7IG?iC|Qq!1Enl/zM1o@PvbR!7_,Ak>G7IY<9*kT~1obllLBs1>:8YbRE2!x\"j2o;Ir@xNgSYztE&kTdFKT<[*KUB2!xUoASR2<:qCbRT%AbWobdx2C.qC*TD2{xvnYMs1,(pN7RpwZ.UoXdh2:(.@lTYzwb%kGu816="), "9395.0867581536284745.4592178691644318.8051142120574782.8051686804648559.1914175233811861.70381530095121635.37726941492384159.66693352933455801.73995565380853828.20102129163935570.779295408137");
    }

    private static final int BASE = 91;
    private static final int PACK = 13;
    private static final int BITS_IN_BYTE = 8;
    private static final int MIN_VALUE = 88;

    private static final String TRANSLATION = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$%&()*+,./:;<=>?@[]^_`{|}~\"";

    public static String encode(String data) {
        byte[] bytes = data.getBytes();

        BitSet bits = new BitSet();
        StringBuilder bitsStr = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < BITS_IN_BYTE; j++) {
                if ((bytes[i] & 1 << j) > 0) {
                    bits.set(i * BITS_IN_BYTE + j);
                }
            }
        }

        StringBuilder result = new StringBuilder();
        BitSet packBits;
        int fromIndex;
        int toIndex;
        int length = bytes.length * BITS_IN_BYTE;
        for (fromIndex = 0; fromIndex < length; fromIndex = toIndex) {
            toIndex = fromIndex + PACK;
            if (toIndex > length) {
                toIndex = length;
            }
            packBits = bits.get(fromIndex, toIndex);
            int value = packBits.isEmpty() ? 0 : (int)packBits.toLongArray()[0];
            if (toIndex - fromIndex == PACK && value <= MIN_VALUE) {
                packBits = bits.get(fromIndex, ++toIndex);
                value = (int)packBits.toLongArray()[0];
            }
            result.append(TRANSLATION.charAt(value % BASE));
            if (toIndex - fromIndex > PACK / 2 + 1) {
                result.append(TRANSLATION.charAt(value / BASE));
            }
        }
        return result.toString();
    }

    public static String decode(String data) {
        BitSet resultBits = new BitSet();
        for (int i = 0; i < data.length(); i = i + 2) {
            int value = TRANSLATION.indexOf(data.charAt(i));
            if (i + 1 < data.length()) {
                value += TRANSLATION.indexOf(data.charAt(i + 1)) * BASE;
            }
            BitSet bits = BitSet.valueOf(new long[] {value});
            for (int j = bits.nextSetBit(0); j >= 0; j = bits.nextSetBit(j + 1)) {
                resultBits.set(j + i / 2 * PACK);
            }
        }
        return new String(resultBits.toByteArray());
    }

}
