public class PangramChecker {

    public static void main(String[] args) {

        System.out.println(check("The quick brown fox jumps over the lazy dog"));

    }

    public static boolean check(String sentence){
        sentence = sentence.toLowerCase();

        for (char c = 'a'; c <= 'z'; c++) {
            if (sentence.indexOf(c) < 0) {
                return false;
            }
        }
        return true;
    }

}
