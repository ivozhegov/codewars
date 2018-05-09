import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ParseMolecule {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList( new Object[][] {
                {Arrays.asList("H", "O"),
                        Arrays.asList( 2,   1 ),
                        "H2O",
                        "water"},

                {Arrays.asList("Ca", "Cl"),
                        Arrays.asList( 1,   1 ),
                        "CaCl",
                        "salt"},

                {Arrays.asList("C", "H", "0"),
                        Arrays.asList( 6, 12, 6 ),
                        "C6H12O6",
                        "salt"},

                {Arrays.asList("Mg", "H", "O"),
                        Arrays.asList(  1,   2,   2 ),
                        "Mg(OH)2",
                        "magnesium hydroxide"},

                {Arrays.asList("K", "O", "N", "S"),
                        Arrays.asList( 4,   14,  2,   4 ),
                        "K4[ON(SO3)2]2",
                        "Fremy's salt"},
        });
    }

    private Map<String,Integer> expected;
    private String formula, name;

    public ParseMolecule(List<String> atoms, List<Integer> nums, String formula, String name) {
        Map<String,Integer> exp = new HashMap<String,Integer>();
        for (int i = 0 ; i < atoms.size() ; i++) exp.put(atoms.get(i), nums.get(i));

        this.expected = exp;
        this.formula = formula;
        this.name = name;
    }

    @Test
    public void testMolecule() {
        System.out.println(expected);
        assertEquals(String.format("Should parse %s: %s", name, formula), expected, getAtoms(formula));
    }

    public static Map<String, Integer> getAtoms(String formula) {
        Map<String,Integer> atoms = new HashMap<>();

        int index = 0;

        while (index < formula.length()) {

            if (isNewAtom(formula, index)) {
                index = countNewAtom(formula, index, atoms);
            } else if (isSubFormula(formula, index)) {
                index = countSubFormula(formula, index, atoms);
            } else {
                throw new IllegalArgumentException("unexpected character: " + String.valueOf(formula.charAt(index)));
            }
        }

        return atoms;
    }

    private static boolean isNewAtom(String formula, int index) {
        return Character.isUpperCase(formula.charAt(index));
    }

    private static int countNewAtom(String formula, int index, Map<String, Integer> atoms) {
        String atom = String.valueOf(formula.charAt(index));
        int count = 0;
        while (++index < formula.length()) {
            char current = formula.charAt(index);
            if (Character.isLowerCase(current)) {
                atom += String.valueOf(current);
                if (atom.length() > 2) {
                    throw new IllegalArgumentException("incorrect atom " + atom);
                }
            } else if (Character.isDigit(current)) {
                if (count == 0) {
                    count = Character.getNumericValue(current);
                } else {
                    count = count * 10 + Character.getNumericValue(current);
                }
            } else {
                break;
            }
        }
        if (count == 0) {
            count = 1;
        }
        addAtom(atoms, atom, count);
        return index;
    }

    private static boolean isSubFormula(String formula, int index) {
        char current = formula.charAt(index);
        return current == '(' || current == '[' || current == '{';
    }

    private static int countSubFormula(String formula, int index, Map<String, Integer> atoms) {
        char openBracket = formula.charAt(index);
        char closeBracket = getCloseBracket(openBracket);

        int closeBracketCount = 1;
        int closeBracketIndex = index;

        while (closeBracketCount > 0) {
            closeBracketIndex++;
            if (closeBracketIndex == formula.length()) {
                throw new IllegalArgumentException("can not find close bracket");
            }
            if (formula.charAt(closeBracketIndex) == openBracket) {
                closeBracketCount++;
            }
            if (formula.charAt(closeBracketIndex) == closeBracket) {
                closeBracketCount--;
            }
        }
        String subFormula = formula.substring(index + 1, closeBracketIndex);

        index = closeBracketIndex;
        int count = 0;
        while (++index < formula.length()) {
            char current = formula.charAt(index);
            if (Character.isDigit(current)) {
                if (count == 0) {
                    count = Character.getNumericValue(current);
                } else {
                    count = count * 10 + Character.getNumericValue(current);
                }
            } else {
                break;
            }
        }
        if (count == 0) {
            count = 1;
        }
        addAtoms(atoms, getAtoms(subFormula), count);
        return index;
    }

    private static char getCloseBracket(char openBracket) {
        switch (openBracket) {
            case '(': return ')';
            case '[': return ']';
            case '{': return '}';
        }
        throw new IllegalArgumentException();
    }

    private static void addAtom(Map<String, Integer> atoms, String atom, int count) {
        if (atoms.containsKey(atom)) {
            count += atoms.get(atom);
        }
        atoms.put(atom, count);
    }

    private static void addAtoms(Map<String, Integer> atoms, Map<String, Integer> newAtoms, int count) {
        newAtoms.forEach((key, value) -> addAtom(atoms, key, value * count));
    }

}