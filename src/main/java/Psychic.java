import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Random;

import static org.junit.Assert.*;

public class Psychic {

    @Test
    public void testRandom() {
        assertEquals(Psychic.guess(), java.lang.Math.random(), 0);
    }

    public static double guess() {
        Class<?> randomNumberGeneratorHolderClass = java.lang.Math.class.getDeclaredClasses()[0];
        Field randomNumberGeneratorField = randomNumberGeneratorHolderClass.getDeclaredFields()[0];
        try {
            randomNumberGeneratorField.setAccessible(true);

            // remove final modifier from field
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(randomNumberGeneratorField, randomNumberGeneratorField.getModifiers() & ~Modifier.FINAL);

            randomNumberGeneratorField.set(null, new MyRandom());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return java.lang.Math.random();
    }

    private static class MyRandom extends Random {

        @Override
        public double nextDouble() {
            return 0;
        }
    }

}
