public class StringMerger {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println("true - " + StringMerger.isMerge("codewars", "code", "wars"));
        System.out.println("false - " + StringMerger.isMerge("codewars", "code", "wasr"));
        System.out.println("false - " + StringMerger.isMerge("codewars", "code", "warss"));
        System.out.println("false - " + StringMerger.isMerge("codewars", "cwdr", "oeas"));
        System.out.println("true - " + StringMerger.isMerge("kj.P.#*.\\GG#Y", "k.*.G#Y", "j.P#\\G"));
        System.out.println("true - " + StringMerger.isMerge("Can we merge it? Yes, we can!", "Cnwmeit? e can", "a e rge  Yes,w!"));
        System.out.println("true - " + StringMerger.isMerge("S65W-uSuxm&P", "65W-umP", "SuSx&"));
        System.out.println("true - " + StringMerger.isMerge("]A[e1eMF Fu", "]A[1MF", "eeF u"));
        System.out.println("true - " + StringMerger.isMerge("YkP>YdxgD)@Oy7b#zQc:#J-5dp`zb3o$t8/RYkP>YdxgD)@Oy;u;(<B0z$?m.e_P%\"l'J@>[#m#6&Al9h9uy]","YkP>YdxgD)@Oy;u;(<B0z$?m.e_P","YkP>YdxgD)@Oy7b#zQc:#J-5dp`zb3o$t8/R%\"l'J@>[#m#6&Al9h9uy]"));
        System.out.println(System.currentTimeMillis() - start);
    }

    public static boolean isMerge(String s, String part1, String part2) {
        if (s.length() == 0) {
            return part1.length() == 0 && part2.length() == 0;
        }
        return (part1.length() > 0 && s.charAt(0) == part1.charAt(0) && isMerge(s.substring(1), part1.substring(1), part2)) ||
                (part2.length() > 0 && s.charAt(0) == part2.charAt(0) && isMerge(s.substring(1), part1, part2.substring(1)));
    }

}
